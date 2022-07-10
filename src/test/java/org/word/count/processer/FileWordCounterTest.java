package org.word.count.processer;

import static java.util.Arrays.asList;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.word.count.inter.FileOperation;
import org.word.count.inter.WordCountProcessor;
import org.word.count.service.OutputUtils;
import org.word.count.service.WordCountUtils;

@RunWith(MockitoJUnitRunner.class)
public class FileWordCounterTest
{
    @Mock
    FileOperation fileOperationMock;

    @Mock
    WordCountProcessor wordCountProcessorMock;

    FileWordCounter main;
    public final static String param_pathToFileName = "to/path/fileName.txt";
    @Before
    public void setup() throws Exception {
        main = new FileWordCounter(fileOperationMock, wordCountProcessorMock);
    }

    @Test
    public void processTest() throws Exception {
        main.setVariables(new String[]{param_pathToFileName});
        try (MockedStatic<OutputUtils> utilities = Mockito.mockStatic(OutputUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            Map<Integer, Integer> mapList = getMapList();
            Path path = Path.of(param_pathToFileName);
            List<String> list = asList("Hello world & good morning. The date is 18/05/2016");
            when(fileOperationMock.fileExist(path)).thenReturn(true);
            when(fileOperationMock.getFilesAllLines(path)).thenReturn(list);
            when(wordCountProcessorMock.totalNumberWords(any(List.class))).thenReturn(9);
            when(wordCountProcessorMock.getNumberWordsAndLength(any(List.class))).thenReturn(mapList);
            when(wordCountProcessorMock.getAverageLengthWord(any(List.class))).thenReturn(4.666666666666666);
            when(wordCountProcessorMock.getMapFrequentValue(any(Map.class))).thenReturn(2);
            when(wordCountProcessorMock.getStringValuesFromMap(any(Map.class), anyInt())).thenReturn("4 & 5");

            main.process();
            utilities.verify(() -> OutputUtils.infoMsg("Using file name: to/path/fileName.txt"));
            utilities.verify(() -> OutputUtils.infoMsg("Line content: \n" +
                    "'Hello world & good morning. The date is 18/05/2016'\n"));
            utilities.verify(() -> OutputUtils.infoMsg("World count = 9"));
            utilities.verify(() -> OutputUtils.infoMsg("Average word length = 4.667"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 1 is 1"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 2 is 1"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 3 is 1"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 4 is 2"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 5 is 2"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 7 is 1"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 10 is 1"));
            utilities.verify(() -> OutputUtils.infoMsg("The most frequently occurring word length is 2, for word lengths of 4 & 5"));
        }
    }

    @Test
    public void processTest_fileNotExist() throws Exception {
        main.setVariables(new String[]{param_pathToFileName});
        try (MockedStatic<OutputUtils> utilities = Mockito.mockStatic(OutputUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            try {
                Path path = Path.of(param_pathToFileName);
                when(fileOperationMock.fileExist(path)).thenReturn(false);
                main.process();
                fail("error should occur here");
            }
            catch (InvalidFileArgumentProblem e ) {
                assertThat(e.getMessage(), equalTo("Provided file nam does not exist"));
            }
            finally {
                utilities.verify(() -> OutputUtils.errorMsg("Provided file name does not exist"));
                verify(fileOperationMock, never()).getFilesAllLines(any(Path.class));
            }
        }
    }

    @Test
    public void processTest_fileLoadError() throws Exception {

        try (MockedStatic<OutputUtils> utilities = Mockito.mockStatic(OutputUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            try {
                main.setVariables(new String[]{param_pathToFileName});
                Path path = Path.of(param_pathToFileName);
                when(fileOperationMock.fileExist(path)).thenReturn(true);
                doThrow(new IOException("Fail to load file")).when(fileOperationMock).getFilesAllLines(path);
                main.process();
                fail("error should occur here");
            }
            catch (InvalidFileArgumentProblem e ) {
                assertThat(e.getMessage(), equalTo("Fail to load file"));
            }
            finally {
                utilities.verify(() -> OutputUtils.infoMsg("Using file name: " + param_pathToFileName));
                utilities.verify(() -> OutputUtils.errorMsg("Fail to load file"));
                verify(fileOperationMock).getFilesAllLines(any(Path.class));
            }
        }
    }

    @Test
    public void validExtension_1() throws Exception {
        try (MockedStatic<WordCountUtils> utilities = Mockito.mockStatic(WordCountUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            try
            {
                main.setVariables(null);
                utilities.verify(() -> WordCountUtils.trimValue("src/main/resources/helloWorld.txt"), times(1));
            } catch (Exception e)
            {
                fail("validExtension_1: Error should not occur here" + e.getMessage());
            }
        }
    }

    @Test
    public void validExtension_2() throws Exception {
        try (MockedStatic<WordCountUtils> utilities = Mockito.mockStatic(WordCountUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            try
            {
                main.setVariables(new String[]{"path/to/f.txt"});
                utilities.verify(() -> WordCountUtils.trimValue("path/to/f.txt"), times(2));
            } catch (Exception e)
            {
                fail("validExtension_2: Error should not occur here" + e.getMessage());
            }
        }
    }

    @Test
    public void validExtension_error_1() throws Exception {
        try (MockedStatic<WordCountUtils> utilities = Mockito.mockStatic(WordCountUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            try
            {
                main.setVariables(new String[]{"path/to/f.pdf"});
                fail("validExtension_error: Error should occur here");
            } catch (Exception e)
            {
                assertThat(e.getMessage(), equalTo("Unknown File Extension name: pdf") );
            }
        }
    }

    @Test
    public void validExtension_error_2() throws Exception {
        try (MockedStatic<WordCountUtils> utilities = Mockito.mockStatic(WordCountUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            try
            {
                main.setVariables(new String[]{"path/to/f.jp"});
                fail("validExtension_error: Error should occur here");
            } catch (Exception e)
            {
                assertThat(e.getMessage(), equalTo("Unknown File Extension name: .jp") );
            }
        }
    }

    @Test
    public void validExtension_error_3() throws Exception {
        try (MockedStatic<WordCountUtils> utilities = Mockito.mockStatic(WordCountUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            try
            {
                main.setVariables(new String[]{"txt"});
                fail("validExtension_error: Error should occur here");
            } catch (Exception e)
            {
                assertThat(e.getMessage(), equalTo("Invalid file name, Too short") );
            }
        }
    }
    public Map<Integer, Integer> getMapList() {
        Map<Integer, Integer> map = new HashMap<>() {{
            put(1, 1);
            put(2, 1);
            put(3, 1);
            put(4, 2);
            put(5, 2);
            put(7, 1);
            put(10, 1);
        }};
        return map;
    }
}

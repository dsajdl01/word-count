package org.word.count.processer;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.word.count.file.FileOperationImpl;
import org.word.count.inter.FileOperation;
import org.word.count.inter.WordCountProcessor;
import org.word.count.service.OutputUtils;
import org.word.count.service.WordCountProcessorImpl;

public class FileWordCounterInterTest
{

    FileWordCounter main;
    public final static String FAKE_PATH_TO_FILE_NAME = "to/path/fileName.txt";
    public final static String PATH_TO_FILE_NAME = "src/main/resources/javaCoding.txt";
    @Before
    public void setup() throws Exception {
        WordCountProcessor wordCountProcessor = new WordCountProcessorImpl();
        FileOperation fileOperation = new FileOperationImpl();
        main = new FileWordCounter(fileOperation, wordCountProcessor);
    }

    @Test
    public void processTest() throws Exception {
        main.setVariables(new String[]{PATH_TO_FILE_NAME});
        try (MockedStatic<OutputUtils> utilities = Mockito.mockStatic(OutputUtils.class, Mockito.CALLS_REAL_METHODS))
        {
            main.process();
            utilities.verify(() -> OutputUtils.infoMsg("Using file name: src/main/resources/javaCoding.txt"));
            utilities.verify(() -> OutputUtils.infoMsg("Line content: \n" +
                    "'Hello world, in Java coding.'\n"));
            utilities.verify(() -> OutputUtils.infoMsg("World count = 5"));
            utilities.verify(() -> OutputUtils.infoMsg("Average word length = 4.400"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 2 is 1"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 4 is 1"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 5 is 2"));
            utilities.verify(() -> OutputUtils.infoMsg("Number of words of length 6 is 1"));
            utilities.verify(() -> OutputUtils.infoMsg("The most frequently occurring word length is 2, for word lengths of 5"));
        }
    }
}

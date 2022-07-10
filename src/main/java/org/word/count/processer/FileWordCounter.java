package org.word.count.processer;

import static java.util.stream.Collectors.toList;
import static org.word.count.service.OutputUtils.errorMsg;
import static org.word.count.service.OutputUtils.infoMsg;
import static org.word.count.service.WordCountUtils.trimValue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.word.count.inter.FileOperation;
import org.word.count.file.FileOperationImpl;
import org.word.count.inter.WordCountProcessor;
import org.word.count.service.WordCountProcessorImpl;

public class FileWordCounter
{
    private Path PATH_TO_FILE_NAME;
    private final static String FILE_NAME = "src/main/resources/helloWorld.txt";

    private FileOperation fileOperation;
    WordCountProcessor wordCountProcessor;

    private final static String WORD_COUNT =  "World count = %d";
    private final static String LINE_CONTENT = "Line content: %n'%s'%n";
    private final String AVG_WORDS_LENGTH = "Average word length = %.3f";

    private final String NUM_WORDS_OF_LENGTH = "Number of words of length %d is %d";

    private final String LENGTH_FREQUENTLY_OCCURRING = "The most frequently occurring word length is %d, for word lengths of %s";

    public FileWordCounter(FileOperation fileOperation, WordCountProcessor wordCountProcessor) {
        this.fileOperation = fileOperation;
        this.wordCountProcessor = wordCountProcessor;
    }

    public static void main(String[] args)
    {
        try
        {
            FileOperation fileOper = new FileOperationImpl();
            WordCountProcessor wordCountProcessor = new WordCountProcessorImpl();
            FileWordCounter main = new FileWordCounter(fileOper, wordCountProcessor);
            main.setVariables(args);
            main.process();
        } catch (InvalidFileArgumentProblem e) {
            System.exit(1);
        }
    }

    protected void setVariables(String[] args) throws InvalidFileArgumentProblem {


        String fileName = args != null && args.length > 0 && validExtension(trimValue(args[0])) ? args[0] : FILE_NAME;
        this.PATH_TO_FILE_NAME = Path.of(trimValue(fileName));
//        this.PATH_TO_FILE_NAME = Path.of(trimValue("`src/main/resources/bible_daily.txt`"));
    }

    protected void process() throws InvalidFileArgumentProblem
    {
        if (fileOperation.fileExist(PATH_TO_FILE_NAME)) {
            infoMsg("Using file name: " + PATH_TO_FILE_NAME.toString());
        } else {
            errorMsg("Provided file nam does not exist");
            throw new InvalidFileArgumentProblem("Provided file nam does not exist");
        }

        try
        {
            List<String> fileLines = fileOperation.getFilesAllLines(PATH_TO_FILE_NAME).stream().distinct().collect(toList());
            for (String line : fileLines) {
                List<String> words = getListOfWords(line);
                Map<Integer, Integer> numberWordsAndLength = wordCountProcessor.getNumberWordsAndLength(words);
                Integer mostFrequentlyValue = wordCountProcessor.getMapFrequentValue(numberWordsAndLength);


                infoMsg(String.format(LINE_CONTENT, line));
                infoMsg(String.format(WORD_COUNT, wordCountProcessor.totalNumberWords(words)));
                infoMsg(String.format(AVG_WORDS_LENGTH, wordCountProcessor.getAverageLengthWord(words)));
                numberWordsAndLength.entrySet().stream().forEach( e ->
                        infoMsg(String.format(NUM_WORDS_OF_LENGTH, e.getKey(), e.getValue())));
                infoMsg(String.format(LENGTH_FREQUENTLY_OCCURRING, mostFrequentlyValue, wordCountProcessor.getStringValuesFromMap(numberWordsAndLength, mostFrequentlyValue)));
            }
        }
        catch (IOException e) {
            errorMsg(e.getMessage());
            throw new InvalidFileArgumentProblem(e.getMessage());
        }
    }

    private List<String> getListOfWords(String line) {
        String withoutChar = line.replaceAll("[.,]", "");
        return Stream.of(withoutChar.split(" ")).collect(toList());
    }

    private boolean validExtension(String fileName) throws InvalidFileArgumentProblem {
        if (fileName == null || fileName.length() < 4) throw new InvalidFileArgumentProblem("Invalid file name, Too short");
        return FileExtension.validExtFromString(fileName.substring(fileName.length() - 3));
    }
}
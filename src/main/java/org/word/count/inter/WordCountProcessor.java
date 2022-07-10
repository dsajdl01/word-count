package org.word.count.inter;

import java.util.List;
import java.util.Map;

public interface WordCountProcessor
{
    int totalNumberWords(List<String> words);

    double getAverageLengthWord(List<String> words);

    Map<Integer, Integer> getNumberWordsAndLength(List<String> words);

    Integer getMapFrequentValue(Map<Integer, Integer> numWordsAndLength);

    String getStringValuesFromMap(Map<Integer, Integer> numWordsAndLength, Integer key);
}

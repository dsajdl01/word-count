package org.word.count.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.word.count.inter.WordCountProcessor;

public class WordCountProcessorImpl implements WordCountProcessor
{
    @Override
    public int totalNumberWords(List<String> words) {
        return words.size();
    }

    @Override
    public double getAverageLengthWord(List<String> words) {
        return words.stream().mapToInt(w -> w.length()).average().getAsDouble();
    }

    @Override
    public Map<Integer, Integer> getNumberWordsAndLength(List<String> words) {
        Map<Integer, Integer> map = new HashMap<>();
        for (String w: words) {
            int length = w.length();
            if (map.containsKey(length)) {
                map.put(length, map.get(length) + 1);
            } else {
                map.put(length, 1);
            }
        }
        return map;
    }

    @Override
    public String getStringValuesFromMap(Map<Integer, Integer> numWordsAndLength, Integer key) {
        List<String> list = numWordsAndLength.entrySet().stream().filter(enter -> enter.getValue().intValue() == key.intValue()).map(Map.Entry::getKey).map(String::valueOf).sorted().collect(Collectors.toList());
        return getText(list);
    }

    @Override
    public Integer getMapFrequentValue(Map<Integer, Integer> numWordsAndLength) {
        return numWordsAndLength.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getValue();
    }

    private String getText(List<String> list) {
        if (list.isEmpty()) return "0";
        if (list.size() == 1) return list.get(0);
        if (list.size() == 2) return list.get(0) + " & " + list.get(1);
        String lastValue = list.remove(list.size() - 1);
        return String.join(", ", list) + " & " + lastValue;

    }
}

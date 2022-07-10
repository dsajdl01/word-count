package org.word.count.service;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.word.count.inter.WordCountProcessor;
import org.junit.Before;
import org.junit.Test;


public class WordCountProcessorImplTest
{
    public WordCountProcessor wordCountProcessor;

    private static final List<String> worlds1 = asList("Hello", "world", "&", "good", "morning", "The", "date", "is", "18/05/2016");
    private static final List<String> worlds2 = asList("Count", "word", "&", "good", "morning", "The", "date", "is", "June", "05", "2022");
    private static final List<String> worlds3 = asList("Hello", "world", "in", "Java", "coding");

    private static final List<String> worlds4 = asList("Hi", "welcome", "to", "real", "world");

    Map<Integer, Integer> map1  = new HashMap<>() {{
        put(4, 5);
        put(2, 3);
        put(5, 5);
        put(6, 5);
        put(3, 5);
        put(1, 1);
        put(7, 2);
    }};

    Map<Integer, Integer> map2  = new HashMap<>() {{
        put(4, 8);
        put(2, 3);
        put(5, 3);
        put(6, 2);
        put(1, 1);
    }};

    Map<Integer, Integer> map3  = new HashMap<>() {{
        put(4, 6);
        put(2, 6);
        put(5, 4);
        put(6, 4);
        put(1, 1);
        put(7, 1);
    }};

    Map<Integer, Integer> map4  = new HashMap<>() {{
        put(1, 1);
        put(2, 1);
        put(3, 1);
        put(4, 2);
        put(5, 2);
        put(7, 1);
        put(10, 1);
    }};

    @Before
    public void setup() throws Exception {
        wordCountProcessor = new WordCountProcessorImpl();
    }

    @Test
    public void totalNumberWordsTest() {
        assertThat(wordCountProcessor.totalNumberWords(worlds1), equalTo(9));
        assertThat(wordCountProcessor.totalNumberWords(worlds2), equalTo(11));
        assertThat(wordCountProcessor.totalNumberWords(worlds3), equalTo(5));
        assertThat(wordCountProcessor.totalNumberWords(worlds4), equalTo(5));
    }

    @Test
    public void getAverageLengthWordTest() {
        assertThat(wordCountProcessor.getAverageLengthWord(worlds1), equalTo(4.555555555555555));
        assertThat(wordCountProcessor.getAverageLengthWord(worlds2), equalTo(3.6363636363636362));
        assertThat(wordCountProcessor.getAverageLengthWord(worlds3), equalTo(4.4));
        assertThat(wordCountProcessor.getAverageLengthWord(worlds4), equalTo(4.0));
    }

    @Test
    public void getNumberWordsAndLengthTest_words1() {
        Map<Integer, Integer> numberWordsAndLength = wordCountProcessor.getNumberWordsAndLength(worlds1);
        assertThat(numberWordsAndLength.size(), equalTo(7));
        assertMapKeysAndValues(numberWordsAndLength, 1, 1);
        assertMapKeysAndValues(numberWordsAndLength, 2, 1);
        assertMapKeysAndValues(numberWordsAndLength, 3, 1);
        assertMapKeysAndValues(numberWordsAndLength, 4, 2);
        assertMapKeysAndValues(numberWordsAndLength, 5, 2);
        assertMapKeysAndValues(numberWordsAndLength, 7, 1);
        assertMapKeysAndValues(numberWordsAndLength, 10, 1);
    }

    @Test
    public void getNumberWordsAndLengthTest_words2() {
        Map<Integer, Integer> numberWordsAndLength = wordCountProcessor.getNumberWordsAndLength(worlds2);
        assertThat(numberWordsAndLength.size(), equalTo(6));
        assertMapKeysAndValues(numberWordsAndLength, 4, 5);
        assertMapKeysAndValues(numberWordsAndLength, 5, 1);
        assertMapKeysAndValues(numberWordsAndLength, 1, 1);
        assertMapKeysAndValues(numberWordsAndLength, 7, 1);
        assertMapKeysAndValues(numberWordsAndLength, 3, 1);
        assertMapKeysAndValues(numberWordsAndLength, 2, 2);
    }

    @Test
    public void getNumberWordsAndLengthTest_words3() {
        Map<Integer, Integer> numberWordsAndLength = wordCountProcessor.getNumberWordsAndLength(worlds3);
        assertThat(numberWordsAndLength.size(), equalTo(4));
        assertMapKeysAndValues(numberWordsAndLength, 5, 2);
        assertMapKeysAndValues(numberWordsAndLength, 4, 1);
        assertMapKeysAndValues(numberWordsAndLength, 2, 1);
        assertMapKeysAndValues(numberWordsAndLength, 6, 1);
    }

    @Test
    public void getNumberWordsAndLengthTest_words4() {
        Map<Integer, Integer> numberWordsAndLength = wordCountProcessor.getNumberWordsAndLength(worlds4);
        assertThat(numberWordsAndLength.size(), equalTo(4));
        assertMapKeysAndValues(numberWordsAndLength, 2, 2);
        assertMapKeysAndValues(numberWordsAndLength, 4, 1);
        assertMapKeysAndValues(numberWordsAndLength, 5, 1);
        assertMapKeysAndValues(numberWordsAndLength, 7, 1);
    }

    @Test
    public void getMapHighestValueTest() {
        assertThat(wordCountProcessor.getMapFrequentValue(map1), equalTo(5));
        assertThat(wordCountProcessor.getMapFrequentValue(map2), equalTo(8));
        assertThat(wordCountProcessor.getMapFrequentValue(map3), equalTo(6));
        assertThat(wordCountProcessor.getMapFrequentValue(map4), equalTo(2));
    }

    @Test
    public void getStringValuesFromMap() {
        assertThat(wordCountProcessor.getStringValuesFromMap(map1, 5), equalTo("3, 4, 5 & 6"));
        assertThat(wordCountProcessor.getStringValuesFromMap(map2, 8), equalTo("4"));
        assertThat(wordCountProcessor.getStringValuesFromMap(map3, 6), equalTo("2 & 4"));
        assertThat(wordCountProcessor.getStringValuesFromMap(map4, 2), equalTo("4 & 5"));
    }


    private void assertMapKeysAndValues(Map<Integer, Integer> map, int exceptingKey, int exceptingValue) {
        assertTrue(map.containsKey(exceptingKey));
        assertThat(map.get(exceptingKey), equalTo(exceptingValue));
    }


}

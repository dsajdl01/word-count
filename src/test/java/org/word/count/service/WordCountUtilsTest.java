package org.word.count.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class WordCountUtilsTest
{
    @Test
    public void trimValueTest() {
        assertThat(WordCountUtils.trimValue("    to/path/directory/name.txt "), equalTo("to/path/directory/name.txt"));
        assertThat(WordCountUtils.trimValue("     "), equalTo(""));
        assertThat(WordCountUtils.trimValue("    to/path/directory/name.txt"), equalTo("to/path/directory/name.txt"));
        assertThat(WordCountUtils.trimValue("./path/directory/name.txt          "), equalTo("./path/directory/name.txt"));
        assertNull(WordCountUtils.trimValue(null));
    }
}

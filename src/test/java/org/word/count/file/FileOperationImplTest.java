package org.word.count.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.nio.file.Path;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.word.count.inter.FileOperation;

public class FileOperationImplTest
{
    private FileOperation fileOperation;

    @Before
    public void setup() throws Exception {
        fileOperation = new FileOperationImpl();
    }

    @Test
    public void fileExistTest() {
        assertTrue(fileOperation.fileExist(Path.of("src/main/resources/helloWorld.txt")));
        assertFalse(fileOperation.fileExist(Path.of("src/main/resources/newFile.txt")));
    }

    @Test
    public void getFilesAllLinesTest() throws Exception {
        List<String> filesAllLines = fileOperation.getFilesAllLines(Path.of("src/main/resources/helloWorld.txt"));
        assertThat(filesAllLines.size(), equalTo(1));
        assertThat(filesAllLines.get(0), equalTo("Hello world & good morning. The date is 18/05/2016"));
    }
}

package org.word.count.inter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileOperation
{
    boolean fileExist(Path fileName);

    List<String> getFilesAllLines(Path pathToFile) throws IOException;
}

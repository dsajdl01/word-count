package org.word.count.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.word.count.inter.FileOperation;

public class FileOperationImpl implements FileOperation
{
    @Override
    public boolean fileExist(Path fileName) {
        return fileName.toFile().exists();
    }

    @Override
    public List<String> getFilesAllLines(Path pathToFile) throws IOException
    {
        return Files.readAllLines(pathToFile);
    }
}

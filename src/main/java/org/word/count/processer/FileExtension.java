package org.word.count.processer;

public enum FileExtension
{
    TXT ("txt");

    private String fileExtensionName;
    private FileExtension(String fileExtensionName) {
        this.fileExtensionName = fileExtensionName;
    }

    public String getFileExtensionName() {
        return  fileExtensionName;
    }

    public static boolean validExtFromString(String name) throws InvalidFileArgumentProblem {
        for (FileExtension f : FileExtension.values())
            if (f.getFileExtensionName().equalsIgnoreCase(name)) return true;
        throw new InvalidFileArgumentProblem("Unknown File Extension name: " + name);

    }
}

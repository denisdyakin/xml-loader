package ru.lamoda.jdo;

public class Pair {
    private String fileName;
    private boolean isUploaded;

    public Pair(String fileName, boolean isUploaded) {
        this.fileName = fileName;
        this.isUploaded = isUploaded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }
}

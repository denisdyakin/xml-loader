package ru.lamoda.jdo;

import java.util.HashMap;
import java.util.Map;

public class UploadFileResponse {

    private Map<String, Boolean> wereUploaded = new HashMap<>();

    public UploadFileResponse() {
    }

    public void addFileUploadedInfo(String fileName, Boolean wasUploaded) {
        wereUploaded.put(fileName, wasUploaded);
    }

    public Map<String, Boolean> getWereUploaded() {
        return wereUploaded;
    }
}

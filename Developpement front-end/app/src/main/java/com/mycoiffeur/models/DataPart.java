package com.mycoiffeur.models;

public class DataPart {
    private String fileName;
    private byte[] content;
    private String type;

    public DataPart() {
    }

    public DataPart(String name, byte[] data) {
        fileName = name;
        content = data;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }

    String getType() {
        return type;
    }
}

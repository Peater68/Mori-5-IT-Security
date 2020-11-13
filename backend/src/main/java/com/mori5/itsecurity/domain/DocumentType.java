package com.mori5.itsecurity.domain;

public enum DocumentType {
    CAFF("CAFF", "itsecurity-caffs"),
    PREVIEW("PREVIEW", "itsecurity-previews");

    private final String name;
    private final String bucket;

    DocumentType(String name, String bucket) {
        this.name = name;
        this.bucket = bucket;
    }

    public String getName() {
        return name;
    }

    public String getBucket() {
        return bucket;
    }
}

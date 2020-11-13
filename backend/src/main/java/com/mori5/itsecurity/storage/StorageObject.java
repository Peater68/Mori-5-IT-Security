package com.mori5.itsecurity.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorageObject {
    private String bucket;
    private String fileName;
    private byte[] content;
    private String contentType;
}

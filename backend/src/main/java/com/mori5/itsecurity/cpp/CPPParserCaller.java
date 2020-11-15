package com.mori5.itsecurity.cpp;

import org.springframework.stereotype.Service;

@Service
public class CPPParserCaller {

    static {
        System.loadLibrary("parser");
    }

    public native CreatorsImages parse(String fileName);

}
package com.mori5.itsecurity.cpp;

import org.springframework.stereotype.Service;

@Service // TODO a nem működne, lehet emiatt
public class CPPParserCaller {

    static {
        System.loadLibrary("parser");
    }

    public native CreatorsImages parse(String fileName);

}
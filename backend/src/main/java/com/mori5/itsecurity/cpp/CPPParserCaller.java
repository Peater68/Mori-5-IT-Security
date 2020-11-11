package com.mori5.itsecurity.cpp;

public class CPPParserCaller {

    static {
        System.loadLibrary("parser");
    }

    public native CreatorsImages parse(String fileName);

}
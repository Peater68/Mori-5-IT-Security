package com.mori5.itsecurity.cpp;

public class CPPParserCaller {

    static {
        try {
            System.loadLibrary("parser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public native CreatorsImages parse(byte[] caffArray);

}
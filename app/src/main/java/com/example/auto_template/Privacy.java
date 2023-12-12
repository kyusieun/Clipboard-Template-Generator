package com.example.auto_template;

import androidx.annotation.NonNull;

import java.util.List;

public class Privacy {
    String header;
    List<String> contents;
    public  Privacy(String input1, List<String> input2){
        header = input1;
        contents = input2;
    }

    @NonNull
    @Override
    public String toString() {
        return header + contents.toString();
    }
}

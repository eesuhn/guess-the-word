package com.example.nothangman;

import java.util.Random;

public class wordsL {
    String[] word;
    String randomW;

    public wordsL(){
        word = new String[]{
                "Hello",
                "World",
                "Dad",
                "Mom"
        };
    }
    public String getRandomW(){
        Random random = new Random();
        randomW = word[random.nextInt(word.length)];
        return randomW;
    }
    public int randomWLength(){
        return randomW.length();
    }
}

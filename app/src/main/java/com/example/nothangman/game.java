package com.example.nothangman;

import java.util.ArrayList;
import java.util.Arrays;

public class game {
    ArrayList<String> triedL = new ArrayList<>();
    String[] wrongT = new String[6];

    int totalWrong = 0;
    String gameW;

    public game(String word){
        gameW = word;
    }
    public boolean checkExisted(String letter){
        return gameW.toLowerCase().contains(letter);
    }
    public boolean checkDuplicate(String letter){
        return triedL.contains(letter);
    }
    public void triedL(String letter){
        triedL.add(letter);
    }
    public void addWrong(String letter){
        totalWrong ++;
        Arrays.fill(wrongT, letter);
    }
    public int getTries(){
        return wrongT.length;
    }
    public int getTriesLeft(){
        return totalWrong;
    }
}

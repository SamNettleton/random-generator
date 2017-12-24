package com.rng.sam.randomnumbergenerator;

import java.util.Random;

class WordOption extends RandomFormat {
    public String randomMethod(){
        String newRandoms = "";
        String newLine = System.getProperty("line.separator");
        newRandoms += newLine;
        for(int i=0; i < MainActivity.numberOfWords; i++) {
            String newRandomString = getWord();
            newRandoms += newRandomString;
            newRandoms += newLine;
        }
        return newRandoms;
    }
    private String getWord() {
        String lines[] = MainActivity.customWordString.split("\\r?\\n");
        return lines[(int) (Math.random() * lines.length)];
    }
}

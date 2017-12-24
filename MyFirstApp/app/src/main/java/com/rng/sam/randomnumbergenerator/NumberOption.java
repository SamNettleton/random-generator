package com.rng.sam.randomnumbergenerator;

import java.util.Random;

import static com.rng.sam.randomnumbergenerator.MainActivity.randMax;
import static com.rng.sam.randomnumbergenerator.MainActivity.randMin;

class NumberOption extends RandomFormat {
    public String randomMethod(){
        String newRandoms = "";
        String newLine = System.getProperty("line.separator");
        newRandoms += newLine;
        for(int i=0; i < MainActivity.numberOfRandoms; i++) {
            String newRandomString = randomNumber();
            newRandoms += newRandomString;
            newRandoms += newLine;
        }
        return newRandoms;
    }
    public String randomNumber() {
        Random randNum = new Random();
        int range = randMax - randMin + 1;
        int randomInt = randNum.nextInt(range) + randMin;
        newRandom = Integer.toString(randomInt);
        return newRandom;
    }

}

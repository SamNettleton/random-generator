package com.rng.sam.randomnumbergenerator;

/**
 * Created by Sam on 3/6/2017.
 */

abstract class RandomFormat {
    String newRandom;
    abstract String randomMethod();

    public String createNewRandoms(int numberOfRandoms) {
        String newRandoms = "";
        String newLine = System.getProperty("line.separator");
        for(int i=0; i < numberOfRandoms; i++) {
            String newRandomString = randomMethod();
            newRandoms += newRandomString;
            newRandoms += newLine;
        }
        return newRandoms;
    }

}

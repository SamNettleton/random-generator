package com.rng.sam.randomnumbergenerator;

abstract class RandomFormat {
    String newRandom;
    abstract String randomMethod();

    String createNewRandoms() {
        return randomMethod();
    }
}

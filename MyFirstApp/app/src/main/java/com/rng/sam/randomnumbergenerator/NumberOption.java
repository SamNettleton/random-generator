package com.rng.sam.randomnumbergenerator;

import java.util.Random;

import static com.rng.sam.randomnumbergenerator.MainActivity.randMax;
import static com.rng.sam.randomnumbergenerator.MainActivity.randMin;

/**
 * Created by Sam on 3/6/2017.
 */

class NumberOption extends RandomFormat {
    public String randomMethod(){
        Random randNum = new Random();
        int range = randMax - randMin + 1;
        int randomInt = randNum.nextInt(range) + randMin;
        newRandom = Integer.toString(randomInt);
        return newRandom;
    }
}

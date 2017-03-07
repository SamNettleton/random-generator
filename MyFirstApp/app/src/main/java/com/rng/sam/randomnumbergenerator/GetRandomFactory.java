package com.rng.sam.randomnumbergenerator;

/**
 * Created by Sam on 3/6/2017.
 */

class GetRandomFactory {
    //use randomFormat
    public RandomFormat getRandomMethod(String RandomType){
        if(RandomType == null){
            return null;
        }
        if(RandomType.equalsIgnoreCase("NUMBEROPTION")) {
            return new NumberOption();
        }
        /**else if(RandomType.equalsIgnoreCase("WORDOPTION")){
            return new WordOption();
        }
        else if(RandomType.equalsIgnoreCase("DICEOPTION")) {
            return new DiceOption();
        }*/
        return null;
    }
}

package com.rng.sam.randomnumbergenerator;

class GetRandomFactory {
    //use randomFormat
    public RandomFormat getRandomMethod(String randomType){
        if(randomType == null){
            return null;
        }
        if(randomType.equalsIgnoreCase("NUMBEROPTION")) {
            return new NumberOption();
        }
        else if(randomType.equalsIgnoreCase("WORDOPTION")){
            return new WordOption();
        }
        /**else if(RandomType.equalsIgnoreCase("DICEOPTION")) {
            return new DiceOption();
        }*/
        return null;
    }
}

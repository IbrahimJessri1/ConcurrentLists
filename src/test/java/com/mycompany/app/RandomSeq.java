package com.mycompany.app;

import java.util.Random;

public class RandomSeq {
    int seed;
    Random random;
    int maxNum;

    public RandomSeq(int seed, int setmaxNum){
        random = new Random(seed);
        this.seed = seed;
        this.maxNum = setmaxNum;
    }

    public int next(){
        return this.random.nextInt(maxNum);
    }

}

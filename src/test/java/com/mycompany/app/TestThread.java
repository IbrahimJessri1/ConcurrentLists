package com.mycompany.app;

public abstract class TestThread extends Thread{
    protected SortList list;
    protected Integer [] nums;
    public TestThread(RandomSeq seq, int seqPart, SortList setList){
        this.list = setList;
        this.nums = new Integer[seqPart];
        for(int i = 0 ; i < nums.length; i++){
            nums[i] = seq.next();
        }
    }
}

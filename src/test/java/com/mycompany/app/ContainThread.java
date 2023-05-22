package com.mycompany.app;

public class ContainThread extends TestThread{

    private int successTimes;
    private int failedTimes;
    public ContainThread(RandomSeq seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
        this.successTimes = 0;
        this.failedTimes = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            var res = this.list.contain(nums[i]);
            if(res)
                this.successTimes++;
            else
                this.failedTimes++;
        }
    }

    public int getSuccessTimes() {
        return successTimes;
    }

    public int getFailedTimes() {
        return failedTimes;
    }
}

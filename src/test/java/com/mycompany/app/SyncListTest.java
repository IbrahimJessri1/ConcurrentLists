package com.mycompany.app;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

public class SyncListTest extends TestCase {

    public void testAddList() {

        SyncList syncList = new SyncList();

        syncList.add(1);
        syncList.add(2);
        syncList.add(3);
        syncList.add(Integer.MIN_VALUE);
        syncList.add(3);
        System.out.println(syncList.contain(5));
        System.out.println(syncList.contain(2));
        syncList.remove(3);

        syncList.printList();


    }

    public boolean checkSort(SortList list){
        Entry curr = list.head;
        while (curr.next != null){
            if(curr.object.compareTo(curr.next.object) >= 0)
                return false;
            curr = curr.next;
        }
        return true;
    }


    public void testRandomSeq() {
        RandomSeq randomSeq = new RandomSeq(0, 1000);
        for (int i = 0; i < 10; i++) {
            System.out.print(randomSeq.next() + " ");
        }
    }
    public void testRun(SortList list, String label, int randNumsLength, int randNumberRange, int setSeed, int threadsNumber) {
        RandomSeq seq = new RandomSeq(setSeed, randNumberRange);

        List<Thread> addThreads = new ArrayList<>();
        List<Thread> containThreads = new ArrayList<>();
        List<Thread> deleteThreads = new ArrayList<>();

        for(int i = 0 ; i < threadsNumber; i++){
            AddThread addThread = new AddThread(seq, randNumsLength/threadsNumber, list);
            ContainThread containThread = new ContainThread(seq, randNumsLength/threadsNumber, list);
            DeleteThread deleteThread = new DeleteThread(seq, randNumsLength/threadsNumber, list);
            addThreads.add(addThread);
            containThreads.add(containThread);
            deleteThreads.add(deleteThread);
        }
        long starta = System.currentTimeMillis();
        addThreads.stream().forEach(e -> e.start());
        addThreads.stream().forEach(e -> {
            try{
                e.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        long enda = System.currentTimeMillis() - starta;


        int listLengthAfterAdds = list.getLength();

        String addSortStatus = "";
        if(!checkSort(list))
            addSortStatus = "Not";



        long startc = System.currentTimeMillis();
        containThreads.stream().forEach(e -> e.start());
        containThreads.stream().forEach(e -> {
            try{
                e.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        long endc = System.currentTimeMillis() - startc;
        int containSuccessTimes = 0;
        int containFailedTimes = 0;

        for(var containThread : containThreads){
            var conThread = (ContainThread) containThread;
            containSuccessTimes += conThread.getSuccessTimes();
            containFailedTimes += conThread.getFailedTimes();
        }


        long startd = System.currentTimeMillis();
        deleteThreads.stream().forEach(e -> e.start());
        deleteThreads.stream().forEach(e -> {
            try{
                e.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        long endd = System.currentTimeMillis() - startd;

        int listLengthAfterRemove = list.getLength();
        int removeSuccessTimes = 0;
        int removeFailedTimes = 0;

        for(var removeThread : deleteThreads){
            var remThread = (DeleteThread) removeThread;
            removeSuccessTimes += remThread.getSuccessTimes();
            removeFailedTimes += remThread.getFailedTimes();
        }

        String delSortStatus = "";
        if(!checkSort(list))
            delSortStatus = "Not";



        System.out.println(label + " Add Execution Task: " + enda + "ms");
        System.out.println("List Length after add " + listLengthAfterAdds);
        System.out.println("List After Addition is " + addSortStatus + "sorted");


        System.out.println(label + " Contain Execution Task: " + endc + "ms");
        System.out.println("Total number of success found: " + containSuccessTimes + ", failures found: " + containFailedTimes);

        System.out.println(label + " Delete Execution Task: " + endd + "ms");
        System.out.println("List length after remove: " + listLengthAfterRemove);
        System.out.println("List After Remove is " + delSortStatus + "sorted");
        System.out.println("Total number of success removed: " + removeSuccessTimes + ", failed removes: " + removeFailedTimes);
        System.out.println("Total Time For Execution: " + (enda + endc + endd) + "ms");
    }

    @ParameterizedTest
    @CsvSource({
            "50000, 80000, 0, 7"
    })
    public void testAllRun(int randNumsLength, int randNumberRange, int setSeed, int threadsNumber){
        System.out.println("Random Numbers Length: " + randNumsLength);
        System.out.println("Random Numbers Range:" + randNumberRange);
        System.out.println("Random Seed: " + setSeed);
        System.out.println("--------------------------");

        SyncList synclist = new SyncList();
        testRun(synclist, "Synchronization", randNumsLength, randNumberRange, setSeed, threadsNumber);
        System.out.println("===================");
        LockList lockList = new LockList();
        testRun(lockList, "LockList", randNumsLength, randNumberRange, setSeed, threadsNumber);
        System.out.println("================");
        RWLockList rwlocklist = new RWLockList();
        testRun(rwlocklist, "RWLockList", randNumsLength, randNumberRange, setSeed, threadsNumber);
    }







}

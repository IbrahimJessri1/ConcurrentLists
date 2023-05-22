package com.mycompany.app;

public abstract class SortList {

    public Entry head;

    public SortList() {
        this.head = new Entry(Integer.MIN_VALUE);
       this.head.next =new Entry(Integer.MAX_VALUE);
    }

    public  abstract  boolean add(Integer obj);
    public  abstract  boolean remove(Integer obj);
    public  abstract  boolean contain(Integer obj);

    public void printList(){
        Entry curr = this.head;
        while (curr != null){
            System.out.println(curr.object);
            curr = curr.next;

        }
    }

    public int getLength(){
        int len = 0;
        Entry curr = this.head;
        while (curr != null){
            len++;
            curr = curr.next;
        }
        return len;
    }


}

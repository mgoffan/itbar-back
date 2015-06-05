package com.itbar.frontend.appmanager;

public class Counter {

    int count = 0;

    public void add(){
        count++;
    }

    public int get(){
        return count;
    }

    public void set(int a){
        count=a;
    }

    public int moduleFour(){
        return count%4;
    }

    public boolean equals(int n){
        return count==n;
    }




}

package com.itbar.frontend.appmanager;

public class BooleanStatus {

    public boolean state = true;

    public BooleanStatus(boolean state){
        this.state = state;
    }

    public boolean change(){
        if (state==true){
            state=false;
        }else{
            state=true;
        }
        return state;
    }

    public boolean getState(){
        return state;
    }



}

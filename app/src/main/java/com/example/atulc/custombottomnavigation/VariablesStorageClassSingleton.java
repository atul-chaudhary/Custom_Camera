package com.example.atulc.custombottomnavigation;

public class VariablesStorageClassSingleton {


    public byte[] picture;
    private static VariablesStorageClassSingleton instance=null;
    private void VariablesStorageClass(){

    }

    public static VariablesStorageClassSingleton getInstance(){
        if(instance==null){
            instance = new VariablesStorageClassSingleton();
        }
        return instance;
    }

    public static VariablesStorageClassSingleton newInstance(){
        if(instance==null){
            instance = new VariablesStorageClassSingleton();
        }else{
            instance=null;
            instance = new VariablesStorageClassSingleton();
        }
        return instance;
    }

    public static VariablesStorageClassSingleton destroyInstance(){
        if(instance!=null){
            instance = null;
        }
        return instance;
    }

}



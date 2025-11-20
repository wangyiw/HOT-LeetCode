package com.yww.code.hot.instance;

public class single {
    /**
     * 实现两个方式的单例 懒汉式和饿汉式
     */
    private static volatile single instance;
    private single(){
    }
    public static single getInstance(){
        if(instance == null){
            instance = new single();
        }
        return instance;
    }

    public static single getInstance2(){
        if(instance == null){
            synchronized(single.class){
                if(instance == null){
                    instance = new single();
                }
            }
        }
        return instance;
    }
    
}


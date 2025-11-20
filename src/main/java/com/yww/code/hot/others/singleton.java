package com.yww.code.hot.others;
public class singleton {
    /**
     * 实现一个单例模式
     */
    private static volatile singleton instance;
    private singleton(){};
    public static singleton getInstance(){
        if(instance == null){
            synchronized(singleton.class){
                if(instance == null){
                    instance = new singleton();
                }
            }
        }
        return instance;
    }
}

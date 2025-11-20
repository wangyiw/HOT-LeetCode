package com.yww.code.hot.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class turnString {
    /**
     * 翻转倒叙输出一个字符串，不能用spilt
     * 输入："ABCD"
     * 输出："DCBA"
     */
    public static String turningString(String str){
        // 要求不能用reverse等函数
        char[] chars = str.toCharArray();
        int left = 0,right = chars.length-1;
        while(left<right){
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }
    public static String turn2String(String str){
        Deque<Character> deque = new ArrayDeque<>();
        for(char s:str.toCharArray()){
            deque.push(s);
        }
        StringBuilder sb = new StringBuilder();
        while(!deque.isEmpty()){
            sb.append(deque.pop());
        }
        return sb.toString();
    }
    public static void main(String[] args){
        String str = "ABCD";
        System.out.println(turningString(str));
        System.out.println(turn2String(str));
    }
}

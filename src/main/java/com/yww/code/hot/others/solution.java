package com.yww.code.hot.others;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class solution {
    /**
     * 一个长度为n的数组a和一个整数k，数组中的元素都是整数，
     * 数组的两个数配对需要 (x+k)*(y+k)为偶数，每个元素最多配对一次
     * 输入:7 5
            1 5 4 4 3 6 3
        输出:3
            6 3
            4 3
            4 5
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }
        // 关键洞察：(x+k)*(y+k) 为偶数  至少一个为偶数
        // 分组：按 (nums[i] + k) 的奇偶性
        List<Integer> evenGroup = new ArrayList<>();  // x+k为偶数
        List<Integer> oddGroup = new ArrayList<>();   // x+k为奇数
        
        for (int i = 0; i < n; i++) {
            if ((nums[i] + k) % 2 == 0) {
                evenGroup.add(nums[i]);
            } else {
                oddGroup.add(nums[i]);
            }
        }
        
        int count = 0;
        
        for (int i = 0; i < evenGroup.size() - 1; i += 2) {
            System.out.println(evenGroup.get(i) + " " + evenGroup.get(i + 1));
            count++;
        }
        
        // 如果偶数组有剩余，与奇数配对
        if (evenGroup.size() % 2 == 1 && oddGroup.size() > 0) {
            System.out.println(evenGroup.get(evenGroup.size() - 1) + " " + oddGroup.get(0));
            count++;
        }
        
        System.out.println(count);
    }
   
    
}

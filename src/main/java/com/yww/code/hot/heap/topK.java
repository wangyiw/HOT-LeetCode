package com.yww.code.hot.heap;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class topK {
    /**
     * 前k个高频元素
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * 输入：nums = [1,1,1,2,2,3], k = 2
     * 输出：[1,2]
     */
    public int[] topKFrequent(int[] nums, int k) {
        // 最小堆实现
        HashMap<Integer,Integer> map = new HashMap<>();
        // map记录出现频率 key 数字 value 出现频率
        for(int num:nums){
            map.put(num,map.getOrDefault(num,0)+1);
        }
        // 构造最小堆
        PriorityQueue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>((a,b)->a.getValue()-b.getValue());
        // 遍历nums,把元素添加到最小堆，如果堆不满则直接添加，堆满了则比较堆顶元素和当前元素的频率，如果当前元素频率大于堆顶元素则替换
       for(Map.Entry<Integer,Integer> entryMap:map.entrySet()){
        if(queue.size()<k){
            queue.offer(entryMap);
        }else{
            if(queue.peek().getValue()<entryMap.getValue()){
                queue.poll();
                queue.offer(entryMap);
            }
        }

       }
       int[] res = new int[k];
       for(int i=0;i<k;i++){
        res[i] = queue.poll().getKey();
       }
       return res;
    }
    public static void main(String[] args) {
        topK tk = new topK();
        int[] nums = {1,1,1,2,2,3};
        int k = 2;
        int[] res = tk.topKFrequent(nums, k);
        for(int i:res){
            System.out.println(i);
        }
    }

    
}

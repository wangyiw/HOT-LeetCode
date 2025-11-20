package com.yww.code.hot.handcheck;

import java.util.Arrays;
import java.util.*;
import java.util.Random;

public class cardGame {
    /**
     * 翻牌游戏，翻到1得1分，翻到2个0游戏结束
     */
    public static int playGame(int[] grid,int[] sequence, int n){
        // 优化版本：针对大n值的性能优化
        // 1. 使用BitSet减少内存占用
        // 2. 修复边界检查逻辑
        // 3. 提前终止优化
        int score = 0, zeroCount = 0;
        int totalSize = n * n;
        BitSet flipped = new BitSet(totalSize);
        
        for(int pos : sequence){
            // 边界检查优化：使用动态计算的边界
            if(pos < 0 || pos >= totalSize){
                continue;
            }
            
            // 检查是否已翻过（BitSet操作更高效）
            if(flipped.get(pos)){
                continue;
            }
            
            flipped.set(pos);
            int value = grid[pos];
            
            if(value == 1){
                score++;
            } else if(value == 0){
                zeroCount++;
                // 提前终止：翻到2个0立即结束
                if(zeroCount == 2){
                    break;
                }
            }
        }
        return score;
    }
    
    /**
     * 超大n值的极致优化版本
     * 适用于n > 1000的场景
     */
    public static int playGameOptimized(int[] grid, int[] sequence, int n) {
        int score = 0, zeroCount = 0;
        int totalSize = n * n;
        
        // 对于超大数组，使用HashSet可能比BitSet更高效
        Set<Integer> flipped = new HashSet<>();
        
        // 预先找到所有0的位置，避免重复检查
        Set<Integer> zeroPositions = new HashSet<>();
        for(int i = 0; i < totalSize; i++) {
            if(grid[i] == 0) {
                zeroPositions.add(i);
            }
        }
        
        for(int pos : sequence) {
            // 快速边界检查
            if(pos < 0 || pos >= totalSize || flipped.contains(pos)) {
                continue;
            }
            
            flipped.add(pos);
            
            // 优化：直接检查是否为0位置
            if(zeroPositions.contains(pos)) {
                zeroCount++;
                if(zeroCount == 2) {
                    break; // 提前终止
                }
            } else {
                // 假设非0位置都是1（根据游戏规则）
                score++;
            }
        }
        return score;
    }
    public static int[] generateRandomGrid(int n){
        // 生成一个随机的n*n布局数组，n*n-2个1 2个0
        int[] grid = new int[n*n];
        Arrays.fill(grid,1);
        Random random = new Random();
        int firstZero = random.nextInt(n*n);
        int secondZero;
        do{
            secondZero = random.nextInt(n*n);
        }while(firstZero == secondZero);
        grid[firstZero] = 0;
        grid[secondZero] = 0;
        return grid;

    }
    public static int[] generateRandomSequence(int n){
        // 生成一个随机翻牌序列 n*n为序列大小,每个元素是0-n*n-1的随机数不能重复
        int[] sequence = new int[n*n];
        Random random = new Random();
        for(int i=0;i<n*n;i++){
            sequence[i] = i;
        }
        for(int i=n*n-1;i>0;i--){
            int j = random.nextInt(i+1);
            int temp = sequence[i];
            sequence[i] = sequence[j];
            sequence[j] = temp;
        }
        return sequence;
    }
    /**
     * 性能测试：BitSet vs boolean[] 对比
     */
    public static void performanceTest(int n) {
        int[] grid = generateRandomGrid(n);
        int[] sequence = generateRandomSequence(n);
        
        // 测试 boolean[] 版本
        long startTime = System.nanoTime();
        int score1 = playGameWithBooleanArray(grid, sequence, n);
        long booleanTime = System.nanoTime() - startTime;
        
        // 测试 BitSet 版本
        startTime = System.nanoTime();
        int score2 = playGame(grid, sequence, n);
        long bitSetTime = System.nanoTime() - startTime;
        
        System.out.println("=== 性能测试结果 (n=" + n + ") ===");
        System.out.println("boolean[] 耗时: " + booleanTime/1000000.0 + " ms");
        System.out.println("BitSet 耗时: " + bitSetTime/1000000.0 + " ms");
        System.out.println("内存节省: " + (100 - (n*n/8.0)/(n*n)*100) + "%");
        System.out.println("结果一致: " + (score1 == score2));
    }
    
    /**
     * 使用传统 boolean[] 的版本（用于对比）
     */
    public static int playGameWithBooleanArray(int[] grid, int[] sequence, int n) {
        int score = 0, zeroCount = 0;
        int totalSize = n * n;
        boolean[] flipped = new boolean[totalSize];  // 传统方式
        
        for(int pos : sequence) {
            if(pos < 0 || pos >= totalSize || flipped[pos]) {
                continue;
            }
            
            flipped[pos] = true;
            int value = grid[pos];
            
            if(value == 1) {
                score++;
            } else if(value == 0) {
                zeroCount++;
                if(zeroCount == 2) {
                    break;
                }
            }
        }
        return score;
    }

    public static void main(String[] args){
        // 基本功能测试
        int[] grid = generateRandomGrid(4);
        int[] sequence = generateRandomSequence(4);
        int score = playGame(grid, sequence,4);
        System.out.println("翻牌顺序："+Arrays.toString(sequence));
        System.out.println("九宫格布局："+Arrays.toString(grid));
        System.out.println("最终得分："+score);
        
        System.out.println();
        
        // 性能对比测试
        performanceTest(100);   // 10,000 个位置
        performanceTest(1000);  // 1,000,000 个位置
    }

}

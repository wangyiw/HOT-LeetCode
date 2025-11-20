package com.yww.code.hot.handcheck;

import java.util.Random;
import java.util.Arrays;

/**
 * 九宫格翻牌游戏
 * 
 * 游戏规则：
 * - 9个格子随机放置7个1和2个0
 * - 按给定顺序翻牌，翻到1得1分
 * - 翻到2个0游戏结束
 * - 返回最终得分
 */
public class game {
    
    /**
     * 核心方法：计算翻牌游戏得分
     * 
     * 数据结构：
     * - int[] grid: 存储九宫格布局
     * - boolean[] flipped: 记录翻牌状态
     * - 基本变量: score, zeroCount, gameOver
     * 
     * 算法思路：
     * 1. 遍历翻牌顺序
     * 2. 对每个位置检查并翻牌
     * 3. 更新得分和游戏状态
     * 4. 翻到2个0时结束
     * 
     * @param grid 九宫格布局，包含7个1和2个0
     * @param sequence 翻牌顺序，一维数组索引0-8
     * @return 最终得分
     */
    public static int playGame(int[] grid, int[] sequence) {
        // 参数校验
        if (grid == null || grid.length != 9) {
            throw new IllegalArgumentException("九宫格必须是长度为9的数组");
        }
        if (sequence == null || sequence.length == 0) {
            throw new IllegalArgumentException("翻牌顺序不能为空");
        }
        
        // 游戏状态变量
        int score = 0;                    // 得分
        int zeroCount = 0;                // 翻到0的次数
        boolean[] flipped = new boolean[9]; // 翻牌状态记录
        
        // 按顺序翻牌 - 核心算法
        for (int pos : sequence) {
            // 边界检查
            if (pos < 0 || pos >= 9) {
                continue; // 跳过无效位置
            }
            
            // 重复翻牌检查
            if (flipped[pos]) {
                continue; // 跳过已翻过的位置
            }
            
            // 执行翻牌
            flipped[pos] = true;
            int value = grid[pos];
            
            // 更新游戏状态
            if (value == 1) {
                score++; // 翻到1，得分+1
            } else if (value == 0) {
                zeroCount++; // 翻到0，计数+1
                if (zeroCount == 2) {
                    break; // 翻到第2个0，游戏结束
                }
            }
        }
        
        return score;
    }
    
    /**
     * 生成随机九宫格布局
     * 
     * 算法：
     * 1. 初始化全为1
     * 2. 随机选择2个不同位置放0
     * 
     * @return 包含7个1和2个0的随机布局
     */
    public static int[] generateRandomGrid() {
        int[] grid = new int[9];
        Arrays.fill(grid, 1); // 填充7个1
        
        Random random = new Random();
        
        // 随机放置第1个0
        int firstZero = random.nextInt(9);
        grid[firstZero] = 0;
        
        // 随机放置第2个0（确保不重复）
        int secondZero;
        do {
            secondZero = random.nextInt(9);
        } while (secondZero == firstZero);
        grid[secondZero] = 0;
        
        return grid;
    }
    
    /**
     * 生成随机翻牌顺序
     * 
     * 算法：Fisher-Yates洗牌算法
     * 时间复杂度：O(n)
     * 
     * @return 0-8的随机排列
     */
    public static int[] generateRandomSequence() {
        int[] sequence = new int[9];
        for (int i = 0; i < 9; i++) {
            sequence[i] = i;
        }
        
        // Fisher-Yates洗牌
        Random random = new Random();
        for (int i = 8; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // 交换 sequence[i] 和 sequence[j]
            int temp = sequence[i];
            sequence[i] = sequence[j];
            sequence[j] = temp;
        }
        
        return sequence;
    }
    
    /**
     * 打印九宫格布局（3x3格式）
     */
    public static void printGrid(int[] grid) {
        System.out.println("九宫格布局：");
        for (int i = 0; i < 9; i++) {
            System.out.print(grid[i] + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
    
    /**
     * 测试主方法
     */
    public static void main(String[] args) {
        System.out.println("=== 九宫格翻牌游戏 ===");
        System.out.println();
        
        // 测试1：固定布局和顺序
        System.out.println("【测试1：固定布局】");
        int[] grid1 = {1, 1, 0, 1, 1, 1, 0, 1, 1};
        int[] sequence1 = {0, 4, 8, 1, 2, 3, 5, 6, 7}; // 题目给定的顺序
        
        printGrid(grid1);
        System.out.println("翻牌顺序：" + Arrays.toString(sequence1));
        
        int score1 = playGame(grid1, sequence1);
        System.out.println("最终得分：" + score1);
        
        // 测试2：随机布局
        System.out.println("【测试2：随机布局】");
        int[] grid2 = generateRandomGrid();
        int[] sequence2 = generateRandomSequence();
        
        printGrid(grid2);
        System.out.println("翻牌顺序：" + Arrays.toString(sequence2));
        
        int score2 = playGame(grid2, sequence2);
        System.out.println("最终得分：" + score2);
        System.out.println();
        
        // 测试3：最佳情况
        System.out.println("【测试3：最佳情况（先翻7个1）】");
        int[] grid3 = {1, 1, 1, 1, 1, 1, 1, 0, 0};
        int[] sequence3 = {0, 1, 2, 3, 4, 5, 6}; // 只翻前7个
        
        printGrid(grid3);
        System.out.println("翻牌顺序：" + Arrays.toString(sequence3));
        
        int score3 = playGame(grid3, sequence3);
        System.out.println("最终得分：" + score3 + " (期望: 7)");
        
        // 测试4：最坏情况
        System.out.println("\n【测试4：最坏情况（先翻2个0）】");
        int[] grid4 = {0, 0, 1, 1, 1, 1, 1, 1, 1};
        int[] sequence4 = {0, 1, 2, 3, 4, 5, 6, 7, 8}; // 按顺序翻
        
        printGrid(grid4);
        System.out.println("翻牌顺序：" + Arrays.toString(sequence4));
        
        int score4 = playGame(grid4, sequence4);
        System.out.println("最终得分：" + score4 + " (期望: 0，游戏提前结束)");
    }
}

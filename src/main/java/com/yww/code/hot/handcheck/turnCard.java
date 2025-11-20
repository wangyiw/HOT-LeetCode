package com.yww.code.hot.handcheck;

import java.util.Random;
import java.util.Arrays;

/**
 * 九宫格翻牌游戏
 * 
 * 游戏规则：
 * - 9个格子中随机放置7个1和2个0
 * - 玩家按顺序翻牌，翻到1加1分
 * - 翻到2个0游戏结束
 * - 最多9次翻牌机会
 */
public class turnCard {
    
    /**
     * 游戏结果类
     */
    public static class GameResult {
        private int score;        // 最终得分
        private int flipCount;    // 实际翻牌次数
        private boolean gameOver; // 是否因翻到2个0而提前结束
        
        public GameResult(int score, int flipCount, boolean gameOver) {
            this.score = score;
            this.flipCount = flipCount;
            this.gameOver = gameOver;
        }
        
        public int getScore() {
            return score;
        }
        
        public int getFlipCount() {
            return flipCount;
        }
        
        public boolean isGameOver() {
            return gameOver;
        }
        
        @Override
        public String toString() {
            return String.format("GameResult{得分=%d, 翻牌次数=%d, 提前结束=%s}", 
                               score, flipCount, gameOver);
        }
    }
    
    /**
     * 核心方法：模拟翻牌游戏（二维数组版本）
     * 
     * @param grid 九宫格布局，3x3二维数组，包含7个1和2个0
     * @param sequence 翻牌顺序，每个元素是[row, col]坐标
     * @return GameResult 游戏结果
     */
    public static GameResult playGame(int[][] grid, int[][] sequence) {
        // 参数校验
        if (grid == null || grid.length != 3 || grid[0].length != 3) {
            throw new IllegalArgumentException("九宫格必须是3x3的二维数组");
        }
        if (sequence == null || sequence.length == 0) {
            throw new IllegalArgumentException("翻牌顺序不能为空");
        }
        
        int score = 0;           // 得分
        int zeroCount = 0;       // 翻到0的次数
        int flipCount = 0;       // 实际翻牌次数
        boolean gameOver = false; // 游戏是否结束
        boolean[][] flipped = new boolean[3][3]; // 记录哪些格子已翻过
        
        // 按顺序翻牌
        for (int[] pos : sequence) {
            // 检查坐标格式
            if (pos == null || pos.length != 2) {
                System.out.println("警告：坐标格式错误，跳过");
                continue;
            }
            
            int row = pos[0];
            int col = pos[1];
            
            // 检查位置合法性
            if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                System.out.printf("警告：位置[%d,%d]超出范围，跳过\n", row, col);
                continue;
            }
            
            // 检查是否已翻过
            if (flipped[row][col]) {
                System.out.printf("警告：位置[%d,%d]已翻过，跳过\n", row, col);
                continue;
            }
            
            // 翻牌
            flipped[row][col] = true;
            flipCount++;
            int value = grid[row][col];
            
            System.out.printf("第%d次翻牌：位置[%d,%d] = %d\n", flipCount, row, col, value);
            
            if (value == 1) {
                // 翻到1，加分
                score++;
            } else if (value == 0) {
                // 翻到0，计数
                zeroCount++;
                if (zeroCount == 2) {
                    // 翻到第2个0，游戏结束
                    gameOver = true;
                    System.out.println("游戏结束：翻到第2个0！");
                    break;
                }
            }
        }
        
        return new GameResult(score, flipCount, gameOver);
    }
    
    /**
     * 兼容方法：一维数组版本（保持向后兼容）
     * 
     * @param grid 九宫格布局，长度为9的数组
     * @param sequence 翻牌顺序，数组元素为0-8的索引
     * @return GameResult 游戏结果
     */
    public static GameResult playGame(int[] grid, int[] sequence) {
        // 转换为二维数组
        int[][] grid2D = convertTo2D(grid);
        int[][] sequence2D = convertSequenceTo2D(sequence);
        
        return playGame(grid2D, sequence2D);
    }
    
    /**
     * 将一维数组转换为3x3二维数组
     * 
     * @param grid 长度为9的一维数组
     * @return 3x3二维数组
     */
    private static int[][] convertTo2D(int[] grid) {
        if (grid == null || grid.length != 9) {
            throw new IllegalArgumentException("输入数组必须长度为9");
        }
        
        int[][] result = new int[3][3];
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            result[row][col] = grid[i];
        }
        return result;
    }
    
    /**
     * 将一维索引序列转换为二维坐标序列
     * 
     * @param sequence 一维索引数组（0-8）
     * @return 二维坐标数组，每个元素是[row, col]
     */
    private static int[][] convertSequenceTo2D(int[] sequence) {
        if (sequence == null) {
            throw new IllegalArgumentException("翻牌顺序不能为空");
        }
        
        int[][] result = new int[sequence.length][2];
        for (int i = 0; i < sequence.length; i++) {
            int index = sequence[i];
            result[i][0] = index / 3; // row
            result[i][1] = index % 3; // col
        }
        return result;
    }
    
    /**
     * 生成随机九宫格布局（7个1，2个0）- 二维数组版本
     * 
     * @return 3x3随机布局的二维数组
     */
    public static int[][] generateRandomGrid2D() {
        int[][] grid = new int[3][3];
        
        // 填充7个1
        for (int i = 0; i < 3; i++) {
            Arrays.fill(grid[i], 1);
        }
        
        // 随机选择2个位置放0
        Random random = new Random();
        
        // 第一个0
        int firstRow = random.nextInt(3);
        int firstCol = random.nextInt(3);
        grid[firstRow][firstCol] = 0;
        
        // 第二个0（确保不重复）
        int secondRow, secondCol;
        do {
            secondRow = random.nextInt(3);
            secondCol = random.nextInt(3);
        } while (secondRow == firstRow && secondCol == firstCol);
        grid[secondRow][secondCol] = 0;
        
        return grid;
    }
    
    /**
     * 生成随机九宫格布局（7个1，2个0）- 一维数组版本（兼容）
     * 
     * @return 随机布局的数组
     */
    public static int[] generateRandomGrid() {
        int[][] grid2D = generateRandomGrid2D();
        
        // 转换为一维数组
        int[] grid = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i * 3 + j] = grid2D[i][j];
            }
        }
        
        return grid;
    }
    
    /**
     * 生成随机翻牌顺序（二维坐标版本）
     * 
     * @return 随机坐标顺序数组，每个元素是[row, col]
     */
    public static int[][] generateRandomSequence2D() {
        // 先生成所有坐标
        int[][] allPositions = new int[9][2];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                allPositions[index][0] = i;
                allPositions[index][1] = j;
                index++;
            }
        }
        
        // Fisher-Yates洗牌算法
        Random random = new Random();
        for (int i = 8; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int[] temp = allPositions[i];
            allPositions[i] = allPositions[j];
            allPositions[j] = temp;
        }
        
        return allPositions;
    }
    
    /**
     * 生成随机翻牌顺序（0-8的随机排列）- 兼容版本
     * 
     * @return 随机顺序数组
     */
    public static int[] generateRandomSequence() {
        int[][] sequence2D = generateRandomSequence2D();
        
        // 转换为一维索引
        int[] sequence = new int[9];
        for (int i = 0; i < 9; i++) {
            int row = sequence2D[i][0];
            int col = sequence2D[i][1];
            sequence[i] = row * 3 + col;
        }
        
        return sequence;
    }
    
    /**
     * 打印九宫格布局（二维数组版本）
     */
    public static void printGrid(int[][] grid) {
        System.out.println("九宫格布局：");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * 打印九宫格布局（一维数组版本 - 兼容）
     */
    public static void printGrid(int[] grid) {
        int[][] grid2D = convertTo2D(grid);
        printGrid(grid2D);
    }
    
    /**
     * 打印翻牌序列（二维坐标版本）
     */
    public static void printSequence(int[][] sequence) {
        System.out.print("翻牌顺序：");
        for (int i = 0; i < sequence.length; i++) {
            System.out.printf("[%d,%d]", sequence[i][0], sequence[i][1]);
            if (i < sequence.length - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
    
    /**
     * 测试主方法
     */
    public static void main(String[] args) {
        System.out.println("=== 九宫格翻牌游戏（二维数组版本）===");
        System.out.println();
        
        // 测试1：二维数组固定布局
        System.out.println("【测试1：二维数组固定布局】");
        int[][] grid1 = {
            {1, 1, 0},
            {1, 1, 1},
            {0, 1, 1}
        };
        int[][] sequence1 = {{0,0}, {0,1}, {0,2}, {1,0}, {1,1}}; // 按顺序翻前5个位置
        
        printGrid(grid1);
        printSequence(sequence1);
        System.out.println();
        
        GameResult result1 = playGame(grid1, sequence1);
        System.out.println("结果：" + result1);
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // 测试2：随机二维布局
        System.out.println("【测试2：随机二维布局】");
        int[][] grid2 = generateRandomGrid2D();
        int[][] sequence2 = generateRandomSequence2D();
        
        printGrid(grid2);
        printSequence(sequence2);
        System.out.println();
        
        GameResult result2 = playGame(grid2, sequence2);
        System.out.println("结果：" + result2);
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // 测试3：最佳情况（先翻7个1）
        System.out.println("【测试3：最佳情况】");
        int[][] grid3 = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 0, 0}
        }; // 0放在最后
        int[][] sequence3 = {{0,0}, {0,1}, {0,2}, {1,0}, {1,1}, {1,2}, {2,0}}; // 只翻前7个
        
        printGrid(grid3);
        printSequence(sequence3);
        System.out.println();
        
        GameResult result3 = playGame(grid3, sequence3);
        System.out.println("结果：" + result3);
        System.out.println("期望：得分=7，未提前结束");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("【兼容性测试：一维数组版本】");
        
        // 测试兼容性
        int[] grid1D = {1, 1, 0, 1, 1, 1, 0, 1, 1};
        int[] sequence1D = {0, 1, 2, 3, 4};
        
        System.out.println("一维数组布局：" + Arrays.toString(grid1D));
        printGrid(grid1D);  // 应该显示为3x3格式
        System.out.println("一维翻牌顺序：" + Arrays.toString(sequence1D));
        
        GameResult result4 = playGame(grid1D, sequence1D);
        System.out.println("结果：" + result4);
    }
}

package com.yww.code.hot.handcheck;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class nineBoardGame {
    /**
     * 实现一个九宫格翻牌游戏
     * 
     * 游戏规则：
     * - 9个格子中随机放置7个1和2个0
     * - 玩家按顺序翻牌，翻到1加1分
     * - 翻到2个0游戏结束
     * - 最多9次翻牌机会
     */
    public static int[] generateRandomGrid(){
        /**
         * 生成一个随机的9个格子布局，7个1和2个0
         */
        int[] grid = new int[9];
        Arrays.fill(grid,1);
        Random random = new Random();
        int first = random.nextInt(9);
        grid[first] = 0;
        int secondZero;
        do{
            secondZero = random.nextInt(9);
        }while(secondZero == first);
        grid[secondZero] = 0;
        return grid;
    }
    public static int[] generateRandomSequence(){
        /**
         * 生成1-9随机的序列
         */
        int[] sequence = new int[9];
        for(int i=0;i<9;i++){
            sequence[i] = i;
        }
        // 随机交换
        Random random = new Random();
        for(int i=8;i>0;i--){
            int j = random.nextInt(i+1);
            int temp = sequence[i];
            sequence[i] = sequence[j];
            sequence[j] = temp;
        }
        return sequence;
    }
    public static int playGame(int[] grid, int[] sequence){
        int zeroCount = 0;
        int score = 0;
        boolean[] flipped = new boolean[9];

        for(int pos:sequence){
            if(flipped[pos]){
                continue;
            }
            if(pos<0 || pos>=9){
                continue;
            }
            flipped[pos] = true;
            int value = grid[pos];
            if(value == 1){
                score++;
            }else if(value == 0){
                zeroCount++;
                if(zeroCount == 2){
                    break;
                }
            }
        }
        return score;
    }
    public static void main(String[] args){
        int[] grid = generateRandomGrid();
        // int[] sequence = generateRandomSequence();
        // 用户自己输入序列
        Scanner in = new Scanner(System.in);
        int[] sequence = new int[9];
        System.out.println("请输入翻牌顺序：");
        for(int i=0;i<9;i++){
            System.out.print("第"+(i+1)+"次翻牌：");
                sequence[i] = in.nextInt();
            }
            int score = playGame(grid,sequence);
            System.out.println("翻牌顺序："+Arrays.toString(sequence));
            System.out.println("九宫格布局："+Arrays.toString(grid));
            System.out.println("最终得分："+score);
    }
}

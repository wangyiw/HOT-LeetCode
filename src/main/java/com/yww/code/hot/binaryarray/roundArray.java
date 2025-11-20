package com.yww.code.hot.binaryarray;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class roundArray {
    public List<Integer> spiralOrder(int[][] matrix) {
        /**
         * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
         * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
         * 输出：[1,2,3,6,9,8,7,4,5]
         */
        int m = matrix.length,n = matrix[0].length;
        int[] res = new int[m*n];
        int index = 0;
        int left = 0,right = n-1,top = 0,bottom = m-1;
        while(left<=right && top<=bottom){
            for(int i=left;i<=right;i++){
                res[index++] = matrix[top][i];
            }
            top++;
            for(int i=top;i<=bottom;i++){
                res[index++] = matrix[i][right];
            }
            right--;
            if(top<=bottom){
                for(int i=right;i>=left;i--){
                    res[index++] = matrix[bottom][i];
                }
                bottom--;
            }
            if(left<=right){
                for(int i=bottom;i>=top;i--){
                    res[index++] = matrix[i][left];
                }
                left++;
            }
        }
        return Arrays.stream(res).boxed().collect(Collectors.toList());
    }
    public static void main(String[] args) {
        roundArray ra = new roundArray();
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(ra.spiralOrder(matrix));
    }
}   

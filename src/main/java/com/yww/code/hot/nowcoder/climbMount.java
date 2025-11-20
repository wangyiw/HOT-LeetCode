package com.yww.code.hot.nowcoder;
import java.util.Scanner;

public class climbMount {
    /**
     * 在盘古开天辟地之前，他需要对当前所处的地形进行调查。盘古的面前一共有 n座山，从左往右第i座山的高度为h。盘古会选择一段连续的山进行开辟。记他选择的区间为【1r】，盘古选择的山必须满足h<h+<.<h,,也就是从左往右对应的山的高度严格单调递增。
盘古在开山之前，可以选择任意一座山，将其高度修改为任意非负整数值，由于神力限制，该操作最多进行一次。盘古想知道:他能够选择的区间最长是多少?
        输入
            3
            5
            3 6 2 3 1
            6
            1 1 4 5 1 4
            10
            7 8 3 5 6 1 2 4 9 10
        输出
            3
            4
            6
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt(); // 数据组数

        while (T-- > 0) {
            int n = scanner.nextInt(); // 山的数量
            int[] h = new int[n];
            for (int i = 0; i < n; i++) {
                h[i] = scanner.nextInt();
            }

            if (n == 1) {
                System.out.println(1);
                continue;
            }

            int[] left = new int[n];
            left[0] = 1;
            for (int i = 1; i < n; i++) {
                if (h[i] > h[i - 1]) {
                    left[i] = left[i - 1] + 1;
                } else {
                    left[i] = 1;
                }
            }

            int[] right = new int[n];
            right[n - 1] = 1;
            for (int i = n - 2; i >= 0; i--) {
                if (h[i] < h[i + 1]) {
                    right[i] = right[i + 1] + 1;
                } else {
                    right[i] = 1;
                }
            }

            int maxLength = 1;
            for (int len : left) {
                if (len > maxLength) {
                    maxLength = len;
                }
            }

            for (int i = 0; i < n; i++) {
                int best = 1; // 至少包含被修改的当前元素

                if (i > 0) {
                    best = Math.max(best, left[i - 1] + 1);
                }

                if (i < n - 1 && h[i + 1] > 0) {
                    best = Math.max(best, right[i + 1] + 1);
                }

                if (i > 0 && i < n - 1) {
                    int minCandidate = Math.max(h[i - 1] + 1, 0);
                    int maxCandidate = h[i + 1] - 1;
                    if (minCandidate <= maxCandidate) {
                        best = Math.max(best, left[i - 1] + 1 + right[i + 1]);
                    }
                }

                maxLength = Math.max(maxLength, best);
            }

            System.out.println(maxLength);
        }

        scanner.close();
    }
}

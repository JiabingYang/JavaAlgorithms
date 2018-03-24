package com.yjb.algorithm.dp;

/**
 * 背包问题
 * <p>
 * 1. 0/1背包
 * 2. 完全背包（物品无限）
 */
public class Knapsack {

    public static void main(String[] args) {
        int[] vs = {12, 3, 10, 3, 6};
        int[] ws = {5, 4, 7, 2, 6};
        int maxW = 15;
        System.out.println(zeroOneKnapsackRecursive(vs, ws, maxW, vs.length));
        System.out.println(zeroOneKnapsackDp1(vs, ws, maxW));
        System.out.println(zeroOneKnapsackDp2(vs, ws, maxW));
    }

    /* ---------------- 1. 0/1背包 --------------
     * dp[i][w]表示到第i个元素为止，在限制总重量为w的情况下我们所能选择到的最优解
     * 递推关系：
     * 1) i == 0 || w == 0
     * dp[i][w] = 0;
     * 2) wi > w
     * dp[i][w] = dp[i - 1][w];
     * 3) wi <= w && i != 0 && w != 0
     * dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - wi] + vi);
     *
     * 复杂度说明：
     * 下面的n表示物品的数量，也就是vs.length或ws.length
     * */

    /**
     * 递归
     * <p>
     * 时间2^n
     */
    private static int zeroOneKnapsackRecursive(int[] vs, int[] ws, int maxW, int i) {
        if (i == 0 || maxW == 0) {
            //要求恰好装满时加上下面注释掉的
//            if (i == 0 && maxW !=0) {
//                return Integer.MIN_VALUE;
//            }
            return 0;
        }
        if (ws[i - 1] > maxW) {
            return zeroOneKnapsackRecursive(vs, ws, maxW, i - 1);
        }
        return Math.max(zeroOneKnapsackRecursive(vs, ws, maxW, i - 1),
                zeroOneKnapsackRecursive(vs, ws, maxW - ws[i - 1], i - 1) + vs[i - 1]);
    }

    /**
     * dp
     * <p>
     * 时间n*maxW 空间n*maxW
     */
    private static int zeroOneKnapsackDp1(int[] vs, int[] ws, int maxW) {
        int[][] dp = new int[vs.length + 1][maxW + 1];
//        Arrays.fill(dp[0], 1, maxW + 1, Integer.MIN_VALUE); //要求恰好装满时加上这句
        for (int i = 1; i <= vs.length; i++) {
            for (int w = 1; w <= maxW; w++) {
                if (ws[i - 1] > w) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - ws[i - 1]] + vs[i - 1]);
                }
            }
        }
        return dp[vs.length][maxW];
    }

    /**
     * dp
     * <p>
     * 时间n*maxW 空间maxW
     */
    private static int zeroOneKnapsackDp2(int[] vs, int[] ws, int maxW) {
        int[] dp = new int[maxW + 1];
//        Arrays.fill(dp, 1, maxW + 1, Integer.MIN_VALUE); //要求恰好装满时加上这句
        for (int i = 1; i <= vs.length; i++) {
            for (int w = maxW; w >= ws[i - 1]; w--) { // 反向遍历
                dp[w] = Math.max(dp[w], dp[w - ws[i - 1]] + vs[i - 1]);
            }
        }
        return dp[maxW];
    }

    /* ---------------- 2. 完全背包（物品无限） --------------
     * dp[i][w]表示到第i个元素为止，在限制总重量为w的情况下我们所能选择到的最优解
     * 递推关系：
     * 1) i == 0 || w == 0
     * dp[i][w] = 0;
     * 2) 1 <= k * wi <= w
     * dp[i][w] = max(dp[i - 1][w], dp[i - 1][w - k * wi] + k * vi);
     *
     * TODO: 代码待实现
     * */
}

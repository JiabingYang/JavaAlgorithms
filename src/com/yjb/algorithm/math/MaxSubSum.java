package com.yjb.algorithm.math;

import com.yjb.util.Timer;

import java.util.Random;

import static com.yjb.util.MathUtil.max3;

public class MaxSubSum {

    public static void main(String[] args) {

        Random random = new Random();

        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(100) - 50;
        }

        Timer.printlnNano("getMaxSubSumON3", () -> getMaxSubSumON3(a));
        Timer.printlnNano("getMaxSubSumON2", () -> getMaxSubSumON2(a));
        Timer.printlnNano("getMaxSubSumONLogN", () -> getMaxSubSumONLogN(a, 0, a.length - 1));
        Timer.printlnNano("getMaxSubSumON", () -> getMaxSubSumON(a));
        Timer.printlnNano("getMaxSubSumONAnother", () -> getMaxSubSumONAnother(a));
    }

    //return 0 when the sum is negative
    private static int getMaxSubSumON3(int[] a) {
        int thisSum, maxSum = 0;
        int i, j, k;
        for (i = 0; i < a.length; i++) {
            for (j = i; j < a.length; j++) {
                thisSum = 0;
                for (k = i; k <= j; k++)
                    thisSum += a[k];

                if (thisSum > maxSum)
                    maxSum = thisSum;
            }
        }
        return maxSum;
    }

    //return 0 when the sum is negative
    private static int getMaxSubSumON2(int[] a) {
        int thisSum, maxSum = 0;
        int i, j;
        for (i = 0; i < a.length; i++) {
            thisSum = 0;
            for (j = i; j < a.length; j++) {
                thisSum += a[j];

                if (thisSum > maxSum)
                    maxSum = thisSum;
            }
        }
        return maxSum;
    }

    //return 0 when the sum is negative
    private static int getMaxSubSumONLogN(int[] a, int left, int right) {
        int maxLeftSum, maxRightSum;
        int maxLeftBorderSum, maxRightBorderSum;
        int leftBorderSum, rightBorderSum;
        int center, i;

        if (left == right)
            if (a[left] > 0)
                return a[left];
            else
                return 0;

        center = (left + right) / 2;
        maxLeftSum = getMaxSubSumONLogN(a, left, center);
        maxRightSum = getMaxSubSumONLogN(a, center + 1, right);

        maxLeftBorderSum = 0;
        leftBorderSum = 0;
        for (i = center; i >= left; i--) {
            leftBorderSum += a[i];
            if (leftBorderSum > maxLeftBorderSum)
                maxLeftBorderSum = leftBorderSum;
        }

        maxRightBorderSum = 0;
        rightBorderSum = 0;
        for (i = center + 1; i <= right; i++) {
            rightBorderSum += a[i];
            if (rightBorderSum > maxRightBorderSum)
                maxRightBorderSum = rightBorderSum;
        }

        return max3(maxLeftSum, maxRightSum, maxLeftBorderSum + maxRightBorderSum);
    }

    //return 0 when the sum is negative
    private static int getMaxSubSumON(int[] nums) {
        int sum = 0, max = Integer.MIN_VALUE;
        for (int num : nums) {
            sum += num;
            if (sum > max) {
                max = sum;
            } else if (sum < 0) {
                sum = 0;
            }
        }
        return max;
    }

    //return the sum when the sum is negative
    private static int getMaxSubSumONAnother(int[] nums) {
        if (nums.length == 0)
            throw new RuntimeException("the length of the array can't be 0.");
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            if (sum <= 0) {
                sum = num;
            } else {
                sum += num;
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }
}

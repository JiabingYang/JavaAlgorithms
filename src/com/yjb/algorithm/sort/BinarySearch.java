package com.yjb.algorithm.sort;

public class BinarySearch {

    private static int binarySearchOLogN(int[] a, int x) {
        int low, mid, high;

        low = 0;
        high = a.length - 1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (a[mid] < x)
                low = mid + 1;
            else if (a[mid] > x)
                high = mid - 1;
            else
                return mid;
        }
        return -1;
    }
}

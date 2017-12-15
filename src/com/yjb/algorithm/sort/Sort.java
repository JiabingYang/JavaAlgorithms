package com.yjb.algorithm.sort;

import java.util.Arrays;

public class Sort {

    public static void main(String[] args) {
        int[] a = {34, 8, 64, 51, 32, 21};
        System.out.println(Arrays.toString(a));
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }

    // ----------------------- 冒泡排序 -----------------------
    public static void bubbleSort1(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 1; j < a.length - i; j++) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    public static void bubbleSort2(int[] a) {
        boolean changed;
        do {
            changed = false;
            for (int j = 1; j < a.length; j++) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    changed = true;
                }
            }
        } while (changed);
    }

    public static void bubbleSort3(int[] a) {
        boolean changed = true;
        for (int i = 0; i < a.length && changed; i++) {
            changed = false;
            for (int j = 1; j < a.length - i; j++) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    changed = true;
                }
            }
        }
    }

    // ----------------------- 插入排序 -----------------------
    public static void insertionSort(int[] a) {
        // increment = 1
        // 增量为1的插入排序
        for (int i = 1; i < a.length; i++) {
            int temp = a[i];
            int j;
            for (j = i; j >= 1 && a[j - 1] > temp; j--) {
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
    }

    // ----------------------- 希尔排序 -----------------------
    public static void shellSort(int[] a) {
        for (int increment = a.length / 2; increment > 0; increment /= 2) {
            // increment = a.length / 2, a.length / 4, a.length / 8, ...
            // 增量为increment的插入排序
            for (int i = increment; i < a.length; i++) { // increment, increment + 1, ..., a.length - 1
                int temp = a[i];
                int j;
                for (j = i; j >= increment && a[j - increment] > temp; j -= increment) {
                    a[j] = a[j - increment];
                }
                a[j] = temp;
            }
        }
    }

    // ----------------------- 堆排序 -----------------------
    public static void heapSort(int[] a) {
        // 创建最大堆，堆元素从数组位置0开始
        for (int i = a.length / 2; i >= 0; i--) {
            percDown(a, i, a.length);
        }
        // 每个循环中将0和i的元素互换后，将
        for (int i = a.length - 1; i > 0; i--) {
            // 将a[0]和a[i]的元素互换
            int max = a[0];
            a[0] = a[i];
            a[i] = max;
            // 互换完成，此时i为当前堆的大小，i到a.length-1放置已经排好序的元素，a[0]放置着待下滤的元素
            // 对a[0]元素进行下滤操作
            percDown(a, 0, i);
        }
    }

    private static void percDown(int[] a, int i, int n) {
        int child;
        int temp;
        for (temp = a[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            if (child != n - 1 && a[child + 1] > a[child]) {
                child++;
            }
            if (temp < a[child]) {
                a[i] = a[child];
            } else {
                break;
            }
        }
        a[i] = temp;
    }

    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    // ----------------------- 归并排序 -----------------------
    public static void mergeSort(int[] a) {
        mSort(a, new int[a.length], 0, a.length - 1);
    }

    private static void mSort(int[] a, int[] tempArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mSort(a, tempArray, 0, center);
            mSort(a, tempArray, center + 1, right);
            merge(a, tempArray, left, center + 1, right);
        }
    }

    private static void merge(int[] a, int[] tempArray, int lPos, int rPos, int rEnd) {
        int lEnd = rPos - 1;
        int tempPos = lPos;
        int numElements = rEnd - lPos + 1;

        // main loop
        while (lPos <= lEnd && rPos <= rEnd) {
            if (a[lPos] <= a[rPos]) {
                tempArray[tempPos++] = a[lPos++];
            } else {
                tempArray[tempPos++] = a[rPos++];
            }
        }

        // copy rest
        while (lPos <= lEnd) {
            tempArray[tempPos++] = a[lPos++];
        }
        while (rPos <= rEnd) {
            tempArray[tempPos++] = a[rPos++];
        }

        // copy tempArray back to a
        for (int i = 0; i < numElements; i++, rEnd--) {
            a[rEnd] = tempArray[rEnd];
        }
    }

    // ----------------------- 快速排序(不带CUTOFF，子数组长度小于等于CUTOFF时转插入排序可以提高速度) -----------------------
    public static void quickSort(int[] a) {
        qSort(a, 0, a.length - 1);
    }

    private static void qSort(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = median3(a, left, right);
        int i = left;
        int j = right - 1;
        while (true) {
            while (a[++i] < pivot) { // 右移i
            }
            while (a[--j] > pivot) { // 左移j
            }
            // 此时i >= pivot, j <= pivot
            if (i < j) {
                swap(a, i, j);
            } else { // i和j交错
                break;
            }
        }
        // 此时 i和j交错或重合， <i的元素必然小于pivot，>i的元素必然大于pivot
        swap(a, i, right - 1);
        qSort(a, left, i - 1);
        qSort(a, i + 1, right);
    }

    private static int median3(int[] a, int left, int right) {
        int center = (left + right) / 2;

        // sort left, center, right
        if (a[left] > a[center]) {
            swap(a, left, center);
        }
        if (a[left] > a[right]) {
            swap(a, left, right);
        }
        if (a[center] > a[right]) {
            swap(a, center, right);
        }

        // hide pivot
        swap(a, center, right - 1);
        return a[right - 1];
    }

    private static void swap(int[] a, int pos1, int pos2) {
        int temp = a[pos1];
        a[pos1] = a[pos2];
        a[pos2] = temp;
    }
}

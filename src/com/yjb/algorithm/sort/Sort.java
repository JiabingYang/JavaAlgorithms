package com.yjb.algorithm.sort;

public class Sort {

    public static void main(String[] args) {
        int[] a = {34, 8, 64, 51, 32, 21};
//        int[] a = {2, 1};
//        int[] a = {2};
        System.out.println(quickSelectBasic(a, 2));
//        quickSortBasic(a);
//        System.out.println(Arrays.toString(a));
    }

    // ----------------------- 冒泡排序 -----------------------
    private static void bubbleSort1(int[] a) {
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

    private static void bubbleSort2(int[] a) {
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

    private static void bubbleSort3(int[] a) {
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

    // ----------------------- 选择排序 -----------------------
    private static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = a[i];
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < min) {
                    min = a[j];
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = temp;
            }
        }
    }

    // ----------------------- 插入排序 -----------------------
    private static void insertionSort(int[] a) {
        // increment = 1
        // 增量为1的插入排序
        for (int i = 1; i < a.length; i++) {
            int temp = a[i];
            int j;
            for (j = i; j > 0 && a[j - 1] > temp; j--) {
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
    }

    // ----------------------- 希尔排序 -----------------------
    private static void shellSort(int[] a) {
        for (int increment = a.length / 2; increment > 0; increment /= 2) {
            // increment = a.length / 2, a.length / 4, a.length / 8, ...
            // 增量为increment的插入排序
            for (int i = increment; i < a.length; i++) { // increment, increment + 1, ..., a.length - 1
                int temp = a[i];
                int j;
                for (j = i; j > 0 && a[j - increment] > temp; j -= increment) {
                    a[j] = a[j - increment];
                }
                a[j] = temp;
            }
        }
    }

    // ----------------------- 堆排序 -----------------------
    private static void heapSort(int[] a) {
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
        for (temp = a[i]; (child = 2 * i + 1) < n; i = child) {
            if (child + 1 < n && a[child + 1] > a[child]) {
                child++;
            }
            if (temp >= a[child]) {
                break;
            }
            a[i] = a[child];
        }
        a[i] = temp;
    }

    // ----------------------- 归并排序 -----------------------
    private static void mergeSort(int[] a) {
        mSort(a, new int[a.length], 0, a.length - 1);
    }

    private static void mSort(int[] a, int[] arr, int l, int r) {
        if (l < r) {
            int mid = (l + r) / 2;
            mSort(a, arr, 0, mid);
            mSort(a, arr, mid + 1, r);
            merge(a, arr, l, mid + 1, r);
        }
    }

    private static void merge(int[] a, int[] arr, int ls, int rs, int re) {
        int le = rs - 1;
        int n = re - ls + 1;
        int p = ls;

        // main loop
        while (ls <= le && rs <= re) {
            arr[p++] = a[ls] <= a[rs] ? a[ls++] : a[rs++];
        }

        // copy rest
        while (ls <= le) {
            arr[p++] = a[ls++];
        }
        while (rs <= re) {
            arr[p++] = a[rs++];
        }

        // copy arr back to a
        for (int i = 0; i < n; i++, re--) {
            a[re] = arr[re];
        }
    }

    // ----------------------- 快速排序(选择第一个元素为枢纽元，不带CUTOFF，子数组长度小于等于CUTOFF时转插入排序可以提高速度) -----------------------
    private static void quickSortBasic(int[] a) {
        if (a.length > 0) {
            qSortBasic(a, 0, a.length - 1);
        }
    }

    private static void qSortBasic(int[] a, int l, int r) {
        if (l < r) {
            int pivotIndex = partition(a, l, r); //将数组一分为二
            qSortBasic(a, l, pivotIndex - 1);   //对低字段表进行递归排序
            qSortBasic(a, pivotIndex + 1, r); //对高字段表进行递归排序
        }
    }

    private static int partition(int[] a, int l, int r) {
        int pivot = a[l]; //数组的第一个作为中轴
        while (l < r) {
            while (l < r && a[r] >= pivot) {
                r--;
            }
            a[l] = a[r];//比中轴小的记录移到低端
            while (l < r && a[l] <= pivot) {
                l++;
            }
            a[r] = a[l]; //比中轴大的记录移到高端
        }
        a[l] = pivot; //中轴记录到尾
        return l; // 返回中轴的位置
    }

    // ----------------------- 快速选择(选择第一个元素为枢纽元，不带CUTOFF，子数组长度小于等于CUTOFF时转插入排序可以提高速度) -----------------------
    private static int quickSelectBasic(int[] a, int k) {
        if (a.length > 0) {
            return qSelectBasic(a, k,0, a.length - 1);
        }
        return -1;
    }

    private static int qSelectBasic(int[] a, int k, int l, int r) {
        if (l < r) {
            int pivotIndex = partition(a, l, r); //将数组一分为二
            if (k <= pivotIndex) {
                return qSelectBasic(a, k, l, pivotIndex - 1);
            } else if (k > pivotIndex + 1) {
                return qSelectBasic(a, k, pivotIndex + 1, r);
            } else {
                return a[pivotIndex];
            }
        }
        return -1;
    }

    // ----------------------- 快速排序(三数中值分割法，不带CUTOFF，子数组长度小于等于CUTOFF时转插入排序可以提高速度) -----------------------
    private static void quickSort(int[] a) {
        qSort(a, 0, a.length - 1);
    }

    private static void qSort(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = median3(a, left, right);
        // 注意下面不能写成i = left + 1; j = right -2; (a[i++] < pivot) (a[j--] > pivot)
        // 否则在a[i] == a[j] == pivot时会产生无限循环
        int i = left;
        int j = right - 1;
        while (i < j) { // i和j交错
            while (a[++i] < pivot) { // 右移i
            }
            while (a[--j] > pivot) { // 左移j
            }
            // 此时i >= pivot, j <= pivot
            if (i < j) {
                swap(a, i, j);
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

    // ----------------------- 快速选择(三数中值分割法，不带CUTOFF，子数组长度小于等于CUTOFF时转插入排序可以提高速度) -----------------------
    private static int quickSelect(int[] a, int k) {
        return qSelect(a, k, 0, a.length - 1);
    }

    private static int qSelect(int[] a, int k, int left, int right) {
        int pivot = median3(a, left, right);
        int i = left;
        int j = right - 1;
        while (i < j) { // i和j交错
            while (a[++i] < pivot) { // 右移i
            }
            while (a[--j] > pivot) { // 左移j
            }
            // 此时i >= pivot, j <= pivot
            if (i < j) {
                swap(a, i, j);
            }
        }
        // 此时 i和j交错或重合， <i的元素必然小于pivot，>i的元素必然大于pivot
        swap(a, i, right - 1);
        if (k <= i) {
            return qSelect(a, k, left, i - 1);
        } else if (k > i + 1) {
            return qSelect(a, k, i + 1, right);
        } else {
            return a[i];
        }
    }
}

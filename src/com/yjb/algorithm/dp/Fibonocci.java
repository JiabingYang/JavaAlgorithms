package com.yjb.algorithm.dp;

import java.util.ArrayList;
import java.util.List;

public class Fibonocci {

    public static void main(String[] args) {
        List<Integer> fibs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            fibs.add(fib((i)));
        }
        System.out.println(fibs);
    }

    private static int fib(int n) {
        if (n < 2) {
            return 1;
        }
        int last = 1;
        int nextToLast = 1;
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = last + nextToLast;
            nextToLast = last;
            last = result;
        }
        return result;
    }
}

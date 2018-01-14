package com.yjb.algorithm.math;

public class Pow {

    private static long powOLogN(long x, int n) {
        if (n == 0)
            return 1;
        if (n == 1)
            return x;
        if (n % 2 == 0)//n is even
            return powOLogN(x * x, n / 2);
        else
            return powOLogN(x * x, n / 2) * x;
    }
}

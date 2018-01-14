package com.yjb.algorithm.math;

public class Gcd {

    public static int gcdOLogN(int m, int n) {
        int rem;
        while (n > 0) {
            rem = m % n;
            m = n;
            n = rem;
        }
        return m;
    }
}

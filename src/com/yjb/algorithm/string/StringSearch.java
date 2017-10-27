package com.yjb.algorithm.string;

public class StringSearch {

    public static int bruteForce(char[] s, char[] p) {
        int i = 0;
        int j = 0;
        while (i < s.length && j < p.length) {
            if (s[i] == p[j]) {
                i++;
                j++;
                continue;
            }
            i = i - j + 1;
            j = 0;
        }
        return (j == p.length) ? i - j : -1;
    }

    public static int kmp(char[] s, char[] p) {
        int i = 0;
        int j = 0;
        int[] next = getNextArray(p);
        while (i < s.length && j < p.length) {
            if (j == -1 || s[i] == p[j]) {
                i++;
                j++;
                continue;
            }
            j = next[j];
        }
        return (j == p.length) ? i - j : -1;
    }

    public static int[] getNextArray(char[] p) {
        int[] next = new int[p.length];
        int j = 0;
        int k = -1;
        next[0] = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                ++k;
                ++j;
//                next[j] = k;
                next[j] = (p[j] != p[k]) ? k : next[k];
                continue;
            }
            k = next[k];
        }
        return next;
    }
}

package com.yjb.algorithm.string;

import com.yjb.util.Printer;
import com.yjb.util.Timer;

public class StringSearch {

    public static void main(String[] args) {
        char[] s = "KMP的匹配是从模式串的开头开始匹配的，而1977年，德克萨斯大学的Robert S. Boyer教授和J Strother Moore教授发明了一种新的字符串匹配算法：Boyer-Moore算法，简称BM算法。该算法从模式串的尾部开始匹配，且拥有在最坏情况下O(N)的时间复杂度。在实践中，比KMP算法的实际效能高".toCharArray();
        char[] p = "效能".toCharArray();
        Timer.printlnNano("bruteForce costs", () -> Printer.println("bruteForce returns", String.valueOf(bruteForce(s, p))));
        Timer.printlnNano("kmp costs", () -> Printer.println("kmp returns", String.valueOf(kmp(s, p))));
    }

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

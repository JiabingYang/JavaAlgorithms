package com.yjb.algorithm.math;

import com.yjb.util.Printer;

public class RelativelyPrimePair {

    private static double getPercentageOfRelativelyPrimePairsLessEqualNON2LogN(int n) {
        int rel = 0, tot = 0;
        int i, j;
        for (i = 0; i <= n; i++) {
            for (j = i + 1; j <= n; j++) {
                tot++;
                if (Gcd.gcdOLogN(i, j) == 1)
                    rel++;
            }
        }
        double result = ((double) rel) / tot;
        Printer.println("Percentage of relatively prime pairs is ", String.valueOf(result));
        return result;
    }
}

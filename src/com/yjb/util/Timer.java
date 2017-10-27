package com.yjb.util;

public class Timer {

    public static long nano(Action0 action0) {
        long start = System.nanoTime();
        action0.call();
        return System.nanoTime() - start;
    }

    public static long milli(Action0 action0) {
        long start = System.currentTimeMillis();
        action0.call();
        return System.currentTimeMillis() - start;
    }

    public static long printlnNano(String tag, Action0 action0) {
        long result = nano(action0);
        Printer.println(tag, String.valueOf(result));
        return result;
    }

    public static long printlnMilli(String tag, Action0 action0) {
        long result = milli(action0);
        Printer.println(tag, String.valueOf(result));
        return result;
    }

}

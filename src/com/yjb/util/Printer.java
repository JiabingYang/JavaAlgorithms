package com.yjb.util;

public class Printer {

    public static void println() {
        System.out.println();
    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public static void println(String tag, String msg) {
        System.out.println(tag + ": " + msg);
    }

    public static void print(String msg) {
        System.out.print(msg);
    }

    public static void print(String tag, String msg) {
        System.out.print(tag + ": " + msg);
    }
}

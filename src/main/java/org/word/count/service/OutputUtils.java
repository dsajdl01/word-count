package org.word.count.service;

public class OutputUtils
{
    public static void errorMsg(String msg)
    {
        print("\033[1;31mError: %s\033[m", msg);
    }

    public static void infoMsg(String msg)
    {
        print(msg);
    }

    public static void print(String msg, Object... obj){
        System.out.printf(msg + "%n", obj);
    }
}

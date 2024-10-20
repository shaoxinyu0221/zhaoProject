package com.zhao.zhaoproject.utils;


public class ValidateUtil {


    public static boolean isPositiveInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // 正整数的正则表达式
        String regex = "^\\d+$";
        return str.matches(regex) && !str.equals("0");
    }


}

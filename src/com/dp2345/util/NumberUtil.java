package com.dp2345.util;

public class NumberUtil {

    /**
     * 获取N位随机整数
     * 
     * @param count
     *            要获取的位数
     * @return
     */
    public static int getRandomNumber(int count) {

        return (int) ((Math.random() * 9 + 1) * Math.pow(10, count - 1));
    }

}

package com.iyeed.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DAVID on 2017/12/5.
 */
public class MathUtil {
    /**
     * 判断字符是否为正整数
     * @param num
     * @return
     */
    public static boolean isPositiveInteger(String num){
        boolean flag = false;
        if (num == null || num.equals("")) {
            return flag;
        }
        try{
            String regex = "^[1-9]+[0-9]*$";
            //^[1-9]+\\d*$
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(num);
            if (m.find()) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}

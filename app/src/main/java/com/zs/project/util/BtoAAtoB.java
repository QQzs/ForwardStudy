package com.zs.project.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zs
 * Date：2018年 05月 23日
 * Time：17:01
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class BtoAAtoB {

    private static String base64hash = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static boolean isMatcher(String inStr, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(inStr);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * btoa method
     *
     * @param inStr
     * @return
     */
    public static String btoa(String inStr) {

        if (inStr == null || isMatcher(inStr, "([^\\u0000-\\u00ff])")) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        int i = 0;
        int mod = 0;
        int ascii;
        int prev = 0;
        while (i < inStr.length()) {
            ascii = inStr.charAt(i);
            mod = i % 3;

            switch (mod) {
                case 0:
                    result.append(String.valueOf(base64hash.charAt(ascii >> 2)));
                    break;
                case 1:


                    result.append(String.valueOf(base64hash.charAt((prev & 3) << 4 | (ascii >> 4))));
                    break;
                case 2:
                    result.append(String.valueOf(base64hash.charAt((prev & 0x0f) << 2 | (ascii >> 6))));
                    result.append(String.valueOf(base64hash.charAt(ascii & 0x3f)));
                    break;
            }

            prev = ascii;
            i++;
        }

        if (mod == 0) {
            result.append(String.valueOf(base64hash.charAt((prev & 3) << 4)));
            result.append("==");
        } else if (mod == 1) {
            result.append(String.valueOf(base64hash.charAt((prev & 0x0f) << 2)));
            result.append("=");
        }

        return result.toString();
    }

    /**
     * // atob method
     * // 逆转encode的思路即可
     *
     * @param inStr
     * @return
     */
    public static String atob(String inStr) {
        if (inStr == null) return null;
        //s = s.replace(/\s|=/g, '');
        inStr = inStr.replaceAll("\\s|=", "");
        StringBuilder result = new StringBuilder();

        int cur;
        int prev = -1;
//        Integer prev=null;
        int mod;
        int i = 0;

        while (i < inStr.length()) {
            cur = base64hash.indexOf(inStr.charAt(i));
            mod = i % 4;
            switch (mod) {
                case 0:
                    break;
                case 1:
                    result.append(String.valueOf((char) (prev << 2 | cur >> 4)));
                    break;
                case 2:


                    result.append(String.valueOf((char) ((prev & 0x0f) << 4 | cur >> 2)));
                    break;
                case 3:


                    result.append(String.valueOf((char) ((prev & 3) << 6 | cur)));
                    break;
            }


            prev = cur;
            i++;
        }

        return result.toString();
    }


}

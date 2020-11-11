package com.woniu.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {

    public static String join(String sourceStr, String nextStr, final String separator) {
        if (sourceStr == null || sourceStr.length() == 0) {
            return nextStr;
        } else if (nextStr == null || nextStr.length() == 0) {
            return sourceStr;
        } else {
            return sourceStr + separator + nextStr;
        }
    }

    /**
     * 比较版本大小
     * <p>
     * 说明：支n位基础版本号+1位子版本号
     * 示例：1.0.2>1.0.1 , 1.0.1.1>1.0.1
     *
     * @param version1 版本1
     * @param version2 版本2
     * @return 0:相同 1:version1大于version2 -1:version1小于version2
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0; //版本相同
        }
        String[] v1Array = version1.split("\\.");
        String[] v2Array = version2.split("\\.");
        int v1Len = v1Array.length;
        int v2Len = v2Array.length;
        int baseLen = 0; //基础版本号位数（取长度小的）
        if (v1Len > v2Len) {
            baseLen = v2Len;
        } else {
            baseLen = v1Len;
        }

        for (int i = 0; i < baseLen; i++) { //基础版本号比较
            if (v1Array[i].equals(v2Array[i])) { //同位版本号相同
                continue; //比较下一位
            } else {
                return Integer.parseInt(v1Array[i]) > Integer.parseInt(v2Array[i]) ? 1 : -1;
            }
        }
        //基础版本相同，再比较子版本号
        if (v1Len != v2Len) {
            return v1Len > v2Len ? 1 : -1;
        } else {
            //基础版本相同，无子版本号
            return 0;
        }
    }

    /**
     * 身份证号校验 （支持18位）
     */
    public static boolean isIdCard(String identityCode) {
        if (!identityCode.matches("\\d{17}(\\d|x|X)$")) {
            return false;
        }
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        int year = Integer.parseInt(df.format(d));
        if (Integer.parseInt(identityCode.substring(6, 10)) < 1900 || Integer.parseInt(identityCode.substring(6, 10)) > year) {// 7-10位是出生年份，范围应该在1900-当前年份之间
            return false;
        }
        if (Integer.parseInt(identityCode.substring(10, 12)) < 1 || Integer.parseInt(identityCode.substring(10, 12)) > 12) {// 11-12位代表出生月份，范围应该在01-12之间
            return false;
        }
        if (Integer.parseInt(identityCode.substring(12, 14)) < 1 || Integer.parseInt(identityCode.substring(12, 14)) > 31) {// 13-14位是出生日期，范围应该在01-31之间
            return false;
        }
        return true;
    }

    private static Pattern numberPattern = Pattern.compile("-?[0-9]+.?[0-9]*");

    public static boolean isNumber(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        return numberPattern.matcher(str).matches();
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String underlineToCamelhump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String camelhumpToUnderline(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}

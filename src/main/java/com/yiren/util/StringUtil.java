package com.yiren.util;

public class StringUtil {
	/**
     * 去除前后指定字符
     * @param source 目标字符串
     * @param beTrim 要删除的指定字符
     * @return 删除之后的字符串
     * 调用示例：System.out.println(trim(", ashuh  ",","));
     */
public static String trim(String source, String beTrim) {
    if(source==null){
        return "";
    }
    source = source.trim(); // 循环去掉字符串首的beTrim字符
    if(source.isEmpty()){
        return "";
    }
    String beginChar = source.substring(0, 1);
    if (beginChar.equalsIgnoreCase(beTrim)) {
        source = source.substring(1, source.length());
        beginChar = source.substring(0, 1);
    }
    // 循环去掉字符串尾的beTrim字符
    String endChar = source.substring(source.length() - 1, source.length());
    if (endChar.equalsIgnoreCase(beTrim)) {
        source = source.substring(0, source.length() - 1);
        endChar = source.substring(source.length() - 1, source.length());
    }
    return source;
}
}

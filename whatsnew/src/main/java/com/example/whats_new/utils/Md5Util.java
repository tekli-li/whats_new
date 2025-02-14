package com.example.whats_new.utils;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5加密工具类
 */
public class Md5Util {
    /**
     * 字符串加密方法
     * @param source：待加密的原始字符串
     * @return String：解密后的MD5摘要字符串
     */
    public static String md5Digest(String source){
        return DigestUtils.md5Hex(source);
    }

    /**
     * 字符串加盐混淆+MD5加密方法
     * @param source：原始字符串
     * @param salt:盐值
     * @return String：混淆后并且MD5处理后的字符串
     */
    public static String md5Digest(String source,Integer salt){
        //首先，对字符串进行混淆：这儿采用的策略是加盐混淆。
        char[] ca = source.toCharArray();//得到原始字符串的字符数组
        for(int i = 0; i < ca.length; i++){
            ca[i] = (char)(ca[i] + salt); //背后的基础是：字符和整数可以互相转换；
        }
        String target = new String(ca);
        //然后，对加盐后的字符串再MD5加密
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }
}
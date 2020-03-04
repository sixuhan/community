package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    //生成随机字符串
    public static  String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
        //去掉横线,只要字母和数字
    }

    //md5加密,被盗后不容易泄露
    //md5加密后不能解密，但是相同的字符串经过md5加密的结果是一样的
    //因此引入salt(随机字符)
    //密码存储方式为 用户输入密码+salt
    public  static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());//将传入的结果加密成十六进制的字符串返回
    }
}

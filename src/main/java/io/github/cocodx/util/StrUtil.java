package io.github.cocodx.util;

import java.util.Random;

/**
 * @author amazfit
 * @date 2022-08-28 上午6:58
 **/
public class StrUtil {

    public static String randomStr(int size){
        String str = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            char c = str.charAt(random.nextInt(str.length()-1));
            buffer.append(c);
        }
        return buffer.toString();
    }

    public static String likeStr(String str){
        return "'%"+str+"%'";
    }
}

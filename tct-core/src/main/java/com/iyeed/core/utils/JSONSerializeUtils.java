package com.iyeed.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Junhua.Zhang on 2016/8/8.
 */
public class JSONSerializeUtils {
    private static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz == String.class
                || clazz == Long.class
                || clazz == Integer.class
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Float.class
                || clazz == Double.class
                || clazz == BigInteger.class
                || clazz == BigDecimal.class
                || clazz.isEnum();
    }

    /**
     * 序列化
     */
    @org.jetbrains.annotations.Contract("null -> null")
    public static String serialize(Object obj) {
        if(obj == null){
            return null;
        }
        if(isPrimitive(obj.getClass())){
            return obj.toString();
        }
        return JSON.toJSONString(obj);
    }

    /**
     * 反序列化
     */
    @Nullable
    public static <T> T deserialize(String str, Type type) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        if (type == Integer.class) {
            return (T) Integer.valueOf(str);
        }
        if (type == Long.class) {
            return (T) Long.valueOf(str);
        }
        if (type == String.class) {
            return (T) str;
        }
        if (type == Character.class || type == char.class) {
            char c = str.toCharArray()[0];
            Object o = c;
            return (T) o;
        }
        return JSON.parseObject(str, type, Feature.SortFeidFastMatch);
    }

}

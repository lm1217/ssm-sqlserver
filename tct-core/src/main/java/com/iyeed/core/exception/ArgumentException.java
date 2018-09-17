package com.iyeed.core.exception;


/**
 * 参数错误，方法调用的入参不符合要求
 *                       
 * @Filename: ArgumentException.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class ArgumentException extends BusinessException{

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5961619353798906031L;

    public ArgumentException(String message) {
        super(message);
    }

}

package com.iyeed.core.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * 
 */
public final class Identities {
	private static AtomicInteger id = new AtomicInteger(0);
	private Identities() {
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成,中间有-分割
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成,中间无-分割
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static long getId() {
		return ((1 * 1L & 0xFFFF) << 48) | ((System.currentTimeMillis() / 1000L & 0xFFFFFFFF) << 16) | (id.addAndGet(1) & 0xFFFF);
	}

	public static void main(String[] args) {
		System.out.println("uuid = [" + uuid() + "]");
		System.out.println("uuid2 = [" + uuid2() + "]");
		System.out.println("id = [" + getId() + "]");
	}
}

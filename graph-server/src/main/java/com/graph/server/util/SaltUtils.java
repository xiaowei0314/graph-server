package com.graph.server.util;

import java.security.SecureRandom;

/**
 * 盐生成器
 */
public class SaltUtils {

	//assicc码表的空格
	private static final Integer FROM_INDEX = 33;

	//生成的数据区间
	private static final Integer RANGE = 93;

	//默认生成的长度
	public static final Integer DEFAULT_SALT_LENGTH = 16;


	public static String generateSalt() { return generateSalt(DEFAULT_SALT_LENGTH); }

	public static String generateSalt(int length) {

		if (length < 1) {
			length = DEFAULT_SALT_LENGTH;
		}
		char[] list = new char[length];

		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			int rInt = random.nextInt(RANGE) + FROM_INDEX;
			list[i] = (char) rInt;
		}
		return new String(list);
	}

	
	public static void main(String[] args) {
		System.out.println(generateSalt(4));
	}
}

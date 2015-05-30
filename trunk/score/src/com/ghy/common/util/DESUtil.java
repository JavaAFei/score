package com.ghy.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtil {
	private static final String PASSWORD_CRYPT_KEY = "_B2B_CRYPT_KEY_RANDOM_";
	private final static String DES = "DES";

	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}

	/**
	 * BASE64加密
	 * 
	 * @param src
	 * @return
	 */
	public static String BASE64Encoder(byte[] src) {
		return new BASE64Encoder().encode(src);
	}

	/**
	 * BASE64解密
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static byte[] BASE64Dncoder(String src) throws Exception {
		return new BASE64Decoder().decodeBuffer(src);
	}

	public static String jiaM(String src) throws Exception {
		byte[] s = encrypt(src.getBytes(), PASSWORD_CRYPT_KEY.getBytes());
		return BASE64Encoder(s);
	}

	public static String jieM(String src) throws Exception {
		return new String(decrypt(BASE64Dncoder(src), PASSWORD_CRYPT_KEY.getBytes()));
	}

	public static void main(String[] arg) {
		try {
			String temp = "000000";
			String jiaMStr = jiaM(temp);
			System.out.println(jiaMStr);
			System.out.println(jieM(jiaMStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

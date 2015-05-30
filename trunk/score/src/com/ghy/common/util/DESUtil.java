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
	 * ����
	 * 
	 * @param src
	 *            ����Դ
	 * @param key
	 *            ��Կ�����ȱ�����8�ı���
	 * @return ���ؼ��ܺ������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES�㷨Ҫ����һ�������ε������Դ
		SecureRandom sr = new SecureRandom();
		// ��ԭʼ�ܳ����ݴ���DESKeySpec����
		DESKeySpec dks = new DESKeySpec(key);
		// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����
		// һ��SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher����ʵ����ɼ��ܲ���
		Cipher cipher = Cipher.getInstance(DES);
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// ���ڣ���ȡ���ݲ�����
		// ��ʽִ�м��ܲ���
		return cipher.doFinal(src);
	}

	/**
	 * ����
	 * 
	 * @param src
	 *            ����Դ
	 * @param key
	 *            ��Կ�����ȱ�����8�ı���
	 * @return ���ؽ��ܺ��ԭʼ����
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		// DES�㷨Ҫ����һ�������ε������Դ
		SecureRandom sr = new SecureRandom();
		// ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
		DESKeySpec dks = new DESKeySpec(key);
		// ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����
		// һ��SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher����ʵ����ɽ��ܲ���
		Cipher cipher = Cipher.getInstance(DES);
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		// ���ڣ���ȡ���ݲ�����
		// ��ʽִ�н��ܲ���
		return cipher.doFinal(src);
	}

	/**
	 * BASE64����
	 * 
	 * @param src
	 * @return
	 */
	public static String BASE64Encoder(byte[] src) {
		return new BASE64Encoder().encode(src);
	}

	/**
	 * BASE64����
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

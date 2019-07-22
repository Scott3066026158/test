package com.kunyi.bitamexJava.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class QEncodeUtil {


	/**
	 * 将byte[]转为各种进制的字符串
	 * 
	 * @param bytes
	 *            byte[]
	 * @param radix
	 *            可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String Binary(byte[] bytes, int radix)
	{
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}

	/**
	 * base 64 encode
	 * 
	 * @param bytes
	 *            待编码的byte[]
	 * @return 编码后的base 64 code
	 */
	private static String Base64Encode(byte[] bytes)
	{
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * base 64 decode
	 * 
	 * @param base64Code
	 *            待解码的base 64 code
	 * @return 解码后的byte[]
	 * @throws Exception
	 */
	private static byte[] Base64Decode(String base64Code) throws Exception
	{
		return base64Code.length() <= 0 ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}

	/**
	 * 获取byte[]的md5值
	 * 
	 * @param bytes
	 *            byte[]
	 * @return md5
	 * @throws Exception
	 */
	private static byte[] Md5(byte[] bytes) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(bytes);
		return md.digest();
	}

	/**
	 * 获取字符串md5值
	 * 
	 * @param msg
	 * @return md5
	 * @throws Exception
	 */
	private static byte[] Md5(String msg)
	{
		try
		{
			return msg.length() <= 0 ? null : Md5(msg.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 结合base64实现md5加密
	 * 
	 * @param msg
	 *            待加密字符串
	 * @return 获取md5后转为base64
	 * @throws Exception
	 */
	public static String Md5Encrypt(String msg)
	{
		try
		{
			return msg.length() <= 0 ? null : Base64Encode(Md5(msg));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	public static byte[] AesEncryptToBytes(String content, String encryptKey) throws Exception
	{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(encryptKey.getBytes()));

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

		return cipher.doFinal(content.getBytes("utf-8"));
	}

	/**
	 * AES加密为base 64 code
	 * 
	 * @param content
	 *            待加密的内容
	 * @param encryptKey
	 *            加密密钥
	 * @return 加密后的base 64 code
	 * @throws Exception
	 */
	public static String AesEncrypt(String content)
	{
		try
		{
			// 加密密钥“已写死”;
			String encryptKey = "000000";
			return Base64Encode(AesEncryptToBytes(content, encryptKey));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @return
	 * @throws Exception
	 */
	public static String AesDecryptByBytes(byte[] encryptBytes)
	{
		try
		{
			String decryptKey = "000000";
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(decryptKey.getBytes()));
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
			byte[] decryptBytes = cipher.doFinal(encryptBytes);
			return new String(decryptBytes);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 将base 64 code AES解密
	 * 
	 * @param encryptStr
	 *            待解密的base 64 code
	 * @return
	 * @throws Exception
	 */
	public static String AesDecrypt(String encryptStr)
	{
		String result = "";
		try
		{
			result = encryptStr.length() <= 0 ? null : AesDecryptByBytes(Base64Decode(encryptStr));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
}

package com.hongrant.www.achieve.comm.util;

import java.security.MessageDigest;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@SuppressWarnings("restriction")
@Component
@ConfigurationProperties(prefix="encrypt")
public class EncryptUtils {
	
    /**
     * 根据参数生成Key;
     *
     * @param strKey
     */
    static String algorithm = "DESede";
    
    static String key = "abcdefghijklmnopqrstuvwx";

    public static void setKey(String key) {
		EncryptUtils.key = key;
	}

	static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }

    private static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try { // 生成密钥-+
            SecretKey deskey = new SecretKeySpec(keybyte, algorithm); // 加密
            Cipher c1 = Cipher.getInstance(algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return src;
    }

    // 解密字符串
    private static byte[] decryptMode(byte[] keybyte, byte[] src) throws Exception {
        SecretKey deskey = new SecretKeySpec(keybyte, algorithm); // 解密
        Cipher c1 = Cipher.getInstance(algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    public static String encrypt(String msg) {
        if (StringUtils.isBlank(msg)) return "";
        try {
            byte[] encoded = encryptMode(key.getBytes(), msg.getBytes("utf-8"));
            return Base64.getEncoder().encodeToString(encoded);
        } catch (Exception e) {
        }
        return msg;
    }

    public static String desEncrypt(String encryptMsg) {
        if (StringUtils.isBlank(encryptMsg)) return "";
        try {
            byte[] srcByte = Base64.getDecoder().decode(encryptMsg);
            byte[] encoded = decryptMode(key.getBytes(), srcByte);
            return new String(encoded, "utf-8");
        } catch (Exception e) {
        }
        return encryptMsg;
    }
    
    
	public static String MD5(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			return toHex(bytes).toLowerCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String toHex(byte[] bytes) {

		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
	
	public static String BASE64Encode(String s) {
		return Base64.getEncoder().encodeToString(s.getBytes());
	}
	
	public static String BASE64Decode(String s) {
		return new String(Base64.getDecoder().decode(s));
	}

    public static void main(String[] args) {
    	// 添加新安全算法,如果用JCE就要把它添加进去
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
//        final byte[] keyBytes = key.getBytes();    //8字节的密钥
        String szSrc = "11";
        //System.out.println("加密前的字符串:" + szSrc);
//        byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());
//        //System.out.println("加密后的字符串:" + new BASE64Encoder().encode(encoded));
        String encoded = encrypt(szSrc);
        //System.out.println("加密后的字符串:" + encoded);
        //System.out.println("md5 : " + MD5(szSrc));
        try {
            String des = "cWDFQyu9tC4=";
            //System.out.println("解密后的字符串:" + EncryptUtils.desEncrypt(des));
            
            String sss = "123456";
            String base64 = BASE64Encode(sss);
            //System.out.println(base64);
            //System.out.println(BASE64Decode(base64));
            
            
        } catch (Exception e) {

        }
    }
}

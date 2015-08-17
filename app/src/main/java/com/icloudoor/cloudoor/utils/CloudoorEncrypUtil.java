package com.icloudoor.cloudoor.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 信息加密工具类
 * Created by Derrick Guan on 8/7/15.
 */
public class CloudoorEncrypUtil {

    private static final String SHA_512 = "SHA-512";
    private static final String MD5 = "MD5";

    /**
     * 使用SHA-512加密算法对密码进行加密
     *
     * @param password 密码
     * @return String
     */
    public String encodePassword(String password) {
        return encodePassword(password, SHA_512);
    }

    /**
     * 使用指定的加密算法对密码进行加密
     *
     * @param password 密码
     * @return String
     */
    public String encodePassword(String password, String algorithm) {
        String encode = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(password.getBytes("UTF-8"));
            encode = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     * 二进制数组转换成十六进制字串
     *
     * @param byteArray byteArray
     * @return String
     */
    private String bytesToHexString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        String tmp;
        for (byte aByteArray : byteArray) {
             tmp = (Integer.toHexString(aByteArray & 0XFF));
            if (tmp.length() == 1)
                sb.append("0").append(tmp);
            else
                sb.append(tmp);
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 十六进制字串转换成二进制数组
     *
     * @param hexString hexString
     * @return byte[]
     */
    private byte[] hexStringToByts(String hexString) {
        if (hexString.length() < 1)
            return null;
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length() / 2; i++) {
            int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 获取字串MD5
     *
     * @param src 字串
     * @return String
     * @throws Exception
     */
    public String getMD5Digest(String src) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(MD5);
        messageDigest.update(src.getBytes());
        byte[] digest = messageDigest.digest();
        return bytesToHexString(digest);
    }
}

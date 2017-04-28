package com.lib.common.coder;

import android.util.Base64;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author allen
 * @since 2016-12-10
 */
public final class AESCoder {

    private static final String KEY = "431f1f909f8f75f279f7f1c85de0dcb3";
    //AESCoder-ObjC uses blank IV (not the best security, but the aim here is compatibility)
    private static final byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private static final String AESTYPE = "AES/CBC/PKCS5Padding";

    /**
     * @param content  需要加密的内容
     * @param key 加密的秘钥
     * @return 加密后的内容
     */
    public static String encrypt(String content, String key) {
        try {
            byte[] textBytes = content.getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return Base64.encodeToString(cipher.doFinal(textBytes), 0);
        } catch (Exception e) {
            return null;
        }

    }

    public static String decrypt(String password, String encryptData) {
        try {
            byte[] textBytes = Base64.decode(encryptData, 0);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return new String(cipher.doFinal(textBytes), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }




}

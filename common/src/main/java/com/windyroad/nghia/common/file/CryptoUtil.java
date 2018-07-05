package com.windyroad.nghia.common.file;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Imark-N on 12/19/2015.
 * Encryption file
 */
public class CryptoUtil {
    private static final String AES_ALGORITHM = "AES";
    private static final String DES_ALGORITHM = "DES";

    private static final int KEY_SIZE_128 = 128;
    private static final int KEY_SIZE_64 = 64;


    /**
     * Encryption AES
     * Example: byte[] toBytes = CryptoUtil.encryptAES(password, data.getBytes("UTF-8"));
     * Log.d("nghia", Base64.encodeToString(toBytes, Base64.DEFAULT));
     * @param password password key
     * @param inputBytes input data
     * @return output data
     * @throws Exception
     */
    public static byte[] encryptAES(String password, byte[] inputBytes) throws Exception {
        return doCryptoAES(Cipher.ENCRYPT_MODE, password, inputBytes);
    }

    /**
     * Decryption AES
     * Example: byte[] toBytes2 = CryptoUtil.decryptAES(password, toBytes);
     * Log.d("nghia", new String(toBytes2, "UTF-8"));
     * @param password password key
     * @param inputBytes input data
     * @return output data
     * @throws Exception
     */
    public static byte[] decryptAES(String password, byte[] inputBytes) throws Exception {
        return doCryptoAES(Cipher.DECRYPT_MODE, password, inputBytes);
    }

    /**
     * Generate Key
     * @param password
     * @return Key
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static Key generateKey(String password, String algorithm, int keySize) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] byteKey = password.getBytes("UTF-8");
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(byteKey);
        keyGenerator.init(keySize, sr);
        return keyGenerator.generateKey();
    }

    /**
     * Encryption type: Advanced Encryption Standard
     * Default AES encryption: AES/ECB/PKCS5Padding. Less Security
     * @param cipherMode Cipher.ENCRYPT_MODE/ Cipher.DECRYPT_MODE
     * @param password password key
     * @param inputBytes input data
     * @return output data
     * @throws Exception
     */
    public static byte[] doCryptoAES(int cipherMode, String password, byte[] inputBytes) throws Exception {
        // gen password 128
        Key passwordKey = generateKey(password, AES_ALGORITHM, KEY_SIZE_128);

        // init Cipher
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipherMode, passwordKey);

        // Crypto final
        return cipher.doFinal(inputBytes);
    }

    /**
     * get fix byte array size, follow string
     * @param strData
     * @param byteLength
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] getFixByteArray(String strData, int byteLength) throws UnsupportedEncodingException {
        byte[] dst = new byte[byteLength];
        byte[] src = strData.getBytes("utf-8");

        int length = dst.length > src.length ? src.length : dst.length;  // lấy độ dài nhỏ hơn
        for (int i = 0; i < length; i++) {
            dst[i] = src[i];
        }

        return dst;
    }

    /**
     * Encryption type: Advanced Encryption Standard
     * CBC Mode:
     * PKCS5Padding:
     * Example: byte[] toBytes = CryptoUtil.doCryptoAES_ECB_PKCS5Padding(Cipher.ENCRYPT_MODE, password1, data.getBytes("UTF-8"));
     * @param cipherMode Cipher.ENCRYPT_MODE/ Cipher.DECRYPT_MODE
     * @param password1 password1 key
     * @param inputBytes input data
     * @return output data
     * @throws Exception
     */
    public static byte[] doCryptoAES_CBC_PKCS5Padding(int cipherMode, String password1, String password2, byte[] inputBytes) throws Exception {
        // gen password1 128
        Key passwordKey = generateKey(password1, AES_ALGORITHM, KEY_SIZE_128);

        // init Cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // get password 2 to byte[16]
        byte[] bytesPassword2 = password2.getBytes("UTF-8");
        if (bytesPassword2.length != 16) {
            bytesPassword2 = getFixByteArray(password2, 16);
        }
        IvParameterSpec ivSpec = new IvParameterSpec(bytesPassword2);  // requite byte[16]
        cipher.init(cipherMode, passwordKey, ivSpec);

        // Crypto final
        return cipher.doFinal(inputBytes);
    }

    /**
     * Encryption type: Data Encryption Standard
     * Example: byte[] toBytes = CryptoUtil.doCryptoDES(Cipher.ENCRYPT_MODE, password, data.getBytes("UTF-8"));
     * @param cipherMode Cipher.ENCRYPT_MODE/ Cipher.DECRYPT_MODE
     * @param password password key
     * @param inputBytes input data
     * @return output data
     * @throws Exception
     */
    public static byte[] doCryptoDES(int cipherMode, String password, byte[] inputBytes) throws Exception {
        // gen password
        Key passwordKey = generateKey(password, DES_ALGORITHM, KEY_SIZE_64);

        // init Cipher
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(cipherMode, passwordKey);

        // doing
        return cipher.doFinal(inputBytes);
    }
}

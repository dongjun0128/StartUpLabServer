package com.startuplab.common.secret;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypto {
  private static final String TRANSFORM = "AES/ECB/PKCS5Padding";

  public static Key generateKey(String algorithm, byte[] keyData) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
    String upper = algorithm.toUpperCase();
    if ("DES".equals(upper)) {
      KeySpec keySpec = new DESKeySpec(keyData);
      SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
      SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
      return secretKey;
    } else if ("DESede".equals(upper) || "TripleDES".equals(upper)) {
      KeySpec keySpec = new DESedeKeySpec(keyData);
      SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
      SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
      return secretKey;
    } else {
      SecretKeySpec keySpec = new SecretKeySpec(keyData, algorithm);
      return keySpec;
    }
  }

  public static Key getKey(String password) throws Exception {
    Key secureKey = new SecretKeySpec(password.getBytes("utf-8"), "AES");
    return secureKey;
  }

  public static String encrypt(String plainText, String key) throws Exception {
    Cipher cipher = Cipher.getInstance(TRANSFORM);
    byte[] keyData = Arrays.copyOf(key.getBytes(), 16);
    cipher.init(Cipher.ENCRYPT_MODE, generateKey("AES", keyData));
    byte[] encrypted = cipher.doFinal(plainText.getBytes("utf-8"));
    // return Base64.encodeBase64String(encrypted);
    return ByteUtils.toHexString(encrypted);
  }

  public static String decrypt(String cipherText, String key) throws Exception {
    Cipher cipher = Cipher.getInstance(TRANSFORM);
    byte[] keyData = Arrays.copyOf(key.getBytes(), 16);
    cipher.init(Cipher.DECRYPT_MODE, generateKey("AES", keyData));
    // byte[] original = cipher.doFinal(Base64.decodeBase64(cipherText));
    byte[] original = cipher.doFinal(ByteUtils.toBytesFromHexString(cipherText));
    String originalString = new String(original);
    return originalString;
  }
  // javascript encode decode sample
  // <script
  // src="//cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
  // <script type="text/javascript">
  // var message =
  // '{"manager_id":1,"manager_group_id":1,"expire_date":"20190411145616"}';
  // var key = "VSsYGFRW51i58Ee7";
  // var options = {};
  // options.mode = CryptoJS.mode.ECB;
  // options.padding = CryptoJS.pad.Pkcs7;
  //
  // function encrypt(message, key) {
  // var keyHex = CryptoJS.enc.Utf8.parse(key);
  // var encrypted = CryptoJS.AES.encrypt(message, keyHex, options).toString();
  // var e64 = CryptoJS.enc.Base64.parse(encrypted);
  // var eHex = e64.toString(CryptoJS.enc.Hex);
  // return eHex;
  // }
  // function decrypt(ciphertext, key) {
  // var keyHex = CryptoJS.enc.Utf8.parse(key);
  // var target = {};
  // target.ciphertext = CryptoJS.enc.Hex.parse(ciphertext);
  // var decrypted = CryptoJS.AES.decrypt(target, keyHex, options);
  // return decrypted.toString(CryptoJS.enc.Utf8);
  // }
  //
  // var ciphertext = encrypt(message, key);
  // console.info('ciphertext:', ciphertext);
  // console.info('decode :', decrypt(ciphertext, key));
}

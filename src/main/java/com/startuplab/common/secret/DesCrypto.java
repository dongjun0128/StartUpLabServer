package com.startuplab.common.secret;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.binary.Base64;

public class DesCrypto {
  private static final String UNICODE_FORMAT = "UTF8";
  private static final String DES_ENCRYPTION_SCHEME = "DES";

  public SecretKey getKey(String password) throws Exception {
    byte[] keyAsBytes = password.getBytes(UNICODE_FORMAT);
    KeySpec keySpec = new DESKeySpec(keyAsBytes);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ENCRYPTION_SCHEME);
    SecretKey key = keyFactory.generateSecret(keySpec);
    return key;
  }

  public String encrypt(String text, String key) {
    String encrypted = "";
    try {
      Cipher cipher = Cipher.getInstance(DES_ENCRYPTION_SCHEME);
      cipher.init(Cipher.ENCRYPT_MODE, getKey(key));
      byte[] cleartext = text.getBytes(UNICODE_FORMAT);
      byte[] ciphertext = cipher.doFinal(cleartext);
      encrypted = Base64.encodeBase64String(ciphertext);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return encrypted;
  }

  public String decrypt(String cipherText, String key) {
    String decrypt = "";
    try {
      Cipher cipher = Cipher.getInstance(DES_ENCRYPTION_SCHEME);
      cipher.init(Cipher.DECRYPT_MODE, getKey(key));
      byte[] original = cipher.doFinal(Base64.decodeBase64(cipherText));
      decrypt = new String(original);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return decrypt;
  }
}

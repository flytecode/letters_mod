package net.minecraft.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {
   private static final String f_144611_ = "AES";
   private static final int f_144612_ = 128;
   private static final String f_144613_ = "RSA";
   private static final int f_144614_ = 1024;
   private static final String f_144615_ = "ISO_8859_1";
   private static final String f_144616_ = "SHA-1";

   public static SecretKey m_13578_() throws CryptException {
      try {
         KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
         keygenerator.init(128);
         return keygenerator.generateKey();
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   public static KeyPair m_13604_() throws CryptException {
      try {
         KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
         keypairgenerator.initialize(1024);
         return keypairgenerator.generateKeyPair();
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   public static byte[] m_13590_(String p_13591_, PublicKey p_13592_, SecretKey p_13593_) throws CryptException {
      try {
         return m_13602_(p_13591_.getBytes("ISO_8859_1"), p_13593_.getEncoded(), p_13592_.getEncoded());
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   private static byte[] m_13602_(byte[]... p_13603_) throws Exception {
      MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");

      for(byte[] abyte : p_13603_) {
         messagedigest.update(abyte);
      }

      return messagedigest.digest();
   }

   public static PublicKey m_13600_(byte[] p_13601_) throws CryptException {
      try {
         EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(p_13601_);
         KeyFactory keyfactory = KeyFactory.getInstance("RSA");
         return keyfactory.generatePublic(encodedkeyspec);
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   public static SecretKey m_13597_(PrivateKey p_13598_, byte[] p_13599_) throws CryptException {
      byte[] abyte = m_13605_(p_13598_, p_13599_);

      try {
         return new SecretKeySpec(abyte, "AES");
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   public static byte[] m_13594_(Key p_13595_, byte[] p_13596_) throws CryptException {
      return m_13586_(1, p_13595_, p_13596_);
   }

   public static byte[] m_13605_(Key p_13606_, byte[] p_13607_) throws CryptException {
      return m_13586_(2, p_13606_, p_13607_);
   }

   private static byte[] m_13586_(int p_13587_, Key p_13588_, byte[] p_13589_) throws CryptException {
      try {
         return m_13579_(p_13587_, p_13588_.getAlgorithm(), p_13588_).doFinal(p_13589_);
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }

   private static Cipher m_13579_(int p_13580_, String p_13581_, Key p_13582_) throws Exception {
      Cipher cipher = Cipher.getInstance(p_13581_);
      cipher.init(p_13580_, p_13582_);
      return cipher;
   }

   public static Cipher m_13583_(int p_13584_, Key p_13585_) throws CryptException {
      try {
         Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
         cipher.init(p_13584_, p_13585_, new IvParameterSpec(p_13585_.getEncoded()));
         return cipher;
      } catch (Exception exception) {
         throw new CryptException(exception);
      }
   }
}
package com.example.a18.path.number;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption
{
    public static void main1() throws Exception {
        System.out.println();
        System.out.println(encrypt());
        System.out.println();
        String s = desEncrypt();
        for (int i = 0; i < s.length(); i++) {
            char numChar = s.charAt(i);
            int  intNum = (int)numChar;
            System.out.print(intNum);
        }
        System.out.println();
        System.out.println(s);

        System.out.println(System.getProperty("user.home"));
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("java.vendor.url"));

    }

    public static String encrypt() throws Exception {
        try {
            String data = "Test String";
            String key = "1234567812345678";
            String iv = "1234567812345678";

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String desEncrypt() throws Exception {
        try
        {
            String data = "2fbwW9+8vPId2/foafZq6Q==";
            String key = "1234567812345678";
            String iv = "1234567812345678";

            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
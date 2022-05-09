package com.sunc.cwy.util;

import sun.misc.BASE64Encoder;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Md5加密工具类
 */
public class Md5 {

    public Md5() {
    }

    public static String makeMd5(String password) {
        String pwd = "";
        boolean seccess = true;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            pwd = (new BASE64Encoder()).encodeBuffer(md.digest());
        } catch (Exception var7) {
            seccess = false;
            var7.printStackTrace();
        }

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(pwd.getBytes());
            pwd = (new BigInteger(1, sha.digest())).toString(16);
        } catch (Exception var6) {
            seccess = false;
            var6.printStackTrace();
        }

        return seccess ? pwd : password;
    }
}


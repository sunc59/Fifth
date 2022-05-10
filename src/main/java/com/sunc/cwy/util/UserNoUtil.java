package com.sunc.cwy.util;

import java.util.List;

public class UserNoUtil {



    public static String userName2userNo(String userName){

        StringBuilder userNo = new StringBuilder("0");

        for (int i = 0; i < userName.length(); i++) {
            if (Character.isDigit(userName.charAt(i))){
                userNo.append(userName.charAt(i));
            }
        }
        return userNo.toString();
    }

    /*public static void main(String[] args) {
        String test15 = userName2userNo("test15");
        System.out.println(test15);
    }*/
}

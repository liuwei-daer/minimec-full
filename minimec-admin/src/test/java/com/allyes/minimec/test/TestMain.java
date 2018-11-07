package com.allyes.minimec.test;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author liuwei
 * @date 2018-11-07
 */
public class TestMain {
    public static void main(String[] args) {
        String salt = BCrypt.gensalt();
        String password = "123456";
        System.out.println(salt);
        System.out.println(BCrypt.hashpw(password , salt));
    }
}


package com.api.test;

/**
 * @author liuwei
 * @date 2018-03-20
 */
public class TestMain {


    public static void main(String[] args) {


    }


    public static boolean isPrime(int a) {

        boolean flag = true;
        if (a < 2) {
            return false;
        } else {
            for (int i = 2; i <= Math.sqrt(a); i++) {
                if (a % i == 0) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public static  int FindNextPrime(int i) {
        while (true) {
            i++;
            boolean flag = true;
            if (i < 2) {
                break;
            } else {
                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag){
                break;
            }
        }
        return i;
    }


}


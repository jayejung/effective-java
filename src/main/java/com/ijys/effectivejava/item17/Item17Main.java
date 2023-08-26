package com.ijys.effectivejava.item17;

import java.math.BigInteger;

public class Item17Main {
    public static void main(String[] args) {
        BigInteger bInt = new BigInteger("123123123123123");
        System.out.println("BigInteger(\"123123123123123\")'s\nsignum: " + bInt.signum());
        BigInteger bInt2 = bInt.negate();
        System.out.println("BigInteger(\"123123123123123\")'s\nsignum: " + bInt2.signum());
    }
}

package com.ijys.effectivejava.item04;

public class ImposibleInstantiationUtils {
    // 기본 생성자가 만들어지는 것을 막는다(인스턴스 방지용).
    private ImposibleInstantiationUtils() {
        throw new AssertionError();
    }

    public static String someStaticUtil() {
        return "utils return value";
    }
}

package com.ijys.effectivejava.item04;

public class Item04Main {
    public static void main(String[] args) {
        System.out.println(ImposibleInstantiationUtils.someStaticUtil());
        /*
        static method(util)은 사용가능하지만, 인스턴스화 하지 못하게 막았다.
        상속후 인스턴스화 하는 것도 막을 수 있음.
         */
    }
}

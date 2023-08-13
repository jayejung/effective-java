package com.ijys.effectivejava.item15;

public class Item15Main {
    public static void main(String[] args) {
        ChildClass jaye = new ChildClass("jaye", "jaye.jung@kakaoent.com", 12);

        // listkov substitution principle
        ParentClass parentClass = jaye;
        parentClass.introduceYourself();
    }
}

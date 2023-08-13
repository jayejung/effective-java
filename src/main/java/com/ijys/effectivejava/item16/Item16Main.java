package com.ijys.effectivejava.item16;

public class Item16Main {
    public static void main(String[] args) {
        Time time = new Time(2, 30);
        // time.minute = 20; --> public 이지만 불변이어서 compile error
    }
}

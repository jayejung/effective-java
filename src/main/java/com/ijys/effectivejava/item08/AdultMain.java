package com.ijys.effectivejava.item08;

public class AdultMain {
    public static void main(String[] args) throws Exception {
        try (Room myRoom = new Room(7)) {
            System.out.println("안녕~");
        }
    }
}

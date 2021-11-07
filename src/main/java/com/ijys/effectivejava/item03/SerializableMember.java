package com.ijys.effectivejava.item03;

import java.io.Serializable;

public class SerializableMember implements Serializable {
    private String id;
    private String name;
    private int age;

    private static final SerializableMember INSTANCE = new SerializableMember();
    private SerializableMember() {
        this.id = "myId";
        this.name = "myName";
        this.age = 22;
    }

    public static SerializableMember getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "SerializableMember{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

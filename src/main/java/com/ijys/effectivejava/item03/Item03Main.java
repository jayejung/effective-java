package com.ijys.effectivejava.item03;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Item03Main {
    public static void main(String[] args) {
        /*
        public static final member로 싱글톤 객체 전달 받음.
         */
        Elvis01 elvis01 = Elvis01.INSTANCE;
        elvis01.leaveTheBuilding();

        /*
        public static method로 싱글톤 객체 전달 받음.
        해당 객체는 private static final로 선언되어 있음.
         */
        Elvis02 elvis02 = Elvis02.getInstance();
        elvis02.leaveTheBuilding();

        /*
        위와 같은 방식으로 싱글톤이 보장받더라도, serialize -> unserialize를 거치면 새로운 객체가 생성된다.
        serializableMember를 serializing -> unserializing을 해보면 새로운 객체가 생성되어서 싱글톤 보장이 안됨.
         */
        SerializableMember serializableMember = SerializableMember.getInstance();
        SerializableMember serializableMember2 = SerializableMember.getInstance();

        System.out.println("(serializableMember == serializableMember2): " + (serializableMember == serializableMember2));
        System.out.println("serializableMember: " + serializableMember);

        byte[] serializedMember;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializableMember);
            serializedMember = byteArrayOutputStream.toByteArray();

            SerializableMember unSerializedMember;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedMember);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            unSerializedMember = (SerializableMember) objectInputStream.readObject();

            System.out.println("unSerializedMember: " + unSerializedMember);
            System.out.println("(serializableMember == unSerializedMember): " + (serializableMember == unSerializedMember));

        } catch (Exception ex) {
        }

        /*
        열거형 상수로 싱글톤 보장.
         */
        Elvis03 elvis03 = Elvis03.INSTANCE;
        elvis03.leavingTheBuilding();
    }
}

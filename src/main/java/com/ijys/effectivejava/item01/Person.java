package com.ijys.effectivejava.item01;

public class Person {
    private String id;
    private String name;
    private int age;

    public static Person withName(String name) {
        Person person = new Person();
        person.name = name;

        return person;
    }

    public static Person withId(String id) {
        Person person = new Person();
        person.id = id;

        return person;
    }

    public static Person withAge(int age) {
        Person person;
        if (age >= 50) {
            person = new OldPerson();
        }  else {
            person = new YoungPerson();
        }
        person.age = age;

        return person;
    }

    public static Person gameMaster() {
        Person person = new Person();
        person.id = "master";
        person.name = "GM";
        person.age = 99;

        return person;
    }

    public static StrongPerson getStrongPerson() {
        return new StrongPerson(99);
    }

    Person() {
    }

    private Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public static Person ADMIN = new Person("id_admin", "admin", 100);

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    static class StrongPerson extends Person {
        private int powerLevel;

        public StrongPerson(int powerLevel) {
            this.powerLevel = powerLevel;
        }

        @Override
        public String toString() {
            return super.toString() + "\nStrongPerson's power level is " + powerLevel;
        }
    }
}

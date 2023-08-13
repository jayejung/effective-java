package com.ijys.effectivejava.item15;

public class ParentClass {
    private String name;
    private String email;
    private int age;

    public ParentClass() {
    }

    public ParentClass(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    protected void introduceYourself() {
        StringBuffer sb = new StringBuffer()
                .append("My name is ")
                .append(this.name)
                .append(" email is ")
                .append(this.email);
        System.out.println(sb.toString());
    }
}

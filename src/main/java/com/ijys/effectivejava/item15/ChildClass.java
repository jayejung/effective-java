package com.ijys.effectivejava.item15;

public class ChildClass extends ParentClass {
    public ChildClass() {
    }

    public ChildClass(String name, String email, int age) {
        super(name, email, age);
    }

    // 상속 받은 인스턴스의 method는 노출 범위를 줄일 수 없다.
    // private이 될 수 없음.
    // private이 되면 상위클래스를 하위클래스로 치환이 안됨. (Liskov Substitution Principle 위배됨)
    @Override
    protected void introduceYourself() {
        super.introduceYourself();
    }
}

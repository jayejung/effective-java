package com.ijys.effectivejava.item01;

public class Item01Main {
    public static void main(String[] args) {
        /*
         * "게임마스터" 라는 특정 person을 생성해야하는 상황에서 new Person("master", "GM", 99) 보다는
         * Person.gameMaster()가 훨씬 직관적인 naming임. (이름을 가질 수 있다.)
         */
        Person gameMaster = Person.gameMaster();
        System.out.println("gameMaster.toString(): " + gameMaster.toString());

        /*
         * Person 클래스의 String member가 2개 있음. name, id
         * name만 혹은 id만을 받는 signature가 동일한 생성자를 생성할 수 없음. (new Person(String id), new Person(String name))
         * 이때 static method를 생성하면 됨.
         */
        String id = "id_jaye";
        String name = "jaye";
        Person person1 = Person.withName(name);
        Person person2 = Person.withId(id);

        System.out.println("person1.toString(): " + person1.toString());
        System.out.println("person2.toString(): " + person2.toString());

        /*
         * 아래와 같이 사용하면 admin은 늘 생성하지 않음.
         * 캐싱된 객체를 사용하게 됨.
         */
        Person admin1 = Person.ADMIN;
        Person admin2 = Person.ADMIN;
        System.out.println("admin1.toString(): " + admin1.toString());
        System.out.println("admin1.equals(admin2): " + admin1.equals(admin2));

        /*
         * 하위타입 리턴
         */
        Person strongPerson = Person.getStrongPerson();
        System.out.println("strongPerson.toString(): " + strongPerson.toString());

        /*
         * 매개변수에 따른 객체 생성
         */
        Person person50YearsOld = Person.withAge(50);
        Person person10YearsOld = Person.withAge(10);
        System.out.println("person50YearsOld.getClass().getName(): " + person50YearsOld.getClass().getName());
        System.out.println("person10YearsOld.getClass().getName(): " + person10YearsOld.getClass().getName());
    }
}

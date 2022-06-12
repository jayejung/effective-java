package com.ijys.effectivejava.item13;

public class Item13Main {
    public static void main(String[] args) {
        Stack stack1 = new Stack();
        stack1.push("1");
        stack1.push("3");
        stack1.push("5");
        stack1.push("7");
        stack1.push("9");
        stack1.push("11");

        System.out.println("**** stack #1 is ready");
        System.out.println(stack1.toString());

        Stack stack2 = stack1.clone();
        System.out.println("**** stack #2 is ready");
        System.out.println(stack2.toString());

        stack2.pop();
        stack2.pop();

        System.out.println("**** after stack 2 changed");
        System.out.println("**** stack 1");
        System.out.println(stack1.toString());

        System.out.println("**** stack 2");
        System.out.println(stack2.toString());
    }
}

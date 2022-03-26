package com.ijys.effectivejava.item10;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Item10Main {
    public static void main(String[] args) {
        symmetryError();
        transitivityError();
    }

    /*
    대치성 위배
     */
    private static void symmetryError() {
        System.out.println("Symmetry Test");

        CaseInsensitiveString cis = new CaseInsensitiveString("polish");
        String s = "polish";

        System.out.println("cis.equals(s): " + cis.equals(s));
        System.out.println("s.equals(cis): " + s.equals(cis)); // 대칭성에 위배 된다.

        List<CaseInsensitiveString> list = new ArrayList<>();
        list.add(cis);
        System.out.println("list.contains(s): " + list.contains(s)); // ArrayList에서 동치성 여부를 s.equals(cis)로 검사한다.
    }

    /*
    추이성 위배
     */
    private static void transitivityError() {
        System.out.println("\n\n");
        System.out.println("Transitivity Test");

        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.RED);
        System.out.println("p.equals(cp): " + p.equals(cp));
        System.out.println("cp.equals(p): " + cp.equals(p)); // 대칭성 위배 된다. (with old equals)

        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);

        System.out.println("p1.equals(p2): " + p1.equals(p2));
        System.out.println("p2.equals(p3): " + p2.equals(p3));
        System.out.println("p1.equals(p3): " + p1.equals(p3)); // 새로운 equals로 대칭성 위배는 해소가 되었지만 추이성 위배가 새롭게 발생함.
    }
}

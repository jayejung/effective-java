package com.ijys.effectivejava.item18;

import java.util.List;

public class Item18Main {
    public static void main(String[] args) {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.addAll(List.of("틱", "틱틱", "펑"));

        System.out.println("s.size(): " + s.size());
        System.out.println("s.getAllCount(): " + s.getAddCount());
        // HashSet에서 addAll은 add를 호출하여 element를 추가한. 위의 예제에서는 addCount는 6이 된다.
    }
}

package com.ijys.effectivejava.item11;

import java.util.HashMap;
import java.util.Map;

public class Item11Main {
    public static void main(String[] args) {
        Map<PhoneNumber, String> m = new HashMap<>();
        m.put(new PhoneNumber((short) 707, (short) 867, (short) 5309), "제니");

        System.out.println(m.get(new PhoneNumber((short) 707, (short) 867, (short) 5309)));
    }


}



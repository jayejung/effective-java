package com.ijys.effectivejava.item10;

import java.util.Objects;

public class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }

    // 대칭성 위배
//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof CaseInsensitiveString) {
//            return  s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
//        }
//
//        if (o instanceof String) {  // 한방향으로 작동한다.
//            return s.equalsIgnoreCase((String) o);
//        }
//
//        return false;
//    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CaseInsensitiveString &&
                ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
    }
}

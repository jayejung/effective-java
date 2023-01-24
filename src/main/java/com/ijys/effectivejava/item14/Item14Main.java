package com.ijys.effectivejava.item14;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Item14Main {
    public static void main(String[] args) {
        String[] stringArrays = {"aaa", "bbb", "abcd"};
        printOrderedString(stringArrays);
        exampleOfInconsistancyOfEqualsAndCompareTo();

        CaseInsensitiveString cis = new CaseInsensitiveString("abc");
        CaseInsensitiveString cis2 = new CaseInsensitiveString("aBc");
        System.out.println("cis.compareTo(cis2): " + cis.compareTo(cis2));

        ComparablePhoneNumber cpn = new ComparablePhoneNumber((short) 131, (short) 111, (short) 1234);
        ComparablePhoneNumber cpn2 = new ComparablePhoneNumber((short) 230, (short) 222, (short) 1234);

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("cpn.compareTo(cpn2): " + cpn.compareTo(cpn2));
        System.out.println("elapsed time: " + (System.currentTimeMillis() - currentTimeMillis));

        ComparatorPhoneNumber cpt = new ComparatorPhoneNumber((short) 131, (short) 111, (short) 1234);
        ComparatorPhoneNumber cpt2 = new ComparatorPhoneNumber((short) 230, (short) 222, (short) 1234);

        currentTimeMillis = System.currentTimeMillis();
        System.out.println("cpt.compareTo(cpt2): " + cpt.compareTo(cpt2));
        System.out.println("elapsed time: " + (System.currentTimeMillis() - currentTimeMillis));
    }

    private static void printOrderedString(String[] stringArray) {
        Set<String> s = new TreeSet<>();
        Collections.addAll(s, stringArray);
        System.out.println(s);
    }

    /**
     * equals와 compareTo의 동치성 결과가 다른 예시(BigDecimal)
     */
    private static void exampleOfInconsistancyOfEqualsAndCompareTo() {

        BigDecimal big1 = new BigDecimal("1.0");
        BigDecimal big2 = new BigDecimal("1.00");

        System.out.println("big1.equals(big2): " + big1.equals(big2));
        System.out.println("big1.compareTo(big2): " + big1.compareTo(big2));

        // HashSet에서는 equals로 동일 key 여부 판단한다.
        HashSet<BigDecimal> hashSet = new HashSet<>();
        hashSet.add(big1);
        hashSet.add(big2);

        System.out.println("number of HashSet's elements: " + hashSet.size());

        // TreeSet은 compareTo로 동일키 여부 판단한다.
        TreeSet<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(big1);
        treeSet.add(big2);

        System.out.println("number of TreeSet's elements: " + treeSet.size());

    }
}

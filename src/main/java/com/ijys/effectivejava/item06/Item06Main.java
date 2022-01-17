package com.ijys.effectivejava.item06;

public class Item06Main {
    public static void main(String[] args) {

        String[] text = {"12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"
                , "12321321", "12321322", "12321323", "12321324", "12321325", "12321326"};

        final long start1 = System.currentTimeMillis();
        for (String s : text) {
            //System.out.println("RomanNumerals.isRomanNumeral(text): " + RomanNumerals.isRomanNumeral(s));
            RomanNumerals.isRomanNumeral(s);
        }
        System.out.println("Elapsed millis of RomanNumerals.isRomanNumeral method: " + (System.currentTimeMillis() - start1));

        final long start2 = System.currentTimeMillis();
        for (String s : text) {
            //System.out.println("isRomanNumeral(text): " + isRomanNumeral(s));
            isRomanNumeral(s);
        }
        System.out.println("Elapsed millis of isRomanNumeral method: " + (System.currentTimeMillis() - start2));

        final long start3 = System.currentTimeMillis();
        sum();
        System.out.println("Elapsed millis of autoBoxing sum(): " + (System.currentTimeMillis() - start3));

        final long start4 = System.currentTimeMillis();
        betterSum();
        System.out.println("Elapsed millis of betterSum(): " + (System.currentTimeMillis() - start4));
    }

    private static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }

    /*
    sum이 Long이고 sum에 추가되는 i는 long이므로, long이 Long으로 박싱되어서 2^31 개의 Long 인스턴스가 생성된다.
     */
    private static long sum() {
        Long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }

    /*
    sum을 long으로 변경하고, 성능이 10배 이상 좋아졌음.
     */
    private static long betterSum() {
        long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }
}

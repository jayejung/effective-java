package com.ijys.effectivejava.item14;

import java.util.Comparator;

import static java.util.Comparator.comparingInt;

public class ComparatorPhoneNumber implements Comparable<ComparatorPhoneNumber> {
    protected final short areaCode, prefix, lineNum;

    public ComparatorPhoneNumber(short areaCode, short prefix, short lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "지역코드");
        this.prefix = rangeCheck(prefix, 999, "프리픽스");
        this.lineNum = rangeCheck(lineNum, 9999, "가입자 번호");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max) {
            throw new IllegalArgumentException(arg + ": " + val);
        }
        return (short) val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof com.ijys.effectivejava.item11.PhoneNumber))
            return false;
        com.ijys.effectivejava.item14.ComparatorPhoneNumber pn = (com.ijys.effectivejava.item14.ComparatorPhoneNumber) o;
        return pn.lineNum == this.lineNum && pn.prefix == this.prefix && pn.areaCode == this.areaCode;
    }

    @Override
    public int hashCode() {
        /* 아주 좋은 방법(단순하고 빠른..)의 해시 생성 */
        int result = Short.hashCode(this.areaCode);
        result = 31 * result + Short.hashCode(this.prefix);
        result = 31 * result + Short.hashCode(this.lineNum);

        return result;
    }

    /*
    여러 필드로 비교할때는 중요한 필드 먼저.
     */
    private static final Comparator<ComparatorPhoneNumber> COMPARATOR =
            comparingInt((ComparatorPhoneNumber cpn) -> cpn.areaCode)
                    .thenComparingInt(cpn -> cpn.prefix)
                    .thenComparingInt(cpn -> cpn.lineNum);

    @Override
    public int compareTo(ComparatorPhoneNumber cpn) {
        return COMPARATOR.compare(this, cpn);
    }
}

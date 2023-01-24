package com.ijys.effectivejava.item14;

public class ComparablePhoneNumber implements Comparable<ComparablePhoneNumber> {
    protected final short areaCode, prefix, lineNum;

    public ComparablePhoneNumber(short areaCode, short prefix, short lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
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
    @Override
    public int compareTo(ComparablePhoneNumber cpn) {
        int result = Short.compare(areaCode, cpn.areaCode);
        if (result == 0) {
            result = Short.compare(prefix, cpn.prefix);
            if (result == 0)
                result = Short.compare(lineNum, cpn.lineNum);
        }
        return result;
    }
}

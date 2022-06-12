package com.ijys.effectivejava.item13;

public class PhoneNumber implements Cloneable {
    protected final short areaCode, prefix, lineNum;

    public PhoneNumber(short areaCode, short prefix, short lineNum) {
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
        com.ijys.effectivejava.item13.PhoneNumber pn = (com.ijys.effectivejava.item13.PhoneNumber) o;
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

    @Override
    public PhoneNumber clone() {
        try {
            return (PhoneNumber) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // 일어날 수 없는 일이다.
        }
    }
}

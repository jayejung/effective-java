package com.ijys.effectivejava.item11;

import java.util.Objects;

public final class PhoneNumber {
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
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == this.lineNum && pn.prefix == this.prefix && pn.areaCode == this.areaCode;
    }

    @Override
    public int hashCode() {
        /* 아주 좋은 방법(단순하고 빠른..)의 해시 생성 */
//        int result = Short.hashCode(this.areaCode);
//        result = 31 * result + Short.hashCode(this.prefix);
//        result = 31 * result + Short.hashCode(this.lineNum);
//
//        return result;

        /* 느리고 성능에 이슈는 있지만, 코드가 가장 단순하다.
         성능에 중요한 클래스가 아니면 충분하게 사용 가능하다. */
        return Objects.hash(this.areaCode, this.prefix, this.lineNum);
    }
}
package com.ijys.effectivejava.item03;

public class Elvis02 {
    private static final Elvis02 INSTANCE = new Elvis02();
    private Elvis02() {}

    public static Elvis02 getInstance() {
        return INSTANCE;
    }

    public void leaveTheBuilding() {
        System.out.println("Elvis#02 is leaving from the building");
    }
}

package com.ijys.effectivejava.item03;

public class Elvis01 {
    public static final Elvis01 INSTANCE = new Elvis01();

    private Elvis01() {
    }

    public void leaveTheBuilding() {
        System.out.println("Elvis#01 is leaving from the building");
    }
}

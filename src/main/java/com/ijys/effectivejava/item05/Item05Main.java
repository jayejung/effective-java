package com.ijys.effectivejava.item05;

public class Item05Main {
    public static void main(String[] args) {
        SpellChecker spellChecker = new SpellChecker(() -> new DictEng());
        spellChecker.getDictionary().printMyLanguage();
    }
}

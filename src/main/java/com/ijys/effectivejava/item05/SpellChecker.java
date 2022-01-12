package com.ijys.effectivejava.item05;

import java.util.List;
import java.util.function.Supplier;

public class SpellChecker {
    private final Dictionary dictionary;

    public SpellChecker(Supplier<? extends Dictionary> dictFactory) {
        this.dictionary = dictFactory.get();
    }

    public boolean isValid(String word) {
        System.out.println("This is isValid Method. Parameter is " + word);
        return true;
    }

    public List<String> suggestions(String typo) {
        System.out.println("This is suggestions Method. Parameter is " + typo);
        return null;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
}

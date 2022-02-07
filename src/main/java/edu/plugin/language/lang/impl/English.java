package edu.plugin.language.lang.impl;

import edu.plugin.language.lang.Language;
import edu.plugin.language.lang.LanguagesFactory;

public class English implements Language {

    @Override
    public char getFirstUpper() {
        return 'A';
    }

    @Override
    public char getLastUpper() {
        return 'Z';
    }

    @Override
    public char getFirstLower() {
        return 'a';
    }

    @Override
    public char getLastLower() {
        return 'z';
    }

    @Override
    public String getName() {
        return "en";
    }
}

package edu.plugin.language.lang.impl;

import edu.plugin.language.lang.Language;
import edu.plugin.language.lang.LanguagesFactory;

public class Russian implements Language {

    @Override
    public char getFirstUpper() {
        return 'А';
    }

    @Override
    public char getLastUpper() {
        return 'Я';
    }

    @Override
    public char getFirstLower() {
        return 'а';
    }

    @Override
    public char getLastLower() {
        return 'я';
    }

    @Override
    public String getName() {
        return "ru";
    }
}

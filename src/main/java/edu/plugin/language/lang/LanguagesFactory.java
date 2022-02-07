package edu.plugin.language.lang;

import edu.plugin.language.lang.impl.English;
import edu.plugin.language.lang.impl.Russian;

import java.util.ArrayList;
import java.util.List;

public class LanguagesFactory {

    private static List<Language> list = new ArrayList<>();

    static {
        list.add(new English());
        list.add(new Russian());
    }

    public static List<Language> getRegisteredLanguages() {
        return list;
    }
}

package edu.plugin.language;

import edu.plugin.language.lang.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Result {

    private String word;
    private List<Language> letters;

    public Result(String word) {
        this.word = word;
        letters = new ArrayList<>(word.length());
    }

    public void set(Language language) {
        letters.add(language);
    }

    public int getCount(Language language) {
        int count = 0;
        for (Language letter : letters) {
            if (Objects.equals(letter, language)) {
                count++;
            }
        }
        return count;
    }

    public List<Language> getLetters() {
        return letters;
    }

    public String getWord() {
        return word;
    }
}

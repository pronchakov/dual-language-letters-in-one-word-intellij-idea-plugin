package edu.plugin.language;

import java.util.Objects;

class Result {

    private String word;
    private Language[] letters;

    public Result(String word) {
        this.word = word;
        letters = new Language[word.length()];
    }

    public void set(int i, Language language) {
        letters[i] = language;
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

    public Language[] getLetters() {
        return letters;
    }

    public String getWord() {
        return word;
    }
}

package edu.plugin.language;

public enum Language {
    ENGLISH('A', 'Z', 'a', 'z', "en"),
    RUSSIAN('А', 'Я', 'а', 'я', "ru");

    private int firstUpper;
    private int lastUpper;
    private int firstLower;
    private int lastLower;
    private String title;


    Language(int firstUpper, int lastUpper, int firstLower, int lastLower, String title) {
        this.firstUpper = firstUpper;
        this.lastUpper = lastUpper;
        this.firstLower = firstLower;
        this.lastLower = lastLower;
        this.title = title;
    }

    public int getFirstUpper() {
        return firstUpper;
    }

    public int getLastUpper() {
        return lastUpper;
    }

    public int getFirstLower() {
        return firstLower;
    }

    public int getLastLower() {
        return lastLower;
    }

    public String getTitle() {
        return title;
    }
}

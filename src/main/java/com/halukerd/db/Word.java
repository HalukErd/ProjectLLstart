package com.halukerd.db;

public class Word {
    private String wordString;
    private int seen;
    private int seenToLearn;
    private boolean known;

    public Word() {
        this.wordString = null;

    }
    public Word(String wordString) {
        this.wordString = wordString;
        this.seen = 1;
        this.seenToLearn = 5;
        this.known = false;
    }

    public Word(String wordString, int seen, int seenToLearn, boolean known) {
        this.wordString = wordString;
        this.seen = seen;
        this.seenToLearn = seenToLearn;
        this.known = known;
    }

    public String getWordString() {
        return wordString;
    }

    public void setWordString(String wordString) {
        this.wordString = wordString;
    }

    public int getSeen() {
        return seen;
    }

    public void setSeen(int seen) {
        this.seen = seen;
    }

    public int getSeenToLearn() {
        return seenToLearn;
    }

    public void setSeenToLearn(int seenToLearn) {
        this.seenToLearn = seenToLearn;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }
}

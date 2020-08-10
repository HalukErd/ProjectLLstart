package com.halukerd.subtitle;

import com.github.dnbn.submerge.api.subtitle.ass.ASSSub;
import com.halukerd.db.Datasource;
import com.halukerd.db.DatasourceManager;
import com.halukerd.db.DatasourceQueryResult;
import com.halukerd.dictionary.Dictionary;
import com.halukerd.dictionary.TurengDictionary;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ASSSubWithMeanings {
    private ASSSub assSub;
    private String position;
    private String scale;

    public ASSSubWithMeanings(ASSSub assSub) {
        this.assSub = assSub;
        position = "\\an6";
        scale = "\\fscx70\\fscy70";
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(assSub.toString());
//        StringBuilder sb = new StringBuilder("");

        assSub.getEvents().forEach(e -> {
            e.setLayer(1);
            e.setTextLines(getMeanings(e.getTextLines()));
            if (e.getTextLines() != null) {
                sb.append(e.toString()).append("\n");
            }
        });

        return sb.toString();
    }

    public List<String> getMeanings(List<String> textLines) {
        List<String> meaningLists = new ArrayList<>();
        meaningLists.add("{" + position + scale + "}");

        for (String line : textLines) {
            for (String word : line.split("[\\[\\].,\\d+\\s!;?:'\"]+")) {
                if (!word.matches("\\w{3,}")) {
                    continue;
                }
                if (isUnknownWord(word)) {
                    String translation = lookFor(word);
                    if (translation != null) {
                        updateWordInDatabase(word);
                        meaningLists.add("{\\b1}" + word + "{\\b}" + "\\N" + translation + "\\N");
                    }
                }
            }
        }

        if (meaningLists.size() > 1) {
            return meaningLists;
        } else {
            return null;
        }
    }

    public boolean isUnknownWord(String word) {

        DatasourceManager datasourceManager = new DatasourceManager();


        return datasourceManager.queryWord(word) != (DatasourceQueryResult.FOUND_AND_KNOWN);
    }

    public void updateWordInDatabase(String word) {

        DatasourceManager datasourceManager = new DatasourceManager();
        datasourceManager.updateWord(word);
    }

    public String lookFor(String unknownWord) {
        Dictionary dictionary = new TurengDictionary();
        List<String> translations = dictionary.search(unknownWord);
        if (translations == null) {
            try {
                Datasource.getInstance().insertKnownWord(unknownWord);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int translationNumber = Math.min(translations.size(), 3);
        for (int i = 0; i < translationNumber; i++) {
            String translation = translations.get(i);
            if (translation.split(" ").length >= 4) {
                translationNumber = Math.min(translations.size(), 4);
                continue;
            }
            sb.append(translation);
            if (i == translationNumber - 1) {
                continue;
            }
            sb.append(",\\N");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        String a = "2311414d";
        if (a.matches("\\w{3,}")) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}

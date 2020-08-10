package com.halukerd.dictionary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TurengDictionary implements Dictionary {
    @Override
    public List<String> search(String unknownWord) {
        try {
            List<String> resultList = new ArrayList<String>();

            Document doc = Jsoup.connect("https://tureng.com/tr/turkce-ingilizce/" + unknownWord).get();
//            Elements englishKeyWords = doc.select(".en.tm a[href]");
//            for (Element element : englishKeyWords) {
//                if (element.text().equals(unknownWord)) {
//                    break;
//                }
//                return null;
//            }
            Element firstElement = doc.selectFirst(".en.em a[href]");
            Elements elements = doc.select(".tr.ts a[href]");

            System.out.print("Tureng search: " + unknownWord);
            if (elements.isEmpty()) {
                System.out.println(" NOT_FOUND");
                return null;
            }

//            if (!firstElement.text().equals(unknownWord)) {
////                String editedWord = editWord(unknownWord);
////                if (editedWord != null) {
////                    return search(editedWord);
////                }
//                resultList.add(firstElement.text());
//            }
            System.out.println(" FOUND");

            for (Element element : elements) {
                resultList.add(element.text());
            }
            return resultList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public String editWord(String word) {
//        if (word.endsWith("s")) {
//            return word.substring(0, word.length() - 1);
//        }
//
//    }
}

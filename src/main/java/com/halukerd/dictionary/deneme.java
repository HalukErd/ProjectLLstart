package com.halukerd.dictionary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class deneme {
    public static void main(String[] args) throws IOException {
        String unknownWord = "winkleasdas";
        Document doc = Jsoup.connect("https://tureng.com/tr/turkce-ingilizce/" + unknownWord).get();

        Elements elements = doc.select(".tr.ts a[href]");

        if (elements.isEmpty()) {
            System.out.println("nullllll");
        }
        for (Element element : elements) {
            System.out.println(element.text());
        }
    }
}
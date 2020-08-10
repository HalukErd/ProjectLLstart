package com.halukerd.db;

import java.sql.SQLException;

public class TestDataSource {


    public static void main(String[] args) throws SQLException {

//            Datasource datasource = new Datasource();
//            datasource.open();
//            datasource.insertWord("home");
//            Word word = datasource.queryWord("home");
//            System.out.println(word.getWordString() + " - " + word.getSeen() + " - " +
//                    word.getSeenToLearn() + " - " + word.isKnown());
//
//
//
//            word = datasource.queryAndUpdateCount("home");
//            System.out.println(word.getWordString() + " - " + word.getSeen() + " - " +
//                    word.getSeenToLearn() + " - " + word.isKnown());
//
//            datasource.close();
//
//        DatasourceManager datasourceManager = new DatasourceManager();
//
//        System.out.println(datasourceManager.search("hello"));
//        System.out.println(datasourceManager.search("house"));
//        System.out.println(datasourceManager.search("you"));
//        System.out.println(datasourceManager.search("home"));
//        System.out.println(datasourceManager.search("they"));

        Datasource.getInstance().open();
        DatasourceManager datasourceManager = new DatasourceManager();
        System.out.println(datasourceManager.updateWord("home"));
        System.out.println(datasourceManager.insertWord("ladder"));
        System.out.println(datasourceManager.queryWord("ladder"));
        System.out.println(datasourceManager.insertWord("mouse"));
        System.out.println(datasourceManager.updateWord("mouse"));
        System.out.println(datasourceManager.queryWord("mouse"));
        System.out.println(datasourceManager.insertWord("bottle"));
        System.out.println(datasourceManager.updateWord("bottle"));
        System.out.println(datasourceManager.queryWord("bottle"));
        System.out.println(datasourceManager.insertWord("walking"));
        System.out.println(datasourceManager.updateWord("walking"));
        System.out.println(datasourceManager.queryWord("restart"));
        Datasource.getInstance().close();
    }

}
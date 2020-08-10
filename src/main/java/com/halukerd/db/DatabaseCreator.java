package com.halukerd.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class DatabaseCreator {
    Datasource datasource;

    public DatabaseCreator() {
        datasource = Datasource.getInstance();
    }

    public static void addMostCommonWords(int number) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("1000MostCommonWords.txt"));
            Datasource.getInstance().open();
            for (int i = 0; i < number; i++) {
                String[] textLine = br.readLine().split("[\\d\\s]+");
                if (textLine[1] != null) {
                    Datasource.getInstance().insertKnownWord(textLine[1]);
                }
                System.out.println(Arrays.toString(textLine));
            }
            Datasource.getInstance().close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addMostCommonWords(1000);
    }
}

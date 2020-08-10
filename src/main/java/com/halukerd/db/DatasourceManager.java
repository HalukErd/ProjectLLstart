package com.halukerd.db;

import java.sql.SQLException;

public class DatasourceManager {
    Datasource datasource;

    public DatasourceManager() {
        this.datasource = Datasource.getInstance();
    }

    public DatasourceQueryResult updateWord(String wordString) {
        try {
            if (!datasource.isOpen()) {
                throw new SQLException("datasource.isOpen returned false from DatasourceManager's insertWord method. WordString=" + wordString);
            }
            Word word = datasource.queryWord(wordString);
            if (word == null) {
                if (datasource.insertWord(wordString)) {
                    return DatasourceQueryResult.NOT_FOUND_AND_INSERTED;
                } else {
                    throw new SQLException("DatasourceManager insertWord method failed.");
                }
//                throw new SQLException("DatasourceManager updateWord failed. \"" + wordString + "\" == null");
            }

            return datasource.queryAndUpdateCount(word);

        } catch (SQLException e) {
            System.out.println("Datasource Manager error: " + e.getMessage());
            e.printStackTrace();
            return DatasourceQueryResult.FAIL_ERROR_PANIC;
        }
    }

    public DatasourceQueryResult insertWord(String wordString) {
        try {
            if (!datasource.isOpen()) {
                throw new SQLException("datasource.isOpen returned false from DatasourceManager's insertWord method. WordString=" + wordString);
            }
            Word word = datasource.queryWord(wordString);
            if (word == null) {
                if (datasource.insertWord(wordString)) {
                    return DatasourceQueryResult.NOT_FOUND_AND_INSERTED;
                } else {
                    throw new SQLException("DatasourceManager insertWord method failed.");
                }
            } else {
                return DatasourceQueryResult.FOUND;
            }
        } catch (SQLException e) {
            System.out.println("Datasource Manager error: " + e.getMessage());
            e.printStackTrace();
            return DatasourceQueryResult.FAIL_ERROR_PANIC;
        }
    }

    public DatasourceQueryResult queryWord(String wordString) {
        try {
            if (!datasource.isOpen()) {
                throw new SQLException("datasource.isOpen returned false from DatasourceManager's insertWord method ");
            }
//            System.out.print("Datasource queryWord: " + wordString);
            Word word = datasource.queryWord(wordString);
            if (word == null) {
//                System.out.println(" NOT_FOUND");
                return DatasourceQueryResult.NOT_FOUND;
            } else {
                if (word.isKnown()) {
//                    System.out.println(" FOUND_AND_KNOWN");
                    return DatasourceQueryResult.FOUND_AND_KNOWN;
                } else {
//                    System.out.println(" FOUND_AND_UNKNOWN");
                    return DatasourceQueryResult.FOUND_AND_UNKNOWN;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return DatasourceQueryResult.FAIL_ERROR_PANIC;
        }
    }

    public void open() {
        datasource.open();
    }

    public void close() {
        datasource.close();
    }


}

package com.halukerd.db;

import java.io.File;
import java.nio.file.Paths;
import java.sql.*;

public class Datasource {
    private int numberToLearn = 3;
    private static final String ABSOLUTE_PATH = Paths.get("").toAbsolutePath().toString();
    private static final String DB_NAME = "knownword.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + ABSOLUTE_PATH + "\\" + DB_NAME;
    public static final String TABLE_WORDS = "words";
    public static final String COLUMN_WORDS_ID = "_id";
    public static final String COLUMN_WORDS_WORD = "word";
    public static final String COLUMN_WORDS_SEEN = "seen";
    public static final String COLUMN_WORDS_SEENTOLEARN = "seentolearn";
    public static final String COLUMN_WORDS_KNOWN = "known";

    //    CREATE TABLE IF NOT EXISTS words2 (_id INTEGER PRIMARY KEY, word TEXT NOT NULL, seen INTEGER, seentolearn INTEGER, known BOOLEAN);
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_WORDS + " (" +
            COLUMN_WORDS_ID + " INTEGER PRIMARY KEY, " + COLUMN_WORDS_WORD + " TEXT NOT NULL, " +
            COLUMN_WORDS_SEEN + " INTEGER, " + COLUMN_WORDS_SEENTOLEARN + " INTEGER, " + COLUMN_WORDS_KNOWN +
            " BOOLEAN)";

//    INSERT INTO words (word, seen, seentolearn, known) VALUES ("they", 1, 5, false);
    private static final String ADD_WORD = "INSERT INTO " + TABLE_WORDS + " (" +
        COLUMN_WORDS_WORD + ", " + COLUMN_WORDS_SEEN + ", " +
        COLUMN_WORDS_SEENTOLEARN + ", " + COLUMN_WORDS_KNOWN + ") VALUES (?, ?, ?, ?)";

//    UPDATE words SET seen=3, known=true WHERE word="they";
    public static final String UPDATE_WORD_COUNT = "UPDATE " + TABLE_WORDS + " SET " + COLUMN_WORDS_SEEN +
        "=?, " + COLUMN_WORDS_KNOWN + "=? WHERE " + " LOWER(" +  COLUMN_WORDS_WORD + ") " + "=?";

    //  SELECT seen, seentolearn, known FROM words WHERE word = "they";
    public static final String QUERY_WORD = "SELECT " + COLUMN_WORDS_SEEN + ", " + COLUMN_WORDS_SEENTOLEARN + ", "
            + COLUMN_WORDS_KNOWN + " FROM " + TABLE_WORDS + " WHERE " + " LOWER(" +  COLUMN_WORDS_WORD + ") " + " = ?";

    private Connection conn;

    private PreparedStatement addWord;
    private PreparedStatement updateWordCount;
    private PreparedStatement queryWord;

    private static class LazyInit {
        public static Datasource instance = new Datasource();
    }

    private Datasource() {

    }

    public static Datasource getInstance() {
        return LazyInit.instance;
    }

    public boolean isOpen() {
        if (conn != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            createIfNotExist();

            addWord = conn.prepareStatement(ADD_WORD);
            updateWordCount = conn.prepareStatement(UPDATE_WORD_COUNT);
            queryWord = conn.prepareStatement(QUERY_WORD);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not connect database SQL Exception: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (addWord != null) {
                addWord.close();
            }
            if (updateWordCount != null) {
                updateWordCount.close();
            }
            if (queryWord != null) {
                queryWord.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Could not close database: " + e.getMessage());
        }
    }

    public boolean createIfNotExist() {
        try (Statement statement = conn.createStatement()){
            statement.execute(CREATE_TABLE);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean insertKnownWord(String word) throws SQLException {
        addWord.setString(1, word);
        addWord.setInt(2, numberToLearn);
        addWord.setInt(3, numberToLearn);
        addWord.setBoolean(4, true);

        int affectedRows = addWord.executeUpdate();

        if (affectedRows != 1) {
            throw new SQLException("Could not add word");
        }
        return true;
    }

    public boolean insertWord(String word) throws SQLException {
        boolean isWordKnown = numberToLearn == 1;
        addWord.setString(1, word);
        addWord.setInt(2, 1);
        addWord.setInt(3, numberToLearn);
        addWord.setBoolean(4, isWordKnown);

        int affectedRows = addWord.executeUpdate();

        if (affectedRows != 1) {
            throw new SQLException("Could not add word");
        }
        return true;
    }

    public Word queryWord(String wordString) throws SQLException {
        wordString = wordString.toLowerCase();
        queryWord.setString(1, wordString);
        Word returnWord = new Word();
        ResultSet results = queryWord.executeQuery();
        if (results.next()) {
            returnWord.setWordString(wordString);
            returnWord.setSeen(results.getInt(1));
            returnWord.setSeenToLearn(results.getInt(2));
            returnWord.setKnown(results.getBoolean(3));
            return returnWord;
        } else {
            return null;
        }
    }

    public boolean updateWordCount(Word word) throws SQLException {
        word.setWordString(word.getWordString().toLowerCase());

        updateWordCount.setInt(1, word.getSeen());
        updateWordCount.setBoolean(2, word.isKnown());
        updateWordCount.setString(3, word.getWordString());

        int affecteedRows = updateWordCount.executeUpdate();
        if (affecteedRows != 1) {
            throw new SQLException("Could not update word count");
        }
        return true;
    }

    public DatasourceQueryResult queryAndUpdateCount(Word wordToUpdate) throws SQLException {

        if ((wordToUpdate.getSeen() + 1) >= wordToUpdate.getSeenToLearn()) {
            wordToUpdate.setKnown(true);
        }

        wordToUpdate.setSeen(wordToUpdate.getSeen() + 1);
        if (updateWordCount(wordToUpdate)) {
            if (wordToUpdate.isKnown()) {
                return DatasourceQueryResult.FOUND_COUNTED_KNOWN;
            } else {
                return DatasourceQueryResult.FOUND_COUNTED_STILL_UNKNOWN;
            }
        } else {
            throw new SQLException("Query and Update Count failed");
        }
    }

    public static void main(String[] args) {
        Datasource.getInstance().open();
        Datasource.getInstance().close();
        File file = new File("knownWord.db");
        System.out.println(file.getAbsolutePath());
        //        if (file.getPath()) {
//            System.out.println("no database");
//        } else {
//            System.out.println("not null");
//        }
    }
}

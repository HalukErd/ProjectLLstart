package com.halukerd.subtitle;

import com.github.dnbn.submerge.api.SubmergeAPI;
import com.github.dnbn.submerge.api.parser.ParserFactory;
import com.github.dnbn.submerge.api.parser.SubtitleParser;
import com.github.dnbn.submerge.api.subtitle.ass.ASSSub;
import com.github.dnbn.submerge.api.subtitle.common.TimedTextFile;
import com.github.dnbn.submerge.api.subtitle.config.SimpleSubConfig;
import com.halukerd.db.Datasource;
import org.apache.commons.io.FilenameUtils;

import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SubDicMergeAPI extends SubmergeAPI {
    File subtitleFile;

//    private static class LazyInit {
//        public static Datasource instance = new Datasource();
//    }
//
//    private Datasource() {
//
//    }
//
//    public static Datasource getInstance() {
//        return Datasource.LazyInit.instance;
//    }

    public static SubDicMergeAPI getInstance() {
        return SubDicMergeAPI.LazyInit.instance;
    }

    private static class LazyInit {
        public static SubDicMergeAPI instance = new SubDicMergeAPI();
    }

    private SubDicMergeAPI() {

    }

    private ASSSub convertSubtitleToASS() {
        String extension = FilenameUtils.getExtension(subtitleFile.getName());

        SubtitleParser parser = ParserFactory.getParser(extension);
        TimedTextFile subtitle = parser.parse(subtitleFile);

        SimpleSubConfig config = new SimpleSubConfig();
        config.setSub(subtitle);
        config.setAlignment(2);

        ASSSub ass = new SubmergeAPI().toASS(config);
        return ass;
    }

    private ASSSubWithMeanings addTranslations() {
        if (subtitleFile != null) {
            ASSSub assSub = convertSubtitleToASS();
            return new ASSSubWithMeanings(assSub);
        }
        return null;
    }

    private String convertAndMergeTranslations() {
        ASSSubWithMeanings result = addTranslations();
        if (result != null) {
            return result.toString();
        }
        return null;
    }

    public void createNewSubtitle() {
        try {
            Datasource.getInstance().open();
            BufferedWriter bw = new BufferedWriter(new FileWriter(getTargetSubFilePath()));
            bw.write(convertAndMergeTranslations());
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            Datasource.getInstance().close();
        }
    }

    private String getTargetSubFilePath() {
        String sourceSubFilePath = subtitleFile.getAbsolutePath();
        String targetSubFilePath = sourceSubFilePath.substring(0, sourceSubFilePath.length() - 4) + ".ass";
        System.out.println(targetSubFilePath);
        return targetSubFilePath;
    }

    public File getSubtitleFile() {
        return subtitleFile;
    }

    public void setSubtitleFile(File subtitleFile) {
        this.subtitleFile = subtitleFile;
    }
}

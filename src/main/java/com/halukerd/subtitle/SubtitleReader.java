package com.halukerd.subtitle;

import com.halukerd.db.Datasource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SubtitleReader {
    public static void main(String[] args) throws IOException {
//        Datasource.getInstance().open();
//        File file = new File("the.boys.srt");
//        String extension = FilenameUtils.getExtension(file.getName());
//
//        SubtitleParser parser = ParserFactory.getParser(extension);
//        TimedTextFile subtitle = parser.parse(file);
//
//        SimpleSubConfig config = new SimpleSubConfig();
//        config.setSub(subtitle);
//        config.setAlignment(2);
//
//        ASSSub ass = new SubmergeAPI().toASS(config);
//        ASSSubWithMeanings assSubWithMeanings = new ASSSubWithMeanings(ass);
//        System.out.println("***");
//        System.out.println(assSubWithMeanings.toString());
//
//        Datasource.getInstance().close();


        Datasource.getInstance().open();

        File file = new File("subtitle.srt");
        String newPath = file.getAbsolutePath();
        newPath = newPath.substring(0, newPath.length() - 4) + ".ass";

        System.out.println(newPath);
//        SubDicMergeAPI subDicMergeAPI = SubDicMergeAPI.getInstance();
//        subDicMergeAPI.setSubtitleFile(file);
//        System.out.println(subDicMergeAPI.convertAndMergeTranslations());
//        BufferedWriter bw = new BufferedWriter(new FileWriter("subtitle.ass"));
//        bw.write(subDicMergeAPI.convertAndMergeTranslations());
//        bw.close();

        Datasource.getInstance().close();

    }

}

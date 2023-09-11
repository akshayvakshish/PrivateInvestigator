package utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import models.Entry;

import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<Entry> readCSV(String file) {
        try {
            // Create an object of file reader class with CSV file as a parameter.
            java.io.FileReader filereader = new java.io.FileReader(file);

            // create csvParser object with
            // custom separator semi-colon
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

            // create csvReader object with parameter
            // filereader and parser
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .build();
            List<Entry> entries=new ArrayList<>();
            // Read all data at once
            String[] current;
            while((current=csvReader.readNext())!=null){
                entries.add(new Entry(current[0],current[1],current[2]));
            }
            return entries;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

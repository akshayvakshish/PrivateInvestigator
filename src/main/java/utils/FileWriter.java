package utils;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;

public class FileWriter {
    CSVWriter writer;

    public FileWriter(String file) {
        this.writeDataLineByLine(file);
    }


    public void writeDataLineByLine(String filePath) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            java.io.FileWriter outputfile = new java.io.FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            writer = new CSVWriter(outputfile);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeSingleLine(String[] line) throws IOException {
        if (this.writer != null) {
            // add data to csv
            for (String sentence : line) {
                writer.writeNext(new String[]{sentence}, false);
            }

        }
    }

    public void closeWriter() throws IOException {
        if (this.writer != null) {
            // closing writer connection
            writer.close();
        }
    }
}

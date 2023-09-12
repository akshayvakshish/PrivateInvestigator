import models.Entry;
import service.NotesService;
import utils.FileReader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Application {

    /**
     * This is the driver method which is used to call NotesService and generate the output csv from input csv
     * @param args
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        NotesService notesService = new NotesService();
        List<Entry> entryList = FileReader.readCSV("src/main/resources/notes.csv");
        Set entrySet = notesService.groupNotes(entryList);
        notesService.writeToFile(entrySet);
        System.out.println("The output file is generated under resources!");
    }
}

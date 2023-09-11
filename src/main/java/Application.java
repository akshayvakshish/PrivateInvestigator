import models.Entry;
import service.NotesService;
import utils.FileReader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Application {


    public static void main(String[] args) throws IOException {
        NotesService notesService=new NotesService();
        List<Entry> entryList=FileReader.readCSV( "src/main/resources/notes.csv");
        Set entrySet=notesService.groupNotes(entryList);
        notesService.findUnCommonWordInSentences(entrySet);


    }
}

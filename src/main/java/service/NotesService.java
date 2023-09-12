package service;

import models.Entry;
import utils.FileWriter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class NotesService {

    public Set<Set<Entry>> groupNotes(List<Entry> notes) throws CloneNotSupportedException {
        Map<String, Set<Entry>> groups = new ConcurrentHashMap<>();
        for (Entry note : notes) {
            String[] wordsList = note.getNote().split("\\s+");
            for (int i = 0; i < wordsList.length; i++) {
                String[] wordsListClone = Arrays.copyOf(wordsList, wordsList.length);
                Entry cloneNote = note.clone();
                cloneNote.setDiffWord(wordsListClone[i]);
                wordsListClone[i] = "?";
                String sentenceWithoutWord = String.join(" ", wordsListClone);
                if (!groups.containsKey(sentenceWithoutWord)) {
                    groups.put(sentenceWithoutWord, new HashSet<>());
                }
                groups.get(sentenceWithoutWord).add(cloneNote);
            }
        }
        groups.keySet().forEach(key -> {
            if (groups.get(key).size() == 1) {
                groups.remove(key);
            }
        });

        return new HashSet<>(groups.values());
    }

    public void writeToFile(Set<Set<Entry>> entrySet) throws IOException {

        FileWriter fileWriter = new FileWriter("src/main/resources/output.csv");

        entrySet.forEach(entries -> {

            StringBuilder diffWords = new StringBuilder().append("The changing word was: ");
            for (Entry entry : entries) {
                String[] stringArray = new String[]{new StringBuilder().append(entry.getDate()).append(" ")
                        .append(entry.getTimestamp()).append(" ")
                        .append(entry.getNote()).toString()};
                diffWords.append(entry.getDiffWord()).append(",");
                writeToFile(fileWriter, stringArray);
            }
            String[] stringArray2 = new String[]{diffWords.substring(0, diffWords.length() - 1)};
            writeToFile(fileWriter, stringArray2);

        });
        fileWriter.closeWriter();

    }

    public void writeToFile(FileWriter fileWriter, String[] stringArray) {
        try {
//                String[] stringArray=new String[]{};
//                stringArray=strings.toArray(stringArray);
//                stringArray[strings.size()]= "The changing word was: "+uncommonSet.toString().replace("[","").replace("]","");
            fileWriter.writeSingleLine(stringArray);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

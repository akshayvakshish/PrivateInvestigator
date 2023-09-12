package service;

import models.Entry;
import utils.FileWriter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotesService {
    /**
     * This is the main algorithm ->> We assume each word to be a repeating word and mask it with '?'
     * This new sentence is cloned and used as a key, so next time same key is formed we add those notes to the corresponding set
     * We also know which word is being masked so we store it in a cloned Entry object, as this object can be saved in multiple groups
     *
     * @param notes
     * @return Set<Set<Entry>>
     * @throws CloneNotSupportedException
     */
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

    /**
     * This is a service method which writes to the file after extracting data in required format
     * @param entrySet
     * @throws IOException
     */
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

    /**
     * The file remains open and its written to on need basis, it will be closed once the operation completes
     * @param fileWriter
     * @param stringArray
     */
    public void writeToFile(FileWriter fileWriter, String[] stringArray) {
        try {
            fileWriter.writeSingleLine(stringArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

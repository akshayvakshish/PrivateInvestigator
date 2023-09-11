package service;

import models.Entry;
import utils.FileWriter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class NotesService {

    public Set<Set<Entry>> groupNotes(List<Entry> notes){
        Map<Entry, Set<Entry>> groups=new LinkedHashMap();
        SentenceComparator comparator=new SentenceComparator();
        notes.stream().forEach(note->{
            groups.computeIfAbsent(createGroup(groups,note,comparator),k-> new HashSet<>()).add(note);
        });
        groups.forEach((k,v)-> {
            createGroup(groups,k,comparator);
        });
        return new HashSet<>( groups.values());
    }

    private Entry createGroup(Map<Entry, Set<Entry>> groups, Entry entry, SentenceComparator comparator) {

        groups.keySet().stream().forEach(key->{
            if(key!=null && comparator.compare(key.getNote(),entry.getNote())==0 ){
                AtomicBoolean matchedAll = new AtomicBoolean(true);
                groups.get(key).forEach(val->{
                    if(comparator.compare(val.getNote(),entry.getNote())!=0){
                        matchedAll.set(false);
                    }
                });
                if(matchedAll.get()){
                    groups.get(key).add(entry);
                }
            }
        });

        return entry;
    }

    public String findUnCommonWordInSentences(Set<Set<Entry>> entrySet) throws IOException {
        FileWriter fileWriter=new FileWriter("src/main/resources/output.csv");

        entrySet.forEach(entries -> {
            List<String> entryList=new ArrayList<>();
            entries.forEach(entry -> entryList.add(entry.getNote()));
            Set<String> uncommonSet=new HashSet<>();
            for(int i=0;i<entryList.size()-1;i++){
                findUnCommonWords(entryList.get(i),entryList.get(i+1),uncommonSet);
            }
            List<String> strings=new ArrayList<>();
            entries.forEach(entry -> {
                strings.add(new StringBuilder().append(entry.getDate()).append(" ")
                        .append(entry.getTimestamp()).append(" ")
                .append(entry.getNote()).toString());

            });
            try {
                String[] stringArray=new String[strings.size()+1];
                stringArray=strings.toArray(stringArray);
                stringArray[strings.size()]= "The changing word was: "+uncommonSet.toString().replace("[","").replace("]","");
                fileWriter.writeSingleLine(stringArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(uncommonSet);

        });
        fileWriter.closeWriter();
        return null;
    }

    public void findUnCommonWords(String sentence1,String sentence2,Set<String> uncommonSet){
        List<String> wordList1= Arrays.asList(sentence1.split("\\s+"));
        List<String> wordList2= Arrays.asList(sentence2.split("\\s+"));

        for(int i=0;i<wordList1.size();i++){
            if(!wordList1.get(i).equalsIgnoreCase(wordList2.get(i))){
                uncommonSet.add(wordList1.get(i));
                uncommonSet.add(wordList2.get(i));
            }
        }
    }

}

package service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SentenceComparator implements Comparator<String> {

    @Override
    public int compare(String sentence1,String sentence2){
        List<String> wordList1= Arrays.asList(sentence1.split("\\s+"));
        List<String> wordList2= Arrays.asList(sentence2.split("\\s+"));

        if(wordList1.size()!=wordList2.size()){
            return wordList1.size()-wordList2.size();
        }
        int diffCount=0;
        for(int i=0;i<wordList1.size();i++){
            if(!wordList1.get(i).equalsIgnoreCase(wordList2.get(i))){
                diffCount++;
            }
        }

        if(diffCount==1){
            return 0;
        }else{
            return diffCount;
        }
    }
}

package NoviZadaci.TermFrequency75;

import java.io.InputStream;
import java.util.*;

public class TermFrequency {
    String text;
    String [] ignoreWords;
    Map<String, Integer> wordsCount;
    int numberWords;

    public TermFrequency(InputStream in, String[] ignoreWords) {
        this.ignoreWords = ignoreWords;
        wordsCount = new TreeMap<>();
        numberWords = 0;
        readFromIn(in);
    }

    public void readFromIn (InputStream in){
        Scanner sc = new Scanner(in);
        StringBuilder sb = new StringBuilder();

        while(sc.hasNext()){
            String word = transformWord(sc.next());
            sb.append(word);

            String noWord = Arrays.stream(ignoreWords).filter(w -> w.equals(word)).findFirst().orElse(null);
            if(noWord != null || word.isEmpty())
                continue;

            numberWords ++;

            wordsCount.putIfAbsent(word, 0);
            wordsCount.computeIfPresent(word, (key, value) -> value + 1);
        }

        text = sb.toString();
    }

    public String transformWord(String word){
        return word.toLowerCase().replace(",", "").replace(".", "").trim();
    }

    @Override
    public String toString() {
        return text;
    }

    public int countTotal() {
        return numberWords;
    }

    public int countDistinct() {
        return (int) wordsCount.entrySet().stream().map(e -> e.getKey()).count();
    }

    public List<String> mostOften(int i) {
        List<String>  mostOften = new ArrayList<String>();

       wordsCount.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
               .map(Map.Entry::getKey).limit(i).forEach(mostOften::add);

       return mostOften;
    }
}

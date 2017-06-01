package analyzer;

import analyzer.node.PartsOfSpeech;
import analyzer.node.WordCost;
import javafx.util.Pair;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CorpusCostManager {
    private ArrayList<WordCost> wordCosts = new ArrayList<>();
    private Map<String, Integer> indexes = new LinkedHashMap<>();

    public CorpusCostManager(String csvFile) throws IOException {

        FileReader fileReader = new FileReader(ClassLoader.getSystemResource(csvFile).getFile());
        ICsvListReader reader = new CsvListReader(fileReader, CsvPreference.STANDARD_PREFERENCE);
        List<String> values;
        String before = "";

        //  input
        while ((values = reader.read()) != null) {
            try {
                String word = values.get(0);
                String kana = values.get(1);
                Double cost = Double.parseDouble(values.get(3));
                PartsOfSpeech partsOfSpeech = PartsOfSpeech.get(values.get(16));

                Double prenounAdjectival = Double.parseDouble(values.get(5));
                Double noun = Double.parseDouble(values.get(6));
                Double adverb = Double.parseDouble(values.get(7));
                Double verb = Double.parseDouble(values.get(8));
                Double prefix = Double.parseDouble(values.get(9));
                Double conjuction = Double.parseDouble(values.get(10));
                Double auxiliaryVerb = Double.parseDouble(values.get(11));
                Double particle = Double.parseDouble(values.get(12));
                Double adjective = Double.parseDouble(values.get(13));
                Double period = Double.parseDouble(values.get(14));
                Double interjection = Double.parseDouble(values.get(15));
                wordCosts.add(new WordCost(word, kana, partsOfSpeech, cost,
                        prenounAdjectival,
                        noun,
                        adverb,
                        verb,
                        prefix,
                        conjuction,
                        auxiliaryVerb,
                        particle,
                        adjective,
                        period,
                        interjection));
            } catch (RuntimeException e) {
                //e.printStackTrace();
                System.out.println("Ignored to parse the line of csv: " + values);
            }
        }

        //  indexing
        wordCosts.sort((x1, x2) -> x1.getKana().compareTo(x2.getKana()));
        for (int i = 0; i < wordCosts.size(); i++) {
            WordCost wordCost = wordCosts.get(i);
            String initial = wordCost.getKana();
            if (!initial.equals(before)) {
                before = initial;
                indexes.put(initial, i);
            }
        }
    }

    private Pair<Integer, Integer> getIndexRange(String targetWord) {
        Integer index = indexes.get(getFirstCharacter(targetWord));
        Integer first = null;
        for (Integer i = index; i < wordCosts.size(); i++) {
            WordCost wordCost = wordCosts.get(i);
            if (first == null) {
                if (targetWord.equals(wordCost.getKana())) {
                    first = i;
                } else if (wordCost.getKana().startsWith(targetWord)) {
                    return new Pair<>(i - 1, i - 1);
                }
            } else if (!targetWord.equals(wordCost.getKana())) {
                return new Pair<>(first, i);
            }
        }
        return new Pair<>(-1, -1);
    }

    public List<WordCost> findAllByKana(String word) {
        Pair<Integer, Integer> interval = getIndexRange(word);
        if (interval.getKey() > 0) {
            return wordCosts.subList(interval.getKey(), interval.getValue());
        }
        return Collections.emptyList();
    }

    public Boolean isExistPartialMatchWord(String word) {
        Pair<Integer, Integer> interval = getIndexRange(word);
        if (interval.getKey() > 0 && interval.getValue() + 1 < wordCosts.size()) {
            WordCost wordCost = wordCosts.get(interval.getValue() + 1);
            return wordCost.getKana().startsWith(word);
        }
        return false;
    }

    public static String getFirstCharacter(String text) {
        List<String> strings = sparateCharacters(text);
        return strings.size() > 0 ? strings.get(0) : "";
    }

    public static List<String> sparateCharacters(String text) {
        return Arrays
                .stream(text.split(""))
                .filter(x -> !Objects.equals(x, ""))
                .collect(Collectors.toList());
    }
}

package analyzer;

import analyzer.node.WordCost;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SyntaxAnalyzer {
    private final CorpusCostManager corpusCostManager = new CorpusCostManager();

    public SyntaxAnalysisResult convert(String text) {
        try {
            corpusCostManager.open();

            ArrayList<WordCost> words = new ArrayList<>();
            Integer currentWordStringPoint = 0;
            List<String> strings = Arrays
                    .stream(text.split(""))
                    .filter(x -> !Objects.equals(x, ""))
                    .collect(Collectors.toList());

            while (currentWordStringPoint < strings.size()) {
                Integer currentStringPoint = 0;
                StringBuilder stringBuilder = new StringBuilder();
                Map<Double, WordCost> costTreeMap = new TreeMap<>();

                while (currentWordStringPoint + currentStringPoint < strings.size()) {
                    stringBuilder.append(strings.get(currentWordStringPoint + currentStringPoint));
                    String currentWord = stringBuilder.toString();

                    List<WordCost> list = corpusCostManager.findAllByKana(currentWord);
                    costTreeMap.putAll(list.stream().collect(
                            Collectors.toMap(x -> calculateCost(words, x), x -> x, (x1, x2) -> x1)));

                    if (list.size() == 0 && !corpusCostManager.isExistMatchedPartWord(currentWord)) {
                        break;
                    }
                    currentStringPoint++;
                }

                List<WordCost> list = costTreeMap.values().stream().collect(Collectors.toList());
                if (list.size() == 0) {
                    continue;
                }

                words.add(list.get(0));
                currentWordStringPoint += list.get(0).getKana().length();
            }

            corpusCostManager.close();
            return new SyntaxAnalysisResult(words);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Double calculateCost(final List<WordCost> wordHistories, WordCost wordCost) {
        return wordCost.getCost();
    }
}

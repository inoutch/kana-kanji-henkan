package analyzer;

import analyzer.node.WordCost;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Value
public class SyntaxAnalyzer {
    private final CorpusCostManager corpusCostManager;

    public SyntaxAnalysisResult convert(String text) {

        ArrayList<WordCost> words = new ArrayList<>();
        Integer currentWordStringPoint = 0;
        List<String> strings = CorpusCostManager.sparateCharacters(text);

        while (currentWordStringPoint < strings.size()) {
            Integer currentStringPoint = 0;
            StringBuilder stringBuilder = new StringBuilder();
            Multimap<Double, WordCost> costTreeMap = TreeMultimap.create(Double::compare, (x1, x2) -> x1.getKana().compareTo(x2.getKana()));

            while (currentWordStringPoint + currentStringPoint < strings.size()) {
                stringBuilder.append(strings.get(currentWordStringPoint + currentStringPoint));
                String currentWord = stringBuilder.toString();

                List<WordCost> list = corpusCostManager.findAllByKana(currentWord);
                list.forEach(x -> costTreeMap.put(calculateCost(words, x), x));

                if (list.size() == 0 && !corpusCostManager.isExistPartialMatchWord(currentWord)) {
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

        return new SyntaxAnalysisResult(words);
    }

    private Double calculateCost(final List<WordCost> wordHistories, WordCost wordCost) {
        return wordCost.getCost();
    }
}

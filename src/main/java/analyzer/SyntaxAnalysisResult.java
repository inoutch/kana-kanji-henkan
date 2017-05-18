package analyzer;

import analyzer.node.WordCost;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;


@RequiredArgsConstructor
@Value
public class SyntaxAnalysisResult {

    @NonNull
    private final List<WordCost> words;

    public String toString() {
        if (words.size() <= 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        words.forEach(x -> stringBuilder.append(x.getWord()));
        return stringBuilder.toString();
    }
}

package analyzer.node;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class WordCost {
    @NonNull
    private final String word;

    @NonNull
    private final String kana;

    @NonNull
    private final PartsOfSpeech partsOfSpeech;

    @NonNull
    private final Double cost;
}

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

    @NonNull
    private final Double prenounAdjectival;

    @NonNull
    private final Double noun;

    @NonNull
    private final Double adverb;

    @NonNull
    private final Double verb;

    @NonNull
    private final Double prefix;

    @NonNull
    private final Double conjuction;

    @NonNull
    private final Double auxiliaryVerb;

    @NonNull
    private final Double particle;

    @NonNull
    private final Double adjective;

    @NonNull
    private final Double period;

    @NonNull
    private final Double interjection;

    public String toString() {
        return kana;
    }
}

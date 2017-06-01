package analyzer.node;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PartsOfSpeech {
    NOUN(0, "名詞"),
    PRONOUN(1, "代名詞"),
    ADJECTIVE(2, "形容詞"),
    VERB(3, "動詞"),
    ADVERB(4, "副詞"),
    PREPOSITION(5, "前置詞"),
    CONJUCTION(6, "接続詞"),
    INTERJECTION(7, "感動詞"),
    PARTICLE(8, "助詞"),
    SYMBOL(9, "記号"),
    PRENOUN_ADJECTIVAL(10, "連体詞"),
    AUXILIARY_VERB(11, "助動詞"),
    PREFIX(12, "接頭詞"),
    FILER(13, "フィラー"),
    OTHER(14, "その他"),
    UNDEFINED(15, "未定義"),
    PERIOD(16, "句点");

    private final Integer code;
    private final String displayName;

    public static PartsOfSpeech get(String displayName) {
        for (PartsOfSpeech e : values()) {
            if (e.displayName.equals(displayName)) return e;
        }
        return null;
    }

    public static PartsOfSpeech get(Integer code) {
        for (PartsOfSpeech e : values()) {
            if (e.code.equals(code)) return e;
        }
        return null;
    }
}

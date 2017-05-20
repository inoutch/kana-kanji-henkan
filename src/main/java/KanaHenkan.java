import analyzer.CorpusCostManager;
import analyzer.SyntaxAnalysisResult;
import analyzer.SyntaxAnalyzer;

public class KanaHenkan {
    public static void main(String args[]) {
        String testText = "こんにちは、あなたはなにものですか";

        try {
            CorpusCostManager corpusCostManager = new CorpusCostManager("tangoCost_ver2_s_utf8.csv");
            SyntaxAnalyzer analyzer = new SyntaxAnalyzer(corpusCostManager);
            SyntaxAnalysisResult result = analyzer.convert(testText);

            System.out.println("入力: " + testText);
            System.out.println("出力: " + result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import analyzer.CorpusCostManager;
import analyzer.SyntaxAnalysisResult;
import analyzer.SyntaxAnalyzer;

import java.io.IOException;
import java.sql.SQLException;

public class KanaHenkan {
    public static void main(String args[]) {
        /*CorpusCostManager corpusCostManager = new CorpusCostManager();
        try {
            corpusCostManager.create("tangoCost_ver2_utf8.csv");
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }*/
        String testText = "こんにちは、あなたはなにものですか";
        SyntaxAnalyzer analyzer = new SyntaxAnalyzer();

        try {
            SyntaxAnalysisResult result = analyzer.convert(testText);
            System.out.println(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

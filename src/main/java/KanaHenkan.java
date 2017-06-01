import analyzer.CorpusCostManager;
import analyzer.SyntaxAnalysisResult;
import analyzer.SyntaxAnalyzer;

import java.util.Scanner;

public class KanaHenkan {

    public static void main(String args[]) {
        try {
            //System.out.print("Please enter kana text: ");
            //Scanner scanner = new Scanner(System.in);
            //String input = "こんにちは、きょうはいいてんきですね";//scanner.nextLine();
            String input = "あしたもはれるといいですね";//scanner.nextLine();

            System.out.println("--- Loading corpus ---");
            CorpusCostManager corpusCostManager = new CorpusCostManager("costTable_utf8.csv");


            SyntaxAnalyzer analyzer = new SyntaxAnalyzer(corpusCostManager);
            SyntaxAnalysisResult result = analyzer.convert(input);

            System.out.println("--- Loading corpus ---");
            System.out.println("Input\t: " + input);
            System.out.println("Output\t: " + result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

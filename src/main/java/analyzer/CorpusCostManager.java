package analyzer;

import analyzer.node.PartsOfSpeech;
import analyzer.node.WordCost;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CorpusCostManager {
    private Connection connection;

    private static final String databaseName = "word_cost.db";

    public void create(String csvFile) throws ClassNotFoundException, SQLException, IOException {
        final String url = "jdbc:sqlite:" + databaseName;

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(url);

        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS word_costs (" +
                        "name TEXT PRIMARY KEY ," +
                        "kana TEXT," +
                        "parts_of_speech INTEGER," +
                        "cost NUMBER)");

        FileReader fileReader = new FileReader(ClassLoader.getSystemResource(csvFile).getFile());
        ICsvListReader reader = new CsvListReader(fileReader, CsvPreference.STANDARD_PREFERENCE);
        List<String> values;

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO word_costs (name, kana, parts_of_speech, cost) VALUES (?, ?, ?, ?)");

        while ((values = reader.read()) != null) {
            try {
                String word = values.get(0);
                String kana = values.get(1);
                Double cost = Double.parseDouble(values.get(3));
                PartsOfSpeech partsOfSpeech = PartsOfSpeech.get(values.get(5));

                preparedStatement.setString(1, word);
                preparedStatement.setString(2, kana);
                preparedStatement.setInt(3, partsOfSpeech.getCode());
                preparedStatement.setDouble(4, cost);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ignored to parse the line of csv: " + values);
            }
        }

        connection.close();
    }

    public void open() throws ClassNotFoundException, SQLException {
        final String url = "jdbc:sqlite:" + databaseName;

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(url);
    }

    public void close() throws SQLException {
        connection.close();
    }

    public List<WordCost> findAllByKana(String word) {
        try {
            ArrayList<WordCost> wordCosts = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM word_costs WHERE kana = ?");
            statement.setString(1, word);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                wordCosts.add(new WordCost(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        PartsOfSpeech.get(resultSet.getInt(3)),
                        resultSet.getDouble(4)));
            }
            return wordCosts;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Boolean isExistMatchedPartWord(String word) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM word_costs WHERE kana = ? ");
            statement.setString(1, word);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

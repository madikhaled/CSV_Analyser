import java.io.IOException;
import java.util.List;
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        CsvAnalyser csv = new CsvAnalyser();
        String records = csv.parse("src/data.csv").toString();

            System.out.println(records);


    }
}
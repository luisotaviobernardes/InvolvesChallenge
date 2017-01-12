package luis.bernardes.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSVReader {

  /**
   * inspired by: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
   **/

  public static List<String> read(String path) {
    String line;

    try(BufferedReader buffer = new BufferedReader(new FileReader(path))) {
      List<String> result = new ArrayList<>();

      while ((line = buffer.readLine()) != null) {
        result.add(line);
      }

      return result;
    } catch(IOException ex) {
      return Collections.emptyList();
    }
  }
}

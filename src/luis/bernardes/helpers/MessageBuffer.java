package luis.bernardes.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageBuffer {
  private static List<String> buffer = new ArrayList<>();

  private static void initialize() {
    MessageBuffer.buffer = new ArrayList<>();
  }

  private static void store(List<String> messages) {
    MessageBuffer.buffer.addAll(messages);
  }

  public static void store(String... messages) {
    MessageBuffer.store(Arrays.asList(messages));
  }

  public static List<String> get() {
    // moves off and caches out.
    List<String> data = MessageBuffer.buffer;
    initialize();

    return data;
  }
}

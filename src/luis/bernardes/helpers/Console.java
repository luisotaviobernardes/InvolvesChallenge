package luis.bernardes.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Console {
  private static final String DEFAULT_CLEAR_REGEX = "\033[H\033[2J";
  private static final String DEFAULT_SLASH_SEPARATOR = " - ";
  private static final String DEFAULT_LIST_BULLET = "* ";
  private static final int DEFAULT_LIST_SPACING = 2;
  private static final String DEFAULT_ERROR_PREFIX = "[Error]: ";

  private static void flush() {
    System.out.flush();
  }

  private static void print(String text) {
    System.out.println(text);
  }

  private static void skip() {
    Console.print(StringHelper.spacing(1));
  }

  private static void printWithBulletAndSpacing(String text, String bullet, int spacing) {
    StringBuilder builder = new StringBuilder();

    builder.append(StringHelper.spacing(spacing));
    builder.append(bullet);
    builder.append(text);
    Console.print(builder.toString());
  }

  private static void printWithSlash(String text) {
    Console.print(DEFAULT_SLASH_SEPARATOR + text);
  }

  public static void clear() {
    Console.print(DEFAULT_CLEAR_REGEX);
    Console.flush();
  }

  private static void waitInput() {
    Console.message(" (press any key to exit) ");
    Console.input();
  }

  public static void error(String text, Boolean willWaitForInput) {
    Console.print(DEFAULT_ERROR_PREFIX + text);

    if (willWaitForInput) {
      Console.waitInput();
    }
  }

  public static void error(String text) {
    Console.error(text, false);
  }

  public static void messages(List<String> messages, Boolean willWaitForInput) {
    messages.forEach(Console::message);

    if (willWaitForInput) {
      Console.waitInput();
    }
  }

  public static void message(String text) {
    Console.print(text);
  }

  public static void title(String text) {
    Console.clear();
    Console.printWithSlash(text);
    Console.skip();
  }

  public static void list(String title, Boolean willWaitForInput, List<String> messages) {
    if (!messages.isEmpty()) {
      if (StringHelper.isValid(title)) {
        Console.title(title);
      }

      messages.stream().forEach(text -> Console.printWithBulletAndSpacing(text, DEFAULT_LIST_BULLET, DEFAULT_LIST_SPACING));
      Console.skip();

      if (willWaitForInput) {
        Console.waitInput();
      }
    }
  }

  public static void list(String title, Boolean willWaitForInput, String... messages) {
    Console.list(title, willWaitForInput, Arrays.asList(messages));
  }

  public static void list(String title, String... messages) {
    Console.list(title, false, messages);
  }

  public static String input() {
    try {
      BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
      return buffer.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return StringHelper.EMPTY;
    }
  }
}

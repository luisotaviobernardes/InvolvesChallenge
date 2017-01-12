package luis.bernardes.helpers;

import static java.util.Objects.isNull;

public class StringHelper {
  private static final String EMPTY_STRING_REFERENCE = "";
  private static final String SPACE_STRING_REFERENCE = " ";

  // org.apache.commons.lang.StringUtils.EMPTY
  public static String EMPTY = EMPTY_STRING_REFERENCE;

  // checks if not null or empty
  public static Boolean isValid(String text) {
    return (!isNull(text) && !text.isEmpty());
  }

  // search the string for an argument and removes it returning only what's left..
  public static String removeFromString(String full, String removable) {
    return full.replace(removable, EMPTY_STRING_REFERENCE);
  }

  public static String trimSpaces(String text) {
    return text.trim();
  }

  public static String spacing(int spacing) {
    return StringHelper.repeat(SPACE_STRING_REFERENCE, spacing);
  }

  public static String remove(String text, int index) {
    return text.substring(0, index);
  }

  public static String repeat(String text, int times) {
    StringBuilder builder = new StringBuilder();

    for(int i = 0; i < times; i++) {
      builder.append(text);
    }

    return builder.toString();
  }
}

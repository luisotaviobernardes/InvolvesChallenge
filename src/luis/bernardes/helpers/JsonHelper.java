package luis.bernardes.helpers;

import java.util.Arrays;
import java.util.List;

public class JsonHelper {
  private static final String DEFAULT_SEPARATOR = ",";
  private static final String DEFAULT_OPEN_OBJECT = "{";
  private static final String DEFAULT_CLOSE_OBJECT = "}";
  private static final String DEFAULT_OPEN_LIST = "[";
  private static final String DEFAULT_CLOSE_LIST = "]";
  private static final String DEFAULT_COMMA = "\"";
  private static final String DEFAULT_ATTRIBUTION = ": ";

  private static String asListOfEntries(List<String> arguments) {
    return String.join(DEFAULT_SEPARATOR, arguments);
  }

  private static String applyCommas(String text) {
    return DEFAULT_COMMA + text + DEFAULT_COMMA;
  }

  private static String withObjectBrackets(String pairing) {
    return DEFAULT_OPEN_OBJECT + pairing + DEFAULT_CLOSE_OBJECT;
  }

  private static String withListBrackets(String pairing) {
    return DEFAULT_OPEN_LIST + pairing + DEFAULT_CLOSE_LIST;
  }

  private static String withKeyAndValuePairing(String key, String value) {
    return applyCommas(key) + DEFAULT_ATTRIBUTION + value;
  }

  private static String asObject(List<String> arguments) {
    return withObjectBrackets(asListOfEntries(arguments));
  }

  public static String asKeyAndStringValue(String key, String value) {
    return withKeyAndValuePairing(key, applyCommas(value));
  }

  public static String asKeyAndObjectValue(String key, String value) {
    return withKeyAndValuePairing(key, value);
  }

  public static String asKeyAndIntegerValue(String key, String value) {
    return withKeyAndValuePairing(key, value);
  }

  public static String asKeyandListOfObjectValue(String key, List<String> value) {
    return withKeyAndValuePairing(key, withListBrackets(asListOfEntries(value)));
  }

  public static String asObject(String... arguments) {
    return asObject(Arrays.asList(arguments));
  }

  public static String asListOfObject(List<String> arguments) {
    return withListBrackets(asObject(arguments));
  }

  public static String asKeyAndBooleanValue(String key, Boolean value) {
    return withKeyAndValuePairing(key, String.valueOf(value));
  }
}

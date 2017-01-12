package luis.bernardes.helpers;

import static java.util.Objects.isNull;

public class LongHelper {
  private static final Long NULL_LONG = null;

  // checks for null only at the moment!
  public static Boolean isValid(Long instance) {
    return (!isNull(instance));
  }

  public static Long parse(String text) {
    try {
      return Long.valueOf(text);
    } catch(NumberFormatException ex) {
      return NULL_LONG;
    }
  }
}

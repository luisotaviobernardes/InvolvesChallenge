package luis.bernardes.enumerators;

import luis.bernardes.helpers.MessageBuffer;

import java.util.List;
import java.util.stream.Collectors;

public enum CommandType {
  DISPLAY("DISPLAY", true),
  DISTINCT("DISTINCT", true),
  FILTER("FILTER", true),
  ORDER("ORDER", true),
  INVALID("INVALID", false);

  private String name;
  private Boolean isValid;

  private CommandType(String name, Boolean isValid) {
    this.isValid = isValid;
  }

  public Boolean isValid() {
    return this.isValid;
  }

  public static Boolean validate(List<CommandType> types) {
    for(CommandType type : CommandType.values()) {
      List<CommandType> allByTypes = types.stream().filter(cur -> (cur.isValid && cur.equals(type))).collect(Collectors.toList());

      // only one of each is allowed...
      if (allByTypes.size() > 1) {
        MessageBuffer.store("Invalid query, command type: " + type.name + " is declared more than once.");
        return false;
      }
    }

    // all is good...
    return true;
  }
}

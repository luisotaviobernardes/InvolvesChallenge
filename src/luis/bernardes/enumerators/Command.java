package luis.bernardes.enumerators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Command {
  COUNT("count", CommandType.DISPLAY, true),
  SELECT("select", CommandType.DISPLAY, true),
  DISTINCT("distinct", CommandType.DISTINCT, true),
  FILTER("filter", CommandType.FILTER, true),
  ORDER("order", CommandType.ORDER, true),
  INVALID("invalid", CommandType.INVALID, false);

  private String name;
  private CommandType type;
  private Boolean isValid;

  private Command(String name, CommandType type, Boolean isValid) {
    this.name = name;
    this.type = type;
    this.isValid = isValid;
  }

  public String getName() {
    return this.name;
  }

  public Boolean isValid() {
    return this.isValid;
  }

  public CommandType getType() {
    return this.type;
  }

  public static Command getInString(String text) {
    Optional<Command> promise = Arrays.asList(Command.values()).stream().filter(cmd -> text.contains(cmd.name)).findAny();
    return (promise.isPresent()) ? promise.get() : Command.INVALID;
  }

  public static Boolean validate(List<Command> commands) {
    return CommandType.validate(commands.stream().filter(command -> command.isValid).map(command -> command.type).collect(Collectors.toList()));
  }

  public static List<Command> getAll() {
    return Arrays.asList(Command.values()).stream().filter(Command::isValid).collect(Collectors.toList());
  }

  public static String getAllAsString() {
    return String.join(", ", Command.getAll().stream().map(cmd -> cmd.name).collect(Collectors.toList()));
  }
}

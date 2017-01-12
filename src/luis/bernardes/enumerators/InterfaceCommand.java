package luis.bernardes.enumerators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum InterfaceCommand {
  EXIT(true, "exit", "quit"),
  HELP(true, "help"),
  QUERY(true, "query"),
  INVALID(false);

  private Boolean isValid;
  private List<String> commands;

  private InterfaceCommand(Boolean isValid, String... commands) {
    this.isValid = isValid;
    this.commands = Arrays.asList(commands);
  }

  public Boolean matches(String command) {
    return this.commands.contains(command);
  }

  public static InterfaceCommand getByCommand(String command) {
    Optional<InterfaceCommand> promise = Arrays.asList(InterfaceCommand.values()).stream()
      .filter(cmd -> cmd.commands.contains(command.toLowerCase())).findAny();

    return (promise.isPresent()) ? promise.get() : InterfaceCommand.INVALID;
  }
}

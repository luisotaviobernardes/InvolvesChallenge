package luis.bernardes.resource;

import luis.bernardes.enumerators.Command;
import luis.bernardes.helpers.JsonHelper;
import luis.bernardes.helpers.MessageBuffer;
import luis.bernardes.helpers.StringHelper;
import luis.bernardes.services.command.api.CommandService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Query {
  public Command command;
  public List<Argument> arguments;
  public Boolean isValid;

  private Query(Command command, List<Argument> arguments, Boolean isValid) {
    this.command = command;
    this.arguments = arguments;
    this.isValid = isValid;
  }

  public String toJson() {
    return JsonHelper.asObject(
      JsonHelper.asKeyAndStringValue("command", this.command.getName()),
      JsonHelper.asKeyandListOfObjectValue("arguments", this.arguments.stream().map(Argument::toJson).collect(Collectors.toList())),
      JsonHelper.asKeyAndBooleanValue("isValid", this.isValid)
    );
  }

  private static Query create(Command command, List<Argument> arguments, Boolean isValid) {
    return new Query(command, arguments, isValid);
  }

  private static Query create(Command command, List<Argument> arguments) {
    return Query.create(command, arguments, validate(command, arguments));
  }

  private static Boolean validate(Command command, List<Argument> arguments) {
    // checks if command is valid and arguments are not empty...
    return (command.isValid() && (!arguments.isEmpty()));
  }

  private static Query create(String argument) {
    // grabs the command inside the string.
    Command key = Command.getInString(argument);

    // splits with the default separator.
    List<Argument> value = Argument.create(
      Arrays.asList(StringHelper.removeFromString(argument, key.getName()).split(CommandService.SEPARATOR))
    );

    if (!key.isValid()) {
      MessageBuffer.store("Did not found a suitable command in: " + argument);
    }

    if (value.isEmpty()) {
      MessageBuffer.store("no suitable fields or values found in: " + argument);
    }

    return Query.create(key, value);
  }

  public static List<Query> create(List<String> arguments) {
    List<Query> all = arguments.stream().map(Query::create).collect(Collectors.toList());

    // filter so we return only the valid ones..
    return all.stream().filter(arg -> arg.isValid).collect(Collectors.toList());
  }
}

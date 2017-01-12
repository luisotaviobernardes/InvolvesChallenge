package luis.bernardes.services.interfaces.impl;

import luis.bernardes.enumerators.Command;
import luis.bernardes.enumerators.InterfaceCommand;
import luis.bernardes.enumerators.LayoutField;
import luis.bernardes.helpers.Console;
import luis.bernardes.helpers.MessageBuffer;
import luis.bernardes.services.city.api.CityService;
import luis.bernardes.services.command.api.CommandService;
import luis.bernardes.services.interfaces.api.InterfaceService;
import java.util.List;

public class DefaultInterfaceServiceImpl implements InterfaceService {
  private Boolean isCurrentlyRunning;
  private CityService cityService;
  private CommandService commandService;

  private DefaultInterfaceServiceImpl(Boolean isCurrentlyRunning, CommandService commandService, CityService cityService) {
    this.isCurrentlyRunning = isCurrentlyRunning;
    this.commandService = commandService;
    this.cityService = cityService;
  }

  public static DefaultInterfaceServiceImpl create(CommandService commandService, CityService cityService) {
    return new DefaultInterfaceServiceImpl(true, commandService, cityService);
  }

  @Override
  public void run() {
    Console.title("welcome! (type command or help for instructions) ");
    this.interpret(InterfaceCommand.getByCommand(Console.input()));
  }

  private void interpret(InterfaceCommand command) {
    switch(command) {

      case EXIT:
        this.exit();
        break;

      case HELP:
        this.help();
        break;

      case QUERY:
        this.query();
        break;

      default:
        Console.error("unrecognizable command", true);
        break;
    }
  }

  private void exit() {
    Console.clear();
    this.isCurrentlyRunning = false;
  }

  private void help() {
    Console.list("help", true, "to run a query, type 'query'", "to exit, type 'exit'");
  }

  private void query() {
    Console.list(
      "query",
      "commands: " + Command.getAllAsString(),
      "you can also queue then together by using '&&' and chose multiple fields by using ','",
      "ex: select <FIELD> && filter <FIELD-1> <VALUE-1>, <FIELD-2> <VALUE-2> && ORDER <FIELD>",
      "current fields available for use: " + LayoutField.getAllAsString(),
      "note: you can only select one command type per query!",
      "also note: if you did not provide any display type command(count or select), it will display the entire table."
    );
    Console.message(" (type the query you want to run) ");
    List<String> result = this.cityService.query(this.commandService.interpret(Console.input()));

    Console.list("Warning, the following errors were found during validation: ", true, MessageBuffer.get());
    Console.messages(result, true);
  }

  @Override
  public Boolean isRunning() {
    return this.isCurrentlyRunning;
  }
}

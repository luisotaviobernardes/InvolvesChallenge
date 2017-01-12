package luis.bernardes.services.command.api;

import luis.bernardes.resource.Procedure;

public interface CommandService {
  String SEPARATOR = ",";
  String OPERATOR = "&&";

  Procedure interpret(String argument);
}

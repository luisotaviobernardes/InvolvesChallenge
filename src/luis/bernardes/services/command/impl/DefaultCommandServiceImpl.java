package luis.bernardes.services.command.impl;

import luis.bernardes.resource.Procedure;
import luis.bernardes.services.command.api.CommandService;

public class DefaultCommandServiceImpl implements CommandService {
  private DefaultCommandServiceImpl() { }

  public static DefaultCommandServiceImpl create() {
    return new DefaultCommandServiceImpl();
  }

  @Override
  public Procedure interpret(String argument) {
    return Procedure.create(argument);
  }
}

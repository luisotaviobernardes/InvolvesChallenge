package luis.bernardes;

import luis.bernardes.helpers.Console;
import luis.bernardes.services.interfaces.api.InterfaceService;
import luis.bernardes.utils.Bootstrap;

public class Main {
  private static InterfaceService interfaceService;

  public static void main(String[] args) {
    if (!Bootstrap.initialize()) {
      Console.error("application failed to initialize");
    } else {
      Main.interfaceService = Bootstrap.injectInterfaceService();

      while(interfaceService.isRunning()) {
        interfaceService.run();
      }
    }
  }
}

package luis.bernardes.services.city.api;

import luis.bernardes.resource.Procedure;
import java.util.List;

public interface CityService {
  List<String> query(Procedure procedure);
}

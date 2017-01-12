package luis.bernardes.services.city.api;

import luis.bernardes.enumerators.LayoutField;
import luis.bernardes.models.City;
import java.util.List;

public interface CityTemplateService {
  List<String> display(List<City> cities, List<LayoutField> displayable);
  List<String> count(List<City> cities, String placeholder);
}

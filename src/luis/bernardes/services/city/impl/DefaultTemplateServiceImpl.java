package luis.bernardes.services.city.impl;

import luis.bernardes.enumerators.LayoutField;
import luis.bernardes.helpers.StringHelper;
import luis.bernardes.models.City;
import luis.bernardes.services.city.api.CityTemplateService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultTemplateServiceImpl implements CityTemplateService {
  private static final String DEFAULT_SEPARATOR = "|";
  private static final int DEFAULT_TEMPLATE_MAXIMUM_SIZE = 130;
  private static final String DEFAULT_CLOSURE_CHARACTER = "_";
  private static final String DEFAULT_ATTRIBUTION_CHARACTER = ": ";

  private String separator;
  private int size;
  private String closure;
  private String attribution;

  private DefaultTemplateServiceImpl(String separator, int size, String closure, String attribution) {
    this.separator = separator;
    this.size = size;
    this.closure = closure;
    this.attribution = attribution;
  }

  public static DefaultTemplateServiceImpl create() {
    return new DefaultTemplateServiceImpl(
      DEFAULT_SEPARATOR, DEFAULT_TEMPLATE_MAXIMUM_SIZE, DEFAULT_CLOSURE_CHARACTER, DEFAULT_ATTRIBUTION_CHARACTER
    );
  }

  @Override
  public List<String> display(List<City> cities, List<LayoutField> displayable) {
    return this.build(cities, displayable);
  }

  @Override
  public List<String> count(List<City> cities, String placeholder) {
    return Collections.singletonList(placeholder + this.attribution + cities.size());
  }

  private List<String> build(List<City> cities, List<LayoutField> fields) {
    List<String> result = new ArrayList<>();
    int allFieldsSize = fields.stream().mapToInt(LayoutField::getMaxSize).sum();
    Boolean asMaxSize = this.willBuildOnMaxSize(allFieldsSize);
    String header = this.header(fields, asMaxSize);
    String boxFrame = StringHelper.repeat(this.closure, header.length());

    result.add(boxFrame);
    result.add(header);
    cities.stream().forEach(city -> result.add(this.row(city, fields, asMaxSize)));
    result.add(boxFrame);
    return result;
  }

  private String row(City city, List<LayoutField> fields, Boolean asMaxsize) {
    return withSeparators(String.join(this.separator, fields.stream()
      .map(field -> this.buildWithLayoutSpacing(city.getValueByField(field), field.getSize(asMaxsize))).collect(Collectors.toList())));
  }

  private String header(List<LayoutField> fields, Boolean asMaxsize) {
    return withSeparators(String.join(this.separator, fields.stream()
      .map(field -> this.buildWithLayoutSpacing(field.getDescription(), field.getSize(asMaxsize))).collect(Collectors.toList())));
  }

  private Boolean willBuildOnMaxSize(int totalSizeOfFields) {
    return (this.size >= totalSizeOfFields);
  }

  private String buildWithLayoutSpacing(String text, int size) {
    int spacing = (size - text.length());

    return (spacing < 0)
      ? StringHelper.remove(text, (text.length() - (text.length() - size)))
      : StringHelper.spacing(spacing) + text;
  }

  private String withSeparators(String text) {
    return this.separator + text + this.separator;
  }
}

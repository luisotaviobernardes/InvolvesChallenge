package luis.bernardes.resource.strategy.impl;

import luis.bernardes.helpers.CSVReader;
import luis.bernardes.helpers.LongHelper;
import luis.bernardes.resource.CityResource;
import luis.bernardes.enumerators.LayoutField;
import luis.bernardes.resource.strategy.api.CityIOStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WithKnownHeaderPositionCityIOStrategyImpl implements CityIOStrategy {
  private static final String DEFAULT_SEPARATOR = ",";

  private String path;
  private String separator;
  private Map<String, Integer> mapping;

  private WithKnownHeaderPositionCityIOStrategyImpl(String path, Map<String, Integer> mapping, String separator) {
    this.path = path;
    this.mapping = mapping;
    this.separator = separator;
  }

  public static WithKnownHeaderPositionCityIOStrategyImpl create(String path) {
    return new WithKnownHeaderPositionCityIOStrategyImpl(
      path, WithKnownHeaderPositionCityIOStrategyImpl.mapDefaultLayoutPositions(), DEFAULT_SEPARATOR
    );
  }

  @Override
  public List<CityResource> process() {
    return CSVReader.read(this.path).stream().map(this::build).collect(Collectors.toList());
  }

  private CityResource build(String line) {
    String[] info = line.split(this.separator);

    Long ibge = LongHelper.parse(info[LayoutField.IBGE.getPosition()]);
    String uf = info[LayoutField.UF.getPosition()];
    String name = info[LayoutField.NAME.getPosition()];
    Boolean isCapital = Boolean.valueOf(info[LayoutField.IS_CAPITAL.getPosition()]);
    String longitude = info[LayoutField.LONGITUDE.getPosition()];
    String latitude = info[LayoutField.LATITUDE.getPosition()];
    String nameNoAccents = info[LayoutField.NAME_NO_ACCENTS.getPosition()];
    String alternativeNames = info[LayoutField.ALTERNATIVE_NAMES.getPosition()];
    String microRegion = info[LayoutField.MICROREGION.getPosition()];
    String mesoRegion = info[LayoutField.MESOREGION.getPosition()];

    return LongHelper.isValid(ibge)
      ? CityResource.create(ibge, uf, name, isCapital, longitude, latitude, nameNoAccents, alternativeNames, microRegion, mesoRegion)
      : CityResource.empty();
  }

  private static Map<String, Integer> mapDefaultLayoutPositions() {
    Map<String, Integer> result = new HashMap<>();

    LayoutField.getAll().stream().map(set -> result.put(set.getName(), set.getPosition()));
    return result;
  }
}

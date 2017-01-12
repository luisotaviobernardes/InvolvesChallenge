package luis.bernardes.models;

import luis.bernardes.enumerators.LayoutField;
import luis.bernardes.helpers.JsonHelper;
import luis.bernardes.helpers.LongHelper;
import luis.bernardes.helpers.MessageBuffer;
import luis.bernardes.helpers.StringHelper;
import luis.bernardes.resource.CityResource;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class City {
  private Long ibge;
  private String uf;
  private String name;
  private Boolean isCapital;
  private String longitude;
  private String latitude;
  private String nameNoAccents;
  private String alternativeNames;
  private String microRegion;
  private String mesoRegion;

  private City(Long ibge, String uf, String name, Boolean isCapital, String longitude, String latitude, String nameNoAccents, String alternativeNames, String microRegion, String mesoRegion) {
    this.ibge = ibge;
    this.uf = uf;
    this.name = name;
    this.isCapital = isCapital;
    this.longitude = longitude;
    this.latitude = latitude;
    this.nameNoAccents = nameNoAccents;
    this.alternativeNames = alternativeNames;
    this.microRegion = microRegion;
    this.mesoRegion = mesoRegion;
  }

  private static City create(Long ibge, String uf, String name, Boolean isCapital, String longitude, String latitude, String nameNoAccents, String alternativeNames, String microRegion, String mesoRegion) {
    return new City(ibge, uf, name, isCapital, longitude, latitude, nameNoAccents, alternativeNames, microRegion, mesoRegion);
  }

  public static City create(CityResource resource) {
    return City.create(
      resource.ibge,
      resource.uf,
      resource.name,
      resource.isCapital,
      resource.longitude,
      resource.latitude,
      resource.nameNoAccents,
      resource.alternativeNames,
      resource.microRegion,
      resource.mesoRegion
    );
  }

  public String toJson() {
    return JsonHelper.asObject(
      JsonHelper.asKeyAndIntegerValue("ibge", String.valueOf(this.ibge)),
      JsonHelper.asKeyAndIntegerValue("uf", String.valueOf(this.uf)),
      JsonHelper.asKeyAndIntegerValue("name", String.valueOf(this.name)),
      JsonHelper.asKeyAndIntegerValue("isCapital", String.valueOf(this.isCapital)),
      JsonHelper.asKeyAndIntegerValue("nameNoAccents", String.valueOf(this.nameNoAccents)),
      JsonHelper.asKeyAndIntegerValue("alternativeNames", String.valueOf(this.alternativeNames)),
      JsonHelper.asKeyAndIntegerValue("microRegion", String.valueOf(this.microRegion)),
      JsonHelper.asKeyAndIntegerValue("mesoRegion", String.valueOf(this.mesoRegion))
    );
  }

  public String getValueByField(LayoutField field) {
    switch(field) {
      case IBGE:
        return String.valueOf(this.ibge);

      case UF:
        return this.uf;

      case NAME:
        return this.name;

      case IS_CAPITAL:
        return String.valueOf(this.isCapital);

      case LONGITUDE:
        return this.longitude;

      case LATITUDE:
        return this.latitude;

      case NAME_NO_ACCENTS:
        return this.nameNoAccents;

      case ALTERNATIVE_NAMES:
        return this.alternativeNames;

      case MICROREGION:
        return this.microRegion;

      case MESOREGION:
        return this.mesoRegion;

      default:
        return StringHelper.EMPTY;
    }
  }

  public static Predicate<City> filter(LayoutField field, String value) {
    switch(field) {
      case IBGE:
        return (arg -> arg.ibge.equals(LongHelper.parse(value)));

      case UF:
        return (arg -> arg.uf.equals(value));

      case NAME:
        MessageBuffer.store("Warning: 'name' field may contain accents on characters, if you want to filter without accents, use the 'no_accents' field");
        return (arg -> arg.name.equals(value));

      case IS_CAPITAL:
        return (arg -> arg.isCapital.equals(Boolean.valueOf(value)));

      case LONGITUDE:
        return (arg -> arg.longitude.equals(value));

      case LATITUDE:
        return (arg -> arg.latitude.equals(value));

      case NAME_NO_ACCENTS:
        return (arg -> arg.nameNoAccents.equals(value));

      case ALTERNATIVE_NAMES:
        return (arg -> arg.alternativeNames.equals(value));

      case MICROREGION:
        return (arg -> arg.microRegion.equals(value));

      case MESOREGION:
        return (arg -> arg.mesoRegion.equals(value));

      default:
        return null;
    }
  }

  public static Comparator<City> order(LayoutField field) {
    switch(field) {
      case IBGE:
        return (((city1, city2) -> city1.ibge.compareTo(city2.ibge)));

      case UF:
        return (((city1, city2) -> city1.uf.compareTo(city2.uf)));

      case NAME:
        return (((city1, city2) -> city1.name.compareTo(city2.name)));

      case IS_CAPITAL:
        return (((city1, city2) -> city1.isCapital.compareTo(city2.isCapital)));

      case LONGITUDE:
        return (((city1, city2) -> city1.longitude.compareTo(city2.longitude)));

      case LATITUDE:
        return (((city1, city2) -> city1.latitude.compareTo(city2.latitude)));

      case NAME_NO_ACCENTS:
        return (((city1, city2) -> city1.nameNoAccents.compareTo(city2.nameNoAccents)));

      case ALTERNATIVE_NAMES:
        return (((city1, city2) -> city1.alternativeNames.compareTo(city2.alternativeNames)));

      case MICROREGION:
        return (((city1, city2) -> city1.microRegion.compareTo(city2.microRegion)));

      case MESOREGION:
        return (((city1, city2) -> city1.mesoRegion.compareTo(city2.mesoRegion)));

      default:
        return null;
    }
  }

  public static Predicate<City> distinct(LayoutField field) {
    switch(field) {
      case IBGE:
        return City.distinctByKey(city -> city.ibge);

      case UF:
        return City.distinctByKey(city -> city.uf);

      case NAME:
        return City.distinctByKey(city -> city.name);

      case IS_CAPITAL:
        return City.distinctByKey(city -> city.isCapital);

      case LONGITUDE:
        return City.distinctByKey(city -> city.longitude);

      case LATITUDE:
        return City.distinctByKey(city -> city.latitude);

      case NAME_NO_ACCENTS:
        return City.distinctByKey(city -> city.nameNoAccents);

      case ALTERNATIVE_NAMES:
        return City.distinctByKey(city -> city.ibge);

      case MICROREGION:
        return City.distinctByKey(city -> city.microRegion);

      case MESOREGION:
        return City.distinctByKey(city -> city.mesoRegion);

      default:
        return null;
    }
  }

  /**
   * seen @ http://stackoverflow.com/questions/23699371/java-8-distinct-by-property
   **/
  private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    final Set<Object> seen = new HashSet<>();
    return t -> seen.add(keyExtractor.apply(t));
  }
}

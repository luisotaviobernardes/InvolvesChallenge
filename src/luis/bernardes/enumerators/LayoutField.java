package luis.bernardes.enumerators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: change name!
public enum LayoutField {

  /**
   * based on: https://raw.githubusercontent.com/involvestecnologia/selecaoinvolves/master/cidades.csv
   * instance @ the time: ibge_id,uf,name,capital,lon,lat,no_accents,alternative_names,microregion,mesoregion
   **/

  IBGE("ibge_id", 0, "IBGE", 7, 7, true),
  UF("uf", 1, "UF", 2, 2, true),
  NAME("name", 2, "NAME", 30, 20, true),
  IS_CAPITAL("capital", 3, "CAP.", 5, 5, true),
  LONGITUDE("lon", 4, "LONGITUDE", 15, 15, true),
  LATITUDE("lat", 5, "LATITUDE", 15, 15, true),
  NAME_NO_ACCENTS("no_accents", 6, "NAME NO ACCENTS", 30, 15, true),
  ALTERNATIVE_NAMES("alternative_names", 7, "ALT. NAME", 40, 10, true),
  MICROREGION("microregion", 8, "MICROREGION", 30, 20, true),
  MESOREGION("mesoregion", 9, "MESOREGION", 30, 20, true),
  INVALID("invalid");

  private String name;
  private int position;
  private String description;
  private int maxSize;
  private int minSize;
  private Boolean isValid;

  // valid constructor
  private LayoutField(String name, int position, String description, int maxSize, int minSize, Boolean isValid) {
    this.name = name;
    this.position = position;
    this.description = description;
    this.maxSize = maxSize;
    this.minSize = minSize;
    this.isValid = isValid;
  }

  // invalid constructor
  private LayoutField(String name) {
    this.name = name;
    this.isValid = false;
  }

  public String getName() {
    return this.name;
  }

  public Boolean isValid() {
    return this.isValid;
  }

  public int getPosition() {
    return this.position;
  }

  public int getMaxSize() {
    return this.maxSize;
  }

  public int getMinSize() { return this.minSize; }

  public int getSize(Boolean asMaximum) {
    return (asMaximum) ? this.maxSize : this.minSize;
  }

  public String getDescription() {
    return this.description;
  }

  public static Long getLayoutHeadersCount() {
    return Arrays.asList(LayoutField.values()).stream().filter(current -> current.isValid).count();
  }

  public static List<LayoutField> getAll() {
    return Arrays.asList(LayoutField.values()).stream().filter(current -> current.isValid).collect(Collectors.toList());
  }

  public static String getAllAsString() {
    return String.join(", ", LayoutField.getAll().stream().map(cmd -> cmd.name).collect(Collectors.toList()));
  }

  public static LayoutField getInString(String text) {
    // all entries that contains the parameter.
    List<LayoutField> list = Arrays.asList(LayoutField.values()).stream().filter(curr -> text.contains(curr.name)).collect(Collectors.toList());

    // grabs the bigger one.
    Optional<Integer> promise = list.stream().map(curr -> curr.getName().length()).max(Integer::compare);

    if (promise.isPresent()) {
      // grabs the parameter inside the list with the matching length.
      Optional<LayoutField> another = list.stream().filter(current -> current.getName().length() == promise.get()).findAny();

      return (another.isPresent()) ? another.get() : LayoutField.INVALID;
    } else {
      return LayoutField.INVALID;
    }
  }
}

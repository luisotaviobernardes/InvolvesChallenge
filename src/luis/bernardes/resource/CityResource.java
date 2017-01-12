package luis.bernardes.resource;

public class CityResource {
  public Long ibge;
  public String uf;
  public String name;
  public Boolean isCapital;
  public String longitude;
  public String latitude;
  public String nameNoAccents;
  public String alternativeNames;
  public String microRegion;
  public String mesoRegion;
  public Boolean isValid;

  private CityResource(Long ibge, String uf, String name, Boolean isCapital, String longitude, String latitude, String nameNoAccents, String alternativeNames, String microRegion, String mesoRegion, Boolean isValid) {
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
    this.isValid = isValid;
  }

  private static CityResource create(Long ibge, String uf, String name, Boolean isCapital, String longitude, String latitude, String nameNoAccents, String alternativeNames, String microRegion, String mesoRegion, Boolean isValid) {
    return new CityResource(ibge, uf, name, isCapital, longitude, latitude, nameNoAccents, alternativeNames, microRegion, mesoRegion, isValid);
  }

  public static CityResource create(Long ibge, String uf, String name, Boolean isCapital, String longitude, String latitude, String nameNoAccents, String alternativeNames, String microRegion, String mesoRegion) {
    return CityResource.create(ibge, uf, name, isCapital, longitude, latitude, nameNoAccents, alternativeNames, microRegion, mesoRegion, true);
  }

  public static CityResource empty() {
    return CityResource.create(null, null, null, false, null, null, null, null, null, null, false);
  }
}

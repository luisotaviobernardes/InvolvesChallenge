package luis.bernardes.services.city.impl;

import luis.bernardes.helpers.Console;
import luis.bernardes.models.City;
import luis.bernardes.resource.CityResource;
import luis.bernardes.resource.strategy.api.CityIOStrategy;
import luis.bernardes.services.city.api.CityCacheService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultCityCacheServiceImpl implements CityCacheService {
  private CityIOStrategy strategy;
  private List<City> cache;

  private DefaultCityCacheServiceImpl(CityIOStrategy strategy, List<City> cache) {
    this.strategy = strategy;
    this.cache = cache;
  }

  private static DefaultCityCacheServiceImpl create(CityIOStrategy strategy, List<City> cache) {
    return new DefaultCityCacheServiceImpl(strategy, cache);
  }

  public static DefaultCityCacheServiceImpl create(CityIOStrategy strategy) {
    return DefaultCityCacheServiceImpl.create(strategy, Collections.emptyList());
  }

  private static List<City> build(List<CityResource> resources) {
    Console.message(" (building cache) ");
    return resources.stream().filter(res -> res.isValid).map(City::create).collect(Collectors.toList());
  }

  @Override
  public List<City> get() {
    if (this.cache.isEmpty()) {
      this.cache = DefaultCityCacheServiceImpl.build(this.strategy.process());
    }

    return this.cache;
  }
}

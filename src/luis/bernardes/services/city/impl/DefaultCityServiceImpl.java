package luis.bernardes.services.city.impl;

import luis.bernardes.enumerators.Command;
import luis.bernardes.enumerators.CommandType;
import luis.bernardes.enumerators.LayoutField;
import luis.bernardes.helpers.MessageBuffer;
import luis.bernardes.models.City;
import luis.bernardes.resource.Argument;
import luis.bernardes.resource.Procedure;
import luis.bernardes.resource.Query;
import luis.bernardes.services.city.api.CityCacheService;
import luis.bernardes.services.city.api.CityService;
import luis.bernardes.services.city.api.CityTemplateService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Objects.isNull;

public class DefaultCityServiceImpl implements CityService {
  private static final String DEFAULT_COUNT_MESSAGE = "total rows returned";

  private CityCacheService cache;
  private CityTemplateService cityTemplateService;
  private String countMessage;

  private DefaultCityServiceImpl(CityCacheService cache, CityTemplateService cityTemplateService, String countMessage) {
    this.cache = cache;
    this.cityTemplateService = cityTemplateService;
    this.countMessage = countMessage;
  }

  public static DefaultCityServiceImpl create(CityCacheService cache, CityTemplateService cityTemplateService) {
    return new DefaultCityServiceImpl(cache, cityTemplateService, DEFAULT_COUNT_MESSAGE);
  }

  @Override
  public List<String> query(Procedure procedure) {
    return this.run(procedure);
  }

  private List<String> run(Procedure procedure) {
    List<City> cities = this.stream(this.cache.get(), procedure).collect(Collectors.toList());
    Optional<Query> promise = procedure.queries.stream().filter(cur -> cur.command.getType().equals(CommandType.DISPLAY)).findAny();

    if (promise.isPresent()) {
      Query query = promise.get();

      return (query.command.equals(Command.COUNT))
        ? this.count(cities)
        : this.select(cities, query.arguments.stream().map(arg -> arg.name).collect(Collectors.toList()));
    } else {
      MessageBuffer.store("No fields were selected for display, result will show all entries given the result");
      return this.select(cities, LayoutField.getAll());
    }
  }

  private List<String> count(List<City> cities) {
    return this.cityTemplateService.count(cities, this.countMessage);
  }

  private List<String> select(List<City> cities, List<LayoutField> fields) {
    return this.cityTemplateService.display(cities, fields);
  }

  private Stream<City> stream(List<City> entries, Procedure procedure) {
    Optional<Comparator<City>> order = this.order(procedure.queries);

    return (order.isPresent())
      ? entries.stream().filter(this.filters(procedure.queries)).filter(this.distinct(procedure.queries)).sorted(order.get())
      : entries.stream().filter(this.filters(procedure.queries)).filter(this.distinct(procedure.queries));
  }

  private Predicate<City> distinct(List<Query> queries) {
    List<Predicate<City>> result = new ArrayList<>();
    Optional<Query> promise = queries.stream().filter(cur -> cur.command.getType().equals(CommandType.DISTINCT)).findAny();

    if (promise.isPresent()) {
      for (Argument argument : promise.get().arguments) {
        Predicate<City> current = City.distinct(argument.name);

        if (!isNull(current)) {
          result.add(current);
        }
      }
    } else {
      MessageBuffer.store("No distinct filter was found on query, result may contain repeated entries");
    }

    return result.stream().reduce(predicate -> true, Predicate::and);
  }

  private Predicate<City> filters(List<Query> queries) {
    List<Predicate<City>> result = new ArrayList<>();
    Optional<Query> promise = queries.stream().filter(cur -> cur.command.getType().equals(CommandType.FILTER)).findAny();

    if (promise.isPresent()) {
      for (Argument argument : promise.get().arguments) {
        Predicate<City> current = City.filter(argument.name, argument.value);

        if (!isNull(current)) {
          result.add(current);
        }
      }
    } else {
      MessageBuffer.store("No suitable filters were found on query, result will not be filtered");
    }

    return result.stream().reduce(predicate -> true, Predicate::and);
  }

  private Optional<Comparator<City>> order(List<Query> queries) {
    List<Comparator<City>> result = new ArrayList<>();
    Optional<Query> promise = queries.stream().filter(cur -> cur.command.getType().equals(CommandType.ORDER)).findAny();

    if (promise.isPresent()) {
      for (Argument argument : promise.get().arguments) {
        Comparator<City> current = City.order(argument.name);

        if (!isNull(current)) {
          result.add(current);
        }
      }
    } else {
      MessageBuffer.store("No suitable order query was found, result will not be ordered");
    }

    return result.stream().reduce(Comparator::thenComparing);
  }
}
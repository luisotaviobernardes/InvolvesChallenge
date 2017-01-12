package luis.bernardes.resource;

import luis.bernardes.enumerators.Command;
import luis.bernardes.helpers.JsonHelper;
import luis.bernardes.services.command.api.CommandService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Procedure {
  public String formula;
  public List<Query> queries;
  public Boolean isValid;

  private Procedure(String formula, List<Query> queries, Boolean isValid) {
    this.formula = formula;
    this.queries = queries;
    this.isValid = isValid;
  }

  public String toJson() {
    return JsonHelper.asObject(
      JsonHelper.asKeyAndStringValue("formula", this.formula),
      JsonHelper.asKeyandListOfObjectValue("queries", this.queries.stream().map(Query::toJson).collect(Collectors.toList())),
      JsonHelper.asKeyAndBooleanValue("isValid", this.isValid)
    );
  }

  private static Procedure create(String formula, List<Query> queries, Boolean isValid) {
    return new Procedure(formula, queries, isValid);
  }

  private static Boolean validate(List<Query> queries) {
    return Command.validate(queries.stream().map(query -> query.command).collect(Collectors.toList()));
  }

  private static Procedure create(String formula, List<Query> queries) {
    return Procedure.create(formula, queries, Procedure.validate(queries));
  }

  public static Procedure create(String argument) {
    return Procedure.create(argument, Query.create(Arrays.asList(argument.split(CommandService.OPERATOR))));
  }
}

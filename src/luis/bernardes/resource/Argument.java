package luis.bernardes.resource;

import luis.bernardes.enumerators.LayoutField;
import luis.bernardes.helpers.JsonHelper;
import luis.bernardes.helpers.MessageBuffer;
import luis.bernardes.helpers.StringHelper;
import java.util.List;
import java.util.stream.Collectors;

public class Argument {
  public LayoutField name;
  public String value;
  public Boolean isValid;

  private Argument(LayoutField name, String value, Boolean isValid) {
    this.name = name;
    this.value = value;
    this.isValid = isValid;
  }

  public String toJson() {
    return JsonHelper.asObject(
      JsonHelper.asKeyAndStringValue("name", this.name.getName()),
      JsonHelper.asKeyAndStringValue("value", this.value),
      JsonHelper.asKeyAndBooleanValue("isValid", this.isValid)
    );
  }

  private static Argument construct(LayoutField name, String value, Boolean isValid) {
    return new Argument(name, value, isValid);
  }

  private static Argument empty() {
    return Argument.construct(LayoutField.INVALID, StringHelper.EMPTY, false);
  }

  private static Argument create(LayoutField name, String value) {
    String sanitizedValue = StringHelper.isValid(value) ? StringHelper.trimSpaces(value) : StringHelper.EMPTY;
    return (name.isValid()) ? Argument.construct(name, sanitizedValue, true) : Argument.empty();
  }

  private static Argument create(String argument) {
    LayoutField name = LayoutField.getInString(argument);

    if (!name.isValid()) {
      MessageBuffer.store("argument: '" + argument + "' is not recognizable as a field.");
    }

    return Argument.create(name, StringHelper.removeFromString(argument, name.getName()));
  }

  public static List<Argument> create(List<String> arguments) {
    List<Argument> all = arguments.stream().map(Argument::create).collect(Collectors.toList());

    // filter so we return only the valid ones...
    return all.stream().filter(arg -> arg.isValid).collect(Collectors.toList());
  }
}

package liquibase.ext.utils.preconditions;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import liquibase.ContextExpression;
import liquibase.Contexts;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import org.jetbrains.annotations.Nullable;

public final class ContextsParameters {

  private final Contexts maybeContexts;

  public ContextsParameters(@Nullable Contexts contexts) {
    this.maybeContexts = contexts;
  }

  public ContextsParameters() {
    this((Contexts)null);
  }

  ContextsParameters(Optional<Contexts> contexts) {
    this((Contexts)contexts.orElse(null));
  }

  public ContextsParameters(DatabaseChangeLog changelog) {
    this(from(changelog));
  }

  private static Optional<Contexts> from(DatabaseChangeLog changelog) {
    Optional<Contexts> ctx = Optional.empty();
    if (changelog != null) {
      ChangeLogParameters params = changelog.getChangeLogParameters();
      if (params != null) {
        ctx = Optional.ofNullable(params.getContexts());
      }
    }
    return ctx;
  }

  private Optional<Contexts> contexts() {
    return Optional.ofNullable(maybeContexts);
  }

  public boolean isPresent() {
    return !isEmpty();
  }

  public boolean isEmpty() {
    return contexts().map(Contexts::isEmpty).orElse(true);

  }

  public boolean match(String expression) {
    Optional<Contexts> ctx = contexts();
    return isPresent() && ctx.map(expressionMatcher(expression)).orElse(false);
  }

  private static Function<Contexts, Boolean> expressionMatcher(String expression) {
    return contexts -> new ContextExpression(expression).matches(contexts);
  }

  public String value(){
    return contexts().map(Objects::toString).orElse("");
  }

  @Override
  public String toString() {
    return String.format("ContextsParameters{ %s }", value());
  }


}

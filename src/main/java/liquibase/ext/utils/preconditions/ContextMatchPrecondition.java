package liquibase.ext.utils.preconditions;

import static java.lang.String.format;

import liquibase.ContextExpression;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.PreconditionFailedException;

/**
 * Precondition to check that a context match the specified expression
 */
public final class ContextMatchPrecondition extends BasePrecondition {

  private String expression = "";

  /**
   * Expression to check Example of expression : <code>(a and b) or !c</code>
   *
   * @return the context expression
   * @see ContextExpression
   */
  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression != null ? expression : "";
  }

  @Override
  public String getName() {
    return "contextMatch";
  }

  @Override
  protected void checkDatabaseChangeLog(DatabaseChangeLog changeLog) throws PreconditionFailedException {
    ContextsParameters parameters = new ContextsParameters(changeLog);
    if (!parameters.match(expression)) {
      String message = format(" contexts parameters '%s' does not match '%s'", parameters.value(), expression);
      throw new PreconditionFailedException(message, changeLog, this);
    }
  }
}

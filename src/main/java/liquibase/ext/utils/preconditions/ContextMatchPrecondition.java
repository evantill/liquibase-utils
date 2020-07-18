package liquibase.ext.utils.preconditions;

import liquibase.ContextExpression;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.ext.utils.xml.XmlConstants;
import liquibase.precondition.AbstractPrecondition;

/**
 * Precondition to check that a context match the specified expression
 */
public final class ContextMatchPrecondition extends AbstractPrecondition {

  private static final String EMPTY = "";
  private String expression = EMPTY;

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
    this.expression = expression != null ? expression : EMPTY;
  }

  @Override
  public String getSerializedObjectNamespace() {
    return XmlConstants.UTILS_CHANGELOG_NAMESPACE;
  }

  @Override
  public String getName() {
    return "contextMatch";
  }

  @Override
  public Warnings warn(Database database) {
    return new Warnings();
  }

  @Override
  public ValidationErrors validate(Database database) {
    return new ValidationErrors();
  }

  @Override
  public void check(Database db, DatabaseChangeLog changeLog, ChangeSet s, ChangeExecListener l)
      throws PreconditionFailedException {
    ContextsParameters parameters = new ContextsParameters(changeLog);
    if (!parameters.match(expression)) {
      throw new PreconditionFailedException(
          String
              .format(" contexts parameters '%s' does not match  '%s'", parameters.value(), expression), changeLog, this);
    }
  }
}

package liquibase.ext.utils.preconditions;

import static liquibase.ext.utils.preconditions.ContextDefinedPrecondition.contextDefined;

import liquibase.ContextExpression;
import liquibase.Contexts;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.ext.utils.xml.XmlConstants;
import liquibase.precondition.AbstractPrecondition;

/**
 * Precondition to check that a context match the specified expression
 */
public class ContextMatchPrecondition extends AbstractPrecondition {

  private ContextExpression expression = new ContextExpression();

  /**
   * Expression to check
   * Example of expression : <code>(a and b) or !c</code>
   * @see ContextExpression
   */
  public String getExpression() {
    return expression.toString();
  }

  public void setExpression(String expression) {
    this.expression = new ContextExpression(expression);
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
  public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet,
      ChangeExecListener changeExecListener)
      throws PreconditionFailedException, PreconditionErrorException {
    Contexts contexts = changeLog.getChangeLogParameters().getContexts();
    if (!matchExpression(contexts,expression)) {
      throw new PreconditionFailedException(
          " contexts " + contexts + " does not match  " + expression, changeLog, this);
    }
  }

  static boolean matchExpression(Contexts ctx,ContextExpression e){
    return contextDefined(ctx) && e.matches(ctx);
  }
}

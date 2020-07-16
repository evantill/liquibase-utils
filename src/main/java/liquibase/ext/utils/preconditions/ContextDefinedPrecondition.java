package liquibase.ext.utils.preconditions;

import liquibase.Contexts;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.precondition.AbstractPrecondition;
import liquibase.ext.utils.xml.XmlConstants;

/**
 * Precondition to check that a context was specified when calling liquibase.
 */
public class ContextDefinedPrecondition extends AbstractPrecondition {

  @Override
  public String getSerializedObjectNamespace() {
    return XmlConstants.UTILS_CHANGELOG_NAMESPACE;
  }

  @Override
  public String getName() {
    return "contextDefined";
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
    if (contexts == null || contexts.isEmpty()) {
      throw new PreconditionFailedException("No contexts were set", changeLog, this);
    }
  }
}

package liquibase.ext.utils.preconditions;

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
 * Precondition to check that a context was specified when calling liquibase.
 */
public final class ContextDefinedPrecondition extends AbstractPrecondition {

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
  public void check(Database database, DatabaseChangeLog changeLog, ChangeSet set, ChangeExecListener listener) throws PreconditionFailedException {
    ContextsParameters parameters = new ContextsParameters(changeLog);
    if (parameters.isEmpty()) {
      throw new PreconditionFailedException("No contexts were set", changeLog, this);
    }
  }
}

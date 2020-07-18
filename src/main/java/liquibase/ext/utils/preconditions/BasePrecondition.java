package liquibase.ext.utils.preconditions;

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

public abstract class BasePrecondition extends AbstractPrecondition {

  @Override
  public String getSerializedObjectNamespace() {
    return XmlConstants.UTILS_CHANGELOG_NAMESPACE;
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
  public final void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet,
      ChangeExecListener changeExecListener)
      throws PreconditionFailedException, PreconditionErrorException {
    checkDatabaseChangeLog(changeLog);
  }

  protected  abstract void checkDatabaseChangeLog(DatabaseChangeLog changeLog)
      throws PreconditionFailedException, PreconditionErrorException;
}

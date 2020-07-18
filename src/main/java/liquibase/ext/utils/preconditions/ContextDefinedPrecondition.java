package liquibase.ext.utils.preconditions;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.PreconditionFailedException;

/**
 * Precondition to check that a context was specified when calling liquibase.
 */
public final class ContextDefinedPrecondition extends BasePrecondition {

  @Override
  public String getName() {
    return "contextDefined";
  }

  @Override
  protected void checkDatabaseChangeLog(DatabaseChangeLog changeLog) throws PreconditionFailedException{
    ContextsParameters parameters = new ContextsParameters(changeLog);
    if (parameters.isEmpty()) {
      throw new PreconditionFailedException("No contexts were set", changeLog, this);
    }
  }
}

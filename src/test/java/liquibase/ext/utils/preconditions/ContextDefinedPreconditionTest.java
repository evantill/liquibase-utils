package liquibase.ext.utils.preconditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ValidationFailedException;
import liquibase.precondition.core.PreconditionContainer;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import org.junit.Test;

public class ContextDefinedPreconditionTest extends AbsrtractLiquibaseTest {

  @Test
  public void testCallWithoutContext() {
    try {
      liquibaseForTesting(changeLogForTestClass()).validate();
      fail("precondition should have failed");
    } catch (LiquibaseException e) {
      assertThat(e).isInstanceOf(ValidationFailedException.class)
          .hasMessageContaining("No contexts were set");
    }
  }

  @Test
  public void testCallWithContext() throws LiquibaseException {
    liquibaseForTesting(changeLogForTestClass()).update("dev");
  }

  @Test
  public void testSerialisation() throws LiquibaseException {
    ChangeLogSerializer serializer = new XMLChangeLogSerializer();
    Liquibase l = liquibaseForTesting(changeLogForTestClass());
    PreconditionContainer preconditions = l.getDatabaseChangeLog().getPreconditions();
    String serialized = serializer.serialize(preconditions, true);
    assertThat(serialized).contains("<ext:contextDefined/>");
  }
}

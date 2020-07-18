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

public class ContextMatchPreconditionTest extends AbsrtractLiquibaseTest {

  @Test
  public void testCallWithoutContext() {
    try {
      liquibaseForTesting(changeLogForTestClass()).validate();
      fail("precondition should have failed");
    } catch (LiquibaseException e) {
      String expectedMessage = "contexts parameters '' does not match '(a and b) or !c'";
      assertThat(e).isInstanceOf(ValidationFailedException.class).hasMessageContaining(expectedMessage);
    }
  }

  @Test
  public void testCallWithContextThatMatch() throws LiquibaseException {
    liquibaseForTesting(changeLogForTestClass()).update("b,a");
  }

  @Test
  public void testCallWithContextThatDoesNotMatch() throws LiquibaseException {
    try {
      liquibaseForTesting(changeLogForTestClass()).update("c");
      fail("precondition should have failed");
    } catch (LiquibaseException e) {
      String expectedMessage = "contexts parameters 'c' does not match '(a and b) or !c'";
      assertThat(e).isInstanceOf(ValidationFailedException.class).hasMessageContaining(expectedMessage);
    }
  }

  @Test
  public void testSerialisation() throws LiquibaseException {
    ChangeLogSerializer serializer = new XMLChangeLogSerializer();
    Liquibase l = liquibaseForTesting(changeLogForTestClass());
    PreconditionContainer preconditions = l.getDatabaseChangeLog().getPreconditions();
    String serialized = serializer.serialize(preconditions, true);
    assertThat(serialized).contains("<ext:contextMatch expression=\"(a and b) or !c\"/>");
  }
}

package liquibase.ext.utils.preconditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ValidationFailedException;
import org.junit.Test;

public class ContextMatchPreconditionTest extends AbsrtractLiquibaseTest {

  @Test
  public void testCallWithoutContext() {
    try {
      Liquibase l = new Liquibase(changeLogForTestClass(), resourceAccessor(), database());
      l.validate();
      fail("precondition should have failed");
    } catch (LiquibaseException e) {
      String expectedMessage = "contexts parameters '' does not match  '(a and b) or !c'";
      assertThat(e).isInstanceOf(ValidationFailedException.class).hasMessageContaining(expectedMessage);
    }
  }

  @Test
  public void testCallWithContextThatMatch() throws LiquibaseException {
    Liquibase l = new Liquibase(changeLogForTestClass(), resourceAccessor(), database());
    l.update("b,a");
  }

  @Test
  public void testCallWithContextThatDoesNotMatch() throws LiquibaseException {
    Liquibase l = new Liquibase(changeLogForTestClass(), resourceAccessor(), database());
    try {
      l.update("c");
      fail("precondition should have failed");
    } catch (LiquibaseException e) {
      String expectedMessage = "contexts parameters 'c' does not match  '(a and b) or !c'";
      assertThat(e).isInstanceOf(ValidationFailedException.class).hasMessageContaining(expectedMessage);
    }
  }

}

package liquibase.precondition.ext.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ValidationFailedException;
import org.junit.Test;

public class ContextDefinedPreconditionTest extends AbsrtractLiquibaseTest {

  @Test
  public void testCallWithoutContext() {
    try {
      Liquibase l = new Liquibase("changelog-with-condition.xml", resourceAccessor(), database());
      l.validate();
      fail("precondition should have failed");
    } catch (LiquibaseException e) {
      assertThat(e).isInstanceOf(ValidationFailedException.class)
          .hasMessageContaining("No contexts were set");
    }
  }

  @Test
  public void testCallWithContext() throws LiquibaseException {
    Liquibase l = new Liquibase("changelog-with-condition.xml", resourceAccessor(), database());
    l.update("dev");
  }

}

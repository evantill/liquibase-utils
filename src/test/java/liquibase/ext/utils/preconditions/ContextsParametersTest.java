package liquibase.ext.utils.preconditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import liquibase.Contexts;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class ContextsParametersTest {

  @Test
  public void testIsPresentWithNoParameters() {
    List<Contexts> contexts = Lists.list(
        new Contexts((String) null),
        new Contexts(""),
        new Contexts(" "),
        new Contexts(Lists.emptyList())
    );
    for (Contexts ctx : contexts) {
      assertThat(new ContextsParameters(ctx).isPresent()).isFalse();
      assertThat(new ContextsParameters(ctx).isEmpty()).isTrue();
    }
  }

  @Test
  public void testMatching() {
    List<Contexts> contexts = Lists.list(
        new Contexts("a"),
        new Contexts("b"),
        new Contexts("a,b"),
        new Contexts("a,d")
    );
    String expression="(a or b) and !c";
    for (Contexts ctx : contexts) {
      assertThat(new ContextsParameters(ctx).match(expression)).isTrue();
    }
  }

  @Test
  public void testNotMatching() {
    List<Contexts> contexts = Lists.list(
        new Contexts("c"),
        new Contexts("b,c"),
        new Contexts("d"),
        new Contexts("")
    );
    String expression="(a or b) and !c";
    for (Contexts ctx : contexts) {
      assertThat(new ContextsParameters(ctx).match(expression)).isFalse();
    }
  }

  @Test
  public void testValue() {
    String emptyValue=new ContextsParameters(new Contexts("")).value();
    assertThat(emptyValue).isEqualTo("");
    String complexValue=new ContextsParameters(new Contexts("(a or b) and !c ")).value();
    assertThat(complexValue).isEqualTo("(a or b) and !c");
  }
}
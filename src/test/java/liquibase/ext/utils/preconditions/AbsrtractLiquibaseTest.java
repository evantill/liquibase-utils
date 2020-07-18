package liquibase.ext.utils.preconditions;

import java.sql.Connection;
import java.sql.DriverManager;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class AbsrtractLiquibaseTest {

  private Connection connection() {
    try {
      Class.forName(JDBCDRIVER);
      return DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  protected Database database() {
    try {
      JdbcConnection jdbcConnection = new JdbcConnection(connection());
      DatabaseFactory factory = DatabaseFactory.getInstance();
      return factory.findCorrectDatabaseImplementation(jdbcConnection);
    } catch (DatabaseException e) {
      throw new IllegalStateException(e);
    }
  }

  protected ResourceAccessor resourceAccessor() {
    return new ClassLoaderResourceAccessor();
  }

  protected Liquibase liquibaseForTesting(String changelogFile) {
    return new Liquibase(changelogFile, resourceAccessor(), database());
  }

  protected String changeLogForTestClass() {
    return changeLogForTestClass(getClass());
  }

  protected String sqlChangeLogForTestClass() {
    return sqlChangeLogForTestClass(getClass());
  }

  private String sqlChangeLogForTestClass(Class<?> testClass) {
    return testResourceLocation(testClass, "-changelog.sql");
  }

  protected String changeLogForTestClass(Class<?> testClass) {
    return testResourceLocation(testClass, "-changelog.xml");
  }

  protected String changeLogForTestMethod(String methodName) {
    return testResourceLocation(getClass(), methodName);
  }

  protected String changeLogForTestMethod(Class<?> testClass, String methodName) {
    return testResourceLocation(testClass, "-" + methodName + "-changelog.xml");
  }

  private String testResourceLocation(Class<?> testClass, String suffix) {
    String resourceLocation = testClass.getCanonicalName().replace('.', '/') + suffix;
    return resourceLocation;
  }

  public static final String JDBCDRIVER = "org.hsqldb.jdbc.JDBCDriver";
  public static final String URL = "jdbc:hsqldb:mem:myDb";
  public static final String USER = "sa";
  public static final String PASSWORD = "sa";
}

package liquibase.ext.utils.preconditions;

import java.sql.Connection;
import java.sql.DriverManager;
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
  
  public static final String JDBCDRIVER = "org.hsqldb.jdbc.JDBCDriver";
  public static final String URL = "jdbc:hsqldb:mem:myDb";
  public static final String USER = "sa";
  public static final String PASSWORD = "sa";
}

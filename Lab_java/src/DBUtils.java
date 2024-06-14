import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	
	static String database = "jdbc:mysql://localhost/db_lab_java";
	static String username = "root";
	static String password = "";
	
	public static Connection getConnection() throws SQLException {
		
		try {
			return DriverManager.getConnection(database, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		}
}
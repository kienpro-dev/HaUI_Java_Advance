package tx1;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
	public Connection getConnection(String objectName) throws SQLException;
	
	public void releaseConnection(Connection conn, String objectName) throws SQLException;
}

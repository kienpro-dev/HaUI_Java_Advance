package tx1;

import java.sql.*;
import java.util.*;

public class ConnectionPoolImpl implements ConnectionPool {
	private String driver;

	private String url;

	private String username;
	private String password;

	private Stack<Connection> pools;
	
	public ConnectionPoolImpl() {
		this.driver = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.url = "jdbc:mysql://localhost:3306/it602003_data";

		this.username = "it6020003_kiennt";
		this.password = "123456";
		
		this.pools = new Stack<>();
	}

	@Override
	public Connection getConnection(String objectName) throws SQLException {
		// TODO Auto-generated method stub
		if(this.pools.isEmpty()) {
			System.out.println(objectName + " have created a new connection.");
			return DriverManager.getConnection(this.url, this.username, this.password);
		} else {
			System.out.println(objectName + " have popped the connection.");
			return this.pools.pop();
		}
	}

	@Override
	public void releaseConnection(Connection conn, String objectName) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(objectName + " have pushed the connection.");
		this.pools.push(conn);
	}
	
	protected void finalize() throws Throwable {
		this.pools.clear();
		this.pools = null;
		System.out.println("ConnectionPool is closed.");
		
	}

}

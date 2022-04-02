package chess.repository.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static final String URL = "jdbc:mysql://localhost:3306/chess";
	private static final String USER = "user";
	private static final String PASSWORD = "password";

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection =  DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}

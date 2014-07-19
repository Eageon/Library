package org.jun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnector {
	private Connection conn = null;
	public static DBConnector instance = null;
	
	private DBConnector() {
		try {
			conn = doGetConnection();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DBConnector instance() {
		if(instance == null) {
			instance = new DBConnector();
		}
		return instance;
	}
	
	private Connection doGetConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", "jxy132330");
		connectionProps.put("password", "123456");
		
		//Class.forName("com.mysql.jdbc.Driver");
		
		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://"
				+ "127.0.0.1" + ":" + 
				"3306" + "/Library", connectionProps);

		return conn;
	}
	
	public Statement createStatement() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	public PreparedStatement prepareStatement(String sql) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.conn = null;
	}
}

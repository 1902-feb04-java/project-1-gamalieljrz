package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * SQL Connection Function and Startup
 * Created Data Access Object Util Class to avoid redundancy
 */
public class DAOUtil {
	
	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "rachel101";
	private static final String URL = "jdbc:postgresql://localhost:5432/reimbursedb";
	private static Connection connection;
	private static PreparedStatement stment;
	
	public static synchronized Connection getConnection() throws SQLException{
		
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch(ClassNotFoundException e) {
				System.out.println("Could not register Driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		// If connection closed, get a new one
		if(connection.isClosed()) {
			System.out.println("Starting New Connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}
	
	// Check Credentials
	public Credential getCredential(String user, String password) {
		
		Credential cred = null;
		
		try {
			connection = DAOUtil.getConnection();
			String sql = "SELECT * FROM credentials WHERE user_name = ? AND password = ?";
			stment = connection.prepareStatement(sql);
			
			stment.setString(1, user);
			stment.setString(2, password);
			
			ResultSet rs = stment.executeQuery();
			
			rs.next();
			int id = rs.getInt("id");
			cred = new Credential(id,user,password);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources();
		}
		return cred;
	}
	
	//Attempt to Login
	public int tryLogin(String user, String password) {
		int id = 0;
		try {
			connection = DAOUtil.getConnection();
			String sql = "SELECT id FROM reimburse_process.credentials WHERE user_name = ? AND password = ?";
			stment = connection.prepareStatement(sql);
			
			stment.setString(1, user);
			stment.setString(2, password);
			
			ResultSet rs = stment.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
			}
			System.out.println(id);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources();
		}
		return id;
	}
	
	//Credential Class
	public class Credential{
		int userId;
		String userName, userPassword;
		
		public Credential(int id, String user, String password) {
			this.userId = id;
			this.userName = user;
			this.userPassword = password;
		}
		public int getUserId() {
			return userId;
		}
		public String getUserName() {
			return userName;
		}
		public String getUserPassword() {
			return userPassword;
		}
	}
	
	//Method to Close Resources
	protected void closeResources() {
		
		try {
			if(stment != null)
				stment.close();
		}catch(SQLException e) {
			System.out.println("Could not close statment!");
			e.printStackTrace();
		}
		
		try {
			if(connection != null) 
				connection.close();
			
			}catch(SQLException e) {
				System.out.println("Could not close connection!");
				e.printStackTrace();
		}
	}
	
	//Returns Employee object
	public static EmployeeDAO getEmployeeDAO() {
		return new EmployeeDAO();
	}
	//Returns Reimbursement object
	public static ReimburseDAO getReimburseDAO() {
		return new ReimburseDAO();
	}

}

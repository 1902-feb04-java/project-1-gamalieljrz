package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Functionality for Employee data
 */
public class EmployeeDAO {
	
	private static Connection connection;
	private static Connection nextConnection;
	private static PreparedStatement stment;
	private static PreparedStatement nextStment;
	
	//Retrieve by id
	public EmployeeDAO getEmployeeById(int id) {
		String sql = String.format("WHERE reimburse_process.employees.id = %d", id);
		return getAllEmployees(sql).get(0);
	}
	
	//Retrieve by name
	public EmployeeDAO getEmployeeByName(String fName, String lName) {
		String sql = String.format("WHERE "
				+ "reimburse_process.employees.first_name = '%s' AND "
				+ "reimburse_process.employees.last_name = '%s'", fName,lName);
		return getAllEmployees(sql).get(0);
	}
	
	//Retrieve all employees in list
	public List<EmployeeDAO> getAllEmployees(){
		return getAllEmployees("");
	}
	
	//Retrieve all employees with string arg
	public List<EmployeeDAO> getAllEmployees(String sql){
		List<EmployeeDAO> employees = new ArrayList<EmployeeDAO>();
		
		try {
			String start = "SELECT * FROM reimburse_process.employees "
					+ "INNER JOIN reimburse_process.credentials "
					+ "ON reimburse_process.credentials.user_name "
					+ "= reimburse_process.employees.email ";
			connection = DAOUtil.getConnection();
			stment = connection.prepareStatement(start+sql);
			
			ResultSet rs = stment.executeQuery();
			while(rs.next()) {
				
				int eId = rs.getInt("id");
				String fName = rs.getString("first_name");
				String lName = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				boolean isManager = rs.getBoolean("ismanager");
				
				EmployeeDAO e = new EmployeeDAO(eId, fName, lName, email, password, isManager);
				employees.add(e);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources();
		}
		return employees;
		
	}
	
	//Update Employee Info
	public boolean updateEmployee(EmployeeDAO emp) {
		boolean val = false;
		boolean nextVal = false;
		try {
			connection = DAOUtil.getConnection();
			String sql = "UPDATE "
					+ "reimburse_process.employees SET "
					+ "first_name = ?, "
					+ "last_name = ?, "
					+ "email = ? "
					//+ "ismanager = ? "
					+ "WHERE id = ? ";
			stment = connection.prepareStatement(sql);
			
			stment.setString(1, emp.getFirstName());
			stment.setString(2, emp.getLastName());
			stment.setString(3,  emp.getEmail());
			//stment.setBoolean(4, emp.isManager);
			stment.setInt(4, emp.getId());
			
			
			if(stment.executeUpdate() != 0) {
				System.out.println("\nEmployee Update happened in SQL table");
				return val = true;
			}
			else {
				System.out.println("\nUpdate did not happen in SQL table");
				return val = false;
			}

				
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources();
		}
		return val;
	}
	
	//Add employee to table
	public boolean addEmployee(EmployeeDAO emp) {
		try {
			connection = DAOUtil.getConnection();
			String sql = "INSERT INTO employees "
					+ "(first_name, last_name, email, password, isManager)"
					+ "VALUES(?,?,?,?)";
			stment = connection.prepareStatement(sql);
			
			stment.setString(1, emp.getFirstName());
			stment.setString(2, emp.getLastName());
			stment.setString(3, emp.getEmail());
			stment.setString(4,  emp.getPassword());
			stment.setBoolean(5, emp.isManager());
			
			if(stment.executeUpdate() != 0) 
				return true;
			else
				return false;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			closeResources();
		}
	}
	
	//Employee Class
	
	int id;
	String firstName, lastName, email, password;
	boolean isManager;

	int count = 0;

	public EmployeeDAO() {
		this.id = -1;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.password = null;
	}

	public EmployeeDAO(int id, String fName, String lName, String email, String password, boolean isMan) {
		this.id = id;
		this.firstName = fName;
		this.lastName = lName;
		this.email = email;
		this.isManager = isMan;
		this.password = password;
		System.out.println(this.toString());
	}

	public int nextId() {
		return count++;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String first) {
		this.firstName = first;
	}

	public void setLastName(String last) {
		this.lastName = last;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getEmail() {
		return this.email;
	}
	public String getPassword() {
		return this.password;
	}

	public boolean isManager() {
		return this.isManager;
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
}

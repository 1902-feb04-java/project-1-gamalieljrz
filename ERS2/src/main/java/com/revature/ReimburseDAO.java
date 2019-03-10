package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/*
 * Functionality for Reimbursement Data
 */
public class ReimburseDAO {

	private static Connection connection;
	private static PreparedStatement stment;

	int id;
	double amount;
	int employeeId;
	Status status;
	String stat;
	byte[] image;
	String reason;
	String imageString;
	String firstName;
	String lastName;

	public enum Status {
		pending, approved, denied, invalid
	}

	public ReimburseDAO(int id, double money, int empId, String status, String stat, byte[] imgData, String reason, 
			String fName, String lName) {

		this.id = id;
		this.amount = money;
		this.employeeId = empId;
		this.status = status != null ? Status.valueOf(status.toLowerCase()) : null;
		this.stat = stat;
		this.reason = reason;
		this.image = imgData;
		this.firstName = fName;
		this.lastName = lName;
	}

	public ReimburseDAO(double money, int empId, String reason, String stat) {
		this.amount = money;
		this.employeeId = empId;
		this.reason = reason;
		this.status = stat != null ? Status.valueOf(stat.toLowerCase()) : null;
	}

	public ReimburseDAO() {
		super();
	}

	public int getId() {
		return id;
	}

	public double getAmount() {
		return amount;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getStatus() {
		return status.toString();
	}

	public byte[] getImage() {
		return image;
	}

	public String getReason() {
		return reason;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	public void setStatus(String stat) {
		this.stat = stat;
	}

	public void setImage(byte[] data) {
		this.image = data;
	}

	public void setImageString(byte[] data) {
		this.imageString = data == null ? null : Base64.getEncoder().encodeToString(data);

	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<ReimburseDAO> getAllRequest() {
		return getAllRequests("");
	}

	protected List<ReimburseDAO> getAllRequests(String sql) {
		List<ReimburseDAO> requests = new ArrayList<ReimburseDAO>();
		try {
			String start = "SELECT * FROM reimburse_process.reimbursements "
					+ "INNER JOIN reimburse_process.employees "
					+ "ON reimburse_process.employees.id "
					+ "= reimburse_process.reimbursements.employee_id ";
			connection = DAOUtil.getConnection();
			stment = connection.prepareStatement(start + sql);

			ResultSet rs = stment.executeQuery();

			requests = this.ParseReimbursements(rs);

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return requests;
	}
	
	public List<ReimburseDAO> getAllRequestsByEmployee(int employeeId){
		String sql = String.format("WHERE reimburse_process.reimbursements.employee_id = %d", employeeId);
		return getAllRequests(sql);
	}
	
	public List<ReimburseDAO> getAllRequestsByStatus(String status){
		String sql = String.format("WHERE reimburse_process.reimbursements.status = '%s'", status);
		return getAllRequests(sql);
	}
	
	public ReimburseDAO getRequestById(int id) {	
		String sql = String.format("WHERE reimburse_process.reimbursements.id = %d", id);
		return this.getAllRequests(sql).get(0); 
	}
	
	public boolean addRequest(ReimburseDAO r) {
		try {
			connection = DAOUtil.getConnection();
			String sql = "INSERT INTO reimburse_process.reimbursements (status, employee_id, reason, amount, receipt) VALUES(?,?,?,?,?)";
			stment = connection.prepareStatement(sql);
			
			stment.setString(1, r.getStatus());
			stment.setInt(2, r.getEmployeeId());
			stment.setString(3, r.getReason());
			stment.setDouble(4, r.getAmount());
			stment.setBytes(5, r.getImage());
			
			if(stment.executeUpdate() !=0) {
				return true;
			}else
				return false;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			closeResources();
		}
	}
	
	//Make sure you're passing the correct values
	public boolean updateRequest(int requestId, int employeeId, String status) {
		try {
			connection = DAOUtil.getConnection();
			String sql = "UPDATE reimburse_process.reimbursements SET status = ? "
					+ "WHERE id = ? ";
			stment = connection.prepareStatement(sql);
			
			//status = "'" + status + "'";
			stment.setString(1, status);
			//stment.setInt(2, employeeId);
			stment.setInt(2, requestId);
			
			if(stment.executeUpdate() !=0)
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
	
	List<ReimburseDAO> ParseReimbursements(ResultSet rs){
		List<ReimburseDAO> requests = null;
		try {
			requests = new ArrayList<ReimburseDAO>();
			while(rs.next()) {
				int requestId = rs.getInt("id");
				double amount = rs.getDouble("amount");
				int employee = rs.getInt("employee_id");
				String status = rs.getString("status");
				byte[] image = rs.getBytes("receipt");
				String reason = rs.getString("reason");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");
				
				ReimburseDAO request = new ReimburseDAO (requestId, amount, employee, status, stat, image,reason, fname,lname);
				request.setImageString(image);
				requests.add(request);
					
				}
			}catch(Exception e) {
				e.printStackTrace();
		}
		return requests;
	}
	
	
	//Close Resources
	protected void closeResources() {

		try {
			if (stment != null)
				stment.close();
		} catch (SQLException e) {
			System.out.println("Could not close statment!");
			e.printStackTrace();
		}

		try {
			if (connection != null)
				connection.close();

		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}

package com.revature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/update-info")
public class ServletUpdateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletUpdateInfo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int employeeId = -1;
		employeeId = (int) request.getSession(false).getAttribute("id");
		String fName = request.getParameter("first-name");
		String lName = request.getParameter("last-name");
		String email = request.getParameter("email");
		EmployeeDAO empDAO = DAOUtil.getEmployeeDAO();
		EmployeeDAO emp =  empDAO.getEmployeeById(employeeId);
		emp.setId(employeeId);
	
		System.out.println(emp.firstName);
		System.out.println(fName);
		//Check if First Name is Null before updating
		if(fName == "" ) {
			emp.setFirstName(emp.firstName);
		}else {
			emp.setFirstName(fName);
		}
		
		//Check Last Name if null
		if(lName == "") {
			emp.setLastName(emp.lastName);
		}else {
			emp.setLastName(lName);
		}
		
		//Check Email if null
		if(email == "") {
			emp.setEmail(emp.email);
		}else {
			emp.setEmail(email);
		}
		
		if(empDAO.updateEmployee(emp)) {
			System.out.println("Update Successful!");
			request.getRequestDispatcher("homepage.html").forward(request, response);
		}else {
			System.out.println("Update Failed");
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

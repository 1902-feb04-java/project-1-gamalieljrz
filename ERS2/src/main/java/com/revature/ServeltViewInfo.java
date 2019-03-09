package com.revature;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/view-info")
public class ServeltViewInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ServeltViewInfo() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int employeeId = -1;
		employeeId = (int) request.getSession(false).getAttribute("id");
		
		EmployeeDAO empDAO = DAOUtil.getEmployeeDAO();
		EmployeeDAO emp;
		
		
		System.out.println(employeeId);
		emp = empDAO.getEmployeeById(employeeId);
		
		PrintWriter out = response.getWriter();
		out.println("<h1>Employee Id: "+emp.count +"</h1>");
		out.println("<h1>First name: "+emp.firstName +"</h1>");
		out.println("<h1>Last Name: "+emp.lastName +"</h1>");
		out.println("<h1>Email: "+emp.email +"</h1>");
		out.println("<h1>Manager? "+emp.isManager +"</h1>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

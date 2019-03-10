package com.revature;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/single-employee-request")
/*
 * Servlet Allowing Manager to search Request by Employee Name
 */
public class ServletSingleEmpRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletSingleEmpRequest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String fName = request.getParameter("first-name");
		String lName = request.getParameter("last-name");
		EmployeeDAO singleEmp = new EmployeeDAO();
		EmployeeDAO grabEmp = new EmployeeDAO();
		
		List<ReimburseDAO> singleRequest = new ArrayList<ReimburseDAO>();
		ReimburseDAO grabRequest = new ReimburseDAO();
		
		singleEmp = grabEmp.getEmployeeByName(fName, lName);
		singleRequest = grabRequest.getAllRequestsByEmployee(singleEmp.getId());
		
		//System.out.println("What is this?"+singleRequest);
		
		PrintWriter out = response.getWriter();
		
		//First check if we have any request from Employee
		if(singleRequest.isEmpty()) {
			//If so, let the user know it's empty
			out.println("<h1>No Requests For "+singleEmp.firstName+" "+
			singleEmp.lastName+"</h1>");
			
		// Otherwise, print out all of the requests
		}else {
			for(int i=0; i<singleRequest.size(); i++) {
				out.println("<h1>EmployeeID: "+singleEmp.getId()+"</h1>");
				out.println("<h1>Reimbursement ID: "+singleRequest.get(i).id+"</h1>");
				out.println("<h1>First Name: "+singleEmp.getFirstName()+"</h1>");
				out.println("<h1>Last Name: "+singleEmp.getLastName()+"</h1>");
				out.println("<h1>Email: "+singleEmp.getEmail()+"</h1>");
				out.println("*****************************************");

			}
		}
		
		out.println("<a href='man-homepage.html'>BACK</a> ");
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

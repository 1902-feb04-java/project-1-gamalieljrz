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

@WebServlet("/view-employees")
public class ServletViewEmp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletViewEmp() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		EmployeeDAO emps = new EmployeeDAO();
		
		List<EmployeeDAO> employees = new ArrayList<EmployeeDAO>();
		employees = emps.getAllEmployees();
		
		PrintWriter out = response.getWriter();
		System.out.println(employees.get(0).firstName);
		for(int i=0; i < employees.size(); i++) {
			out.println("<h1>First Name: "+employees.get(i).firstName+"</h1>");
			out.println("<h1>Last Name: "+employees.get(i).lastName+"<h1>");
			out.println("<h1>Email: "+employees.get(i).email+"<h1>");
			out.println("<h1>Employee ID: "+employees.get(i).id+"<h1>");
			out.println("<h1>*************************</h1>");


		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

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

@WebServlet("/view-my-requests")
/*
 * Servlet allowing Employee to view their own Request
 */
public class ServletViewMyRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletViewMyRequests() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		ReimburseDAO rDAO = new ReimburseDAO();
		int employeeId = -1;
		employeeId = (int) request.getSession(false).getAttribute("id");
		
		System.out.println(request.getAttribute("id"));
		
		List<ReimburseDAO> requests = new ArrayList<ReimburseDAO>();
		requests = rDAO.getAllRequestsByEmployee(employeeId);
		
		PrintWriter out = response.getWriter();
		for(int i=0; i < requests.size(); i++) {
			out.println("<h1>Reimbursement ID: "+requests.get(i).id+"<h1>");
			out.println("<h1>Amount: "+requests.get(i).amount+"<h1>");
			out.println("<h1>Reason "+requests.get(i).reason+"<h1>");
			out.println("*****************************");
		}
		out.println("<a href='homepage.html'>BACK</h1>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

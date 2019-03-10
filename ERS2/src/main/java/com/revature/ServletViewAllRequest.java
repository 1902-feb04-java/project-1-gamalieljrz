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

@WebServlet("/view-request")
/*
 * Servlet to view All Requests by All Employees
 */
public class ServletViewAllRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ServletViewAllRequest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		ReimburseDAO reDAO = new ReimburseDAO();
		
		String subStatus = request.getParameter("status");
		List<ReimburseDAO> requests = new ArrayList<ReimburseDAO>();
		requests = reDAO.getAllRequestsByStatus(subStatus);
		
		PrintWriter out = response.getWriter();
		for(int i=0; i<requests.size(); i++) {
			out.println("<h1>Reimbursement ID: "+requests.get(i).id+"<h1>");
			out.println("<h1>Amount: $"+ requests.get(i).amount+"</h1>");
			out.println("<h1>Employee Id: "+ requests.get(i).employeeId+"</h1>");
			out.println("<h1>Status: "+ requests.get(i).status+"</h1>");
			out.println("<h1>Reason: "+ requests.get(i).reason+"</h1>");
			out.println("<h1>First Name: "+ requests.get(i).firstName+"</h1>");
			out.println("<h1>Last Name: "+ requests.get(i).lastName+"</h1>");
			out.println("***********************************************");
		}
		
		out.println("<a href='man-homepage.html'>BACK</a>");
		out.close();
		
		//String json = new Gson().toJson(requests);
		//response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

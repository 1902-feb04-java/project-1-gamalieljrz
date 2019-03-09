package com.revature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/approve-deny-requests")
public class ServletApproveDeny extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletApproveDeny() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int employeeId = -1;
		employeeId = (int) request.getSession(false).getAttribute("id");
		int rId = Integer.parseInt(request.getParameter("r-id"));
		int eId = Integer.parseInt(request.getParameter("employee-id"));
		String status = request.getParameter("status");
		
		ReimburseDAO reimburseDAO = DAOUtil.getReimburseDAO();
		ReimburseDAO reimburse = reimburseDAO.getRequestById(rId);
		reimburse.setStatus(status);
		
		if(reimburseDAO.updateRequest(rId, eId, status)) {
			System.out.println("Updated Request Status...");
		}else {
			System.out.println("Request Status NOT Updated");
		}
		
		request.getRequestDispatcher("man-homepage.html").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

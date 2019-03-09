package com.revature;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    public ServletLogin() {
        super();
        
    }
    
    @Override
    public void init() {
    	System.out.println("Hey! Starting...");
    }
	
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String user = (String) session.getAttribute("username");
    	//response.setContentType("text/html); charset=UTF-8");
    	
    	if(user == null) {
    		user = request.getParameter("username");
    	}
		String password = request.getParameter("password");
		
		DAOUtil cred = new DAOUtil();
		EmployeeDAO empDAO = DAOUtil.getEmployeeDAO();
		int empId = cred.tryLogin(user, password);
	
		if(empId > 0) {
			EmployeeDAO eDAO = empDAO.getEmployeeById(empId);
			session.setAttribute("email", user);
			session.setAttribute("id", empId);
			session.setAttribute("isManager", eDAO.isManager());
			System.out.println("Mananger? "+eDAO.isManager());
			
			//Check if Employee is a Manager
			if(eDAO.isManager) {
				request.getRequestDispatcher("/man-homepage.html").forward(request, response);
			}else {
				request.getRequestDispatcher("/homepage.html").forward(request, response);
			}
			
		}else {
			//response.sendRedirect("index.html");
			//response.getWriter().write("Failed to Login");
			
			PrintWriter out = response.getWriter();
			out.println("<body><h2>Login Fail</h2>");
			// Hyperlink "Login" to input page
			out.println("<a href='index.html'>Try Again</a>");
			out.println("</body></html>");
			out.close();
			
			System.out.println("Failed to Login");
			}
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

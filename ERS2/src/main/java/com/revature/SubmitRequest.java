package com.revature;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;


public class SubmitRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String id ="";
    String reason ="";
    String amount="";
   
    public SubmitRequest() {
        super();
    }
    
    public void init() {
    	System.out.println("Submitting Request...");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int employeeId = -1;
		employeeId = (int) request.getSession(false).getAttribute("id");
		Double amount = Double.valueOf(request.getParameter("amount"));
		String reason = request.getParameter("reason");
		String status = "pending";
		ReimburseDAO sub = new ReimburseDAO(amount, employeeId, reason, status);
		
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Request info: "
				+"\nID: $"+id
				+"\nManager: "+reason
				+"\nReason: "+amount);
		
		//Send information back to be displayed
//		JSONObject rei = new JSONObject();
//		
//		rei.put("amount", new String(id));
//		rei.put("manager", new String(reason));
//		rei.put("reason", new String(amount));
//		
//		StringWriter out = new StringWriter();
//		rei.writeJSONString(out);
//		
//		String jsonText = out.toString();
//		response.getWriter().write(jsonText);
		
		ReimburseDAO reDAO = DAOUtil.getReimburseDAO();
		
		if(reDAO.addRequest(sub)) {
			System.out.println("Added Successfully");
			request.getRequestDispatcher("homepage.html").forward(request, response);
		}else {
			System.out.println("Failure!");
		}
		
			
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

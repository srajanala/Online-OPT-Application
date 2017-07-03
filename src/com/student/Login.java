package com.student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Login {
	DataSource ds;
	Connection con;
	Statement stm;
	ResultSet rs;
	
	@Autowired
	Login(DataSource ds){
		this.ds =ds;
	}
	
	@RequestMapping(value="/Login")
	public String Login(){
		System.out.println("Login Controller Invoked");
		return "login";
	}
	
	
	@RequestMapping(value="/CheckLogin")	
	public void  checkLogin(HttpServletRequest request, HttpServletResponse response ){
		boolean sessionCheck = false;
		int stdId = 0;
		String role ="";
		RequestDispatcher rd =request.getRequestDispatcher("Login"); 
		System.out.println("Login Controller invoked");
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		String query ="select id, role from user_details where username='"+userName+"' and " +
						" pass_word='"+passWord+"'";
		try{
			con = ds.getConnection();
			stm = con.createStatement();
			rs = stm.executeQuery(query);
			while(rs.next()){
				sessionCheck = true;
				stdId=rs.getInt(1);
				role = rs.getString(2);
				System.out.println("Returning to Home");
				rd = request.getRequestDispatcher("home");
			}
			if(sessionCheck){
				HttpSession https = request.getSession();
				https.setAttribute("sesCreate","true");
				https.setAttribute("id", stdId);
				https.setAttribute("role", role);
				System.out.println("session Created");
			}
		
		System.out.println("Returning to Login");
		rd.forward(request, response);
		
		}catch(Exception ex){
			System.out.println("Exception occured in "+this.getClass().getName());
			System.out.println(ex);
		}
		
	}
}

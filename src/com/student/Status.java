package com.student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Status {
	
	private DataSource ds;
	private Connection con;
	private Statement stm;
	private ResultSet rm;
	private List ls;
	String role;
	int std_id;
	@Autowired
	Status(DataSource ds){
		this.ds = ds;
		try{
			con = ds.getConnection();
		
		}catch(Exception ex){
			System.out.println("Exception Occured while connecting to DataSource"+this.getClass().getName());
		}
	}
	
	@RequestMapping(value="/Status")
	public String Getstatus(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(false);
		System.out.println("Status Controller Invoked "+session.getAttribute("role")+session.getAttribute("id"));
		 role = (String) session.getAttribute("role");
		 std_id = (Integer) session.getAttribute("id");
		request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		
		return "Status";
	}
	
	@RequestMapping(value="/StatusInfo")
	public String StatusInfo(@RequestParam(value="requestNumber") int reqNum,HttpServletRequest request, HttpServletResponse response ){
		System.out.println("Controller Status Info Invoked"+reqNum);
		ls = new ArrayList();
		HttpSession session = request.getSession(false);
		role = (String) session.getAttribute("role");
		 std_id = (Integer) session.getAttribute("id");
		String query = "select date_of_approve, stat.request_status, stat.pending_with, application_id from "+ 
					" application_status as stat "+ "where  stat.application_id="+reqNum;
		try{
			stm = con.createStatement();
			rm = stm.executeQuery(query);
			while(rm.next()){
				ls.add(rm.getDate(1));
				ls.add(rm.getString(2));
				ls.add(rm.getString(3));
				ls.add(rm.getInt(4));
			}
			System.out.println("Result Set"+ls.size());
			if(ls == null || ls.size()==0){
				System.out.println("Else part Invoked");
				ls.add("No Record Found for given Request Number"+reqNum);
			}
		}catch(Exception ex){
			System.out.println("Exception while retrieving application status"+ex);
		}
		request.setAttribute("StatInfo", ls);
		request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		return "Statusinfo";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex,HttpServletRequest request) {
		System.out.println("Controller Exception Invoked"+request);
		request.setAttribute("role", "STUDENT");
		return "Error";
 
	}

}

package com.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentDetails  {

	Integer std_id;
	String role;
	private DataSource ds;
	private Connection con;
	private Statement stm;
	private ResultSet rm;
	private Student std;
	private List list;
	private ApplicationDetails apd;
	@Autowired	
	StudentDetails(DataSource ds){
		this.ds = ds;
        System.out.println("Got Datasource"+ds);
        
	}
	@RequestMapping("/home")
	 public ModelAndView Home(HttpServletRequest request, HttpServletResponse response) {
		 	System.out.println("Controller Home Invoked");
		 	ModelAndView mv;		 	 	
		 	HttpSession https = request.getSession(false);
		 	System.out.println("request id:"+https.getAttribute("id")+request.getParameter("role"));
		 	System.out.println(https.getAttribute("id"));
		 	System.out.println("https == null"+(https==null));
		 	System.out.println("https == null"+(https.getAttribute("sesCreate")==null));
		 	if(https.getAttribute("sesCreate") != null && (https.getAttribute("sesCreate").equals("true"))){		 	
		 	std = new Student();
		 	
		 		std_id = (Integer)https.getAttribute("id");
		 		role = (String)https.getAttribute("role");
		 	
		 	System.out.println("ID:"+std_id+"role:"+role);
	        try {
				 con = ds.getConnection();
				 stm = con.createStatement();
				 	System.out.println("Statement Created");
					String query = "select * from student where student_id ="+std_id;
					System.out.println(query);
					//ResultSet resultSet = statement.executeQuery("select firstName, mi, lastName from Account where lastName" + " = 'Smith'");
					 rm = stm.executeQuery(query);
					 while (rm.next()){
							System.out.println(rm.getString(1) + "\t" +
									rm.getString(2) + "\t"+rm.getDate(10).toString() );
							std.setFirstName(rm.getString(1));
							std.setLastName(rm.getString(2));
							std.setStudent_id(rm.getInt(3));
							std.setEmail(rm.getString(4));
							std.setAddress(rm.getString(5));
							std.setCity(rm.getString(6));
							std.setState(rm.getString(7));
							std.setZipcode(rm.getString(8));
							std.setPhoneNumber(rm.getString(9));
							std.setDateOfBirth(rm.getDate(10).toString());
							std.setMajor(rm.getString(11));					
					 }
					 query = "select opt.application_id, student.student_id, first_name, last_name, email, major from "+
							 "optapplication as opt join application_status as stat on opt.application_id = stat.application_id"+
							 " join student on student.student_id = opt.student_id where opt.student_id = "+std_id+" and " +
							 "stat.request_status = 'Decline' and stat.pending_with = 'STUDENT'";
					 System.out.println(query);
					 rm = stm.executeQuery(query);
					 list = new ArrayList();
						while(rm.next()){
							System.out.println("Result Set"+rm.getInt(1));
							ApplicationInfo app = new ApplicationInfo();
							app.setAppID(rm.getInt(1));
							app.setStudentId(rm.getInt(2));
							app.setFirstName(rm.getString(3));
							app.setLastName(rm.getString(4));
							app.setEmail(rm.getString(5));
							app.setMajor(rm.getString(6));
							list.add(app);
						}
						System.out.println("LIst Length"+list.size());
							// Close the connection
							con.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         mv = new ModelAndView("student", "std", std);
	        mv.addObject(std);
	        request.setAttribute("role", role);
			request.setAttribute("id", std_id);
	        request.setAttribute("aplicationList", list);
	        return mv;
		 	}else{
		 		System.out.println("Invalid Session");
		 		mv = new ModelAndView("login");
		 	return mv;
		 	}
	    }

	@RequestMapping("/Requestopt")
    public ModelAndView RequestOpt(HttpServletRequest request) {
		ModelAndView mv;
		HttpSession https = request.getSession(false);
		if(https.getAttribute("sesCreate") != null && (https.getAttribute("sesCreate").equals("true"))){
	 	System.out.println("Controller ReauestOpt Invoked");
        mv = new ModelAndView("requestOpt", "std", std);
        mv.addObject(std);
        request.setAttribute("role", role);
		request.setAttribute("id", std_id);
        mv.addObject("application", new ApplicationDetails());
        
        return mv;
		} else{
			System.out.println("Invalid Session");
	 		mv = new ModelAndView("login");
			return mv;
		}
    }
	
	@RequestMapping("/ViewDeclineApplication")
    public ModelAndView ViewDeclineApplication(HttpServletRequest request,@RequestParam(value="std_id") String std_id,
    						@RequestParam(value="applId") String appl_id) {
	 	System.out.println("Controller ViewDeclineApplication Invoked");
	 	ModelAndView mv;
	 	HttpSession https = request.getSession(false);
		if(https.getAttribute("sesCreate") != null && (https.getAttribute("sesCreate").equals("true"))){
	 	std = new Student();
		apd = new ApplicationDetails();
		try {
			 con = ds.getConnection();
			 stm = con.createStatement();
			 	System.out.println("Statement Created");
				String query = "select * from student where student_id ="+Integer.parseInt(std_id);
				System.out.println(query);
				//ResultSet resultSet = statement.executeQuery("select firstName, mi, lastName from Account where lastName" + " = 'Smith'");
				//Retrieving Student Personal INfo
				 rm = stm.executeQuery(query);
				 while (rm.next()){
						System.out.println(rm.getString(1) + "\t" +
								rm.getString(2) + "\t"+rm.getDate(10).toString() );
						std.setFirstName(rm.getString(1));
						std.setLastName(rm.getString(2));
						std.setStudent_id(rm.getInt(3));
						std.setEmail(rm.getString(4));
						std.setAddress(rm.getString(5));
						std.setCity(rm.getString(6));
						std.setState(rm.getString(7));
						std.setZipcode(rm.getString(8));
						std.setPhoneNumber(rm.getString(9));
						std.setDateOfBirth(rm.getDate(10).toString());
						std.setMajor(rm.getString(11));					
				 }
				 System.out.println("First query executed");
				 //Retrieving Student OPT application Info
				 query = "select i_94, first_entered, last_entered, lev_of_edu, begin_date_emp, prev_cpt_from, prev_cpt_to," +
				 		" prev_opt_from, prev_opt_to, date_of_grad, comments from optapplication join application_status " +
				 		"on optapplication.application_id = application_status.application_id where optapplication.application_id="+Integer.parseInt(appl_id);
				 System.out.println("second"+query);
				 rm = stm.executeQuery(query);
				 while(rm.next()){
					 apd.setI_94(rm.getString(1));
					 apd.setFirstEntered(rm.getString(2));
					 apd.setLastEntered(rm.getString(3));
					 apd.setLevel_of_education(rm.getString(4));
					 apd.setEmpAfterEdu(rm.getString(5));
					 apd.setPreCptFrom(rm.getString(6));
					 apd.setPreCptTo(rm.getString(7));
					 apd.setPreOptFrom(rm.getString(8));
					 apd.setPreOptTo(rm.getString(9));
					 apd.setExptDateGrad(rm.getString(10));
					 apd.setComments(rm.getString(11));
				 }
						// Close the connection
						con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		request.setAttribute("std", std);
		request.setAttribute("apd", apd);
		request.setAttribute("applId", Integer.parseInt(request.getParameter("applId")));
		request.setAttribute("role", role);
		request.setAttribute("id", this.std_id);
		mv = new ModelAndView("ViewDeclineApplication");
		mv.addObject("application", new ApplicationDetails());		
        return mv;
		}else{
			System.out.println("Invalid Session");
	 		mv = new ModelAndView("login");
			return mv;
		}
    }
	
	@RequestMapping("/UpdateDeclineApplication")
    public ModelAndView UpdateDeclineApplication(HttpServletRequest request,@RequestParam(value="std_id") String std_id,
    						@RequestParam(value="applId") String appl_id,@ModelAttribute("application")ApplicationDetails application) { 
		System.out.println("Update Decline Controller Invoked");
		System.out.println(application.getComments()+application.getExptDateGrad());
		int studentId = Integer.parseInt(std_id);
		int applicationId = Integer.parseInt(appl_id);
		ModelAndView mv;
	 	HttpSession https = request.getSession(false);
		if(https.getAttribute("sesCreate") != null && (https.getAttribute("sesCreate").equals("true"))){
	 	
		String query = "update optapplication set i_94='"+application.getI_94()+"', first_entered='"+application.getFirstEntered()+ 
				"',last_entered='"+application.getLastEntered()+"', lev_of_edu='"+application.getLevel_of_education()+"',begin_date_emp='" +
				application.getEmpAfterEdu()+"',prev_cpt_from='"+application.getPreCptFrom()+"', prev_cpt_to='"+application.getPreCptTo()+
				"',prev_opt_from='"+application.getPreOptFrom()+"',prev_opt_to='"+application.getPreOptTo()+"',date_of_grad='"+application.getExptDateGrad()+
				"', pending_with='ISSO' where student_id ="+studentId+" and application_id="+applicationId;
		System.out.println("Query:"+query);
		try {
			 con = ds.getConnection();
			 stm = con.createStatement();
			 int i = stm.executeUpdate(query);
			 if(i>0)
				 	System.out.println("Updated Successfuly to OPtapplication table");
			 else
				 System.out.println("OPtapplication table updation failed");
			 
			 query = "update  Application_Status set request_status = 'Submitted', pending_with='ISSO', date_of_approve=curdate()"+
					 "where application_id="+applicationId;
			 System.out.println("query:"+query);
			 i = stm.executeUpdate(query);
			 if(i>0)
				 	System.out.println("Updated Successfuly to Application_status table");
			 else
				 System.out.println("Application_status table updation failed");
		}catch(Exception ex){
			System.out.println("Exception occured"+this.getClass().getName());
			System.out.println(ex);
		}
		request.setAttribute("formAction", "declineUpdate");
		mv = new ModelAndView("ApplicationApprove");
		request.setAttribute("role", role);
		request.setAttribute("id", this.std_id);
		return mv;
		}else{
			System.out.println("Invalid Session");
	 		mv = new ModelAndView("login");
			return mv;
		}
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex,HttpServletRequest request) {
		System.out.println("Controller Exception Invoked"+request);
		request.setAttribute("role", "STUDENT");
		//request.setAttribute("id", 700608171);
		return "Error";
 
	}
	
}



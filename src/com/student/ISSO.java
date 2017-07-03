package com.student;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;

@Controller
public class ISSO {
	Integer std_id;
	String role;
	private DataSource ds;
	private Connection con;
	private Statement stm;
	private ResultSet rm;
	private List list;
	private Student std;
	private ApplicationDetails apd;

	@Autowired
	ISSO(DataSource ds){
		this.ds = ds;
		try{
			con = ds.getConnection();
		
		}catch(Exception ex){
			System.out.println("Exception Occured while connecting to DataSource"+this.getClass().getName());
		}
	}
	@RequestMapping(value="/isso")
	public ModelAndView ApplicationInfo(HttpServletRequest request){
		System.out.println("Invoked Isso applicationINfo");
		String query;
		HttpSession https = request.getSession(false);
		std_id = (Integer)https.getAttribute("id");
 		role = (String)https.getAttribute("role");
 		System.out.println("ID:"+std_id+"role:"+role);
		
		String str ="ISSO";
		if(role.equals("ISSO")){
		 query = "select application_id , s.student_id, first_name, last_name, email, major"+
						" from Student as s, optapplication as opt" + " where s.student_id = opt.student_id" +
								" and pending_with ='ISSO'";
		}else{
			query = "select application_id , s.student_id, first_name, last_name, email, major"+
					" from Student as s, optapplication as opt" + " where s.student_id = opt.student_id" +
							" and pending_with ='IS'";
		}
		System.out.println("query"+query);
		try{
			stm = con.createStatement();
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
		
		}catch(Exception ex){
			System.out.println("Exception while getting INfo"+this.getClass().getName());
			System.out.println(ex);
		}
		
		ModelAndView mv = new ModelAndView("isso");
		//mv.addObject("applList", list);
		request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		request.setAttribute("aplicationList", list);
		return mv;
	}
	@RequestMapping(value="/viewapplication")
	public ModelAndView ViewApplication(HttpServletRequest request){
		System.out.println("ViewApplication Controller Invoked"+request.getParameter("applId"));
		HttpSession https = request.getSession(false);
		std_id = (Integer)https.getAttribute("id");
 		role = (String)https.getAttribute("role");
 		System.out.println("ID:"+std_id+"role:"+role);
		
		std = new Student();
		apd = new ApplicationDetails();
		try {
			 con = ds.getConnection();
			 stm = con.createStatement();
			 	System.out.println("Statement Created");
				String query = "select * from student where student_id ="+Integer.parseInt(request.getParameter("std_id"));
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
				 //Retrieving Student OPT application Info
				 query = "select * from optapplication where application_id="+Integer.parseInt(request.getParameter("applId"));
				 rm = stm.executeQuery(query);
				 while(rm.next()){
					 apd.setI_94(rm.getString(3));
					 apd.setFirstEntered(rm.getString(4));
					 apd.setLastEntered(rm.getString(5));
					 apd.setLevel_of_education(rm.getString(6));
					 apd.setEmpAfterEdu(rm.getString(7));
					 apd.setPreCptFrom(rm.getString(8));
					 apd.setPreCptTo(rm.getString(9));
					 apd.setPreOptFrom(rm.getString(10));
					 apd.setPreOptTo(rm.getString(11));
					 apd.setExptDateGrad(rm.getString(12));
				 }
						// Close the connection
						con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		ModelAndView mv = new ModelAndView("viewapplication");
		request.setAttribute("std", std);
		request.setAttribute("apd", apd);
		request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		request.setAttribute("applId", Integer.parseInt(request.getParameter("applId")));
		System.out.println("Before Return");
		return mv;
		
	}
	
	@RequestMapping(value="ApplicationApprove")
	public String ApplicationApprove(HttpServletRequest request,@RequestParam(value="formAction") String action){
		int stdId = Integer.parseInt(request.getParameter("std_id"));
		int applId = Integer.parseInt(request.getParameter("applId"));
		String query;
		String comments = request.getParameter("comments");
		HttpSession https = request.getSession(false);
		std_id = (Integer)https.getAttribute("id");
 		role = (String)https.getAttribute("role");
 		System.out.println("ID:"+std_id+"role:"+role);
		System.out.println("Application Approve Controller Invoked"+request.getParameter("applId")+"action:"+action);
		if(action.equals("approve")){
			if(role.equals("ISSO")){
				query = "update optapplication set pending_with='IS' where student_id="+stdId
							+" and application_id="+applId;
				}else{
					query = "update optapplication set pending_with='NONE' where student_id="+stdId
							+" and application_id="+applId;
				}
		System.out.println("query"+query);
		try {
			 con = ds.getConnection();
			 stm = con.createStatement();
			 int i = stm.executeUpdate(query);
			 if(i>0)
				 	System.out.println("Updated Successfuly to OPtapplication table");
			 else
				 System.out.println("OPtapplication table updation failed");
			 if(role.equals("ISSO")){
			 query = "update Application_Status set request_status = 'Approved', pending_with='', date_of_approve=curdate(),"+
					 "comments='"+comments+"' where application_id="+applId;
			 }else {
				 query = "update Application_Status set request_status = 'Completed', pending_with='None', date_of_approve=curdate(),"+
						 "comments='"+comments+"' where application_id="+applId;
				
			 }
			 i = stm.executeUpdate(query);
			 System.out.println(query);
			 if(i>0)
				 	System.out.println("Updated Successfuly to Application_status table");
			 else
				 System.out.println("Application_status table updation failed");
		}catch(Exception ex){
			System.out.println("Exception occured"+this.getClass().getName());
			System.out.println(ex);
		}
		request.setAttribute("std_id", stdId);
		request.setAttribute("applId", applId);
		request.setAttribute("formAction", action);
		request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		return "ApplicationApprove";
		}else{
			System.out.println("Decline Controller");
			 query = "update optapplication set pending_with='STUDENT' where student_id="+stdId
					+" and application_id="+applId;
			try {
				con = ds.getConnection();
				 stm = con.createStatement();
				 int i = stm.executeUpdate(query);
				 if(i>0)
					 	System.out.println("Updated Successfuly to OPtapplication table");
				 else
					 System.out.println("OPtapplication table updation failed");
				 query = "update Application_Status set request_status = 'Decline', pending_with='STUDENT', date_of_approve=curdate(),"+
						 "comments='"+comments+"' where application_id="+applId;
				 i = stm.executeUpdate(query);
				 System.out.println(query);
				 if(i>0)
					 	System.out.println("Updated Successfuly to Application_status table");
				 else
					 System.out.println("Application_status table updation failed");
				}catch(Exception ex){
				System.out.println("Exception occured"+this.getClass().getName());
				System.out.println(ex);
			}
			request.setAttribute("std_id", stdId);
			request.setAttribute("applId", applId);
			request.setAttribute("formAction", action);
			request.setAttribute("role", role);
			request.setAttribute("id", std_id);
			
			return "ApplicationApprove";
		}
	}
	
	@RequestMapping(value="/downLoadFile")
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response){
		
		System.out.println("Download File Controller Invoked");
		int BUFFER_SIZE = 4096;
		String filePath = request.getParameter("std_id")+".pdf";
		ServletContext context = request.getServletContext();
		
        String appPath = context.getRealPath("");
        
        
        String rootPath = context.getRealPath("resources");
		//System.getProperty("catalina.home");
        System.out.println(context.getRealPath("resources"));
 
        // construct the complete absolute path of the file
        String fullPath = appPath + filePath;
        File dir = new File( rootPath+File.separator+"images");
        File downloadFile = new File(dir.getAbsolutePath()
                + File.separator +filePath );
        try{
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        // get MIME type of the file
        String mimeType = context.getMimeType(dir.getAbsolutePath()
                + File.separator +filePath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
        }catch(Exception e){
        	System.out.println("Exception"+e);
        }
 
    }
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex,HttpServletRequest request) {
		System.out.println("Controller Exception Invoked"+request);
		request.setAttribute("role", "ISSO");
		return "Error";
 
	}
	
}

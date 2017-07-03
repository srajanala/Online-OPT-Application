package com.student;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;

@Controller
public class EADDetails {
	Integer std_id;
	String role;
	private DataSource ds;
	private Connection con;
	private Statement stm;
	private ResultSet rm;
	private List list;
	private EADDetailsList eadApp;
	private EADApplication apd;
	
	@Autowired
    private JavaMailSender mailSender;
	@Autowired
	EADDetails(DataSource ds){
		this.ds = ds;
		try{
			con = ds.getConnection();
		
		}catch(Exception ex){
			System.out.println("Exception Occured while connecting to DataSource"+this.getClass().getName());
		}
	}

	@RequestMapping(value="/EadApplication")
	public String EadRequests(HttpServletRequest request){
		System.out.println("Invoked Isso applicationINfo");
		HttpSession https = request.getSession(false);
		std_id = (Integer)https.getAttribute("id");
 		role = (String)https.getAttribute("role");
 		System.out.println("ID:"+std_id+"role:"+role);
		String query = "select ead.application_id, first_name, last_name, email, i_94 from ead_request as ead join"+
						" application_status as stat on ead.application_id = stat.application_id"+
						" where stat.pending_with = 'IS'";
		try{
			stm = con.createStatement();
			rm = stm.executeQuery(query);
			list = new ArrayList();
			while(rm.next()){
				eadApp = new EADDetailsList();
				eadApp.setApplId(rm.getInt(1));
				eadApp.setFirstName(rm.getString(2));
				eadApp.setLastName(rm.getString(3));
				eadApp.setEmail(rm.getString(4));
				eadApp.setI_94(rm.getString(5));
				list.add(eadApp);
			}
		}catch(Exception ex){
			System.out.println("Exception while retrieving EAD details");
		}
		request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		request.setAttribute("eadList", list);
		return "EadDetails";
	}
	
	@RequestMapping(value="/downLoadEadFile")
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response){
		
		System.out.println("Download File Controller Invoked");
		int BUFFER_SIZE = 4096;
		String filePath = request.getParameter("i_94")+".rar";
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

	
	@RequestMapping(value="/viewEaddetails")
	public String ViewApplication(HttpServletRequest request){
		System.out.println("ViewApplication Controller Invoked"+request.getParameter("applId"));
		String i_94 = request.getParameter("i_94");
		HttpSession https = request.getSession(false);
		std_id = (Integer)https.getAttribute("id");
 		role = (String)https.getAttribute("role");
 		System.out.println("ID:"+std_id+"role:"+role);
		
		apd = new EADApplication();
		
		try {
			 con = ds.getConnection();
			 stm = con.createStatement();
			 	System.out.println("Statement Created");
				String query = "select * from ead_request where I_94 ='"+i_94+"'";
				System.out.println(query);
				//ResultSet resultSet = statement.executeQuery("select firstName, mi, lastName from Account where lastName" + " = 'Smith'");
				//Retrieving Student Personal INfo
				 rm = stm.executeQuery(query);
				 while (rm.next()){
						
						apd.setFirstName(rm.getString(2));
						apd.setLastName(rm.getString(3));
						apd.setPassPortNum(rm.getString(4));
						apd.setEmail(rm.getString(5));
						apd.setPhoneNumber(rm.getString(6));
						apd.setI_94(rm.getString(7));
						apd.setAddress(rm.getString(8));
				 }
				 //Retrieving Student OPT application Info
				
						// Close the connection
						con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		;
		request.setAttribute("apd", apd);
		request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		request.setAttribute("applId", Integer.parseInt(request.getParameter("applId")));
		System.out.println("Before Return");
		return "viewEaddetails";
		
	}
	
	@RequestMapping(value="/EadApplApprove")
	
	public String ApplicationApprove(HttpServletRequest request,@RequestParam(value="formAction") String action){
		System.out.println("Application Approve Controller Invoked"+request.getParameter("applId")+"action:"+action);
		
		String msg="";
		String text="";
		String mailId = request.getParameter("emailId"); 
		int applId = Integer.parseInt(request.getParameter("applId"));
		String query;
		String comments = request.getParameter("comments");
		HttpSession https = request.getSession(false);
		std_id = (Integer)https.getAttribute("id");
 		role = (String)https.getAttribute("role");
 		SimpleMailMessage simple = new SimpleMailMessage();
 		if(action.equals("approve")){
		 query ="update Application_Status set request_status = 'Approved', pending_with='', date_of_approve=curdate(),"+
					 "comments='"+comments+"' where application_id="+applId;
		 try {
			 con = ds.getConnection();
			 stm = con.createStatement();
			 int i = stm.executeUpdate(query);
			 if(i>0){
				 	System.out.println("Updated Successfuly to Application_Status table"+mailId);
				 	msg = applId+"Application is Approved Successfully";
				 	System.out.println("Sendmail"+mailSender);
					text = "Your EAD request has approved by Immigration service. Your EAD card will be mailed to provided address"+
							"in 4 bussinees weeks";
					simple.setTo(mailId);
					simple.setSubject("EAD Approval");
					simple.setText(text);
				  
			        // sends the e-mail
			        
			        mailSender.send(simple);
			 }
			 else{
				 System.out.println("Application_Status table updation failed");
				 msg = applId+"Application Approval is Unsuccessful";
			 }
		 }catch(Exception ex){
			 System.out.println("Exception during EAD update");
		 }
		
 		}else{
 			
 			query ="update Application_Status set request_status = 'Declined', pending_with='', date_of_approve=curdate(),"+
					 "comments='"+comments+"' where application_id="+applId;
 			 try {
 				 con = ds.getConnection();
 				 stm = con.createStatement();
 				 int i = stm.executeUpdate(query);
 				 if(i>0){
 					 	System.out.println("Updated Successfuly to Application_Status table");
 					 	msg = applId+"Application is Declined Successfully";
 					 	System.out.println("Sendmail"+mailSender);
 						text ="Your EAD request has declined with reason: "+comments+
 								" .please raise new request with modify details";
 						simple.setTo(mailId);
 						simple.setSubject("EAD Approval");
 						simple.setText(text);
 					  
 				        // sends the e-mail
 				        
 				        mailSender.send(simple);
 				 }
 				 else{
 					 System.out.println("Application_Status table updation failed");
 					 msg = applId+"Application Decline is Unsuccessful";
 				 }
 			 }catch(Exception ex){
 				 System.out.println("Exception during EAD update");
 			 }
 			
 		}
 		request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		request.setAttribute("applId", applId);
		request.setAttribute("formAction", action);
		request.setAttribute("msg", msg);
		request.setAttribute("mailId", mailId);
		
		return "EadApplApprove";
	}
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex,HttpServletRequest request) {
		System.out.println("Controller Exception Invoked"+request);
		request.setAttribute("role", "IS");
		return "Error";
 
	}
}

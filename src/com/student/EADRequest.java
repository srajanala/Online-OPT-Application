package com.student;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EADRequest {
	String role;
	int std_id;
	private DataSource ds;
	private Connection con;
	private PreparedStatement stm;
	private int application_id;
	private ResultSet rs;
	String i_94;
		
	@Autowired
	EADRequest(DataSource ds){
		this.ds = ds;
		try{
			con = ds.getConnection();
		
		}catch(Exception ex){
			System.out.println("Exception Occured while connecting to DataSource"+this.getClass().getName());
		}
	}
	@RequestMapping(value="/EadRequest")
	public String EadRequest(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv;
		HttpSession session = request.getSession(false);
		System.out.println("Status Controller Invoked "+session.getAttribute("role")+session.getAttribute("id"));
		HttpSession https = request.getSession(false);
		if(https.getAttribute("sesCreate") != null && (https.getAttribute("sesCreate").equals("true"))){
		 role = (String) session.getAttribute("role");
		 std_id = (Integer) session.getAttribute("id");
		 request.setAttribute("role", role);
		 request.setAttribute("id", std_id);
		 mv = new ModelAndView();
		 mv.addObject("eadApplicaton", new EADApplication());
		 return "EadRequest";
		}else{
			System.out.println("Invalid Session");
	 		return "login";
		}
		
	}
	
	@RequestMapping(value="/EadSubmit")
	public String EadSubmission(@ModelAttribute("eadApplicaton")EADApplication ead,HttpServletRequest request
			,HttpServletResponse response,@RequestParam("fileName") String name, @RequestParam("file") MultipartFile file){
		
		System.out.println("Submit Controller Invoked"+ead.getFirstName()+request.getParameter("student_id"));
		String sql ="insert into ead_request(first_name,last_name,passportNum,email,phonenumber,i_94,address)"+
					 "values(?,?,?,?,?,?,?)";
		i_94 =  ead.getI_94();
		HttpSession https = request.getSession(false);
		std_id = (Integer)https.getAttribute("id");
 		role = (String)https.getAttribute("role");
 		System.out.println("ID:"+std_id+"role:"+role);
		String fileUploadStr = FileUpload(name,file,request);
		try {			
			stm = con.prepareStatement(sql);
			stm.setString(1, ead.getFirstName());
			stm.setString(2,ead.getLastName() );
			stm.setString(3, ead.getPassPortNum());
			stm.setString(4, ead.getEmail());
			stm.setString(5, ead.getPhoneNumber());
			stm.setString(6, ead.getI_94());
			stm.setString(7, ead.getAddress());			
			int i=stm.executeUpdate();
			if(i>0)
				System.out.println("Statement inserted to EADRequest table");
			else
				System.out.println("Insertion Failed to opt application table");
			System.out.println("After Insert Statement");
			sql = "select max(application_id) from ead_request";
			
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			while(rs.next())
				application_id = rs.getInt(1);
			System.out.println("Application"+application_id);
			
			sql = "insert into application_status values("+application_id+",'Submitted','IS',curdate(),' ')";
			System.out.println("sql:"+sql);
			stm = con.prepareStatement(sql);
			i=stm.executeUpdate();
			if(i>0)
				System.out.println("Statement inserted to opt application table");
			else
				System.out.println("Insertion Failed to opt application table");
			
		}catch(Exception ex){
			System.out.println("Exception updating EAD SUBMission"+ex);
		}
		
		request.setAttribute("str", Integer.toString(application_id));
        request.setAttribute("fileUploadStr", fileUploadStr);
        request.setAttribute("role", role);
		request.setAttribute("id", std_id);
		return "EadSubmit";
	}
	
	public String FileUpload(String name, MultipartFile file ,HttpServletRequest request){
		int i = name.lastIndexOf(File.separator);
		String fileName = name.substring(i+1, name.length());
		
		System.out.println("values :"+file+"Name:"+name+"STR"+fileName);
		System.out.println(request.getRequestURL());
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = request.getServletContext().getRealPath("resources");
                		//System.getProperty("catalina.home");
                System.out.println(request.getServletContext().getRealPath("resources"));
                System.out.println("RootPath"+rootPath);
                File dir = new File( rootPath+File.separator+"images");
                if (!dir.exists())
                    dir.mkdirs();
                System.out.println("Directory Created"+dir.getAbsolutePath());
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                File f1 = new File(serverFile.getAbsolutePath());
                File f2 = new File(rootPath+File.separator+"images"+File.separator+i_94+".rar");
                if(f2.exists()){
                	System.out.println("File exists");
                	f2.delete();
                }
                else
                	System.out.println("NO file");
                System.out.println(f1.renameTo(f2));
                System.out.println(f1.renameTo(f2));
 
                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());
 
                return "Successfully uploaded file=" + fileName;
            } catch (Exception e) {
                return "Failed to upload " + fileName + " => " + e.getMessage();
            }
		}else {
                return "Failed to upload file" + fileName
                        + " because the file was empty.";
            }
		

	}
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex,HttpServletRequest request) {
		System.out.println("Controller Exception Invoked"+request);
		request.setAttribute("role", "IS");
		return "Error";
 
	}
}

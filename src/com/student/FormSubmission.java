package com.student;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.portlet.ModelAndView;



@Controller
public class FormSubmission {
	private DataSource ds;
	private Connection con;
	private PreparedStatement stm;
	private int application_id;
	private ResultSet rs;
	public FormSubmission fm;
	Integer std_id;
	String role;
	public int getApplication_id() {
		return application_id;
	}

	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}

	@Autowired
	FormSubmission(DataSource ds){
		this.ds = ds;
		try{
			con = ds.getConnection();
		
		}catch(Exception ex){
			System.out.println("Exception Occured while connecting to DataSource"+this.getClass().getName());
		}
	}
	@RequestMapping(value="/submit")
	public ModelAndView SubmitForm(@ModelAttribute("application")ApplicationDetails application,HttpServletRequest request
									,HttpServletResponse response,@RequestParam("fileName") String name, @RequestParam("file") MultipartFile file){
		System.out.println("Submit Controller Invoked"+application.getI_94()+request.getParameter("student_id"));
		String sql ="insert into OptApplication(student_id,I_94,first_entered,last_entered,lev_of_edu,begin_date_emp,prev_cpt_from,"+
					 "prev_cpt_to,prev_opt_from,prev_opt_to,date_of_grad,date_submitted,pending_with)values(?,?,?,?,?,?,?,?,?,?,?,"+
					 "curdate(),'ISSO')";
		HttpSession https = request.getSession(false);
		std_id = (Integer)https.getAttribute("id");
 		role = (String)https.getAttribute("role");
 		System.out.println("ID:"+std_id+"role:"+role);
		String fileUploadStr = FileUpload(name,file,request);
		try {			
			stm = con.prepareStatement(sql);
			stm.setInt(1, Integer.parseInt(request.getParameter("student_id")));
			stm.setString(2, application.getI_94());
			stm.setString(3,application.getFirstEntered() );
			stm.setString(4, application.getLastEntered());
			stm.setString(5, application.getLevel_of_education());
			stm.setString(6, application.getEmpAfterEdu());
			stm.setString(7, application.getPreCptFrom());
			stm.setString(8, application.getPreCptTo());
			stm.setString(9, application.getPreOptFrom());
			stm.setString(10, application.getPreOptTo());
			stm.setString(11, application.getExptDateGrad());
			int i=stm.executeUpdate();
			if(i>0)
				System.out.println("Statement inserted to opt application table");
			else
				System.out.println("Insertion Failed to opt application table");
			System.out.println("After Insert Statement");
			sql = "select application_id from optApplication where student_id ="+Integer.parseInt(request.getParameter("student_id"));
			
			stm = con.prepareStatement(sql);
			rs = stm.executeQuery();
			while(rs.next())
				application_id = rs.getInt(1);
			System.out.println("Application"+application_id);
			
			sql = "insert into application_status values("+application_id+",'Submitted','ISSO',curdate(),' ')";
			System.out.println("sql:"+sql);
			stm = con.prepareStatement(sql);
			i=stm.executeUpdate();
			if(i>0)
				System.out.println("Statement inserted to opt application table");
			else
				System.out.println("Insertion Failed to opt application table");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception Occured while Inserting Statement"+this.getClass().getName());
			System.out.println(e);
		}
		ModelAndView mv = new ModelAndView("submit");
        mv.addObject("str", new Integer(application_id));
        request.setAttribute("str", Integer.toString(application_id));
        request.setAttribute("fileUploadStr", fileUploadStr);
        request.setAttribute("role", role);
		request.setAttribute("id", std_id);
        return mv;
        
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
                File f2 = new File(rootPath+File.separator+"images"+File.separator+request.getParameter("student_id")+".pdf");
                if(f2.exists()){
                	System.out.println("File exists");
                	f2.delete();
                }
                else
                	System.out.println("NO file");
                System.out.println(f1.renameTo(f2));
 
                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());
 
                return "Successfully uploaded file: " + fileName;
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
		request.setAttribute("role", "STUDENT");
		return "Error";
 
	}

}

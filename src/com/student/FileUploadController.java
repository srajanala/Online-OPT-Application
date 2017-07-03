package com.student;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.portlet.ModelAndView;

@Controller
public class FileUploadController {

	private static final int BUFFER_SIZE = 4096;
	@RequestMapping(value="/file")
	public ModelAndView fileUpload(){
		System.out.println("File Upload Invoked");
		ModelAndView mv = new ModelAndView("file");
		return mv;
	}
	
	
	@RequestMapping(value="/uploadFile")
	public String uploadFile(@RequestParam("fileName") String name, @RequestParam("file") MultipartFile file
			,HttpServletRequest request){
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
                File f2 = new File(rootPath+File.separator+"images"+File.separator+"700608171.pdf");
                System.out.println(f1.renameTo(f2));
 
                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());
 
                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
		}else {
                return "You failed to upload " + name
                        + " because the file was empty.";
            }
		
		
		
	}
	
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response){
		
		System.out.println("Download File Controller Invoked");		
		String filePath = "700608171.pdf";
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
		request.setAttribute("role", "STUDENT");
		return "Error";
 
	}
		
	
}

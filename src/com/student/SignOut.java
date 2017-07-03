package com.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignOut {

	@RequestMapping(value="/SignOut")
	public String LogOut(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		System.out.println("Logout Controller Invoked"+session.getAttribute("id"));
		session.invalidate();
		//System.out.println("After session Invalidated"+session.getAttribute("id"));
		return "login";
	}
}

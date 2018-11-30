package com.microsoft.aad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * @author MehmetAlpGuler
 */

@Controller
public class FormBasedController {
	@RequestMapping("secure/mypage")
	public String mypage(Model model, Principal principal) {

		try {
			String userName = principal.getName();
			model.addAttribute("message", "Hi " + userName + ", Welcome to 'Spring Security Custom Login Form & Azure Active Directory'");
		} catch (Exception e){
			e.getMessage();
		}

		return "secure/mypage";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
}

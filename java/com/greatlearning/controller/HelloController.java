package com.greatlearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HelloController {
	
	@RequestMapping("/")
	
	public String showMainPage() {
		
		return "demo";
	}

}

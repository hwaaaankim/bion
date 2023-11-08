package com.dev.BionLifeScienceWar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/productCenter")
public class ProductManageController {

	@RequestMapping("/productManager")
	public String productManager() {
		
		return "program/company/productManager";
	}
	
	@RequestMapping("/productOverall")
	public String productOverall() {
		
		return "program/company/productOverall";
	}
}

package com.dev.BionLifeScienceWar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/brandCenter")
public class BrandManageController {

	@GetMapping("/brandManager")
	public String brandManager() {
		
		return "program/brand/brandManager";
	}
	
	@RequestMapping("/brandProductManager")
	public String brandProductManager() {
		
		return "program/brand/brandProductManager";
	}
	
	@RequestMapping("/brandProductOverall")
	public String brandProductOverall() {
		
		return "program/brand/brandProductOverall";
	}
	
	@RequestMapping("/brandProductDetail")
	public String brandProductDetail() {
		
		return "program/brand/brandProductDetail";
	}
	
	@RequestMapping("/brandProductInsertForm")
	public String brandProductInsertForm() {
		
		return "program/brand/brandProductInsertForm";
	}
	
	@RequestMapping("/brandFileManager")
	public String brandFileManager() {
		
		return "program/brand/brandFileManager";
	}
}

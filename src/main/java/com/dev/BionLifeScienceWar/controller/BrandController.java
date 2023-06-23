package com.dev.BionLifeScienceWar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BrandController {

	@RequestMapping("/brandManager")
	public String brandManager() {
		
		return "admin/brand/brandManager";
	}
	
	@RequestMapping("/brandSortManager")
	public String brandSortManager() {
		
		return "admin/brand/brandSortManager";
	}
	
	@RequestMapping("/brandProductManager")
	public String brandProductManager() {
		
		return "admin/brand/brandProductManager";
	}
}

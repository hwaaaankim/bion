package com.dev.BionLifeScienceWar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/brandCenter")
public class BrandManageController {

	@RequestMapping("/brandManager")
	public String brandManager() {
		
		return "program/brand/brandManager";
	}
}

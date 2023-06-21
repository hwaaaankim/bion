package com.dev.BionLifeScienceWar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {
	
	@RequestMapping("/memberLoginForm")
	public String memberLoginForm() {
		log.info("MEMBER LOGIN FORM");
		return "admin/login";
	}

}

package com.dev.BionLifeScienceWar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.BionLifeScienceWar.model.Member;
import com.dev.BionLifeScienceWar.service.MemberService;

@Controller
@RequestMapping("/api/v1")
public class APIController {

	@Autowired
	MemberService memberService;
	
	@PostMapping("/join")
	@ResponseBody
	public String adminJoin(Member member) {
		memberService.insertAdmin(member);
		return "success";
	}
}

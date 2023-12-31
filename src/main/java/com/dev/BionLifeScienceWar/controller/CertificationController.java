package com.dev.BionLifeScienceWar.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.Certification;
import com.dev.BionLifeScienceWar.repository.CertificationRepository;
import com.dev.BionLifeScienceWar.service.CertificationService;

@Controller
@RequestMapping("/admin")
public class CertificationController {

	
	@Autowired
	CertificationRepository certificationRepository;
	
	@Autowired
	CertificationService certificationService;
	
	@RequestMapping("/certificationManager")
	public String certificationManager(
			Model model
			) {
		model.addAttribute("certifications", certificationRepository.findAll());
		return "admin/certificationManager";
	}
	
	@RequestMapping("/certificationInsert")
	@ResponseBody
	public String bannerInsert(
			MultipartFile webFile,
			Certification certification,
			Model model
			) throws IllegalStateException, IOException {
		certificationService.certificationInsert(webFile, certification);
		StringBuffer sb = new StringBuffer();
		String msg = "인증서가 등록 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/certificationManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	@RequestMapping("/deleteCertification/{id}")
	@ResponseBody
	public String deleteBanner(
			@PathVariable Long id,
			Model model
			) {
		
		Certification b = certificationRepository.findById(id).get();
		
		certificationRepository.deleteById(id);
		StringBuffer sb = new StringBuffer();
		String msg = "인증서가 삭제 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/certificationManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
}

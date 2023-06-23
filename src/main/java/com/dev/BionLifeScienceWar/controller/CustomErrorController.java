package com.dev.BionLifeScienceWar.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.BionLifeScienceWar.repository.product.BigSortRepository;
import com.dev.BionLifeScienceWar.repository.product.MiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductRepository;
import com.dev.BionLifeScienceWar.repository.product.SmallSortRepository;

@Controller
public class CustomErrorController implements ErrorController{

	private String VIEW_PATH = "/error/";
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	BigSortRepository bigSortRepository;
	
	@Autowired
	MiddleSortRepository middleSortRepository;
	
	@Autowired
	SmallSortRepository smallSortRepository;
	
	@RequestMapping("/error")
	public String handleError(
			HttpServletRequest request,
			Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		if(status != null) {
			int statusCode = Integer.valueOf(status.toString());
//			System.out.println(statusCode);
			
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return VIEW_PATH+"404";
			}else if(statusCode == 500){
				return VIEW_PATH+"500";
			}else {
				return VIEW_PATH+"403";
			}
		}
		return "error";
	}
	
}

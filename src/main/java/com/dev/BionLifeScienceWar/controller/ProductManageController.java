package com.dev.BionLifeScienceWar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.repository.product.BigSortRepository;
import com.dev.BionLifeScienceWar.repository.product.MiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductRepository;
import com.dev.BionLifeScienceWar.repository.product.SmallSortRepository;

@Controller
@RequestMapping("/admin/productCenter")
public class ProductManageController {

	@Autowired
	BigSortRepository bigSortRepository;

	@Autowired
	MiddleSortRepository middleSortRepository;

	@Autowired
	SmallSortRepository smallSortRepository;

	@Autowired
	ProductRepository productRepository;
	
	
	@RequestMapping("/productManager")
	public String productManager(
			Model model, 
			@RequestParam(required = false) Long smallId,
			@RequestParam(required = false) Long middleId,
			@RequestParam(required = false) Long bigId,
			@RequestParam(required = false, defaultValue = "") String searchWord,
			@PageableDefault(size = 10) Pageable pageable
			) {
		Page<Product> products = productRepository.findAllBySubjectContains(pageable, searchWord);
		if (smallId != null) {
			products = productRepository.findAllBySmallSortAndSubjectContains(pageable,
					smallSortRepository.findById(smallId).get(), searchWord);
			model.addAttribute("smallsorts", smallSortRepository.findAll());
			model.addAttribute("middlesorts", middleSortRepository.findAll());
		
		}else if(smallId == null && middleId != null){
			Optional<MiddleSort> m = middleSortRepository.findById(middleId);
			products = productRepository.findAllByMiddleSortAndSubjectContains(pageable, m.get(), searchWord);
			model.addAttribute("middlesorts", middleSortRepository.findAll());
			model.addAttribute("smallsorts", smallSortRepository.findAllByMiddleSort(m.get()));
			
		}else if(smallId == null && middleId == null && bigId != null) {
			Optional<BigSort> b = bigSortRepository.findById(bigId);
			products = productRepository.findAllByBigSortAndSubjectContains(pageable, b.get(), searchWord);
			model.addAttribute("middlesorts", middleSortRepository.findAllByBigSort(b.get()));
		}else {
			products = productRepository.findAllBySubjectContains(pageable, searchWord);
		}
		int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
		int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
		model.addAttribute("products", products);
		model.addAttribute("middleId", middleId);
		model.addAttribute("smallId", smallId);
		model.addAttribute("bigId", bigId);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("bigsorts", bigSortRepository.findAll());
		return "program/company/productManager";
	}
	
	@RequestMapping("/productOverall")
	public String productOverall() {
		
		return "program/company/productOverall";
	}
	
	@RequestMapping("/productDetail")
	public String productDetail() {
		
		return "program/company/productDetail";
	}
	
	@RequestMapping("/productInsertForm")
	public String productInsertForm() {
		
		return "program/company/productInsertForm";
	}
	
	@RequestMapping("/fileManager")
	public String fileManager() {
		
		return "program/company/fileManager";
	}
}

package com.dev.BionLifeScienceWar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.BionLifeScienceWar.dto.MenuDTO;
import com.dev.BionLifeScienceWar.model.brand.BrandProduct;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.repository.NoticeRepository;
import com.dev.BionLifeScienceWar.repository.ReferenceFileRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandBigSortRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandMiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandProductRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandSmallSortRepository;
import com.dev.BionLifeScienceWar.repository.product.BigSortRepository;
import com.dev.BionLifeScienceWar.repository.product.MiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductRepository;
import com.dev.BionLifeScienceWar.repository.product.SmallSortRepository;

@Controller
public class SearchController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	BrandProductRepository brandProductRepository;
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	ReferenceFileRepository referenceRepository;
	
	@Autowired
	BrandRepository brandRepository;
	
	@Autowired
	BrandBigSortRepository brandBigSortRepository;
	
	@Autowired
	BrandMiddleSortRepository brandMiddleSortRepository;
	
	@Autowired
	BrandSmallSortRepository brandSmallSortRepository;
	
	@Autowired
	BigSortRepository bigSortRepository;
	
	@Autowired
	MiddleSortRepository middleSortRepository;
	
	@Autowired
	SmallSortRepository smallSortRepository;
	
	@ModelAttribute("menuList")
	public MenuDTO menuList(MenuDTO menuDto) {
		
		menuDto.setProductList(productRepository.findAllByOrderByProductIndexAsc());
		menuDto.setBigSortList(bigSortRepository.findAllByOrderByBigSortIndexAsc());
		menuDto.setMiddleSortList(middleSortRepository.findAllByOrderByMiddleSortIndexAsc());
		menuDto.setSmallSortList(smallSortRepository.findAllByOrderBySmallSortIndexAsc());
		
		menuDto.setBrandList(brandRepository.findAllByOrderByBrandIndexAsc());
		menuDto.setBrandBigSortList(brandBigSortRepository.findAllByOrderByBrandBigSortIndexAsc());
		menuDto.setBrandMiddleSortList(brandMiddleSortRepository.findAllByOrderByBrandMiddleSortIndexAsc());
		menuDto.setBrandSmallSortList(brandSmallSortRepository.findAllByOrderByBrandSmallSortIndexAsc());
		menuDto.setBrandProductList(brandProductRepository.findAllByOrderByBrandProductIndexAsc());
		return menuDto;
	}
	
	@RequestMapping("/searchAll")
	public String searchAll(
			String searchWord,
			Model model
			) {
		
		List<Product> products = productRepository.findBySubjectContains(searchWord);
		for(Product p : products) {
			if(p.getImages().size() > 0) {
				
				p.setFirstImageRoad(p.getImages().get(0).getProductImageRoad());
			}else {
				p.setFirstImageRoad("null");
			}
		}
		model.addAttribute("products", products);
		
		List<BrandProduct> brandProducts = brandProductRepository.findBySubjectContains(searchWord);
		for(BrandProduct p : brandProducts) {
			if(p.getImages().size() > 0) {
				
				p.setFirstImageRoad(p.getImages().get(0).getProductImageRoad());
			}else {
				p.setFirstImageRoad("null");
			}
		}
		model.addAttribute("brandProducts", brandProducts);
		
		model.addAttribute("notices", noticeRepository.findAllBySubjectContainsOrderBySignDescDateDesc(searchWord));
		model.addAttribute("references", referenceRepository.findByFilesubjectContains(searchWord));
		
		return "front/search/searchAll";
	}
	
	@RequestMapping("/searchSorted")
	public String searchSorted(
			
			) {
		
		return "front/search/searchSorted";
	}
}

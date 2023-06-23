package com.dev.BionLifeScienceWar.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.exception.DeleteViolationException;
import com.dev.BionLifeScienceWar.model.brand.Brand;
import com.dev.BionLifeScienceWar.model.brand.BrandBigSort;
import com.dev.BionLifeScienceWar.model.brand.BrandMiddleSort;
import com.dev.BionLifeScienceWar.model.brand.BrandProduct;
import com.dev.BionLifeScienceWar.model.brand.BrandSmallSort;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.repository.brand.BrandBigSortRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandMiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandProductRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandSmallSortRepository;
import com.dev.BionLifeScienceWar.service.brand.BrandService;

@Controller
@RequestMapping("/admin")
public class BrandController {

	@Autowired
	BrandRepository brandRepository;
	
	@Autowired
	BrandService brandService;
	
	@Autowired
	BrandBigSortRepository brandBigSortRepository;
	
	@Autowired
	BrandMiddleSortRepository brandMiddleSortRepository;
	
	@Autowired
	BrandSmallSortRepository brandSmallSortRepository;
	
	@Autowired
	BrandProductRepository brandProductRepository;
	
	@RequestMapping("/brandManager")
	public String brandManager(
			Model model
			) {
		model.addAttribute("brand",brandRepository.findAll());
		return "admin/brand/brandManager";
	}
	
	@RequestMapping("/brandInsert")
	@ResponseBody
	public String brandInsert(
			Brand brand,
			MultipartFile image
			) throws IllegalStateException, IOException {
		
		brandService.brandInsert(image, brand);
		
		StringBuffer sb = new StringBuffer();
		String msg = "브랜드가 등록 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/brandManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	@RequestMapping("/brandDelete")
	@ResponseBody
	public String brandDelete(
			Long text,
			Model model
			) {
		
		try {
			brandRepository.deleteById(text);
		}catch(DeleteViolationException e) {
			throw new DeleteViolationException();
		}	
			
		StringBuffer sb = new StringBuffer();
		String msg = "브랜드가 삭제 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/brandManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	@RequestMapping("/brandSortManager")
	public String brandSortManager(
			Model model
			) {
		
		model.addAttribute("brand",brandRepository.findAll());
		return "admin/brand/brandSortManager";
	}
	
	@RequestMapping("/brandBigSortInsert")
	@ResponseBody
	public String brandBigSortInsert(
			BrandBigSort brandBigSort,
			Long brandId
			) {
		brandBigSort.setBrand(brandRepository.findById(brandId).get());
		brandBigSortRepository.save(brandBigSort);
		StringBuffer sb = new StringBuffer();
		String msg = "브랜드의 대분류가 등록 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/brandSortManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	@RequestMapping("/brandBigSortSearch")
	@ResponseBody
	public List<BrandBigSort> searchBrandBigSort(
			Model model,
			Long brandId
			){
		return brandBigSortRepository.findAllByBrand(brandRepository.findById(brandId).get());
	}
	
	@RequestMapping("/brandBigSortDelete")
	@ResponseBody
	public String brandBigSortDelete(
			Long text,
			Long brandId,
			Model model
			) {
		
		try {
			brandBigSortRepository.deleteById(text);
		}catch(DeleteViolationException e) {
			throw new DeleteViolationException();
		}	
			
		StringBuffer sb = new StringBuffer();
		String msg = "브랜드의 대분류가 삭제 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/brandSortManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	@RequestMapping("/brandMiddleSortInsert")
	@ResponseBody
	public String brandMiddleSortInsert(
			BrandMiddleSort brandMiddleSort,
			Long brandBigSortId
			) {
		brandMiddleSort.setBigSort(brandBigSortRepository.findById(brandBigSortId).get());
		brandMiddleSortRepository.save(brandMiddleSort);
		StringBuffer sb = new StringBuffer();
		String msg = "브랜드의 중분류가 등록 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/brandSortManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	@RequestMapping("/brandMiddleSortSearch")
	@ResponseBody
	public List<BrandMiddleSort> brandMiddleSortSearch(
			Model model,
			Long brandBigSortId
			){
		return brandMiddleSortRepository.findAllByBigSort(brandBigSortRepository.findById(brandBigSortId).get());
	}
	
	@RequestMapping("/brandMiddleSortDelete")
	@ResponseBody
	public String brandMiddleSortDelete(
			Long brandMiddleSortId,
			Model model
			) {
		
		try {
			brandMiddleSortRepository.deleteById(brandMiddleSortId);
		}catch(DeleteViolationException e) {
			throw new DeleteViolationException();
		}	
			
		StringBuffer sb = new StringBuffer();
		String msg = "브랜드의 중분류가 삭제 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/brandSortManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	@RequestMapping("/brandSmallSortInsert")
	@ResponseBody
	public String brandSmallSortInsert(
			BrandSmallSort brandSmallSort,
			Long brandMiddleSortId
			) {
		brandSmallSort.setMiddleSort(brandMiddleSortRepository.findById(brandMiddleSortId).get());
		brandSmallSortRepository.save(brandSmallSort);
		StringBuffer sb = new StringBuffer();
		String msg = "브랜드의 소분류가 등록 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/brandSortManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	
	@RequestMapping("/brandSmallSortSearch")
	@ResponseBody
	public List<BrandSmallSort> brandSmallSortSearch(
			Model model,
			Long brandMiddleSortId
			){
		return brandSmallSortRepository.findAllByMiddleSort(brandMiddleSortRepository.findById(brandMiddleSortId).get());
	}
	
	@RequestMapping("/brandSmallSortDelete")
	@ResponseBody
	public String brandSmallSortDelete(
			Long brandSmallSortId,
			Model model
			) {
		
		try {
			brandSmallSortRepository.deleteById(brandSmallSortId);
		}catch(DeleteViolationException e) {
			throw new DeleteViolationException();
		}	
			
		StringBuffer sb = new StringBuffer();
		String msg = "브랜드의 소분류가 삭제 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/brandSortManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
	
	@RequestMapping("/brandProductManager")
	public String brandProductManager(
			Model model, 
			@RequestParam(required = false) Long brandSmallSortId,
			@PageableDefault(size = 10) Pageable pageable
			) {
		
		if (brandSmallSortId != null) {
			Page<BrandProduct> products = brandProductRepository.findAllBySmallSort(pageable,
					brandSmallSortRepository.findById(brandSmallSortId).get());
			model.addAttribute("products", products);
			int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
			int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
			model.addAttribute("products", products);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("brandSmallSortId", brandSmallSortId);
		}else {
			Page<BrandProduct> products = brandProductRepository.findAll(pageable);
			model.addAttribute("products", products);
			int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
			int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
			model.addAttribute("clients", products);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("brandSmallSortId", brandSmallSortId);
		}
		model.addAttribute("brand",brandRepository.findAll());

		return "admin/brand/brandProductManager";
	}
}

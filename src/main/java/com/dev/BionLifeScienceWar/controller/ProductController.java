package com.dev.BionLifeScienceWar.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.exception.DeleteViolationException;
import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.model.product.ProductInfo;
import com.dev.BionLifeScienceWar.model.product.ProductSpec;
import com.dev.BionLifeScienceWar.model.product.SmallSort;
import com.dev.BionLifeScienceWar.repository.product.BigSortRepository;
import com.dev.BionLifeScienceWar.repository.product.MiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductFileRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductImageRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductInfoRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductSpecRepository;
import com.dev.BionLifeScienceWar.repository.product.SmallSortRepository;
import com.dev.BionLifeScienceWar.service.product.ProductFileService;
import com.dev.BionLifeScienceWar.service.product.ProductImageService;
import com.dev.BionLifeScienceWar.service.product.ProductService;

@RequestMapping("/admin")
@Controller
public class ProductController {

	@Autowired
	BigSortRepository bigSortRepository;

	@Autowired
	MiddleSortRepository middleSortRepository;

	@Autowired
	SmallSortRepository smallSortRepository;

	@Autowired
	ProductService productService;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductImageRepository productImageRepository;

	@Autowired
	ProductFileRepository productFileRepository;

	@Autowired
	ProductSpecRepository productSpecRepository;

	@Autowired
	ProductInfoRepository productInfoRepository;

	@Autowired
	ProductFileService productFileService;

	@Autowired
	ProductImageService productImageService;

	@RequestMapping("/sortManager")
	public String sortManager(Model model) {
		List<BigSort> b = bigSortRepository.findAll();
//		System.out.println(b.size());
		if(b.size()<1) {
			BigSort bs = new BigSort();
			bs.setName("분류를 등록 해 주세요");
			bs.setId(0L);
			b.add(bs);
		}
		model.addAttribute("bigsorts", b);
		
		return "admin/product/sortManager";
	}

	@RequestMapping("/bigsortInsert")
	public String bigsortInsert(BigSort bigSort, Model model) {
		bigSortRepository.save(bigSort);

		return "redirect:/admin/sortManager";
	}

	@RequestMapping("/bigSortDelete")
	public String bigSortDelete(
			@RequestParam(value = "text[]") Long[] text, 
			Model model) {
		try {
			for (Long id : text) {
				bigSortRepository.deleteById(id);
			}
		}catch(DeleteViolationException e) {
//			System.out.println(e);
			throw new DeleteViolationException();
		}	
			
		
		return "redirect:/admin/sortManager";
	}

	@RequestMapping("/middlesortInsert")
	public String middlesortInsert(MiddleSort middleSort, Model model, Long bigId) {

		middleSort.setBigSort(bigSortRepository.findById(bigId).get());
		middleSortRepository.save(middleSort);
		return "redirect:/admin/sortManager";
	}

	@RequestMapping("/searchMiddleSort")
	@ResponseBody
	public List<MiddleSort> searchMiddleSort(Model model, Long bigId) {
//		model.addAttribute("selectedMiddlesorts", middleSortRepository.findAllByBigSort(bigSortRepository.findById(bigId).get()));
//		return "admin/product/sortManager :: #middlePanelMiddleSort";
		return middleSortRepository.findAllByBigSort(bigSortRepository.findById(bigId).get());
	}

	@RequestMapping("/middleSortDelete")
	public String middleSortDelete(@RequestParam(value = "text[]") Long[] text, Model model, Long bigId) {
		
		try {
			for (Long id : text) {
				middleSortRepository.deleteById(id);
			}
		}catch(DeleteViolationException e) {
//			System.out.println(e);
			throw new DeleteViolationException();
		}	
		return "redirect:/admin/sortManager";
	}

	@RequestMapping("/smallsortInsert")
	public String smallsortInsert(SmallSort smallSort, Model model, Long middleId) {
		smallSort.setMiddleSort(middleSortRepository.findById(middleId).get());
		smallSortRepository.save(smallSort);
		return "redirect:/admin/sortManager";
	}

	@RequestMapping("/searchSmallSort")
	@ResponseBody
	public List<SmallSort> searchSmallSort(Model model, Long middleId) {
		return smallSortRepository.findAllByMiddleSort(middleSortRepository.findById(middleId).get());
	}

	@RequestMapping("/smallSortDelete")
	public String smallSortDelete(@RequestParam(value = "text[]") Long[] text, Model model) {
		
		try {
			for (Long id : text) {
				smallSortRepository.deleteById(id);
			}
		}catch(DeleteViolationException e) {
//			System.out.println(e);
			throw new DeleteViolationException();
		}	
		return "redirect:/admin/sortManager";
	}

	@RequestMapping("/productManager")
	public String productManager(
			Model model, 
			@RequestParam(required = false) Long smallId,
			@PageableDefault(size = 10) Pageable pageable
			) {
		if (smallId != null) {
			Page<Product> products = productRepository.findAllBySmallSort(pageable,
					smallSortRepository.findById(smallId).get());
			model.addAttribute("products", products);
			int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
			int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("smallId", smallId);
		}else {
			Page<Product> products = productRepository.findAll(pageable);
			model.addAttribute("products", products);
			int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
			int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("smallId", smallId);
		}
		model.addAttribute("bigsorts", bigSortRepository.findAll());

		return "admin/product/productManager";
	}

	@RequestMapping("/productInsertForm")
	public String productInsertForm(Model model) {

		model.addAttribute("bigsorts", bigSortRepository.findAll());

		return "admin/product/productInsertForm";
	}

	@RequestMapping("/productInsert")
	@ResponseBody
	public String productInsert(
			Product product, 
			Long smallId, 
			String[] spec, 
			String[] infoQ, 
			String[] infoA,
			MultipartFile productOverviewImage, 
			MultipartFile productSpecImage, 
			List<MultipartFile> slides,
			List<MultipartFile> productFile

	) throws IllegalStateException, IOException {
		product.setSmallSort(smallSortRepository.findById(smallId).get());
		Product p = productService.productInsert(productOverviewImage, productSpecImage, product);

		for (String s : spec) {
			ProductInfo in = new ProductInfo();
			in.setProductId(p.getId());
			in.setProductInfoText(s);
			productInfoRepository.save(in);
		}
		for (int a = 0; a < infoQ.length; a++) {
			ProductSpec sp = new ProductSpec();
			sp.setProductSpecSubject(infoQ[a]);
			sp.setProductSpecContent(infoA[a]);
			sp.setProductId(p.getId());
			productSpecRepository.save(sp);
		}

		productFileService.fileUpload(productFile, p.getId());
		productImageService.fileUpload(slides, p.getId());

		StringBuffer sb = new StringBuffer();
		String msg = "제품이 등록 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/productManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}

	@RequestMapping("/productDetail/{id}")
	public String productDetail(
			@PathVariable Long id,
			Model model
			) {
		model.addAttribute("bigsorts", bigSortRepository.findAll());
		model.addAttribute("middlesorts", middleSortRepository.findAllByBigSort(productRepository.findById(id).get().getSmallSort().getMiddleSort().getBigSort()));
		model.addAttribute("smallsorts", smallSortRepository.findAllByMiddleSort(productRepository.findById(id).get().getSmallSort().getMiddleSort()));
		model.addAttribute("product",productRepository.findById(id).get());
		return "admin/product/productDetail";
	}

	@RequestMapping("/productUpdate")
	public String productUpdate(
			Model model, 
			@PageableDefault(size = 10) Pageable pageable,
			Product product, 
			Long smallId, 
			String[] spec, 
			String[] infoQ, 
			String[] infoA,
			MultipartFile productOverviewImage, 
			MultipartFile productSpecImage, 
			List<MultipartFile> slides,
			List<MultipartFile> productFile
			) throws IllegalStateException, IOException {
		if(product.getSign() == null) {
			product.setSign(false);
		}
		productService.productUpdate(productOverviewImage, productSpecImage, product);
		product.setSmallId(smallId);
		productInfoRepository.deleteAllByProductId(product.getId());
		productSpecRepository.deleteAllByProductId(product.getId());
		for (String s : spec) {
			ProductInfo in = new ProductInfo();
			in.setProductId(product.getId());
			in.setProductInfoText(s);
			productInfoRepository.save(in);
		}
		for (int a = 0; a < infoQ.length; a++) {
			ProductSpec sp = new ProductSpec();
			sp.setProductSpecSubject(infoQ[a]);
			sp.setProductSpecContent(infoA[a]);
			sp.setProductId(product.getId());
			productSpecRepository.save(sp);
		}
		if(slides.size()>0 && !slides.get(0).isEmpty()) {
			productImageRepository.deleteAllByProductId(product.getId());
			productImageService.fileUpload(slides, product.getId());
		}
		if(productFile.size()>0 && !productFile.get(0).isEmpty()) {
			productFileRepository.deleteAllByProductId(product.getId());
			productFileService.fileUpload(productFile, product.getId());
		}
		Page<Product> products = productRepository.findAll(pageable);
		model.addAttribute("products", products);
		int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
		int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
		
		model.addAttribute("clients", products);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("smallId", smallId);
		model.addAttribute("bigsorts", bigSortRepository.findAll());

		return "admin/product/productManager";
	}
	
	@RequestMapping("/productDelete/{id}")
	@ResponseBody
	public String productDelete(
			@PathVariable Long id
			) {
		
		productRepository.deleteById(id);
		StringBuffer sb = new StringBuffer();
		String msg = "제품이 삭제 되었습니다.";

		sb.append("alert('" + msg + "');");
		sb.append("location.href='/admin/productManager'");
		sb.append("</script>");
		sb.insert(0, "<script>");

		return sb.toString();
	}
}

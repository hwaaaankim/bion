package com.dev.BionLifeScienceWar.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.Banner;
import com.dev.BionLifeScienceWar.model.Event;
import com.dev.BionLifeScienceWar.model.HistorySubject;
import com.dev.BionLifeScienceWar.model.Notice;
import com.dev.BionLifeScienceWar.model.ReferenceFile;
import com.dev.BionLifeScienceWar.model.brand.Brand;
import com.dev.BionLifeScienceWar.model.brand.BrandBigSort;
import com.dev.BionLifeScienceWar.model.brand.BrandMiddleSort;
import com.dev.BionLifeScienceWar.model.brand.BrandProduct;
import com.dev.BionLifeScienceWar.model.brand.BrandSmallSort;
import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.model.product.SmallSort;
import com.dev.BionLifeScienceWar.repository.BannerRepository;
import com.dev.BionLifeScienceWar.repository.CertificationRepository;
import com.dev.BionLifeScienceWar.repository.EventRepository;
import com.dev.BionLifeScienceWar.repository.HistoryContentRepository;
import com.dev.BionLifeScienceWar.repository.HistorySubjectRepository;
import com.dev.BionLifeScienceWar.repository.NoticeRepository;
import com.dev.BionLifeScienceWar.repository.NoticeSubjectRepository;
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
import com.dev.BionLifeScienceWar.service.product.ProductService;
import com.dev.BionLifeScienceWar.service.program.ExcelDownloadService;
import com.dev.BionLifeScienceWar.service.program.ExcelUploadService;

@Controller
public class HomeController {


	@Autowired
	HistorySubjectRepository historySubjectRepository;
	
	@Autowired
	HistoryContentRepository historyContentRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	BigSortRepository bigSortRepository;
	
	@Autowired
	MiddleSortRepository middleSortRepository;
	
	@Autowired
	SmallSortRepository smallSortRepository;
	
	@Autowired
	BannerRepository bannerRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	ReferenceFileRepository referenceFileRepository;
	
	@Autowired
	NoticeSubjectRepository noticeSubjectRepository;

	@Autowired
	BrandRepository brandRepository;
	
	@Autowired
	BrandBigSortRepository brandBigSortRepository;
	
	@Autowired
	BrandMiddleSortRepository brandMiddleSortRepository;
	
	@Autowired
	BrandSmallSortRepository brandSmallSortRepository;
	
	@Autowired
	BrandProductRepository brandProductRepository;
	
	@Autowired
	CertificationRepository certificationRepository;
	
	@Autowired
	ExcelDownloadService excelDownloadService;
	
	@Autowired
	ExcelUploadService excelUploadService;
	
	@Autowired
	ProductService productService;
	
	@Value("${spring.upload.path}")
	private String uploadPath;
	

	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="잘못된 접근입니다.")
    public class UrlNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L; }
	
	@SuppressWarnings("null")
	@RequestMapping({"/", "/index"})
	public String index(
			Model model
			) {
//		System.out.println(uploadPath);
		List<Banner> b = bannerRepository.findAll();
		if(b.size()<1) {
			Banner ba = new Banner();
			ba.setMobileroad("/front/images/slider/swiper/1.jpg");
			ba.setWebroad("/front/images/slider/swiper/1.jpg");
			ba.setSubject("WELCOME to Bion Life Science");
			ba.setContent("You'll be surprised to see the Final\r\n" + 
					"										Results of your Creation &amp; would crave for more.");
			b.add(ba);
		}
		
		List<Event> ev = eventRepository.findAll();
		if(ev.size()<1) {
			Event e = new Event();
			e.setContent("이벤트 배너 내용 입니다");
			e.setSubject("이벤트 배너 제목 입니다");
			e.setLink("https://www.naver.com");
			ev.add(e);
		}
		List<Notice> notice = noticeRepository.findTop5ByOrderBySignDescDateDesc();
		List<ReferenceFile> fi = referenceFileRepository.findAll();
		List<Product> pr = productRepository.findAllBySign(true);
		for(Product p : pr) {
			if(p.getImages().size() > 0) {
				
				p.setFirstImageRoad(p.getImages().get(0).getProductImageRoad());
			}else {
				p.setFirstImageRoad("null");
			}
		}
		model.addAttribute("fi", fi);
		model.addAttribute("notice", notice);
		model.addAttribute("product", pr);
		model.addAttribute("ev", ev.get(0));
		model.addAttribute("ba", b);
		
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		return "front/index";
	}
	
	@RequestMapping("/about")
	public String about(
			Model model
			) {
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		return "front/company/about";
	}
	
	
	@RequestMapping("/history")
	public String history(
			Model model
			) {
		
		List<HistorySubject> subject = historySubjectRepository.findAllByOrderByStartDesc();
		for(HistorySubject s : subject) {
			s.setContents(historyContentRepository.findAllBySubjectIdOrderByDateDesc(s.getId()));
		}
		model.addAttribute("list",subject);
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		return "front/company/history";
	}
	
	@RequestMapping("/certifications")
	public String certifications(
			Model model
			) {
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		
		model.addAttribute("certification", certificationRepository.findAll());
		return "front/company/certifications";
	}
	
	@RequestMapping("/address")
	public String address(
			Model model
			) {
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		return "front/company/address";
	}
	
	@RequestMapping("/contact")
	public String contact(
			Model model
			) {
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		return "front/customer/contact";
	}
	
	@RequestMapping("/excelTest")
	@ResponseBody
	public void excelTest(
			HttpServletResponse res
			) throws IOException {
		excelDownloadService.bigSortDownload(res);
		
	}
	
	@RequestMapping("/excelUploadTest")
	@ResponseBody
	public void excelUploadTest(
			MultipartFile file
			) {
		
		excelUploadService.uploadExcel(file);
	}
	
	@RequestMapping("/productOverall")
	public String productOverall(
			Model model
			) {
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		return "front/product/productOverall";
	}
	
	
	@RequestMapping("/productDetail/{id}")
	public String productDetail(
			Model model,
			@PathVariable Long id
			) {
		
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			if(product.get().getImages().size() > 0) {
				
				product.get().setFirstImageRoad(product.get().getImages().get(0).getProductImageRoad());
			}else {
				product.get().setFirstImageRoad("null");
			}
			model.addAttribute("product", product.get());
			model.addAttribute("b", bigSortRepository.findAll());
			model.addAttribute("m", middleSortRepository.findAll());
			model.addAttribute("s", smallSortRepository.findAll());
			model.addAttribute("p", productRepository.findAll());
			
			model.addAttribute("brand", brandRepository.findAll());
			model.addAttribute("bb", brandBigSortRepository.findAll());
			model.addAttribute("bm", brandMiddleSortRepository.findAll());
			model.addAttribute("bs", brandSmallSortRepository.findAll());
			model.addAttribute("bp", brandProductRepository.findAll());
			return "front/product/productDetail";
		}else {
			throw new UrlNotFoundException();
		}
	}
	
	
	@RequestMapping("/brandProductDetail/{id}")
	public String brandProductDetail(
			Model model,
			@PathVariable Long id
			) {
	
		
		Optional<BrandProduct> product = brandProductRepository.findById(id);
		if(product.isPresent()) {
			
			if(product.get().getImages().size() > 0) {
				product.get().setFirstImageRoad(product.get().getImages().get(0).getProductImageRoad());
			}else {
				product.get().setFirstImageRoad("null");
			}
			model.addAttribute("pr", product.get());
			model.addAttribute("brand", brandRepository.findAll());
			model.addAttribute("bb", brandBigSortRepository.findAll());
			model.addAttribute("bm", brandMiddleSortRepository.findAll());
			model.addAttribute("bs", brandSmallSortRepository.findAll());
			model.addAttribute("bp", brandProductRepository.findAll());
			model.addAttribute("b", bigSortRepository.findAll());
			model.addAttribute("m", middleSortRepository.findAll());
			model.addAttribute("s", smallSortRepository.findAll());
			model.addAttribute("p", productRepository.findAll());
			return "front/brand/brandProductDetail";
		}else {
			throw new UrlNotFoundException();
		}
	}
	
	@RequestMapping("/notice")
	public String notice(
			Model model
			) {
		
		List<Notice> notice = noticeRepository.findTop5ByOrderBySignDescDateDesc();
		model.addAttribute("notice", notice);
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		return "front/notice/notice";
	}

	@RequestMapping("/references")
	public String reference(
			
			Model model
			) {
		
		model.addAttribute("file",referenceFileRepository.findAll());
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		return "front/references";
		
	}
	
	
	@RequestMapping("/productSorted/{sort}/{id}")
	public String productSorted(
			Model model,
			@PathVariable String sort,
			@PathVariable Long id,
			@PageableDefault(size = 12) Pageable pageable
			
			) {
			Page<Product> products = null;
			String name = "";
			if(sort.equals("main")) {
				Optional<BigSort> b = bigSortRepository.findById(id);
				if(b.isPresent()) {
					products = productRepository.findAllByBigSort(pageable, b.get());
					if(products.getNumberOfElements()>0) {
						name = b.get().getName();
					}else {
						name="해당 분류에 속하는 제품이 존재하지 않습니다.";
					}
				}else {
					products = null;
					throw new UrlNotFoundException();
					
				}
			}else if(sort.equals("middle")) {
				Optional<MiddleSort> m = middleSortRepository.findById(id);
				if(m.isPresent()) {
					products = productRepository.findAllByMiddleSort(pageable, m.get());
					if(products.getNumberOfElements()>0) {
						name = m.get().getName();
					}else {
						name="해당 분류에 속하는 제품이 존재하지 않습니다.";
					}
				}else {
					products = null;
					throw new UrlNotFoundException();
				}
			}else if(sort.equals("sub")) {
				Optional<SmallSort> s = smallSortRepository.findById(id);
				if(s.isPresent()) {
					products = productRepository.findAllBySmallSort(pageable, s.get());
					if(products.getNumberOfElements()>0) {
						name = smallSortRepository.findById(id).get().getName();
					}else {
						name="해당 분류에 속하는 제품이 존재하지 않습니다.";
					}
					
				}else {
					products = null;
					throw new UrlNotFoundException();
				}
			}
			if(products.getNumberOfElements()>0) {
				for(Product p : products) {
					p.setFirstImageRoad(p.getImages().get(0).getProductImageRoad());
				}
			}
			model.addAttribute("products", products);
			model.addAttribute("name", name);
			int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
			int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("b", bigSortRepository.findAll());
			model.addAttribute("m", middleSortRepository.findAll());
			model.addAttribute("s", smallSortRepository.findAll());
			model.addAttribute("p", productRepository.findAll());
			model.addAttribute("sort", sort);
			model.addAttribute("id",id);
			model.addAttribute("brand", brandRepository.findAll());
			model.addAttribute("bb", brandBigSortRepository.findAll());
			model.addAttribute("bm", brandMiddleSortRepository.findAll());
			model.addAttribute("bs", brandSmallSortRepository.findAll());
			model.addAttribute("bp", brandProductRepository.findAll());
			return "front/product/productSorted";
		
	}
	
	@RequestMapping("/brandProductSorted/{sort}/{id}")
	public String brandProductSorted(
			Model model,
			@PathVariable String sort,
			@PathVariable Long id,
			@PageableDefault(size = 12) Pageable pageable
			
			) {
		
		Page<BrandProduct> products = null;
		Brand brand = null;	
		String name = "";
		

		if(sort.equals("brand")) {
			Optional<Brand> b = brandRepository.findById(id);
			
			if(b.isPresent()) {
				products = brandProductRepository.findAllByBrand(pageable, b.get());
				brand = b.get();
				if(products.getNumberOfElements()>0) {
					
					name = b.get().getName() + "브랜드의 제품들 입니다.";
				}else {
					name="해당 분류에 속하는 제품이 존재하지 않습니다.";
				}
				
			}else {
				brand = null;
				products = null;
				throw new UrlNotFoundException();
			}
		}else if(sort.equals("main")) {
			
			Optional<BrandBigSort> b = brandBigSortRepository.findById(id);
			
			if(b.isPresent()) {
				products = brandProductRepository.findAllByBigSort(pageable, b.get());
				brand = b.get().getBrand();
				if(products.getNumberOfElements()>0) {
					
					name = brand.getName() + "브랜드의 " + b.get().getName() + "분류에 속한 제품들 입니다.";
				}else {
					name="해당 분류에 속하는 제품이 존재하지 않습니다.";
				}
			}else {
				products = null;
				brand = null;
				throw new UrlNotFoundException();
			}
		}else if(sort.equals("middle")) {
			Optional<BrandMiddleSort> b = brandMiddleSortRepository.findById(id);
			if(b.isPresent()) {
				products = brandProductRepository.findAllByMiddleSort(pageable, b.get());
				brand = b.get().getBigSort().getBrand();
				if(products.getNumberOfElements()>0) {
					name = brand.getName() + "브랜드의 " + b.get().getName() + "분류에 속한 제품들 입니다.";
				}else {
					name="해당 분류에 속하는 제품이 존재하지 않습니다.";
				}
				
			}else {
				products = null;
				brand = null;
				throw new UrlNotFoundException();
			}
			
		}else if(sort.equals("sub")) {
			
			Optional<BrandSmallSort> b = brandSmallSortRepository.findById(id);
			if(b.isPresent()) {
				products = brandProductRepository.findAllBySmallSort(pageable, b.get());
				brand = b.get().getMiddleSort().getBigSort().getBrand();
				if(products.getNumberOfElements()>0) {
					
					name = brand.getName() + "브랜드의 " + b.get().getName() + "분류에 속한 제품들 입니다.";
				}else {
					name="해당 분류에 속하는 제품이 존재하지 않습니다.";
				}
				
			}else {
				products = null;
				brand = null;
				throw new UrlNotFoundException();
			}
		}
		
		if(products.getNumberOfElements()>0) {
			for(BrandProduct p : products) {
				p.setFirstImageRoad(p.getImages().get(0).getProductImageRoad());
			}
		}
		
		
		model.addAttribute("brandInfo", brand);
		model.addAttribute("products", products);
		model.addAttribute("name", name);
		int startPage = Math.max(1, products.getPageable().getPageNumber() - 4);
		int endPage = Math.min(products.getTotalPages(), products.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("sort", sort);
		model.addAttribute("id",id);
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		
		return "front/brand/brandProductSorted";
	}
	
	
}























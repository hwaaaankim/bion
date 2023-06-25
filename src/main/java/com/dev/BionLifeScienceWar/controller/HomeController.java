package com.dev.BionLifeScienceWar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.BionLifeScienceWar.model.Banner;
import com.dev.BionLifeScienceWar.model.Event;
import com.dev.BionLifeScienceWar.model.HistorySubject;
import com.dev.BionLifeScienceWar.model.Notice;
import com.dev.BionLifeScienceWar.model.ReferenceFile;
import com.dev.BionLifeScienceWar.model.product.Product;
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
	
	@RequestMapping({"/", "/index"})
	public String index(
			Model model
			) {
		
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
		
		List<Notice> impor = noticeRepository.findAllBySignOrderByDateDesc(true);
		List<Notice> noneImpor = noticeRepository.findAllBySignOrderByDateDesc(false);
		for(Notice n : impor) {
			n.setSubjectText(noticeSubjectRepository.findById(n.getSubjectId()).get().getText());
		}
		
		for(Notice n : noneImpor) {
			n.setSubjectText(noticeSubjectRepository.findById(n.getSubjectId()).get().getText());
		}
		
		List<ReferenceFile> fi = referenceFileRepository.findAll();
		List<Product> pr = productRepository.findAllBySign(true);
		model.addAttribute("fi", fi);
		model.addAttribute("im", impor);
		model.addAttribute("ni", noneImpor);
		model.addAttribute("p", pr);
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
	
	@RequestMapping("/productSorted")
	public String productSorted(
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
		return "front/product/productSorted";
	}
	
	@RequestMapping("/productDetail/{id}")
	public String productDetail(
			Model model,
			@PathVariable Long id
			) {
		model.addAttribute("pr", productRepository.findById(id).get());
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
	}
	
	@RequestMapping("/brandProductDetail/{id}")
	public String brandProductDetail(
			Model model,
			@PathVariable Long id
			) {
		model.addAttribute("brand", brandRepository.findAll());
		model.addAttribute("bb", brandBigSortRepository.findAll());
		model.addAttribute("bm", brandMiddleSortRepository.findAll());
		model.addAttribute("bs", brandSmallSortRepository.findAll());
		model.addAttribute("bp", brandProductRepository.findAll());
		model.addAttribute("pr", brandProductRepository.findById(id).get());
		
		model.addAttribute("b", bigSortRepository.findAll());
		model.addAttribute("m", middleSortRepository.findAll());
		model.addAttribute("s", smallSortRepository.findAll());
		model.addAttribute("p", productRepository.findAll());
		return "front/brand/brandProductDetail";
	}
	
	@RequestMapping("/notice")
	public String notice(
			Model model
			) {
		
		List<Notice> impor = noticeRepository.findAllBySignOrderByDateDesc(true);
		List<Notice> noneImpor = noticeRepository.findAllBySignOrderByDateDesc(false);
		for(Notice n : impor) {
			n.setSubjectText(noticeSubjectRepository.findById(n.getSubjectId()).get().getText());
		}
		
		for(Notice n : noneImpor) {
			n.setSubjectText(noticeSubjectRepository.findById(n.getSubjectId()).get().getText());
		}
		
		model.addAttribute("im", impor);
		model.addAttribute("ni", noneImpor);
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
}
























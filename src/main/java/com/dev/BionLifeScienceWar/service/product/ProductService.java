package com.dev.BionLifeScienceWar.service.product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.repository.product.BigSortRepository;
import com.dev.BionLifeScienceWar.repository.product.MiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductRepository;
import com.dev.BionLifeScienceWar.repository.product.SmallSortRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	SmallSortRepository smallSortRepository;
	
	@Autowired
	MiddleSortRepository middleSortRepository;
	
	@Autowired
	BigSortRepository bigSortRepository;

	@Value("${spring.upload.env}")
	private String env;

	@Value("${spring.upload.path}")
	private String commonPath;

	public void excelInsert(
			Product product
			) {
		product.setTableImagePath("-");
		product.setTableImageRoad("-");
		product.setTableImageName("-");
		product.setSpecImageName("-");
		product.setSpecImagePath("-");
		product.setSpecImageRoad("-");
		int index = 1;
		if(productRepository.findFirstIndex().isPresent()) {
			index = productRepository.findFirstIndex().get() + 1;
		}
		product.setProductIndex(index);
		productRepository.save(product);
	}
	
	public Product productInsert(
			MultipartFile productOverviewImage, 
			MultipartFile productSpecImage, 
			Product product)
			throws IllegalStateException, IOException {
	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String current_date = simpleDateFormat.format(new Date());
		String absolutePath = new File("").getAbsolutePath() + "\\";
//      String overviewPath = "src/main/resources/static/administration/overview/"+current_date;
//      String specPath = "src/main/resources/static/administration/spec/"+current_date;
//      String overviewPath = "/home/hosting_users/bionls/tomcat/webapps/overview/"+current_date;
//      String specPath = "/home/hosting_users/bionls/tomcat/webapps/spec/"+current_date;
//		String overviewPath = commonPath + "/overview/" + current_date;
//		String specPath = commonPath + "/spec/" + current_date;
//		String overviewRoad = "/administration/overview/" + current_date;
//		String specRoad = "/administration/spec/" + current_date;
		

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		String productCode = generatedString + "_" + current_date;
		
		String overviewPath = commonPath + "/overview/" + current_date;
		String specPath = commonPath + "/spec/" + current_date;
		String overviewRoad = "/administration/overview/" + current_date;
		String specRoad = "/administration/spec/" + current_date;
		
		File overviewFileFolder = new File(overviewPath);
		File specFileFolder = new File(specPath);
		
		
		
		if (!overviewFileFolder.exists()) {
			overviewFileFolder.mkdirs();
		}

		if (!specFileFolder.exists()) {
			specFileFolder.mkdirs();
		}

		String overviewContentType = productOverviewImage.getContentType();
		String specContentType = productSpecImage.getContentType();
		String overviewOriginalFileExtension = "";
		String specOriginalFileExtension = "";
		// 확장자 명이 없으면 이 파일은 잘 못 된 것이다
		if (ObjectUtils.isEmpty(overviewContentType)) {
			return null;
		} else {
			if (overviewContentType.contains("image/jpeg")) {
				overviewOriginalFileExtension = ".jpg";
			} else if (overviewContentType.contains("image/png")) {
				overviewOriginalFileExtension = ".png";
			} else if (overviewContentType.contains("image/gif")) {
				overviewOriginalFileExtension = ".gif";
			} else if (overviewContentType.contains("application/pdf")) {
				overviewOriginalFileExtension = ".pdf";
			} else if (overviewContentType.contains("application/x-zip-compressed")) {
				overviewOriginalFileExtension = ".zip";
			} else if (overviewContentType
					.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				overviewOriginalFileExtension = ".xlsx";
			} else if (overviewContentType
					.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
				overviewOriginalFileExtension = ".docx";
			} else if (overviewContentType.contains("text/plain")) {
				overviewOriginalFileExtension = ".txt";
			} else if (overviewContentType.contains("image/x-icon")) {
				overviewOriginalFileExtension = ".ico";
			} else if (overviewContentType.contains("application/haansofthwp")) {
				overviewOriginalFileExtension = ".hwp";
			}
		}

		if (ObjectUtils.isEmpty(specContentType)) {
			return null;
		} else {
			if (specContentType.contains("image/jpeg")) {
				specOriginalFileExtension = ".jpg";
			} else if (specContentType.contains("image/png")) {
				specOriginalFileExtension = ".png";
			} else if (specContentType.contains("image/gif")) {
				specOriginalFileExtension = ".gif";
			} else if (specContentType.contains("application/pdf")) {
				specOriginalFileExtension = ".pdf";
			} else if (specContentType.contains("application/x-zip-compressed")) {
				specOriginalFileExtension = ".zip";
			} else if (specContentType.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				specOriginalFileExtension = ".xlsx";
			} else if (specContentType
					.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
				specOriginalFileExtension = ".docx";
			} else if (specContentType.contains("text/plain")) {
				specOriginalFileExtension = ".txt";
			} else if (specContentType.contains("image/x-icon")) {
				specOriginalFileExtension = ".ico";
			} else if (specContentType.contains("application/haansofthwp")) {
				specOriginalFileExtension = ".hwp";
			}
		}
		String overviewFileName = generatedString + "_" + productOverviewImage.getOriginalFilename();
		String specFileName = generatedString + "_" + productSpecImage.getOriginalFilename();
		if (env.equals("local")) {
			overviewFileFolder = new File(absolutePath + overviewPath + "/" + overviewFileName);
			specFileFolder = new File(absolutePath + specPath + "/" + specFileName);
		} else if (env.equals("prod")) {
			overviewFileFolder = new File(overviewPath + "/" + overviewFileName);
			specFileFolder = new File(specPath + "/" + specFileName);
		}

		productOverviewImage.transferTo(overviewFileFolder);
		productSpecImage.transferTo(specFileFolder);
		product.setProductCode(productCode);
		product.setTableImagePath(overviewPath + "/" + overviewFileName);
		product.setTableImageRoad(overviewRoad + "/" + overviewFileName);
		product.setTableImageName(overviewFileName);
		product.setSpecImageName(specFileName);
		product.setSpecImagePath(specPath + "/" + specFileName);
		product.setSpecImageRoad(specRoad + "/" + specFileName);

		return productRepository.save(product);

	}

	public String productUpdate(MultipartFile productOverviewImage, MultipartFile productSpecImage, Product product)
			throws IllegalStateException, IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = simpleDateFormat.format(new Date());
		String absolutePath = new File("").getAbsolutePath() + "\\";

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		if (!productOverviewImage.isEmpty()) {
			String generatedString = random.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
//			String overviewPath = "src/main/resources/static/administration/overview/"+current_date;
//			String overviewPath = "/home/hosting_users/bionls/tomcat/webapps/overview/"+current_date;
			String overviewPath = commonPath + "/overview/" + current_date;

			String overviewRoad = "/administration/overview/" + current_date;
			File overviewFileFolder = new File(overviewPath);

			if (!overviewFileFolder.exists()) {
				overviewFileFolder.mkdirs();
			}

			String overviewContentType = productOverviewImage.getContentType();
			String overviewOriginalFileExtension = "";

			if (ObjectUtils.isEmpty(overviewContentType)) {
				return "fail";
			} else {
				if (overviewContentType.contains("image/jpeg")) {
					overviewOriginalFileExtension = ".jpg";
				} else if (overviewContentType.contains("image/png")) {
					overviewOriginalFileExtension = ".png";
				} else if (overviewContentType.contains("image/gif")) {
					overviewOriginalFileExtension = ".gif";
				} else if (overviewContentType.contains("application/pdf")) {
					overviewOriginalFileExtension = ".pdf";
				} else if (overviewContentType.contains("application/x-zip-compressed")) {
					overviewOriginalFileExtension = ".zip";
				} else if (overviewContentType
						.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
					overviewOriginalFileExtension = ".xlsx";
				} else if (overviewContentType
						.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
					overviewOriginalFileExtension = ".docx";
				} else if (overviewContentType.contains("text/plain")) {
					overviewOriginalFileExtension = ".txt";
				} else if (overviewContentType.contains("image/x-icon")) {
					overviewOriginalFileExtension = ".ico";
				} else if (overviewContentType.contains("application/haansofthwp")) {
					overviewOriginalFileExtension = ".hwp";
				}
			}

			String overviewFileName = generatedString + "_" + productOverviewImage.getOriginalFilename();
			if (env.equals("local")) {
				overviewFileFolder = new File(absolutePath + overviewPath + "/" + overviewFileName);
			} else if (env.equals("prod")) {
				overviewFileFolder = new File(overviewPath + "/" + overviewFileName);
			}

			productOverviewImage.transferTo(overviewFileFolder);
			productRepository.findById(product.getId()).ifPresent(s -> {
				s.setTableImagePath(overviewPath + "/" + overviewFileName);
				s.setTableImageRoad(overviewRoad + "/" + overviewFileName);
				s.setTableImageName(overviewFileName);
				productRepository.save(s);
			});
		}
		if (!productSpecImage.isEmpty()) {
			String generatedString = random.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
//			String specPath = "src/main/resources/static/administration/spec/"+current_date;
//			String specPath = "/home/hosting_users/bionls/tomcat/webapps/spec/"+current_date;
			String specPath = commonPath + "/spec/" + current_date;
			String specRoad = "/administration/spec/" + current_date;
			File specFileFolder = new File(specPath);

			if (!specFileFolder.exists()) {
				specFileFolder.mkdirs();
			}

			String specContentType = productSpecImage.getContentType();
			String specOriginalFileExtension = "";
			if (ObjectUtils.isEmpty(specContentType)) {
				return null;
			} else {
				if (specContentType.contains("image/jpeg")) {
					specOriginalFileExtension = ".jpg";
				} else if (specContentType.contains("image/png")) {
					specOriginalFileExtension = ".png";
				} else if (specContentType.contains("image/gif")) {
					specOriginalFileExtension = ".gif";
				} else if (specContentType.contains("application/pdf")) {
					specOriginalFileExtension = ".pdf";
				} else if (specContentType.contains("application/x-zip-compressed")) {
					specOriginalFileExtension = ".zip";
				} else if (specContentType
						.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
					specOriginalFileExtension = ".xlsx";
				} else if (specContentType
						.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
					specOriginalFileExtension = ".docx";
				} else if (specContentType.contains("text/plain")) {
					specOriginalFileExtension = ".txt";
				} else if (specContentType.contains("image/x-icon")) {
					specOriginalFileExtension = ".ico";
				} else if (specContentType.contains("application/haansofthwp")) {
					specOriginalFileExtension = ".hwp";
				}
			}

			String specFileName = generatedString + "_" + productSpecImage.getOriginalFilename();

			if (env.equals("local")) {
				specFileFolder = new File(absolutePath + specPath + "/" + specFileName);
			} else if (env.equals("prod")) {
				specFileFolder = new File(specPath + "/" + specFileName);
			}
			productSpecImage.transferTo(specFileFolder);
			productRepository.findById(product.getId()).ifPresent(s -> {
				s.setSpecImageName(specFileName);
				s.setSpecImagePath(specPath + "/" + specFileName);
				s.setSpecImageRoad(specRoad + "/" + specFileName);
				productRepository.save(s);
			});

		}
		productRepository.findById(product.getId()).ifPresent(s -> {
			s.setSmallSort(smallSortRepository.findById(product.getSmallId()).get());
			s.setMiddleSort(middleSortRepository.findById(product.getMiddleId()).get());
			s.setBigSort(bigSortRepository.findById(product.getBigId()).get());
			s.setSubject(product.getSubject());
			s.setContent(product.getContent());
			s.setProductSubContent(product.getProductSubContent());
			s.setSign(product.getSign());
			productRepository.save(s);
		});

		return "success";

	}
}

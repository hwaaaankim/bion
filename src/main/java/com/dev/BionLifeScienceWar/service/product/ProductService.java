package com.dev.BionLifeScienceWar.service.product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
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

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		String productCode = generatedString + "_" + current_date;
		
		String overviewPath = commonPath + "/company/" + productCode + "/overview/" + current_date;
		String overviewRoad = "/administration/company/" + productCode + "/overview/" + current_date;
		File overviewFileFolder = new File(overviewPath);
		if (!overviewFileFolder.exists()) {
			overviewFileFolder.mkdirs();
		}
		
		String overviewContentType = productOverviewImage.getContentType();
		String overviewOriginalFileExtension = "";
		
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
		String overviewFileName = generatedString + "_" + productOverviewImage.getOriginalFilename();
		
		String specPath = commonPath + "/company/" + productCode + "/spec/" + current_date;
		String specRoad = "/administration/company/" + productCode+ "/spec/" + current_date;
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
		String specFileName = generatedString + "_" + productSpecImage.getOriginalFilename();
		
		if (env.equals("local")) {
			overviewFileFolder = new File(absolutePath + overviewPath + "/" + overviewFileName);
			specFileFolder = new File(absolutePath + specPath + "/" + specFileName);
		} else if (env.equals("prod")) {
			overviewFileFolder = new File(overviewPath + "/" + overviewFileName);
			specFileFolder = new File(specPath + "/" + specFileName);
		}
		int index = 1;
		if(productRepository.findFirstIndex().isPresent()) {
			index = productRepository.findFirstIndex().get() + 1;
		}
		
		if(product.getSign()==null) {
			product.setSign(false);
		}else {
			product.setSign(true);
		}
		
		product.setProductIndex(index);
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

	
	public Boolean removeOverViewImage(
			Long id
			) {
		String overViewPath = productRepository.findById(id).get().getTableImagePath();
		Boolean overResult = true;
		try {
			File overViewFile = new File(overViewPath);
			overResult = overViewFile.delete();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return overResult;
	}
	
	public void deleteFiles(Long id) throws IOException {
		
		File folder = new File(commonPath + "/company/" + productRepository.findById(id).get().getProductCode());
		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.submit(() -> {
			try {
				FileUtils.cleanDirectory(folder); 
			} catch (Exception e) {
				System.out.println(e);
			}
		});
		executorService.submit(() -> {
			folder.delete();
		});
	}
	
	public Boolean removeSpecImage(
			Long id
			) {
		String specPath = productRepository.findById(id).get().getSpecImagePath();
		Boolean specResult = true;
		try {
			File specFile = new File(specPath);
			specResult = specFile.delete();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return specResult;
	}
	
	public String productUpdate(
			MultipartFile productOverviewImage, 
			MultipartFile productSpecImage, 
			Product product)
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
			
			
			String overviewPath = commonPath + "/company/" + product.getProductCode() + "/overview/" + current_date;
			String overviewRoad = "/administration/company/" + product.getProductCode() + "/overview/" + current_date;
			
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
			
			String specPath = commonPath + "/company/" + product.getProductCode() + "/spec/" + current_date;
			String specRoad = "/administration/company/" + product.getProductCode()+ "/spec/" + current_date;
			
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

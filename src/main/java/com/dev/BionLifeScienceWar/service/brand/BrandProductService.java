package com.dev.BionLifeScienceWar.service.brand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.ZipUtil;

import com.dev.BionLifeScienceWar.model.brand.BrandProduct;
import com.dev.BionLifeScienceWar.model.brand.BrandProductFile;
import com.dev.BionLifeScienceWar.model.brand.BrandProductImage;
import com.dev.BionLifeScienceWar.repository.brand.BrandBigSortRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandMiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandProductFileRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandProductImageRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandProductRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandRepository;
import com.dev.BionLifeScienceWar.repository.brand.BrandSmallSortRepository;

@Service
public class BrandProductService {

	@Autowired
	BrandProductRepository brandProductRepository;
	
	@Autowired
	BrandSmallSortRepository brandSmallSortRepository;

	@Autowired
	BrandMiddleSortRepository brandMiddleSortRepository;
	
	@Autowired
	BrandBigSortRepository brandBigSortRepository;
	
	@Autowired
	BrandRepository brandRepository;

	@Autowired
	BrandProductFileRepository brandProductFileRepository;
	
	@Autowired
	BrandProductImageRepository brandProductImageRepository;
	
	@Autowired
	BrandProductFileService brandProductFileService;
	
	@Autowired
	BrandProductImageService brandProductImageService;
	
	@Value("${spring.upload.env}")
	private String env;

	@Value("${spring.upload.path}")
	private String commonPath;

	public void excelInsert(
			BrandProduct product
			) {
		product.setTableImagePath("-");
		product.setTableImageRoad("-");
		product.setTableImageName("-");
		product.setSpecImageName("-");
		product.setSpecImagePath("-");
		product.setSpecImageRoad("-");
		int index = 1;
		if(brandProductRepository.findFirstIndex().isPresent()) {
			index = brandProductRepository.findFirstIndex().get() + 1;
		}
		
		product.setBrandProductIndex(index);
		brandProductRepository.save(product);
	}
	
	public void zipProductInsert(
			MultipartFile file
			) throws IOException {
		
		String absolutePath = new File("").getAbsolutePath() + "\\";
		
		File exFile = new File(commonPath + "/brandproduct");
		if(exFile.exists() && exFile.isDirectory()) {
			FileUtils.cleanDirectory(exFile); 
			exFile.delete();
		}

		File zipFile = new File(commonPath + "/brandproduct");
		zipFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(zipFile);
		fos.write(file.getBytes());
		fos.close();
		ZipUtil.explode(zipFile);
		
		for(File product : zipFile.listFiles()) {
			if(product.isDirectory() && !product.getName().equals("brand")) {
				String productCode = product.getName();
				if(product.listFiles().length > 0) {
					for(File sort : product.listFiles()) {
						if(sort.isDirectory()) {
							String fileType = sort.getName();
							switch(fileType) {
								case "slide" :{
									Optional<BrandProduct> p = brandProductRepository.findByBrandProductCode(productCode);
									
									if(product.listFiles().length>0 && product.listFiles()!=null) {
										for(File type : sort.listFiles()) {
											String fileName = type.getName();
											BrandProductImage f = new BrandProductImage();
							        		f.setProductId(p.get().getId());
											if(env.equals("local")) {
							                	f.setProductImagePath(absolutePath + commonPath + "/brandproduct/" + productCode + "/slide/" +  fileName);
											}else if(env.equals("prod")) {
												f.setProductImagePath(commonPath + "/brandproduct/" + productCode + "/slide/" +  fileName);
											}
											f.setProductImageRoad("/administration/brandproduct/" + productCode + "/slide/" + fileName );
							                f.setProductImageName(fileName);
							                brandProductImageRepository.save(f);
										}
									}
									break;
								}
								case "spec" :{
									for(File type : sort.listFiles()) {
										String fileName = type.getName();
										Optional<BrandProduct> p = brandProductRepository.findByBrandProductCode(productCode);
										p.ifPresent(np -> {
											np.setSpecImageName(fileName);
											np.setSpecImagePath(commonPath + "/brandproduct/" + productCode + "/spec/" + fileName);
											np.setSpecImageRoad("/administration/brandproduct/" + productCode + "/spec/" + fileName);
											brandProductRepository.save(np);
										});
									}
									
									break;
									
								}
								case "overview" :{
									for(File type : sort.listFiles()) {
										String fileName = type.getName();
										Optional<BrandProduct> p = brandProductRepository.findByBrandProductCode(productCode);
										p.ifPresent(np -> {
											np.setTableImageName(fileName);
											np.setTableImagePath(commonPath + "/brandproduct/" + productCode + "/overview/" + fileName);
											np.setTableImageRoad("/administration/brandproduct/" + productCode + "/overview/" + fileName);
											brandProductRepository.save(np);
										});
									}
									break;
								}
								case "files" :{
									Optional<BrandProduct> p = brandProductRepository.findByBrandProductCode(productCode);
									
									if(product.listFiles().length>0 && product.listFiles()!=null) {
										for(File type : sort.listFiles()) {
											String fileName = type.getName();
											BrandProductFile f = new BrandProductFile();
											f.setProductId(p.get().getId());
											if(env.equals("local")) {
							                	f.setProductFilePath(absolutePath + commonPath + "/brandproduct/" + productCode + "/files/" +  fileName);
											}else if(env.equals("prod")) {
												f.setProductFilePath(commonPath + "/brandproduct/" + productCode + "/files/" +  fileName);
											}
											f.setProductFileRoad("/administration/brandproduct/" + productCode + "/files/" + fileName);
							                f.setProductFileName(fileName);
							                f.setProductFileDate(new Date());
							                brandProductFileRepository.save(f);
										}
									}
									break;
								}
							}
							
						}
					}
				}
			}
		}
	}
	
	public void zipAddProductInsert(
			MultipartFile file
			) throws IOException {
		
		String absolutePath = new File("").getAbsolutePath() + "\\";
		
		File exFile = new File(commonPath + "/temp");
		if(exFile.exists() && exFile.isDirectory()) {
			FileUtils.cleanDirectory(exFile); 
			exFile.delete();
		}
		
		File zipFile = new File(commonPath + "/temp");
		zipFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(zipFile);
		fos.write(file.getBytes());
		fos.close();
		ZipUtil.explode(zipFile);
		
		for(File product : zipFile.listFiles()) {
			if(product.isDirectory() && !product.getName().equals("brand")) {
				String productCode = product.getName();
				if(product.listFiles().length > 0) {
					for(File sort : product.listFiles()) {
						if(sort.isDirectory()) {
							String fileType = sort.getName();
							switch(fileType) {
								case "slide" :{
									Optional<BrandProduct> p = brandProductRepository.findByBrandProductCode(productCode);
									
									if(product.listFiles().length>0 && product.listFiles()!=null) {
										for(File type : sort.listFiles()) {
											String fileName = type.getName();
											BrandProductImage f = new BrandProductImage();
							        		f.setProductId(p.get().getId());
											if(env.equals("local")) {
							                	f.setProductImagePath(absolutePath + commonPath + "/brandproduct/" + productCode + "/slide/" +  fileName);
											}else if(env.equals("prod")) {
												f.setProductImagePath(commonPath + "/brandproduct/" + productCode + "/slide/" +  fileName);
											}
											f.setProductImageRoad("/administration/brandproduct/" + productCode + "/slide/" + fileName );
							                f.setProductImageName(fileName);
							                brandProductImageRepository.save(f);
										}
									}
									break;
								}
								case "spec" :{
									for(File type : sort.listFiles()) {
										String fileName = type.getName();
										Optional<BrandProduct> p = brandProductRepository.findByBrandProductCode(productCode);
										p.ifPresent(np -> {
											np.setSpecImageName(fileName);
											np.setSpecImagePath(commonPath + "/brandproduct/" + productCode + "/spec/" + fileName);
											np.setSpecImageRoad("/administration/brandproduct/" + productCode + "/spec/" + fileName);
											brandProductRepository.save(np);
										});
									}
									
									break;
									
								}
								case "overview" :{
									for(File type : sort.listFiles()) {
										String fileName = type.getName();
										Optional<BrandProduct> p = brandProductRepository.findByBrandProductCode(productCode);
										p.ifPresent(np -> {
											np.setTableImageName(fileName);
											np.setTableImagePath(commonPath + "/brandproduct/" + productCode + "/overview/" + fileName);
											np.setTableImageRoad("/administration/brandproduct/" + productCode + "/overview/" + fileName);
											brandProductRepository.save(np);
										});
									}
									break;
								}
								case "files" :{
									Optional<BrandProduct> p = brandProductRepository.findByBrandProductCode(productCode);
									
									if(product.listFiles().length>0 && product.listFiles()!=null) {
										for(File type : sort.listFiles()) {
											String fileName = type.getName();
											BrandProductFile f = new BrandProductFile();
											f.setProductId(p.get().getId());
											if(env.equals("local")) {
							                	f.setProductFilePath(absolutePath + commonPath + "/brandproduct/" + productCode + "/files/" +  fileName);
											}else if(env.equals("prod")) {
												f.setProductFilePath(commonPath + "/brandproduct/" + productCode + "/files/" +  fileName);
											}
											f.setProductFileRoad("/administration/brandproduct/" + productCode + "/files/" + fileName);
							                f.setProductFileName(fileName);
							                f.setProductFileDate(new Date());
							                brandProductFileRepository.save(f);
										}
									}
									break;
								}
							}
							
						}
					}
				}
			}
		}
		for(File f : zipFile.listFiles()) {
			File to = new File(commonPath + "/brandproduct/" + f.getName());
			Files.move(f.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		
		if(exFile.exists() && exFile.isDirectory()) {
			FileUtils.cleanDirectory(exFile); 
			exFile.delete();
		}
	}
	
	
	public Boolean removeOverViewImage(
			Long id
			) {
		String overViewPath = brandProductRepository.findById(id).get().getTableImagePath();
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
		
		File folder = new File(commonPath + "/brandproduct/" + brandProductRepository.findById(id).get().getBrandProductCode());
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
		String specPath = brandProductRepository.findById(id).get().getSpecImagePath();
		Boolean specResult = true;
		try {
			File specFile = new File(specPath);
			specResult = specFile.delete();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return specResult;
	}
	
	public BrandProduct productInsert(
			MultipartFile productOverviewImage, 
			MultipartFile productSpecImage,
			BrandProduct product) throws IllegalStateException, IOException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
		
		
		String overviewPath = commonPath + "/brandproduct/" + productCode + "/overview";
		String overviewRoad = "/administration/brandproduct/" + productCode + "/overview";
		File overviewFileFolder = new File(overviewPath);

		if (!overviewFileFolder.exists()) {
			overviewFileFolder.mkdirs();
		}
		
		String overviewContentType = productOverviewImage.getContentType();
		String specContentType = productSpecImage.getContentType();
		String overviewOriginalFileExtension = "";
	
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

		
		String specOriginalFileExtension = "";
		String specPath = commonPath + "/brandproduct/" + productCode +  "/spec"; 
		String specRoad = "/administration/brandproduct/" + productCode +  "/spec"; 
		File specFileFolder = new File(specPath);
		
		if (!specFileFolder.exists()) {
			specFileFolder.mkdirs();
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

		int index = 1;
		if(brandProductRepository.findFirstIndex().isPresent()) {
			index = brandProductRepository.findFirstIndex().get() + 1;
		}
		product.setBrandProductIndex(index);

		
		if (env.equals("local")) {
			overviewFileFolder = new File(absolutePath + overviewPath + "/" + overviewFileName);
			specFileFolder = new File(absolutePath + specPath + "/" + specFileName);
		} else if (env.equals("prod")) {
			overviewFileFolder = new File(overviewPath + "/" + overviewFileName);
			specFileFolder = new File(specPath + "/" + specFileName);
		}

		productOverviewImage.transferTo(overviewFileFolder);
		productSpecImage.transferTo(specFileFolder);
		product.setBrandProductCode(productCode);
		product.setTableImagePath(overviewPath + "/" + overviewFileName);
		product.setTableImageRoad(overviewRoad + "/" + overviewFileName);
		product.setTableImageName(overviewFileName);
		product.setSpecImageName(specFileName);
		product.setSpecImagePath(specPath + "/" + specFileName);
		product.setSpecImageRoad(specRoad + "/" + specFileName);

		return brandProductRepository.save(product);

	}

	public String productUpdate(MultipartFile productOverviewImage, MultipartFile productSpecImage,
			BrandProduct product) throws IllegalStateException, IOException {
		
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
			
			File exOverViewFile = new File(brandProductRepository.findById(product.getId()).get().getTableImagePath());
			if(exOverViewFile.isDirectory()&&exOverViewFile.exists()) {
				FileUtils.cleanDirectory(exOverViewFile);
			}
			
			String overviewPath = commonPath + "/brandproduct/" + product.getBrandProductCode() + "/overview";
			String overviewRoad = "/administration/brandproduct/" + product.getBrandProductCode() + "/overview";
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
//	      
			if (env.equals("local")) {
				overviewFileFolder = new File(absolutePath + overviewPath + "/" + overviewFileName);
			} else if (env.equals("prod")) {
				overviewFileFolder = new File(overviewPath + "/" + overviewFileName);
			}

			productOverviewImage.transferTo(overviewFileFolder);

			brandProductRepository.findById(product.getId()).ifPresent(s -> {
				s.setTableImagePath(overviewPath + "/" + overviewFileName);
				s.setTableImageRoad(overviewRoad + "/" + overviewFileName);
				s.setTableImageName(overviewFileName);
				brandProductRepository.save(s);
			});
		}
		if (!productSpecImage.isEmpty()) {
			
			File exSpecFile = new File(brandProductRepository.findById(product.getId()).get().getSpecImagePath());
			if(exSpecFile.isDirectory()&&exSpecFile.exists()) {
				FileUtils.cleanDirectory(exSpecFile);
			}
			
			String generatedString = random.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

			String specPath = commonPath + "/brandproduct/" + product.getBrandProductCode() + "/spec";
			String specRoad = "/administration/brandproduct/" + product.getBrandProductCode() + "/spec";
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

//	    	 

			if (env.equals("local")) {
				specFileFolder = new File(absolutePath + specPath + "/" + specFileName);
			} else if (env.equals("prod")) {
				specFileFolder = new File(specPath + "/" + specFileName);
			}
			productSpecImage.transferTo(specFileFolder);
			brandProductRepository.findById(product.getId()).ifPresent(s -> {
				s.setSpecImageName(specFileName);
				s.setSpecImagePath(specPath + "/" + specFileName);
				s.setSpecImageRoad(specRoad + "/" + specFileName);
				brandProductRepository.save(s);
			});

		}
		brandProductRepository.findById(product.getId()).ifPresent(s -> {
			s.setSmallSort(brandSmallSortRepository.findById(product.getBrandSmallSortId()).get());
			s.setMiddleSort(brandMiddleSortRepository.findById(product.getBrandMiddleSortId()).get());
			s.setBigSort(brandBigSortRepository.findById(product.getBrandBigSortId()).get());
			s.setBrand(brandRepository.findById(product.getBrandId()).get());
			s.setSubject(product.getSubject());
			s.setContent(product.getContent());
			s.setProductSubContent(product.getProductSubContent());
			s.setSign(product.getSign());
			brandProductRepository.save(s);
		});

		return "success";

	}
	
	public String productOverview(
			MultipartFile productOverviewImage, 
			BrandProduct product)
			throws IllegalStateException, IOException {
		String absolutePath = new File("").getAbsolutePath() + "\\";

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		if (!productOverviewImage.isEmpty()) {
			String generatedString = random.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			File exOverViewFile = new File(brandProductRepository.findById(product.getId()).get().getTableImagePath());
			exOverViewFile.delete();
			
			String overviewPath = commonPath + "/brandproduct/" + product.getBrandProductCode() + "/overview";
			String overviewRoad = "/administration/brandproduct/" + product.getBrandProductCode() + "/overview";
			
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
			brandProductRepository.findById(product.getId()).ifPresent(s -> {
				s.setTableImagePath(overviewPath + "/" + overviewFileName);
				s.setTableImageRoad(overviewRoad + "/" + overviewFileName);
				s.setTableImageName(overviewFileName);
				brandProductRepository.save(s);
			});
		}

		return "success";

	}
	
	public String productSpec(
			MultipartFile productSpecImage, 
			BrandProduct product)
			throws IllegalStateException, IOException {
		String absolutePath = new File("").getAbsolutePath() + "\\";

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		if (!productSpecImage.isEmpty()) {
			File exSpecFile = new File(brandProductRepository.findById(product.getId()).get().getSpecImagePath());
			exSpecFile.delete();
			String generatedString = random.ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			
			String specPath = commonPath + "/brandproduct/" + product.getBrandProductCode() + "/spec" ;
			String specRoad = "/administration/brandproduct/" + product.getBrandProductCode()+ "/spec" ;
			
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
			brandProductRepository.findById(product.getId()).ifPresent(s -> {
				s.setSpecImageName(specFileName);
				s.setSpecImagePath(specPath + "/" + specFileName);
				s.setSpecImageRoad(specRoad + "/" + specFileName);
				brandProductRepository.save(s);
			});

		}

		return "success";

	}
}

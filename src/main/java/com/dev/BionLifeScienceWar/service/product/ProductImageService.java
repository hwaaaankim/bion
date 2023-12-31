package com.dev.BionLifeScienceWar.service.product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.product.ProductFile;
import com.dev.BionLifeScienceWar.model.product.ProductImage;
import com.dev.BionLifeScienceWar.repository.product.ProductImageRepository;

@Service
public class ProductImageService {

	@Autowired
	ProductImageRepository productImageRepository;
	
	@Value("${spring.upload.env}")
	private String env;
	
	@Value("${spring.upload.path}")
	private String commonPath;
	
	public Boolean fileDelete(
			Long id
			) {
		List<ProductImage> files = productImageRepository.findAllByProductId(id);
		for(ProductImage p : files) {
			File pFile = new File(p.getProductImagePath());
			if(!pFile.delete()) {
				return false;
			}
		}
		
		return true;
	}
	
	public String fileUpload(
			List<MultipartFile> productImages,
			Long id,
			String productCode
			) throws IllegalStateException, IOException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = simpleDateFormat.format(new Date());
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = commonPath + "/company/" + productCode + "/slide";
        String road = "/administration/company/" + productCode + "/slide";
        File fileFolder = new File(path);
        if(fileFolder.exists() && fileFolder.isDirectory()) {
        	FileUtils.cleanDirectory(fileFolder);
        }
        int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
        if(!fileFolder.exists()) {
        	fileFolder.mkdirs();
        }
        
        for(MultipartFile file : productImages) {
        	
        	if(!file.isEmpty()) {
        		String generatedString = random.ints(leftLimit,rightLimit + 1)
        				  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        				  .limit(targetStringLength)
        				  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        				  .toString();
        		ProductImage f = new ProductImage();
        		f.setProductId(id);
            	String contentType = file.getContentType();
                String originalFileExtension = "";
                    // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
                if (ObjectUtils.isEmpty(contentType)){
                    return "NONE";
                }else{
                    if(contentType.contains("image/jpeg")){
                        originalFileExtension = ".jpg";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtension = ".png";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExtension = ".gif";
                    }
                    else if(contentType.contains("application/pdf")) {
                    	originalFileExtension = ".pdf";
                    }
                    else if(contentType.contains("application/x-zip-compressed")) {
                    	originalFileExtension = ".zip";
                    }
                    else if(contentType.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    	originalFileExtension = ".xlsx";
                    }
                    else if(contentType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    	originalFileExtension = ".docx";
                    }
                    else if(contentType.contains("text/plain")) {
                    	originalFileExtension = ".txt";
                    }
                    else if(contentType.contains("image/x-icon")) {
                    	originalFileExtension = ".ico";
                    }
                    else if(contentType.contains("application/haansofthwp")) {
                    	originalFileExtension = ".hwp";
                    }
                }
                String new_file_name =generatedString +  "_" + file.getOriginalFilename();
                if(env.equals("local")) {
                	fileFolder = new File(absolutePath + path + "/" + new_file_name);
                	f.setProductImagePath(absolutePath + path + "/" + new_file_name);
				}else if(env.equals("prod")) {
					fileFolder = new File(path + "/" + new_file_name);
					f.setProductImagePath(path + "/" + new_file_name);
				}
                
                file.transferTo(fileFolder);
                f.setProductImageRoad(road + "/" + new_file_name );
                f.setProductImageName(file.getOriginalFilename());
                productImageRepository.save(f);
            }
        }
        
        return "success";
	}
	
}

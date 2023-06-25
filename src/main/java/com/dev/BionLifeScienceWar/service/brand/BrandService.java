package com.dev.BionLifeScienceWar.service.brand;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.brand.Brand;
import com.dev.BionLifeScienceWar.repository.brand.BrandRepository;

@Service
public class BrandService {

	@Autowired
	BrandRepository brandRepository;
	
	public void brandInsert(
			MultipartFile image,
			Brand brand
			) throws IllegalStateException, IOException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = simpleDateFormat.format(new Date());
        
		String absolutePath = new File("").getAbsolutePath() + "\\";
      
//		String brandPath = "src/main/resources/static/administration/brand/"+current_date;
        String brandPath = "/home/hosting_users/bionls/tomcat/webapps/brand/"+current_date;
      
        String brandRoad = "/administration/brand/"+current_date;
        File brandFileFolder = new File(brandPath);
        
        int leftLimit = 48; // numeral '0'
 		int rightLimit = 122; // letter 'z'
 		int targetStringLength = 10;
 		Random random = new Random();
 		
 		String generatedString = random.ints(leftLimit,rightLimit + 1)
			  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			  .limit(targetStringLength)
			  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			  .toString();
 		
 		if(!brandFileFolder.exists()) {
 			brandFileFolder.mkdirs();
 		}
 		
 		String brandFileName = generatedString + image.getOriginalFilename();
 		
// 		brandFileFolder = new File(absolutePath + brandPath + "/" + brandFileName);
 		brandFileFolder = new File(brandPath + "/" + brandFileName);
 		
 		image.transferTo(brandFileFolder);
 		brand.setImagePath(brandPath + "/" + brandFileName);
 		brand.setImageRoad(brandRoad + "/" + brandFileName);
 		brand.setImageName(brandFileName);
 		
 		brandRepository.save(brand);
 		
	}
}




















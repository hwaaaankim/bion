package com.dev.BionLifeScienceWar.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.Banner;
import com.dev.BionLifeScienceWar.repository.BannerRepository;

@Service
public class BannerService {

	@Autowired
	BannerRepository bannerRepository;

	public String bannerInsert(
			List<MultipartFile> files,
			Banner banner)
			throws IllegalStateException, IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = simpleDateFormat.format(new Date());
		String absolutePath = new File("").getAbsolutePath() + "\\";
//		String path = "src/main/resources/static/administration/banner/" + current_date;
        String path = "/home/hosting_users/bionls/tomcat/webapps/banner/" + current_date;
		String road = "/administration/banner/" + current_date;
		File fileFolder = new File(path);
	    if(!fileFolder.exists()) {
        	fileFolder.mkdirs();
        }
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();
		
		if(files.size() == 1) {
			banner.setWebname(files.get(0).getOriginalFilename());
			banner.setWebpath(path + "/" + generatedString + "_" + files.get(0).getOriginalFilename());
			banner.setWebroad(road + "/" + generatedString + "_" + files.get(0).getOriginalFilename());
			banner.setMobilename(files.get(0).getOriginalFilename());
			banner.setMobilepath(path + "/" + generatedString + "_" + files.get(0).getOriginalFilename());
			banner.setMobileroad(road + "/" + generatedString + "_" + files.get(0).getOriginalFilename());
		}else if(files.size() == 2) {
			banner.setWebname(files.get(0).getOriginalFilename());
			banner.setWebpath(path + "/" + generatedString + "_" + files.get(0).getOriginalFilename());
			banner.setWebroad(road + "/" + generatedString + "_" + files.get(0).getOriginalFilename());
			banner.setMobilename(files.get(1).getOriginalFilename());
			banner.setMobilepath(path + "/" + generatedString + "_" + files.get(1).getOriginalFilename());
			banner.setMobileroad(road + "/" + generatedString + "_" + files.get(1).getOriginalFilename());
		}
		int index = 0;
		for (MultipartFile f : files) {
			if (!f.isEmpty()) {

				String contentType = f.getContentType();
				String originalFileExtension = "";
				// 확장자 명이 없으면 이 파일은 잘 못 된 것이다
				if (ObjectUtils.isEmpty(contentType)) {
					return "NONE";
				} else {
					if (contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					} else if (contentType.contains("image/png")) {
						originalFileExtension = ".png";
					} else if (contentType.contains("image/gif")) {
						originalFileExtension = ".gif";
					} else if (contentType.contains("application/pdf")) {
						originalFileExtension = ".pdf";
					} else if (contentType.contains("application/x-zip-compressed")) {
						originalFileExtension = ".zip";
					} else if (contentType
							.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
						originalFileExtension = ".xlsx";
					} else if (contentType
							.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
						originalFileExtension = ".docx";
					} else if (contentType.contains("text/plain")) {
						originalFileExtension = ".txt";
					} else if (contentType.contains("image/x-icon")) {
						originalFileExtension = ".ico";
					} else if (contentType.contains("application/haansofthwp")) {
						originalFileExtension = ".hwp";
					}
				}
				if(index == 0) {
//					fileFolder = new File(absolutePath +  banner.getWebpath());
					fileFolder = new File(banner.getWebpath());
				}else if(index == 1) {
//					fileFolder = new File(absolutePath +  banner.getMobilepath());
					fileFolder = new File(banner.getMobilepath());
				}
//                fileFolder = new File(path + "/" + new_file_name);
				f.transferTo(fileFolder);
			}
		}
		
		bannerRepository.save(banner);
		return "success";
	}
}

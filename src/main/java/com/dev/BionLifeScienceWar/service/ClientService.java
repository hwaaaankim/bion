package com.dev.BionLifeScienceWar.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.Client;
import com.dev.BionLifeScienceWar.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	public String clientInsert(
			Client client, 
			MultipartFile file) throws IllegalStateException, IOException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = simpleDateFormat.format(new Date());
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = "src/main/resources/static/administration/file/"+current_date;
//        String path = "/home/hosting_users/winwinpat/tomcat/webapps/files/";
        String road = "/front/files/"+current_date;
        File fileFolder = new File(path);
        
        if(!fileFolder.exists()) {
        	fileFolder.mkdirs();
        }
		
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
        String new_file_name =Long.toString(System.nanoTime()) +  "_" + file.getOriginalFilename();
        
        fileFolder = new File(absolutePath + path + "/" + new_file_name);
//        fileFolder = new File(path + "/" + new_file_name);
        file.transferTo(fileFolder);
        client.setFiledate(current_date);
//        f.setFilepath(absolutePath + path + "/" + new_file_name);
        client.setFilepath(path + "/" + new_file_name);
        client.setFileroad(road + "/" + new_file_name );
        client.setFilename(file.getOriginalFilename());
        client.setJoindate(new Date());
        client.setContact(false);
        clientRepository.save(client);
		
        return "success";
	}
	
	public Page<Client> findByDate(Pageable pageable, String startDate, String endDate) throws ParseException {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("".equals(startDate) && "".equals(startDate)) {

			Date today = new Date();
			String day = bf.format(today);

			String start = day.substring(0, 10) + " 00:00:00";
			String end = day.substring(0, 10) + " 23:59:00";

			Date first = bf.parse(start);
			Date second = bf.parse(end);
			return clientRepository.findAllByJoindateBetween(pageable, first, second);

		} else if (!"".equals(startDate) && !"".equals(startDate) && startDate.equals(endDate)) {
			String start = startDate.substring(0, 10) + " 00:00:00";
			Date first = f.parse(start);
			Date second = f.parse(start);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return clientRepository.findAllByJoindateBetween(pageable, first, second);

		} else if ("".equals(startDate) && !"".equals(endDate)) {

			Date second = f.parse(endDate);
			return clientRepository.findAllByJoindateLessThan(pageable, second);

		} else if (!"".equals(startDate) && "".equals(endDate)) {
			Date first = f.parse(startDate);
			return clientRepository.findAllByJoindateGreaterThan(pageable, first);
		} else {
			Date first = f.parse(startDate);
			Date second = f.parse(endDate);

			Calendar c = Calendar.getInstance();
			c.setTime(second);
			c.add(Calendar.DATE, 1);
			second = c.getTime();

			return clientRepository.findAllByJoindateBetween(pageable, first, second);
		}
	}
}

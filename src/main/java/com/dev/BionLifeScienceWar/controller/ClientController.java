package com.dev.BionLifeScienceWar.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.Client;
import com.dev.BionLifeScienceWar.model.CompanyEmail;
import com.dev.BionLifeScienceWar.repository.CompanyEmailRepository;
import com.dev.BionLifeScienceWar.service.ClientService;
import com.dev.BionLifeScienceWar.service.EmailService;

@Controller
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@Autowired
	CompanyEmailRepository companyEmailRepository;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping("/clientInsert")
	public String clientInsert(
			Client client,
			MultipartFile file
			) throws IllegalStateException, IOException {
		clientService.clientInsert(client, file);
		List<CompanyEmail> list = companyEmailRepository.findAll();
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
        // 작업1 (스레드)
        executorService.submit(() -> {
        	String[] to = new String[list.size()];
        	int i=0;
        	for(CompanyEmail a : list) {
        		to[i] = a.getEmail();
        		i++;
        	}
        	
        	try {
        		emailService.sendEmail(to, "고객 문의 발생 - 웹서버 발송", client.toString());
        	}catch(MailSendException e) {
        		System.out.println(e);
        	} catch (InterruptedException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
    		
    		
        });

        executorService.shutdown();
		
		return "front/customer/contact";
	}
}

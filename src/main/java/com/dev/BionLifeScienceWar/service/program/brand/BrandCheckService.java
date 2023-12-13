package com.dev.BionLifeScienceWar.service.program.brand;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BrandCheckService {

	public String excelCheck(
			MultipartFile file
			) {
		return "success";
	}
}

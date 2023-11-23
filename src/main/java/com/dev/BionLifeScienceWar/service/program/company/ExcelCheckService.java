package com.dev.BionLifeScienceWar.service.program.company;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelCheckService {

	public String excelCheck(
			MultipartFile file
			) {
		return "success";
	}
}

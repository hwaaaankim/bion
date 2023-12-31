package com.dev.BionLifeScienceWar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.BionLifeScienceWar.model.CompanyInfo;
import com.dev.BionLifeScienceWar.repository.CompanyInfoRepository;

@Service
public class CompanyInfoService {

	@Autowired
	CompanyInfoRepository companyInfoRepository;

	public void changeCompanyInfo(CompanyInfo companyInfo) {
		if (companyInfoRepository.findById(1L).isPresent()) {

			companyInfoRepository.findById(1L).ifPresent(s -> {
				s.setCompanyAddress(companyInfo.getCompanyAddress());
				s.setCompanyName(companyInfo.getCompanyName());
				s.setCompanyNumber(companyInfo.getCompanyNumber());
				s.setCompanyTelephone(companyInfo.getCompanyTelephone());
				s.setCompanyEmailCheck(companyInfo.getCompanyEmailCheck());
				companyInfoRepository.save(s);
			});
		} else {
			CompanyInfo s = new CompanyInfo();
			s.setCompanyAddress(companyInfo.getCompanyAddress());
			s.setCompanyName(companyInfo.getCompanyName());
			s.setCompanyNumber(companyInfo.getCompanyNumber());
			s.setCompanyTelephone(companyInfo.getCompanyTelephone());
			s.setCompanyEmailCheck(companyInfo.getCompanyEmailCheck());
			companyInfoRepository.save(s);
		}
	}
	
	public void changeCompanyMail(Boolean companyEmail) {
		companyInfoRepository.findById(1L).ifPresent(s -> {
			s.setCompanyEmailCheck(companyEmail);
			companyInfoRepository.save(s);
		});
	}
}

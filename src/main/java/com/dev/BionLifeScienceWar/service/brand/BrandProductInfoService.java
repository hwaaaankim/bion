package com.dev.BionLifeScienceWar.service.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.BionLifeScienceWar.repository.brand.BrandProductInfoRepository;

@Service
public class BrandProductInfoService {

	@Autowired
	BrandProductInfoRepository brandProductInfoRepository;
}

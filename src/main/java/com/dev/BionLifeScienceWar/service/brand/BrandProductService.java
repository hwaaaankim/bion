package com.dev.BionLifeScienceWar.service.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.BionLifeScienceWar.repository.brand.BrandProductRepository;

@Service
public class BrandProductService {

	@Autowired
	BrandProductRepository brandProductRepository;
}

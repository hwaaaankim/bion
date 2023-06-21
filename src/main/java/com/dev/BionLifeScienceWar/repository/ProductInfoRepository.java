package com.dev.BionLifeScienceWar.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.product.ProductInfo;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long>{
	
	@Transactional
	int deleteAllByProductId(Long id);
}

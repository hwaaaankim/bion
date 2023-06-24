package com.dev.BionLifeScienceWar.repository.brand;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.BrandProductInfo;

@Repository
public interface BrandProductInfoRepository extends JpaRepository<BrandProductInfo, Long>{
	@Transactional
	int deleteAllByProductId(Long id);
}

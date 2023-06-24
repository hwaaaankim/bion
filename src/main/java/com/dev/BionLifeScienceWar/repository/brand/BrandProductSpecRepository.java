package com.dev.BionLifeScienceWar.repository.brand;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.BrandProductSpec;

@Repository
public interface BrandProductSpecRepository extends JpaRepository<BrandProductSpec, Long>{
	@Transactional
	int deleteAllByProductId(Long ig);
}

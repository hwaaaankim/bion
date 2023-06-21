package com.dev.BionLifeScienceWar.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.product.ProductSpec;

@Repository
public interface ProductSpecRepository extends JpaRepository<ProductSpec, Long>{

	@Transactional
	int deleteAllByProductId(Long ig);
}

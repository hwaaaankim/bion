package com.dev.BionLifeScienceWar.repository.product;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.product.ProductFile;

@Repository
public interface ProductFileRepository extends JpaRepository<ProductFile, Long>{

	@Transactional
	int deleteAllByProductId(Long id);
}

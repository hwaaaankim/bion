package com.dev.BionLifeScienceWar.repository.brand;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.Brand;
import com.dev.BionLifeScienceWar.model.brand.BrandBigSort;
import com.dev.BionLifeScienceWar.model.brand.BrandMiddleSort;
import com.dev.BionLifeScienceWar.model.brand.BrandProduct;
import com.dev.BionLifeScienceWar.model.brand.BrandSmallSort;

@Repository
public interface BrandProductRepository extends JpaRepository<BrandProduct, Long>{

	Page<BrandProduct> findAllBySmallSort(Pageable pageable, BrandSmallSort brandSmallSort);
	
	Page<BrandProduct> findAllByMiddleSort(Pageable pageable, BrandMiddleSort brandMiddleSort);
	
	Page<BrandProduct> findAllByBigSort(Pageable pageable, BrandBigSort brandBigSort);
	
	Page<BrandProduct> findAllByBrand(Pageable pageable, Brand brand);
	
	Page<BrandProduct> findAll(Pageable pageble);

	List<BrandProduct> findAllBySign(Boolean sign);
	
	List<BrandProduct> findBySubjectContains(String subject);
}

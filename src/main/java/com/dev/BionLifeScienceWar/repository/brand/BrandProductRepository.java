package com.dev.BionLifeScienceWar.repository.brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.BrandProduct;
import com.dev.BionLifeScienceWar.model.brand.BrandSmallSort;

@Repository
public interface BrandProductRepository extends JpaRepository<BrandProduct, Long>{

	Page<BrandProduct> findAllBySmallSort(Pageable pageable, BrandSmallSort brandSmallSort);
}

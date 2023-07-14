package com.dev.BionLifeScienceWar.repository.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.model.product.SmallSort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Page<Product> findAllBySmallSort(Pageable pageable, SmallSort smallSort);
	
	Page<Product> findAll(Pageable pageble);
	
	List<Product> findAllBySign(Boolean sign);
	
	Page<Product> findAllByBigSort(Pageable pageable, BigSort bigSort);
	
	Page<Product> findAllByMiddleSort(Pageable pageable, MiddleSort bigSort);
}

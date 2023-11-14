package com.dev.BionLifeScienceWar.repository.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.model.product.SmallSort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Page<Product> findAllBySmallSort(Pageable pageable, SmallSort smallSort);
	
	Page<Product> findAll(Pageable pageble);
	
	Page<Product> findAllByBigSort(Pageable pageable, BigSort bigSort);
	
	Page<Product> findAllByMiddleSort(Pageable pageable, MiddleSort bigSort);
	
	Page<Product> findAllBySmallSortAndSubjectContains(Pageable pageable, SmallSort smallSort, String subject);
	
	Page<Product> findAllBySubjectContains(Pageable pageble, String subject);
	
	Page<Product> findAllByBigSortAndSubjectContains(Pageable pageable, BigSort bigSort, String subject);
	
	Page<Product> findAllByMiddleSortAndSubjectContains(Pageable pageable, MiddleSort bigSort, String subject);

	List<Product> findAllBySign(Boolean sign);
	
	List<Product> findBySubjectContains(String subject);
	
	Optional<Product> findByProductCode(String code);
	
	@Query("SELECT MAX(productIndex) FROM Product")
	Optional<Integer> findFirstIndex();
}

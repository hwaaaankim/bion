package com.dev.BionLifeScienceWar.repository.brand;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.BrandBigSort;
import com.dev.BionLifeScienceWar.model.brand.BrandMiddleSort;

@Repository
public interface BrandMiddleSortRepository extends JpaRepository<BrandMiddleSort, Long>{

	List<BrandMiddleSort> findAllByBigSort(BrandBigSort brandBigSort);
	
	List<BrandMiddleSort> findAllByOrderByBrandMiddleSortIndexAsc();
	
	Page<BrandMiddleSort> findAllByOrderByBrandMiddleSortIndexAsc(Pageable pageable);
	
	List<BrandMiddleSort> findAllByBigSortOrderByBrandMiddleSortIndexAsc(BrandBigSort bigsort);
	
	Page<BrandMiddleSort> findAllByBigSortOrderByBrandMiddleSortIndexAsc(Pageable pageable, BrandBigSort bigsort);	
	
	@Query("SELECT MAX(brandMiddleSortIndex) FROM BrandMiddleSort")
	Optional<Integer> findFirstIndex();
}

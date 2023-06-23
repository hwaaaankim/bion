package com.dev.BionLifeScienceWar.repository.brand;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.BrandBigSort;
import com.dev.BionLifeScienceWar.model.brand.BrandMiddleSort;

@Repository
public interface BrandMiddleSortRepository extends JpaRepository<BrandMiddleSort, Long>{

	List<BrandMiddleSort> findAllByBigSort(BrandBigSort brandBigSort);
}

package com.dev.BionLifeScienceWar.repository.brand;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.BrandMiddleSort;
import com.dev.BionLifeScienceWar.model.brand.BrandSmallSort;

@Repository
public interface BrandSmallSortRepository extends JpaRepository<BrandSmallSort, Long>{

	List<BrandSmallSort> findAllByMiddleSort(BrandMiddleSort middleSort);
}

package com.dev.BionLifeScienceWar.repository.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.BrandBigSort;

@Repository
public interface BrandBigSortRepository extends JpaRepository<BrandBigSort, Long>{

}

package com.dev.BionLifeScienceWar.repository.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.BrandProductImage;

@Repository
public interface BrandProductImageRepository extends JpaRepository<BrandProductImage, Long>{

}

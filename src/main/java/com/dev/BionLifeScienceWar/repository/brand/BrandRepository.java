package com.dev.BionLifeScienceWar.repository.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.brand.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>{

}

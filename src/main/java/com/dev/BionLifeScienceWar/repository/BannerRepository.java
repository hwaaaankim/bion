package com.dev.BionLifeScienceWar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>{

}

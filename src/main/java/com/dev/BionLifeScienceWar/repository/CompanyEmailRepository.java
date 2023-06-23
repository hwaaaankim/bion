package com.dev.BionLifeScienceWar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.CompanyEmail;

@Repository
public interface CompanyEmailRepository extends JpaRepository<CompanyEmail, Long>{

}

package com.dev.BionLifeScienceWar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long>{

}

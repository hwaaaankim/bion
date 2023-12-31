package com.dev.BionLifeScienceWar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.ReferenceFile;

@Repository
public interface ReferenceFileRepository extends JpaRepository<ReferenceFile, Long>{

	List<ReferenceFile> findAllByOrderByFiledateDesc();
	
	List<ReferenceFile> findByFilesubjectContains(String filesubject);
}

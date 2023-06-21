package com.dev.BionLifeScienceWar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.NoticeSubject;

@Repository
public interface NoticeSubjectRepository extends JpaRepository<NoticeSubject, Long>{

	
}

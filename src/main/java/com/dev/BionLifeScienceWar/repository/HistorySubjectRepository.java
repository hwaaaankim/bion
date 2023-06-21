package com.dev.BionLifeScienceWar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.HistorySubject;

@Repository
public interface HistorySubjectRepository extends JpaRepository<HistorySubject, Long>{

	List<HistorySubject> findAllByOrderByStart();
}

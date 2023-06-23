package com.dev.BionLifeScienceWar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.BionLifeScienceWar.repository.HistoryContentRepository;
import com.dev.BionLifeScienceWar.repository.HistorySubjectRepository;

@Service
public class HistoryService {

	
	@Autowired
	HistoryContentRepository historyContentRepository;
	
	@Autowired
	HistorySubjectRepository historySubjectRepository;
}

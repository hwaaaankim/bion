package com.dev.BionLifeScienceWar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.BionLifeScienceWar.model.Notice;
import com.dev.BionLifeScienceWar.repository.NoticeRepository;

@Service
public class NoticeService {

	
	@Autowired
	NoticeRepository noticeRepository;
	
	public String noticeUpdate(Notice notice) {
		
		Optional<Notice> n = noticeRepository.findById(notice.getId());
		n.ifPresent(no->{
			no.setContent(notice.getContent());
			no.setSign(notice.getSign());
			no.setSubject(notice.getSubject());
			no.setSubjectId(notice.getSubjectId());
			
			noticeRepository.save(no);
		});
		
		return "success";
	}
}

package com.dev.BionLifeScienceWar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="history_content")
public class HistoryContent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="HISTORY_CONTENT_ID")
	private Long id;
	
	@Column(name="HISTORY_CONTENT_SUBJECT")
	private String contentSubject;
	
	@Column(name="HISTORY_CONTENT_DATE")
	private Date date;
	
	@Column(name="HISTORY_REFER_ID")
	private Long subjectId;
	
	@Column(name="HISTORY_STRING_DATE")
	private String historyDate;
	
}






































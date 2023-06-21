package com.dev.BionLifeScienceWar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="notice_subject")
@Data
public class NoticeSubject {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NOTICE_SUBJECT_ID")
	private Long id;
	
	@Column(name="NOTICE_SUBJECT_TEXT")
	private String text;
	
}

package com.dev.BionLifeScienceWar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name="notice")
@Data
public class Notice {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NOTICE_ID")
	private Long id;
	
	@Column(name="NOTICE_SUBJECT")
	private String subject;
	
	@Column(name="NOTICE_DATE")
	private Date date;
	
	@Column(name="NOTICE_CONTENT")
	private String content;
	
	@Column(name="NOTICE_SIGN")
	private Boolean sign;
	
	@Transient
	private Long subjectId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="NOTICE_REFER_ID", referencedColumnName="NOTICE_SUBJECT_ID"
			)
	private NoticeSubject noticeSubject;
	
	@Transient
	private String subjectText;
	
}

package com.dev.BionLifeScienceWar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="banner")
public class Banner {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BANNER_ID")
	private Long id;
	
	@Column(name="BANNER_SUBJECT")
	private String subject;
	
	@Column(name="BANNER_CONTENT")
	private String content;
	
	@Column(name="BANNER_WEB_PATH")
	private String webpath;
	
	@Column(name="BANNER_WEB_NAME")
	private String webname;
	
	@Column(name="BANNER_WEB_ROAD")
	private String webroad;
	
	@Column(name="BANNER_MOBILE_NAME")
	private String mobilename;
	
	@Column(name="BANNER_MOBILE_PATH")
	private String mobilepath;
	
	@Column(name="BANNER_MOBILE_ROAD")
	private String mobileroad;
}











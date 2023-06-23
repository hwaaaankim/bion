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
@Table(name="reference_file")
public class ReferenceFile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="REFERENCE_FILE_ID")
	private Long id;
	
	@Column(name="REFERENCE_FILE_SUBJECT")
	private String filesubject;
	
	@Column(name="REFERENCE_FILE_DATE")
	private Date filedate;
	
	@Column(name="REFERENCE_FILE_PATH")
	private String filepath;
	
	@Column(name="REFERENCE_FILE_NAME")
	private String filename;
	
	@Column(name="REFERENCE_FILE_ROAD")
	private String fileroad;
	
	@Column(name="REFERENCE_FILE_EXTENSION")
	private String fileextension;
}

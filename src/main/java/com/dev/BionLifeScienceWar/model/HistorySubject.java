package com.dev.BionLifeScienceWar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="history_subject")
public class HistorySubject {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="HISTORY_SUBJECT_ID")
	private Long id;
	
	@Column(name="HISTORY_SUBJECT_START")
	private String start;
	
	@Column(name="HISTORY_SUBJECT_END")
	private String end;
	
	@OneToMany(
			fetch = FetchType.EAGER, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "subjectId"
			)
	private List<HistoryContent> contents;
	
	
}

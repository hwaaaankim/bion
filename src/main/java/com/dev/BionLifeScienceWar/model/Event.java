package com.dev.BionLifeScienceWar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
@Entity
@Table(name="event")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EVENT_ID")
	private Long id;
	
	@Column(name="EVENT_SUBJECT")
	@Nullable
	private String subject;
	
	@Column(name="EVENT_CONTENT")
	@Nullable
	private String content;
	
	@Column(name="EVENT_LINK")
	@Nullable
	private String link;

}

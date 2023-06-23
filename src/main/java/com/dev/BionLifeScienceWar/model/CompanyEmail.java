package com.dev.BionLifeScienceWar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="company_mail")
@Data
public class CompanyEmail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="COMPANY_MAIL_ID")
	private Long id;
	
	@Column(name="COMPANY_MAIL_ADDRESS")
	private String email;
}

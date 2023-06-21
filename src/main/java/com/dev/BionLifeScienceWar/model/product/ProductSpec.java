package com.dev.BionLifeScienceWar.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="product_spec")
@Data
public class ProductSpec {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_SPEC_ID")
	private Long id;
	
	@Column(name="PRODUCT_SPEC_SUBJECT")
	private String productSpecSubject;
	
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@Column(name="PRODUCT_SPEC_CONTENT")
	private String productSpecContent;
}

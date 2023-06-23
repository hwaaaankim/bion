package com.dev.BionLifeScienceWar.model.brand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="brand_product_spec")
@Data
public class BrandProductSpec {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BRAND_PRODUCT_SPEC_ID")
	private Long id;
	
	@Column(name="BRAND_PRODUCT_SPEC_SUBJECT")
	private String productSpecSubject;
	
	@Column(name="BRAND_PRODUCT_ID")
	private Long productId;
	
	@Column(name="BRAND_PRODUCT_SPEC_CONTENT")
	private String productSpecContent;
}

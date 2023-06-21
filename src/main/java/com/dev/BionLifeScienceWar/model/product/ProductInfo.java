package com.dev.BionLifeScienceWar.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="product_info")
@Data
public class ProductInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_INFO_ID")
	private Long id;
	
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@Column(name="PRODUCT_INFO_TEXT")
	private String productInfoText;
}

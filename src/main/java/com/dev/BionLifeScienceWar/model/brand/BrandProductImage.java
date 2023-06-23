package com.dev.BionLifeScienceWar.model.brand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="brand_product_image")
public class BrandProductImage {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BRAND_PRODUCT_IMAGE_ID")
	private Long id;
	
	@Column(name="BRAND_PRODUCT_ID")
	private Long productId;
	
	@Column(name="BRAND_PRODUCT_IMAGE_PATH")
	private String productImagePath;
	
	@Column(name="BRAND_PRODUCT_IMAGE_NAME")
	private String productImageName;
	
	@Column(name="BRAND_PRODUCT_IMAGE_ROAD")
	private String productImageRoad;
}

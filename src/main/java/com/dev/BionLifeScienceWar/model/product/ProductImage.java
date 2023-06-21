package com.dev.BionLifeScienceWar.model.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="product_image")
public class ProductImage {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PRODUCT_IMAGE_ID")
	private Long id;
	
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@Column(name="PRODUCT_IMAGE_PATH")
	private String productImagePath;
	
	@Column(name="PRODUCT_IMAGE_NAME")
	private String productImageName;
	
	@Column(name="PRODUCT_IMAGE_ROAD")
	private String productImageRoad;
}

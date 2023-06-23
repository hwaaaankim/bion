package com.dev.BionLifeScienceWar.model.brand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="brand_product_file")
@Data
public class BrandProductFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BRAND_PRODUCT_FILE_ID")
	private Long id;
	
	@Column(name="BRAND_PRODUCT_FILE_NAME")
	private String productFileName;
	
	@Column(name="BRAND_PRODUCT_ID")
	private Long productId;
	
	@Column(name="BRAND_PRODUCT_FILE_PATH")
	private String productFilePath;
	
	@Column(name="BRAND_PRODUCT_FILE_ROAD")
	private String productFileRoad;
	
	@Column(name="BRAND_PRODUCT_FILE_DATE")
	private Date productFileDate;

}

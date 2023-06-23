package com.dev.BionLifeScienceWar.model.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="product_file")
@Data
public class ProductFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_FILE_ID")
	private Long id;
	
	@Column(name="PRODUCT_File_NAME")
	private String productFileName;
	
	@Column(name="PRODUCT_ID")
	private Long productId;
	
	@Column(name="PRODUCT_FILE_PATH")
	private String productFilePath;
	
	@Column(name="PRODUCT_FILE_ROAD")
	private String productFileRoad;
	
	@Column(name="PRODUCT_FILE_DATE")
	private Date productFileDate;
	
}

package com.dev.BionLifeScienceWar.model.brand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="brand")
public class Brand {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BRAND_ID")
	private Long id;
	
	@Column(name="BRAND_NAME")
	private String name;
	
	@Column(name="BRAND_CONTENT")
	private String content;
	
	@Column(name="BRAND_IMAGE_PATH")
	private String imagePath;
	
	@Column(name="BRAND_IMAGE_ROAD")
	private String imageRoad;
	
	@Column(name="BRAND_IMAGE_NAME")
	private String iamgeName;

}

package com.dev.BionLifeScienceWar.model.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.lang.Nullable;

import lombok.Data;

@Entity
@Table(name="product")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PRODUCT_ID")
	private Long id;
	
	@Column(name="PRODUCT_SUBJECT")
	private String subject;
	
	@Column(name="PRODUCT_CODE")
	@Nullable
	private String productCode;
	
	@Column(name="PRODUCT_CONTENT")
	private String content;
	
	@Column(name="PRODUCT_SUB_CONTENT")
	private String productSubContent;
	
	@Column(name="TABLE_IMAGE_PATH")
	private String tableImagePath;
	
	@Column(name="TABLE_IMAGE_NAME")
	private String tableImageName;
	
	@Column(name="TABLE_IMAGE_ROAD")
	private String tableImageRoad;
	
	@Column(name="SPEC_IMAGE_PATH")
	private String specImagePath;
	
	@Column(name="SPEC_IMAGE_NAME")
	private String specImageName;
	
	@Column(name="SPEC_IMAGE_ROAD")
	private String specImageRoad;
	
	@Column(name="PRODUCT_SIGN")
	private Boolean sign;
	
	@Column(name="PRODUCT_INDEX")
	private int productIndex;
	
	@Transient
	private Long smallId;
	
	@Transient
	private Long middleId;
	
	@Transient
	private Long bigId;
	
	@Transient
	private String firstImageRoad;
		
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductImage> images;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductInfo> infos;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductFile> files;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<ProductSpec> specs;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="PRODUCT_REFER_ID", referencedColumnName="SMALL_SORT_ID"
			)
	private SmallSort smallSort;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="PRODUCT_MIDDLE_REFER_ID", referencedColumnName="MIDDLE_SORT_ID"
			)
	private MiddleSort middleSort;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="PRODUCT_BIG_REFER_ID", referencedColumnName="BIG_SORT_ID"
			)
	private BigSort bigSort;
	
}

























package com.dev.BionLifeScienceWar.model.brand;

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
@Table(name="brand_product")
@Data
public class BrandProduct {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BRAND_PRODUCT_ID")
	private Long id;
	
	@Column(name="BRAND_PRODUCT_SUBJECT")
	private String subject;
	
	@Column(name="BRAND_PRODUCT_CODE")
	@Nullable
	private String brandProductCode;
	
	@Column(name="BRAND_PRODUCT_CONTENT")
	private String content;
	
	@Column(name="BRAND_PRODUCT_SUB_CONTENT")
	private String productSubContent;
	
	@Column(name="BRAND_TABLE_IMAGE_PATH")
	private String tableImagePath;
	
	@Column(name="BRAND_TABLE_IMAGE_NAME")
	private String tableImageName;
	
	@Column(name="BRAND_TABLE_IMAGE_ROAD")
	private String tableImageRoad;
	
	@Column(name="BRAND_SPEC_IMAGE_PATH")
	private String specImagePath;
	
	@Column(name="BRAND_SPEC_IMAGE_NAME")
	private String specImageName;
	
	@Column(name="BRAND_SPEC_IMAGE_ROAD")
	private String specImageRoad;
	
	@Column(name="BRAND_PRODUCT_SIGN")
	private Boolean sign;
	
	@Column(name="BRAND_PRODUCT_INDEX")
	private int brandProductIndex;
	
	@Transient
	private Long brandSmallSortId;
	
	@Transient
	private Long brandMiddleSortId;
	
	@Transient
	private Long brandBigSortId;
	
	@Transient
	private Long brandId;
	
	@Transient
	private String firstImageRoad;
	
	public String addFirstImage() {
		
		return this.images.get(0).getProductImageRoad();
	}
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<BrandProductImage> images;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<BrandProductInfo> infos;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<BrandProductFile> files;
	
	@OneToMany(
			fetch = FetchType.LAZY, 
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "productId"
			)
	private List<BrandProductSpec> specs;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="BRAND_PRODUCT_REFER_ID", referencedColumnName="BRAND_SMALLSORT_ID"
			)
	private BrandSmallSort smallSort;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="BRAND_PRODUCT_MIDDLE_REFER_ID", referencedColumnName="BRAND_MIDDLESORT_ID"
			)
	private BrandMiddleSort middleSort;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="BRAND_PRODUCT_BIG_REFER_ID", referencedColumnName="BRAND_BIGSORT_ID"
			)
	private BrandBigSort bigSort;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="BRAND_PRODUCT_BRAND_REFER_ID", referencedColumnName="BRAND_ID"
			)
	private Brand brand;
}

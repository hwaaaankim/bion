package com.dev.BionLifeScienceWar.model.brand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
@Table(name="brand_big_sort")
public class BrandBigSort {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BRAND_BIGSORT_ID")
	private Long id;
	
	@Column(name="BRAND_BIGSORT_NAME")
	private String name;
	
	@Transient
	private Long brandId;
	
	@Column(name="BRAND_BIGSORT_INDEX")
	private int brandBigSortIndex;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="BRAND_BIGSORT_REFER_ID", referencedColumnName="BRAND_ID"
			)
	private Brand brand;

}

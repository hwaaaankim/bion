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

@Data
@Entity
@Table(name="brand_middle_sort")
public class BrandMiddleSort {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BRAND_MIDDLESORT_ID")
	private Long id;
	
	@Column(name="BRAND_MIDDLESORT_NAME")
	private String name;
	
	@Transient
	private Long brandBigSortId;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(
			name="BRAND_MIDDLESORT_REFER_ID", referencedColumnName = "BRAND_BIGSORT_ID"
			)
	private BrandBigSort bigSort;
}

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
@Table(name="brand_small_sort")
public class BrandSmallSort {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BRAND_SMALLSORT_ID")
	private Long id;
	
	@Column(name="BRAND_SMALLSORT_NAME")
	private String name;
	
	@Transient
	private Long brandMiddleSortId;
	
	@Column(name="BRAND_SMALLSORT_INDEX")
	private int brandSmallSortIndex;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="BRAND_SMALLSORT_REFER_ID", referencedColumnName = "BRAND_MIDDLESORT_ID"
			)
	BrandMiddleSort middleSort;

}

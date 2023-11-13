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
@Table(name="big_sort")
public class BigSort {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BIG_SORT_ID")
	private Long id;
	
	@Column(name="BIG_SORT_NAME")
	private String name;
	
	@Column(name="BIG_SORT_INDEX")
	private int bigSortIndex;

}

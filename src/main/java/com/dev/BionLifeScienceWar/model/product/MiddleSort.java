package com.dev.BionLifeScienceWar.model.product;

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
@Table(name="middle_sort")
public class MiddleSort {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MIDDLE_SORT_ID")
	private Long id;
	
	@Column(name="MIDDLE_SORT_NAME")
	private String name;
	
	@Transient
	private Long bigId;
	
	@Column(name="MIDDLE_SORT_INDEX")
	private int middleSortIndex;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="MIDDLE_REFER_ID", referencedColumnName="BIG_SORT_ID"
			)
	private BigSort bigSort;
}
















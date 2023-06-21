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
@Table(name="small_sort")
public class SmallSort {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SMALL_SORT_ID")
	private Long id;
	
	@Column(name="SMALL_SORT_NAME")
	private String name;
	
	@Transient
	private Long middleId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
			name="SMALL_REFER_ID", referencedColumnName="MIDDLE_SORT_ID"
			)
	private MiddleSort middleSort;
	
}

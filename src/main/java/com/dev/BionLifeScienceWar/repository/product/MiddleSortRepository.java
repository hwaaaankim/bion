package com.dev.BionLifeScienceWar.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;

@Repository
public interface MiddleSortRepository extends JpaRepository<MiddleSort, Long>{

	List<MiddleSort> findAllByBigSort(BigSort bigsort);
}

package com.dev.BionLifeScienceWar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.SmallSort;

@Repository
public interface SmallSortRepository extends JpaRepository<SmallSort, Long>{

	List<SmallSort> findAllByMiddleSort(MiddleSort middleSort);
}

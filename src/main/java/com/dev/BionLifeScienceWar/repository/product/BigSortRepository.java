package com.dev.BionLifeScienceWar.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.BionLifeScienceWar.model.product.BigSort;

@Repository
public interface BigSortRepository extends JpaRepository<BigSort, Long>{

}

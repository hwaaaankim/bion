package com.dev.BionLifeScienceWar.dto;

import java.util.List;

import com.dev.BionLifeScienceWar.model.brand.Brand;
import com.dev.BionLifeScienceWar.model.brand.BrandBigSort;
import com.dev.BionLifeScienceWar.model.brand.BrandMiddleSort;
import com.dev.BionLifeScienceWar.model.brand.BrandProduct;
import com.dev.BionLifeScienceWar.model.brand.BrandSmallSort;
import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.model.product.SmallSort;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuDTO {


	private List<Product> productList;
	private List<BigSort> bigSortList;
	private List<MiddleSort> middleSortList;
	private List<SmallSort> smallSortList;
	
	private List<Brand> brandList;
	private List<BrandBigSort> brandBigSortList;
	private List<BrandMiddleSort> brandMiddleSortList;
	private List<BrandSmallSort> brandSmallSortList;
	private List<BrandProduct> brandProductList;
}

















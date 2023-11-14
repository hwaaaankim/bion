package com.dev.BionLifeScienceWar.service.program.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.Product;
import com.dev.BionLifeScienceWar.model.product.ProductInfo;
import com.dev.BionLifeScienceWar.model.product.ProductSpec;
import com.dev.BionLifeScienceWar.model.product.SmallSort;
import com.dev.BionLifeScienceWar.repository.product.BigSortRepository;
import com.dev.BionLifeScienceWar.repository.product.MiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductInfoRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductSpecRepository;
import com.dev.BionLifeScienceWar.repository.product.SmallSortRepository;
import com.dev.BionLifeScienceWar.service.product.ProductService;

@Service
public class ExcelUploadService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	BigSortRepository bigSortRepository;

	@Autowired
	MiddleSortRepository middleSortRepository;

	@Autowired
	SmallSortRepository smallSortRepository;

	@Autowired
	ProductSpecRepository productSpecRepository;

	@Autowired
	ProductInfoRepository productInfoRepository;
	
	@Autowired
	ProductService productService;

	public void uploadExcel(MultipartFile file) {

		String bigSortHeaderNames[] = new String[] { "BIG_SORT_ID", "BIG_SORT_NAME", "BIG_SORT_INDEX" };
		String middleSortHeaderNames[] = new String[] { "MIDDLE_SORT_ID", "MIDDLE_SORT_NAME", "MIDDLE_REFER_ID",
				"MIDDLE_SORT_INDEX" };
		String smallSortHeaderNames[] = new String[] { "SMALL_SORT_ID", "SMALL_SORT_NAME", "SMALL_REFER_ID",
				"SMALL_SORT_INDEX" };
		String productHeaderNames[] = new String[] { "PRODUCT_ID", "PRODUCT_SUBJECT", "PRODUCT_CONTENT",
				"PRODUCT_SUB_CONTENT", "PRODUCT_SIGN", "PRODUCT_REFER_ID", "PRODUCT_MIDDLE_REFER_ID",
				"PRODUCT_BIG_REFER_ID", "PRODUCT_INDEX" };
		String productInfoHeaderNames[] = new String[] { "PRODUCT_ID", "PRODUCT_INFO_TEXT" };
		String productSpecHeaderNames[] = new String[] { "PRODUCT_ID", "PRODUCT_SPEC_SUBJECT", "PRODUCT_SPEC_CONTENT" };
		List<String[]> headers = new ArrayList<String[]>();
		headers.add(bigSortHeaderNames);
		headers.add(middleSortHeaderNames);
		headers.add(smallSortHeaderNames);
		headers.add(productHeaderNames);
		headers.add(productInfoHeaderNames);
		headers.add(productSpecHeaderNames);
		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.submit(() -> {
			try {
				System.out.println("11111111111111111111111111111111111");
				productInfoRepository.deleteAll();
				productSpecRepository.deleteAll();
				productRepository.deleteAll();
			} catch (Exception e) {
				System.out.println(e);
			}
		});


		executorService.submit(() -> {
			try {
				System.out.println("333333333333333333333333333333333333333333");
				String fileExtsn = FilenameUtils.getExtension(file.getOriginalFilename()); // 파일 Original 확장명
				Workbook workbook = null;

				try {

					// 엑셀 97 - 2003 까지는 HSSF(xls), 엑셀 2007 이상은 XSSF(xlsx)
					if (fileExtsn.equals("xls")) {
						workbook = new HSSFWorkbook(file.getInputStream());
					} else {
						workbook = new XSSFWorkbook(file.getInputStream());
					}
					
					Sheet productSheet = workbook.getSheetAt(3);
					for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
						Row row = productSheet.getRow(i);

						if (row != null) {
							Product product = new Product();
							Cell code = row.getCell(1);
							Cell subject = row.getCell(2);
							Cell content = row.getCell(3);
							Cell subContent = row.getCell(4);
							Cell sign = row.getCell(5);
							Cell smallSort = row.getCell(6);
							Cell middleSort = row.getCell(7);
							Cell bigSort = row.getCell(8);
							Long smallValue = (long) smallSort.getNumericCellValue();
							Long middleValue = (long) middleSort.getNumericCellValue();
							Long bigValue = (long) bigSort.getNumericCellValue();
							String codeValue = code + "";
							String subjectValue = subject + "";
							String contentValue = content + "";
							String subContentValue = subContent + "";
							String signStr = sign + "";
							Boolean signValue = true;
							if(signStr.equals("TRUE")) {
								signValue = true;
							}else {
								signValue = false;
							}
							product.setProductCode(codeValue);
							product.setSubject(subjectValue);
							product.setContent(contentValue);
							product.setProductSubContent(subContentValue);
							product.setSign(signValue);
							product.setSmallSort(smallSortRepository.findById(smallValue).get());
							product.setMiddleSort(middleSortRepository.findById(middleValue).get());
							product.setBigSort(bigSortRepository.findById(bigValue).get());
							productService.excelInsert(product);
							
						}
					}
					Sheet productInfoSheet = workbook.getSheetAt(5);
					for (int i = 1; i < productInfoSheet.getPhysicalNumberOfRows(); i++) {
						Row row = productInfoSheet.getRow(i);

						if (row != null) {
							ProductInfo info = new ProductInfo();
							Cell code = row.getCell(0);
							Cell text = row.getCell(1);
							String codeValue = code + "";
							String textValue = text + "";
							info.setProductId(productRepository.findByProductCode(codeValue).get().getId());
							info.setProductInfoText(textValue);
							productInfoRepository.save(info);
						}
					}
					
					Sheet productSpecSheet = workbook.getSheetAt(4);
					for (int i = 1; i < productSpecSheet.getPhysicalNumberOfRows(); i++) {
						Row row = productSpecSheet.getRow(i);

						if (row != null) {
							ProductSpec spec = new ProductSpec();
							Cell code = row.getCell(0);
							Cell subject = row.getCell(1);
							Cell content = row.getCell(2);
							String codeValue = code + "";
							String subjectValue = subject + "";
							String contentValue = content + "";
							spec.setProductSpecSubject(subjectValue);
							spec.setProductSpecContent(contentValue);
							spec.setProductId(productRepository.findByProductCode(codeValue).get().getId());
							productSpecRepository.save(spec);
							
						}
					}

				} catch (Exception e) {
					System.out.println(e.fillInStackTrace());
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		});
		
	}
}

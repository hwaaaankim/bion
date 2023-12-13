package com.dev.BionLifeScienceWar.service.program.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.BionLifeScienceWar.model.product.BigSort;
import com.dev.BionLifeScienceWar.model.product.MiddleSort;
import com.dev.BionLifeScienceWar.model.product.SmallSort;
import com.dev.BionLifeScienceWar.repository.product.BigSortRepository;
import com.dev.BionLifeScienceWar.repository.product.MiddleSortRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductFileRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductImageRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductInfoRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductRepository;
import com.dev.BionLifeScienceWar.repository.product.ProductSpecRepository;
import com.dev.BionLifeScienceWar.repository.product.SmallSortRepository;

@Service
public class CheckService {

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
	ProductImageRepository productImageRepository;

	@Autowired
	ProductFileRepository productFileRepository;

	public List<String> resetExcelCheck(MultipartFile file) throws IOException {
		List<String> result = new ArrayList<String>();

		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		try {
			// 대분류 ID가 DB와 일치하는지 체크
			// 대분류에 빈 ROW가 있는지 체크
			Sheet bigSortSheet = workbook.getSheetAt(0);
			List<Long> bigSortSheetList = new ArrayList<Long>();
			List<Long> bigSortDBList = new ArrayList<Long>();
			List<BigSort> bigSorts = bigSortRepository.findAll();

			for (BigSort big : bigSorts) {
				bigSortDBList.add(big.getId());
			}

			for (int i = 1; i < bigSortSheet.getPhysicalNumberOfRows(); i++) {
				Row row = bigSortSheet.getRow(i);
				Cell bigSortId = row.getCell(0);
				Long bigValue = (long) bigSortId.getNumericCellValue();
				bigSortSheetList.add(bigValue);
			}

			for (int x = 0; x < bigSortSheetList.size(); x++) {
				if (bigSortSheetList.get(x) == 0l || !bigSortDBList.contains(bigSortSheetList.get(x))) {
					result.add("대분류의 " + (x + 2) + "번째 행이 빈 값 혹은 잘못된 숫자 입니다. 새로 RESET용 EXCEL을 다운로드 하여 사용 해 주시기 바랍니다.");
				}
			}

			// 중분류 ID가 DB와 일치하는지 체크
			// 중분류에 빈 ROW가 있는지 체크
			Sheet middleSortSheet = workbook.getSheetAt(1);
			List<Long> middleSortSheetList = new ArrayList<Long>();
			List<Long> middleSortDBList = new ArrayList<Long>();
			List<MiddleSort> middleSorts = middleSortRepository.findAll();

			for (MiddleSort middle : middleSorts) {
				middleSortDBList.add(middle.getId());
			}

			for (int i = 1; i < middleSortSheet.getPhysicalNumberOfRows(); i++) {
				Row row = middleSortSheet.getRow(i);
				Cell middleSortId = row.getCell(0);
				Long middleValue = (long) middleSortId.getNumericCellValue();
				middleSortSheetList.add(middleValue);
			}

			for (int x = 0; x < middleSortSheetList.size(); x++) {
				if (middleSortSheetList.get(x) == 0l || !middleSortDBList.contains(middleSortSheetList.get(x))) {
					result.add("중분류의 " + (x + 2) + "번째 행이 빈 값 혹은 잘못된 숫자 입니다. 새로 RESET용 EXCEL을 다운로드 하여 사용 해 주시기 바랍니다.");
				}
			}

			// 소분류 ID가 DB와 일치하는지 체크
			// 소분류에 빈 ROW가 있는지 체크
			Sheet smallSortSheet = workbook.getSheetAt(2);
			List<Long> smallSortSheetList = new ArrayList<Long>();
			List<Long> smallSortDBList = new ArrayList<Long>();
			List<SmallSort> smallSorts = smallSortRepository.findAll();

			for (SmallSort small : smallSorts) {
				smallSortDBList.add(small.getId());
			}

			for (int i = 1; i < smallSortSheet.getPhysicalNumberOfRows(); i++) {
				Row row = smallSortSheet.getRow(i);
				Cell smallSortId = row.getCell(0);
				Long smallValue = (long) smallSortId.getNumericCellValue();
				smallSortSheetList.add(smallValue);
			}

			for (int x = 0; x < smallSortSheetList.size(); x++) {
				if (smallSortSheetList.get(x) == 0l || !smallSortDBList.contains(smallSortSheetList.get(x))) {
					result.add("소분류의 " + (x + 2) + "번째 행이 빈 값 혹은 잘못된 숫자 입니다. 새로 RESET용 EXCEL을 다운로드 하여 사용 해 주시기 바랍니다.");
				}
			}

			// 제품 SHEET에서 존재하지 않는 대분류 ID 체크
			// 제품 SHEET에서 존재하지 않는 중분류 ID 체크
			// 제품 SHEET에서 존재하지 않는 소분류 ID 체크
			Sheet productSheet = workbook.getSheetAt(3);
			List<Long> productSheetBigSortList = new ArrayList<Long>();

			List<String> productSheetSubjectList = new ArrayList<String>();
			List<String> productSheetContentList = new ArrayList<String>();
			List<String> productSheetSignList = new ArrayList<String>();

			List<Long> productSheetMiddleSortList = new ArrayList<Long>();
			List<Long> productSheetSmallSortList = new ArrayList<Long>();
			List<String> productSheetProductCodeList = new ArrayList<String>();

			for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
				Row row = productSheet.getRow(i);
				Cell productCode = row.getCell(0);
				String productCodeValue = productCode + "";
				productSheetProductCodeList.add(productCodeValue);
			}

			for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
				Row row = productSheet.getRow(i);
				Cell productSubject = row.getCell(1);
				String productSubjectValue = productSubject + "";
				productSheetSubjectList.add(productSubjectValue);
			}

			for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
				Row row = productSheet.getRow(i);
				Cell productContent = row.getCell(2);
				String productContentValue = productContent + "";
				productSheetContentList.add(productContentValue);
			}

			for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
				Row row = productSheet.getRow(i);
				Cell signCode = row.getCell(4);
				String signValue = signCode + "";
				productSheetSignList.add(signValue);
			}

			for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
				Row row = productSheet.getRow(i);
				Cell smallSortId = row.getCell(5);
				Long smallValue = (long) smallSortId.getNumericCellValue();
				productSheetSmallSortList.add(smallValue);
			}

			for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
				Row row = productSheet.getRow(i);
				Cell middleSortId = row.getCell(6);
				Long middleSortIdValue = (long) middleSortId.getNumericCellValue();
				productSheetMiddleSortList.add(middleSortIdValue);
			}

			for (int i = 1; i < productSheet.getPhysicalNumberOfRows(); i++) {
				Row row = productSheet.getRow(i);
				Cell bigSortId = row.getCell(7);
				Long bigSortIdValue = (long) bigSortId.getNumericCellValue();
				productSheetBigSortList.add(bigSortIdValue);
			}

			for (int x = 0; x < productSheetSmallSortList.size(); x++) {
				if (productSheetSmallSortList.get(x) == 0l
						|| !smallSortDBList.contains(productSheetSmallSortList.get(x))) {
					result.add("제품(PRODUCT) 시트 소분류 ID의 " + (x + 2)
							+ "번째 행이 빈 값 혹은 잘못된 숫자 입니다. 새로 RESET용 EXCEL을 다운로드 하여 사용 해 주시기 바랍니다.");
				}
			}

			for (int x = 0; x < productSheetMiddleSortList.size(); x++) {
				if (productSheetMiddleSortList.get(x) == 0l
						|| !middleSortDBList.contains(productSheetMiddleSortList.get(x))) {
					result.add("제품(PRODUCT) 시트 중분류 ID의 " + (x + 2)
							+ "번째 행이 빈 값 혹은 잘못된 숫자 입니다. 새로 RESET용 EXCEL을 다운로드 하여 사용 해 주시기 바랍니다.");
				}
			}

			for (int x = 0; x < productSheetBigSortList.size(); x++) {
				if (productSheetBigSortList.get(x) == 0l || !bigSortDBList.contains(productSheetBigSortList.get(x))) {
					result.add("제품(PRODUCT) 시트 대분류 ID의 " + (x + 2)
							+ "번째 행이 빈 값 혹은 잘못된 숫자 입니다. 새로 RESET용 EXCEL을 다운로드 하여 사용 해 주시기 바랍니다.");
				}
			}

			// PRODUCT SHEET PRODUCT CODE 중복 체크
			for (int x = 0; x < productSheetProductCodeList.size(); x++) {
				for (int y = 0; y < x; y++) {
					if (productSheetProductCodeList.get(x).equals(productSheetProductCodeList.get(y))
							&& !productSheetProductCodeList.get(x).equals("")) {
						result.add((x + 2) + "행과 " + (y + 2) + "행이 중복되었습니다. 제품 코드를 중복되지 않게 입력 해 주세요.");
					}
				}
			}

			// PRODUCT SHEET PRODUCT CODE NULL 체크
			for (int x = 0; x < productSheetProductCodeList.size(); x++) {
				if (productSheetProductCodeList.get(x).equals("")) {
					result.add("제품(PRODUCT) 시트의 PRODUCT CODE의 " + (x + 2) + "행이 빈 값입니다.");
				}
			}

			// PRODUCT SHEET SUBJECT NULL 체크
			for (int x = 0; x < productSheetSubjectList.size(); x++) {
				if (productSheetSubjectList.get(x).equals("")) {
					result.add("제품(PRODUCT) 시트의 PRODUCT SUBJECT의 " + (x + 2) + "행이 빈 값입니다.");
				}
			}

			// PRODUCT SHEET CONTENT NULL 체크
			for (int x = 0; x < productSheetContentList.size(); x++) {
				if (productSheetContentList.get(x).equals("")) {
					result.add("제품(PRODUCT) 시트의 PRODUCT CONTENT의 " + (x + 2) + "행이 빈 값입니다.");
				}
			}

			// PRODUCT SHEET SIGN NULL 체크
			for (int x = 0; x < productSheetSignList.size(); x++) {
				if (productSheetSignList.get(x).equals("") || (!productSheetSignList.get(x).equals("FALSE")
						&& !productSheetSignList.get(x).equals("TRUE"))) {
					result.add("제품(PRODUCT) 시트의 PRODUCT SIGN의 " + (x + 2)
							+ "행이 빈 값 혹은 잘못된 값입니다. TRUE 혹은 FALSE(대문자)로 입력 해 주세요.");
				}
			}

			// SPEC SHEET에서 존재하지 않는 PRODUCT CODE 체크
			Sheet specSheet = workbook.getSheetAt(4);
			List<String> specSheetProductCodeList = new ArrayList<String>();
			for (int i = 1; i < specSheet.getPhysicalNumberOfRows(); i++) {
				Row row = specSheet.getRow(i);
				Cell productCode = row.getCell(0);
				String productCodeValue = productCode + "";
				specSheetProductCodeList.add(productCodeValue);
			}

			for (int x = 0; x < specSheetProductCodeList.size(); x++) {
				if (!productSheetProductCodeList.contains(specSheetProductCodeList.get(x)) || specSheetProductCodeList.get(x).equals("")) {
					result.add("제품 스펙 SHEET의 " + (x+2) + "번째 행의 제품 코드가 빈 값 이거나 제품 SHEET에 존재하지 않습니다. 다시 확인해 주세요.");
				}
			}

			// INFO SHEET에서 존재하지 않는 PRODUCT CODE 체크
			Sheet infoSheet = workbook.getSheetAt(5);
			List<String> infoSheetProductCodeList = new ArrayList<String>();
			for (int i = 1; i < infoSheet.getPhysicalNumberOfRows(); i++) {
				Row row = infoSheet.getRow(i);
				Cell productCode = row.getCell(0);
				String productCodeValue = productCode + "";
				infoSheetProductCodeList.add(productCodeValue);
			}

			for (int x = 0; x < infoSheetProductCodeList.size(); x++) {
				if (!productSheetProductCodeList.contains(infoSheetProductCodeList.get(x)) || infoSheetProductCodeList.get(x).equals("")) {
					result.add("제품 인포 SHEET의 " + (x+2) + "번째 행의 제품 코드가 빈 값 이거나 제품 SHEET에 존재하지 않습니다. 다시 확인해 주세요.");
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		if (result.size() > 0) {
			return result;
		} else {
			result.add("success");
			return result;
		}
	}

	public List<String> addExcelCheck(MultipartFile file) {
		List<String> result = new ArrayList<String>();
		result.add("success");
		return result;
	}

	public List<String> resetZipCheck(MultipartFile file) {
		List<String> result = new ArrayList<String>();
		result.add("success");
		return result;
	}

	public List<String> addZipCheck(MultipartFile file) {
		List<String> result = new ArrayList<String>();
		result.add("success");
		return result;
	}
}

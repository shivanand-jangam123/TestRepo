package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {

  private static Logger log = LoggerFactory.getLogger(ExcelReader.class);

  private FileInputStream fis;
  private XSSFWorkbook workbook;
  private XSSFSheet sheet;
  private Row headerRow;
  private Row testDataRow;

  public ExcelReader(String pathText) {
    String path = pathText;
    try {
      fis = new FileInputStream(path);
      workbook = new XSSFWorkbook(fis);
    } catch (Exception e) {
      log.info(e.getMessage());
    }
  }

  public Map<String, String> getTestDataFromExcel(String sheetName) {
    HashMap<String, String> objDataProvider = null;
    try {
      sheet = workbook.getSheet(sheetName);
      headerRow = sheet.getRow(0);
      testDataRow = sheet.getRow(0);
      int rowIndex = 0;
      objDataProvider = new HashMap<>();
      headerRow = sheet.getRow(rowIndex);
      testDataRow = sheet.getRow(rowIndex + 1);
      int clmNo = 0;
      do {
        String header = "";
        String testData = "";
        header = headerRow.getCell(clmNo).toString().trim();
        if (testDataRow.getCell(clmNo) == null || testDataRow.getCell(clmNo).toString().isEmpty()) {
          testData = "";
        } else {
          testData = testDataRow.getCell(clmNo).toString().trim();
        }
        if (!header.equals("")) {
          objDataProvider.put(header, testData);
        }
        clmNo++;
      } while (clmNo < headerRow.getLastCellNum());
      fis.close();
    } catch (Exception exception) {
      log.info(exception.getMessage());
    }
    return objDataProvider;
  }

  public Map<String, Map<String, String>> getTestDataFromExcel(String sheetName, int rowNum) {
    HashMap<String, String> objDataProvider = null;
    Map<String, Map<String, String>> listOfProducts = new HashMap<>();
    try {
      sheet = workbook.getSheet(sheetName);
      headerRow = sheet.getRow(0);
      testDataRow = sheet.getRow(0);
      int rowIndex = 0;
      headerRow = sheet.getRow(rowIndex);
      for (int i = 1; i <= rowNum; i++) {
        objDataProvider = new HashMap<>();
        testDataRow = sheet.getRow(i);
        int clmNo = 0;
        do {
          String header = "";
          String testData = "";
          header = headerRow.getCell(clmNo).toString().trim();
          if (testDataRow.getCell(clmNo) == null
              || testDataRow.getCell(clmNo).toString().isEmpty()) {
            testData = "";
          } else {
            testData = testDataRow.getCell(clmNo).toString().trim();
          }
          if (!header.equals("")) {
            objDataProvider.put(header, testData);
          }
          clmNo++;
        } while (clmNo < headerRow.getLastCellNum());
        if (objDataProvider != null) {
          listOfProducts.put(Integer.toString(i), objDataProvider);
        }
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    } finally {
      try {
        fis.close();
      } catch (IOException e) {
        log.info(e.getMessage());
      }
    }
    return listOfProducts;
  }
}

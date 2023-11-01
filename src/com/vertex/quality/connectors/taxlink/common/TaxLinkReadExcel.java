package com.vertex.quality.connectors.taxlink.common;

import com.vertex.quality.common.utils.VertexLogger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This page contains all the methods required to read data from Excel Sheet
 *
 * @author Shilpi.Verma
 */

public class TaxLinkReadExcel {
    FileInputStream fi;
    XSSFWorkbook wb;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;
    TreeMap<Object, List<Object>> excelMap = new TreeMap<>();

    /**
     * Constructor to load and read the sheet
     */
    public TaxLinkReadExcel() {
        Path rootPath = Paths
                .get("")
                .toAbsolutePath();
        String textFilePath = File.separator + "resources" + File.separator + "csvfiles" + File.separator + "taxlink";
        Path filePath = Paths.get(rootPath.toString() + textFilePath);
        Path fileCreatePath = filePath.resolve("system_level_po_options.xlsx");
        File src = new File(String.valueOf(fileCreatePath));

        try {
            fi = new FileInputStream(src);
        } catch (Exception e) {
            VertexLogger.log("Excel could not load");
        }

        try {
            wb = new XSSFWorkbook(fi);
        } catch (Exception e1) {
            VertexLogger.log("Cannot Read Excel");
        }
    }

    /**
     * Method to get String/Numeric cell data from current sheet
     *
     * @param list
     */
    public void getCellData(List<Object> list) {
        if (cell.getCellType() == CellType.STRING) {
            list.add(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            cell.getNumericCellValue();
            cell.setCellType(
                    CellType.STRING); //convert cell type of excel from Numeric to String to remove decimal and trailing zeroes
            list.add(cell.getStringCellValue());
        }
    }

    /**
     * Method to get data in current sheet, column wise
     *
     * @param sheetName
     * @return
     */
    public TreeMap<Object, List<Object>> getExcelData(String sheetName) {
        List<Object> proOptNameList = new ArrayList<>();
        List<Object> userProOptNameList = new ArrayList<>();
        List<Object> limitLevelIdList = new ArrayList<>();
        List<Object> proOptValueList = new ArrayList<>();

        sheet = wb.getSheet(sheetName);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) //Since first row is header,hence starting from 1
        {
            row = sheet.getRow(i);
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);

                if (cell.getColumnIndex() == 0) {
                    getCellData(proOptNameList);
                } else if (cell.getColumnIndex() == 1) {
                    getCellData(userProOptNameList);
                } else if (cell.getColumnIndex() == 2) {
                    getCellData(limitLevelIdList);
                } else if (cell.getColumnIndex() == 3) {
                    getCellData(proOptValueList);
                }
            }
        }

        Iterator<Object> proOptNameItr = proOptNameList.iterator();
        Iterator<Object> userProfOptNameItr = userProOptNameList.iterator();
        Iterator<Object> limitLevelIdListItr = limitLevelIdList.iterator();
        Iterator<Object> proOptValueListItr = proOptValueList.iterator();

        while (proOptNameItr.hasNext() && userProfOptNameItr.hasNext() &&
                limitLevelIdListItr.hasNext() &&
                proOptValueListItr.hasNext()) {
            excelMap.put(proOptNameItr.next(),
                    Arrays.asList(userProfOptNameItr.next(), limitLevelIdListItr.next(), proOptValueListItr.next()));
        }
        return excelMap;
    }
}

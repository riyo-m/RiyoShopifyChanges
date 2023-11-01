package com.vertex.quality.connectors.taxlink.common;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * This class consists of methods for writing data from DB to Excel
 *
 * @author Shilpi.Verma
 */
public class TaxLinkWriteExcel {

    XSSFWorkbook wb;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;

    /**
     * Method to write data from DB to excel
     *
     * @param db_query
     * @param sheetName
     * @throws Exception
     */
    public void export(String db_query, String sheetName) throws Exception {

        String excelFilePath = "C:" + File.separator + "connector-quality-java" + File.separator + "ConnectorQuality" + File.separator +
                "resources" + File.separator + "csvfiles" + File.separator + "taxlink" + File.separator +
                "system_level_po_options.xlsx";

        TaxLinkDatabase db = new TaxLinkDatabase();
        db.dbConnection_94(db_query);
        db.res = db.stmt.executeQuery();

        wb = new XSSFWorkbook();
        sheet = wb.createSheet(sheetName);

        writeHeaderLine(db.res, sheet);
        writeDataLines(db.res, sheet);

        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        wb.write(outputStream);
        wb.close();

        db.stmt.close();
    }

    /**
     * Method to write the headers in excel sheet
     *
     * @param result
     * @param sheet
     * @throws SQLException
     */
    private void writeHeaderLine(ResultSet result, XSSFSheet sheet) throws SQLException {
        // write header line containing column names
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        row = sheet.createRow(0);

        // exclude the first column which is the ID field
        for (int i = 2; i <= numberOfColumns; i++) {
            String columnName = metaData.getColumnName(i);
            cell = row.createCell(i - 2);
            cell.setCellValue(columnName);
        }
    }

    /**
     * Method to write rest of the data in excel sheet
     *
     * @param result
     * @param sheet
     * @throws SQLException
     */
    private void writeDataLines(ResultSet result, XSSFSheet sheet)
            throws SQLException {
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        int rowCount = 1;

        while (result.next()) {
            row = sheet.createRow(rowCount++);

            for (int i = 2; i <= numberOfColumns; i++) {
                Object valueObject = result.getObject(i);

                cell = row.createCell(i - 2);

                if (valueObject instanceof Boolean)
                    cell.setCellValue((Boolean) valueObject);
                else if (valueObject instanceof Double)
                    cell.setCellValue((double) valueObject);
                else if (valueObject instanceof Float)
                    cell.setCellValue((float) valueObject);
                else if (valueObject instanceof Integer)
                    cell.setCellValue(String.valueOf(valueObject));
                else if (valueObject instanceof BigDecimal)
                    cell.setCellValue(String.valueOf(valueObject));
                else cell.setCellValue((String) valueObject);

            }

        }
    }
}


package com.vertex.quality.connectors.oraclecloud.common.utils;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.testng.Assert.fail;

/**
 * Representation of an Oracle test template.
 *
 * COERPC-3434
 *
 * @author msalomone
 */
public class Template extends OracleUtilities
{
	private String templateFile;
	private String csvName;
	private String csv2Name;
	private String csv3Name;
	private String csv4Name;
	private String csvZipName;
	private String templateType;
	private String businessUnit;
	private int rowLength =289;
	public static String transactionNumber;
	public static String batchIdNumber;

    public Template(String excelFile, String csvName, String csv2Name, String csv3Name, String csv4Name,
					String csvZipName, String templateType, String businessUnit) {
        this.templateFile = excelFile;
        this.csvName = csvName;
        this.csv2Name = csv2Name;
		this.csv3Name = csv3Name;
		this.csv4Name = csv4Name;
        this.csvZipName = csvZipName;
    	this.templateType = templateType;
    	this.businessUnit = businessUnit;
    }

    // Public Methods

	/**
	 * Updates excel template file, and creates a zipped csv from it.
	 */
    public void generateCsvFromExcel() {
        try {
        	convertOracleTemplate();
        } catch (Exception e) {
            VertexLogger.log("Error when generating CSV from Excel template.", VertexLogLevel.ERROR);
            e.printStackTrace();
			fail("Test failed due to exception.");
        }
    }

    // Private Methods

	/**
	 * Handles template excel file reading, conversion, and writing to a new csv file.
	 *
	 */
	private void convertOracleTemplate() throws ParseException
	{
		InputStream inp = null;
		try {
			File excelFile = getFile(templateFile, "excel");

			if(templateType.equals("AR"))
			{
				updateArTemplate(excelFile);
			}
			else if(templateType.equals("AP"))
			{
				updateApTemplate(excelFile);
			}
			else if(templateType.equals("OM"))
			{
				updateOmTemplate(excelFile);
			}
			else if(templateType.equals("REQUISITION") || templateType.equals("PO"))
			{
				updateRequisitionTemplate(excelFile);
			}

			String csvFileName = this.csvName;
			String csv2FileName = this.csv2Name;
			String csv3FileName = this.csv3Name;
			String csv4FileName = this.csv4Name;
			String zipFileName = this.csvZipName;

			File csv = getFile(csvFileName, "csv");
			File csv2 = getFile(csv2FileName,"csv"); // For templateType = "AP"
			File csv3 = getFile(csv3FileName,"csv"); // For templateType = "OM" || templateType = "REQUISTION"
			File csv4 = getFile(csv4FileName,"csv"); // For templateType = "PO"
			File zip = getFile(zipFileName, "csv");

			inp = new FileInputStream(excelFile.toString());
			Workbook wb = WorkbookFactory.create(inp);

			for(int i=0;i<wb.getNumberOfSheets();i++) {
				if((wb.getSheetAt(i).getSheetName()).equals("RA_INTERFACE_LINES_ALL"))
				{
					echoAsCSV(wb.getSheetAt(i), csv);
					zipFiles(csv, csv2, csv3, csv4, zip);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("AP_INVOICES_INTERFACE"))
				{
					rowLength = 131;
					echoAsCSV(wb.getSheetAt(i), csv);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("AP_INVOICE_LINES_INTERFACE"))
				{
					rowLength = 132;
					echoAsCSV(wb.getSheetAt(i), csv2);
					zipFiles(csv, csv2, csv3, csv4, zip);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("DOO_ORDER_HEADERS_ALL_INT"))
				{
					rowLength = 64;
					echoAsCSV(wb.getSheetAt(i), csv);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("DOO_ORDER_LINES_ALL_INT"))
				{
					rowLength = 148;
					echoAsCSV(wb.getSheetAt(i), csv2);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("DOO_ORDER_ADDRESSES_INT"))
				{
					rowLength = 57;
					echoAsCSV(wb.getSheetAt(i), csv3);
					if(csv4FileName==""){
						zipFiles(csv, csv2, csv3, csv4, zip);
					}
				}
				if((wb.getSheetAt(i).getSheetName()).equals("DOO_PROJECTS_INT"))
				{
					rowLength = 64;
					echoAsCSV(wb.getSheetAt(i), csv4);
					zipFiles(csv, csv2, csv3, csv4, zip);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("POR_REQ_HEADERS_INTERFACE_ALL"))
				{
					rowLength = 71;
					echoAsCSV(wb.getSheetAt(i), csv);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("POR_REQ_LINES_INTERFACE_ALL"))
				{
					rowLength = 127;
					echoAsCSV(wb.getSheetAt(i), csv2);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("POR_REQ_DISTS_INTERFACE_ALL"))
				{
					rowLength = 121;
					echoAsCSV(wb.getSheetAt(i), csv3);
					zipFiles(csv, csv2, csv3, csv4, zip);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("PO_HEADERS_INTERFACE"))
				{
					rowLength = 100;
					echoAsCSV(wb.getSheetAt(i), csv);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("PO_LINES_INTERFACE"))
				{
					rowLength = 102;
					echoAsCSV(wb.getSheetAt(i), csv2);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("PO_LINE_LOCATIONS_INTERFACE"))
				{
					rowLength = 94;
					echoAsCSV(wb.getSheetAt(i), csv3);
				}
				if((wb.getSheetAt(i).getSheetName()).equals("PO_DISTRIBUTIONS_INTERFACE"))
				{
					rowLength = 125;
					echoAsCSV(wb.getSheetAt(i), csv4);
					zipFiles(csv, csv2, csv3, csv4, zip);
				}
			}
		} catch ( FileNotFoundException fnfe) {
			VertexLogger.log("Test template file not found. Please "+
							 "double check the file is in the resource directory.", VertexLogLevel.ERROR);
			fnfe.printStackTrace();
			fail("File not found.");
		} catch ( IOException ioe ) {
			VertexLogger.log("There was an issue reading or writing a file.", VertexLogLevel.ERROR);
			ioe.printStackTrace();
			fail("Test failed due to IO exception.");
		} finally {
			try {
				inp.close();
			} catch (IOException ex) {
				VertexLogger.log("There was an issue closing the file input stream.");
			}
		}
	}

	/**
	 * Handles iterating through excel sheets to convert to a template
	 * to a zipped csv file.
	 *
	 * @param sheet The current Excel sheet to convert to csv.
	 * @param csv The file object containing a path to the newly created csv in the
	 *            convertOracleTemplate method.
	 * @throws IOException
	 */
	private void echoAsCSV(Sheet sheet, File csv) throws IOException {
		ArrayList<String[]> sheetRows = new ArrayList<String[]>();
		if (templateType.equals("AR"))
		{
			generateArCsv(sheet,sheetRows);
		}
		else if (templateType.equals("AP"))
		{
			generateApCsv(sheet,sheetRows);
		}
		else if (templateType.equals("OM") || templateType.equals("REQUISITION") || templateType.equals("PO"))
		{
			generateOmCsv(sheet,sheetRows);
		}
		if ( !sheetRows.isEmpty() )
		{
			writeToCSV(sheetRows, csv);
		}
	}

	/**
	 * Handles iterating through excel sheets to convert to a template
	 * to a zipped csv file.
	 *
	 * @param sheet The current Excel sheet to convert to csv.
	 * @param sheetRows The array list with the contents of the Excel sheet
	 *
	 * @throws IOException
	 */
	public ArrayList<String[]> generateApCsv(Sheet sheet, ArrayList<String[]> sheetRows) {

		boolean beginWriting = false;
		Row row = null;
		Cell currCell = null;
		String item = null;
		String[] rowItems = null;

		DecimalFormat twoDecimals = new DecimalFormat("#.0#");
		DecimalFormat noDecimals = new DecimalFormat("#");
		DecimalFormat twoZeroDec = new DecimalFormat("#.00");
		twoDecimals.setRoundingMode(RoundingMode.DOWN);
		String oneDecPattern = "^-*[0-9]*[.][0-9]";
		String twoDecPattern = "^-*[0-9]*[.][0-9][0-9]";

		for ( int i = 0 ; i <= sheet.getLastRowNum() ; i++ )
		{
			row = sheet.getRow(i);
			rowItems = new String[rowLength];
			if ( row.getCell(0) != null )
			{
				if (i==4)
				{
					beginWriting = true;
				}
			}
			for ( int j = 0 ; j < rowLength ; j++ )
			{
 				currCell = row.getCell(j);
				if(currCell == null) {
					item = "" + ",";
				}
				else if(currCell.toString().contains(".<>/")) {
					item = currCell.toString().replace(',','.');
					item = item.replace("|\"", "|");
					item = item+",";
				}
				else if(currCell.toString().contains(",")) {
					item = "\""+currCell+"\",";
				}
				else if (currCell.getCellType() == CellType.NUMERIC &&
						 Pattern.matches(oneDecPattern, currCell.toString())) {
					if(j == 3)
						item = twoZeroDec.format(currCell.getNumericCellValue()) + ",";
					else if(currCell.toString().endsWith(".0"))
						item = noDecimals.format(currCell.getNumericCellValue()) + ",";
					else
						item = twoZeroDec.format(currCell.getNumericCellValue()) + ",";
				}
				else if (currCell.getCellType() == CellType.NUMERIC &&
						 Pattern.matches(twoDecPattern, currCell.toString())) {
					item = twoDecimals.format(currCell.getNumericCellValue()) + ",";
				}
				else
				{
					item = currCell.toString() + ",";
				}
				rowItems[j] = item;
			}
			if ( beginWriting )
			{
				if(rowItems[0].equals(",") && rowItems[1].equals(",") && rowItems[2].equals(","))
					break;
				rowItems[rowItems.length - 1] = "END";
				sheetRows.add(rowItems);
			}
		}
		return sheetRows;
	}

	/**
	 * Handles iterating through excel sheets to convert to a template
	 * to a zipped csv file.
	 *
	 * @param sheet The current Excel sheet to convert to csv.
	 * @param sheetRows The array list with the contents of the Excel sheet
	 *
	 * @throws IOException
	 */
	public ArrayList<String[]> generateArCsv(Sheet sheet, ArrayList<String[]> sheetRows) throws IOException {

		boolean beginWriting = false;
		Row row = null;
		Cell currCell = null;
		String item = null;
		String[] rowItems = null;


		DecimalFormat twoDecimals = new DecimalFormat("#.0#");
		twoDecimals.setRoundingMode(RoundingMode.DOWN);
		String twoDecPattern = "^-*[0-9]*[.][0-9][0-9][0-9]";

		for ( int i = 0 ; i <= sheet.getLastRowNum() ; i++ )
		{
			row = sheet.getRow(i);
			rowItems = new String[rowLength];
			if ( row.getCell(0) != null )
			{
				if (i==4)
				{
					beginWriting = true;
				}
			}
			for ( int j = 1 ; j < rowLength ; j++ )
			{
				currCell = row.getCell(j);
				if(currCell == null || j ==1) {
					item = "" + ",";
				}
				else if(currCell.toString().contains(".<>/")) {
					item = currCell.toString().replace(',','.');
					item = item.replace("|\"", "|");
					item = item+",";
				}
				else if(currCell.toString().contains(",")) {
					item = "\""+currCell+"\",";
				}
				else if (currCell.getCellType() == CellType.NUMERIC &&
						Pattern.matches(twoDecPattern, currCell.toString())) {
					item = twoDecimals.format(currCell.getNumericCellValue()) + ",";
				}
				else
				{
					item = currCell.toString() + ",";
				}
				rowItems[j] = item;
			}
			if ( beginWriting && rowItems[2] != "," && rowItems[3] != ",")
			{
				rowItems[rowItems.length - 2] = businessUnit+",";
				rowItems[rowItems.length - 1] = "END";
				sheetRows.add(rowItems);
			}
		}
		return sheetRows;
	}

	/**
	 * Handles iterating through excel sheets to convert to a template
	 * to a zipped csv file.
	 *
	 * @param sheet The current Excel sheet to convert to csv.
	 * @param sheetRows The array list with the contents of the Excel sheet
	 *
	 * @throws IOException
	 */
	public ArrayList<String[]> generateOmCsv(Sheet sheet, ArrayList<String[]> sheetRows) throws IOException {

		boolean beginWriting = false;
		Row row = null;
		Cell currCell = null;
		String item = null;
		String[] rowItems = null;

		DecimalFormat df = new DecimalFormat();

		for ( int i = 0 ; i <= sheet.getLastRowNum() ; i++ )
		{
			row = sheet.getRow(i);
			if ( row != null )
			{
				rowItems = new String[rowLength];
				if ( row.getCell(0) != null )
				{
					if ( i == 4 )
					{
						beginWriting = true;
					}
				}
				for ( int j = 0 ; j < rowLength ; j++ )
				{
					currCell = row.getCell(j);
					if ( currCell == null )
					{
						item = "" + ",";
					}
					else if ( currCell.toString().contains(".<>/") )
					{
						item = currCell.toString().replace(',', '.');
						item = item.replace(":\"", ":\"\"");
						item = item.replace("e\"", "e\"\"");
						item = "\"" + item + ",";
					}
					else if ( currCell.toString().contains(",") )
					{
						item = "\"" + currCell + "\",";
					}
					else if ( currCell.getCellType() == CellType.NUMERIC )
					{
						Number num = df.parse(currCell.toString(), new ParsePosition(0));
						item = num.toString() + ",";
					}
					else
					{
						item = currCell.toString() + ",";
					}
					rowItems[j] = item;
				}
				if ( beginWriting )
				{
					if ( rowItems[0].equals(",") && rowItems[1].equals(",") && rowItems[2].equals(",") )
						break;
					rowItems[rowItems.length - 1] = "END";
					sheetRows.add(rowItems);
				}
			}
		}
		return sheetRows;
	}

	/**
	 * Replace an excel's cell value with a new one.
	 * @param cell The cell to replace.
	 * @param regex (String) Regular expression used for cell replacement.
	 * @param newValue New value of cell.
	 * @return newCell The new cell's value as a String.
	 */
	private String getCellReplacement(Cell cell, String regex, String newValue) {
		String newCell = cell.toString().replaceAll(regex, newValue);
		return newCell;
	}

	/**
	 * Change the template's dates to today's date, and update
	 * all transaction numbers.
	 *
	 * @param template Test file to update with current info.
	 * @throws IOException
	 */
	public void updateApTemplate(File template) throws IOException, ParseException
	{
		String txNumPattern = "[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][\\S]*";
		String txSubPattern = "^[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9]";
		String blkTxNumPattern = "[N][O][T][A][X][\\S][B][L][K][\\S][A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][\\S]*";
		String blkTxSubPattern = "[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9]$";
		boolean numMatch = false;
		boolean blkNumMatch = false;

		String today = getTodaysDate("M/dd/yyyy");
		String yyyyMMdd = getTodaysDate("yyyy/MM/dd");
		Date todaysDate = new SimpleDateFormat("M/dd/yyyy").parse(today);
		String dmyDatePattern = "^[0-9][0-9][\\S][A-Z][a-z][a-z][\\S][0-9][0-9][0-9][0-9]";
		String shortDatePattern = "^([0-9]|[0-9][0-9])[/][0-9][0-9][/][0-9][0-9][0-9][0-9]";
		String yyyyDatePattern = "^[0-9][0-9][0-9][0-9][/][0-9][0-9][/][0-9][0-9]";
		boolean dateMatch = false;

		InputStream inp1 = new FileInputStream(template.toString());
		Workbook wb = WorkbookFactory.create(inp1);

		for(int k=0;k<wb.getNumberOfSheets();k++) {
			if((wb.getSheetAt(k).getSheetName()).equals("AP_INVOICES_INTERFACE") ||
					wb.getSheetAt(k).getSheetName().equals("AP_INVOICE_LINES_INTERFACE"))
			{
				VertexLogger.log("Updating "+wb.getSheetAt(k).getSheetName());
				Sheet sheet = wb.getSheetAt(k);
				Sheet newSheet = sheet;
				Row row = null;
				Cell cell = null;

				for ( int i = 0; i <= sheet.getLastRowNum(); i++ )
				{
					row = sheet.getRow(i);
					for ( int j = 0 ; j < row.getLastCellNum() ; j++ ) {
						cell = row.getCell(j);
						if (cell != null)
						{
							numMatch = Pattern.matches(txNumPattern, cell.toString());
							dateMatch = (Pattern.matches(dmyDatePattern, cell.toString())||
										 Pattern.matches(yyyyDatePattern, cell.toString()));
							blkNumMatch = Pattern.matches(blkTxNumPattern, cell.toString());
							if ( numMatch )
							{
								String newTxCell = getCellReplacement(cell, txSubPattern, transactionNumber);
								cell.setCellValue(newTxCell);
							}
							if(dateMatch)
							{
								cell.setCellValue(yyyyMMdd);
							}
							if ( blkNumMatch )
							{
								String newTxCell = getCellReplacement(cell, blkTxSubPattern, transactionNumber);
								cell.setCellValue(newTxCell);
							}
						}
					}
				}
			}
		}
		inp1.close();

		OutputStream out1 = new FileOutputStream(template.toString());
		wb.write(out1);
		out1.close();
	}

	/**
	 * Change the template's dates to today's date, and update
	 * all transaction numbers.
	 *
	 * @param template Test file to update with current info.
	 * @throws IOException
	 */
	public void updateArTemplate(File template) throws IOException, ParseException
	{
		String txNumPattern = "[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][A-Z][\\S]*";
		String txSubPattern = "^[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9]";
		boolean numMatch = false;

		String today = getTodaysDate("M/dd/yyyy");
		String yyyyMMdd = getTodaysDate("yyyy/MM/dd");
		Date todaysDate = new SimpleDateFormat("M/dd/yyyy").parse(today);
		String dmyDatePattern = "^[0-9][0-9][\\S][A-Z][a-z][a-z][\\S][0-9][0-9][0-9][0-9]";
		String shortDatePattern = "^([0-9]|[0-9][0-9])[/][0-9][0-9][/][0-9][0-9][0-9][0-9]";
		String yyyyDatePattern = "^[0-9][0-9][0-9][0-9][/][0-9][0-9][/][0-9][0-9]";
		boolean dateMatch = false;

		InputStream inp1 = new FileInputStream(template.toString());
		Workbook wb = WorkbookFactory.create(inp1);

		for(int k=0;k<wb.getNumberOfSheets();k++) {
			if((wb.getSheetAt(k).getSheetName()).equals("RA_INTERFACE_LINES_ALL"))
			{
				VertexLogger.log("Updating "+wb.getSheetAt(k).getSheetName());
				Sheet sheet = wb.getSheetAt(k);
				Sheet newSheet = sheet;
				Row row = null;
				Cell cell = null;

				for ( int i = 0; i <= sheet.getLastRowNum(); i++ )
				{
					row = sheet.getRow(i);
					for ( int j = 0 ; j < row.getLastCellNum() ; j++ ) {
						cell = row.getCell(j);
						if (cell != null)
						{
							numMatch = Pattern.matches(txNumPattern, cell.toString());
							dateMatch = (Pattern.matches(dmyDatePattern, cell.toString())||
									Pattern.matches(yyyyDatePattern, cell.toString()));
							if ( numMatch )
							{
								String newTxCell = getCellReplacement(cell, txSubPattern, transactionNumber);
								cell.setCellValue(newTxCell);
							}
							if(dateMatch)
							{
								cell.setCellValue(yyyyMMdd);
							}
						}
					}
				}
			}
		}
		inp1.close();

		OutputStream out1 = new FileOutputStream(template.toString());
		wb.write(out1);
		out1.close();
	}

	/**
	 * Change the template's dates to today's date, and update
	 * all transaction numbers.
	 *
	 * @param template Test file to update with current info.
	 * @throws IOException
	 */
	public void updateOmTemplate(File template) throws IOException, ParseException
	{
		String txNumPattern = "[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][\\S]*";
		String txSubPattern = "^[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9]";
		boolean numMatch = false;

		String today = getTodaysDate("M/dd/yyyy");
		String yyyyMMdd = getTodaysDate("yyyy/MM/dd");
		Date todaysDate = new SimpleDateFormat("M/dd/yyyy").parse(today);
		String dmyDatePattern = "^[0-9][0-9][\\S][A-Z][a-z][a-z][\\S][0-9][0-9][0-9][0-9]";
		String shortDatePattern = "^([0-9]|[0-9][0-9])[/][0-9][0-9][/][0-9][0-9][0-9][0-9]";
		String yyyyDatePattern = "^[0-9][0-9][0-9][0-9][/][0-9][0-9][/][0-9][0-9]";
		boolean dateMatch = false;

		InputStream inp1 = new FileInputStream(template.toString());
		Workbook wb = WorkbookFactory.create(inp1);

		for(int k=0;k<wb.getNumberOfSheets();k++) {
			if((wb.getSheetAt(k).getSheetName()).equals("DOO_ORDER_HEADERS_ALL_INT") ||
			   wb.getSheetAt(k).getSheetName().equals("DOO_ORDER_LINES_ALL_INT") ||
			   wb.getSheetAt(k).getSheetName().equals("DOO_ORDER_ADDRESSES_INT") ||
			   wb.getSheetAt(k).getSheetName().equals("DOO_PROJECTS_INT"))
			{
				VertexLogger.log("Updating "+wb.getSheetAt(k).getSheetName());
				Sheet sheet = wb.getSheetAt(k);
				Sheet newSheet = sheet;
				Row row = null;
				Cell cell = null;
				for ( int i = 0; i <= sheet.getLastRowNum(); i++ )
				{
					row = sheet.getRow(i);
					if ( row != null )
					{
						for ( int j = 0 ; j < row.getLastCellNum() ; j++ ) {
							cell = row.getCell(j);
							if (cell != null)
							{
								numMatch = Pattern.matches(txNumPattern, cell.toString());
								dateMatch = (Pattern.matches(dmyDatePattern, cell.toString())||
											 Pattern.matches(yyyyDatePattern, cell.toString()));
								if ( numMatch )
								{
									String newTxCell = getCellReplacement(cell, txSubPattern, transactionNumber);
									VertexLogger.log(newTxCell);
									cell.setCellValue(newTxCell);
								}
								if(dateMatch)
								{
									cell.setCellValue(yyyyMMdd);
								}
							}
						}
					}
				}
			}
		}
		inp1.close();

		OutputStream out1 = new FileOutputStream(template.toString());
		wb.write(out1);
		out1.close();
	}

	/**
	 * Change the template's dates to today's date, and update
	 * all transaction numbers.
	 *
	 *
	 * @param template Test file to update with current info.
	 * @throws IOException
	 */
	public void updateRequisitionTemplate(File template) throws IOException, ParseException
	{
		String txNumPattern = "[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9][\\S]*";
		String txSubPattern = "^[A-Z][A-Z][0-9][0-9][0-9][0-9][0-9][0-9]";
		boolean numMatch = false;

		String today = getTodaysDate("M/dd/yyyy");
		String yyyyMMdd = getTodaysDate("yyyy/MM/dd");
		Date todaysDate = new SimpleDateFormat("M/dd/yyyy").parse(today);
		String dmyDatePattern = "^[0-9][0-9][\\S][A-Z][a-z][a-z][\\S][0-9][0-9][0-9][0-9]";
		String shortDatePattern = "^([0-9]|[0-9][0-9])[/][0-9][0-9][/][0-9][0-9][0-9][0-9]";
		String yyyyDatePattern = "^[0-9][0-9][0-9][0-9][/][0-9][0-9][/][0-9][0-9]";
		boolean dateMatch = false;

		// The batchId gets read in by Java in the below pattern. For example:
		// 2022102711 gets read in as the cell String "2.022102711E9".
		String batchIdPattern = "^[0-9][\\S][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][A-Z][0-9]";
		String batchIdOtherPattern = "^[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
		boolean batchIdMatch = false;

		InputStream inp1 = new FileInputStream(template.toString());
		Workbook wb = WorkbookFactory.create(inp1);

		for(int k=0;k<wb.getNumberOfSheets();k++) {
			if(wb.getSheetAt(k).getSheetName().equals("POR_REQ_HEADERS_INTERFACE_ALL") ||
			   wb.getSheetAt(k).getSheetName().equals("POR_REQ_LINES_INTERFACE_ALL") ||
			   wb.getSheetAt(k).getSheetName().equals("POR_REQ_DISTS_INTERFACE_ALL") ||
			   wb.getSheetAt(k).getSheetName().equals("PO_HEADERS_INTERFACE") ||
			   wb.getSheetAt(k).getSheetName().equals("PO_LINES_INTERFACE") ||
			   wb.getSheetAt(k).getSheetName().equals("PO_LINE_LOCATIONS_INTERFACE") ||
			   wb.getSheetAt(k).getSheetName().equals("PO_DISTRIBUTIONS_INTERFACE"))
			{
				VertexLogger.log("Updating "+wb.getSheetAt(k).getSheetName());
				Sheet sheet = wb.getSheetAt(k);
				Sheet newSheet = sheet;
				Row row = null;
				Cell cell = null;
				int attribute1Column = 0;
				int attribute2Column = 0;
				int attributeRow = 0;

				for ( int i = 0; i <= sheet.getLastRowNum(); i++ )
				{
					row = sheet.getRow(i);
					for ( int j = 0 ; j < sheet.getRow(0).getLastCellNum() ; j++ ) {
						cell = row.getCell(j);
						if (cell != null)
						{
							String cellString = cell.toString();
							numMatch = Pattern.matches(txNumPattern, cell.toString());
							dateMatch = (Pattern.matches(dmyDatePattern, cell.toString())||
										 Pattern.matches(yyyyDatePattern, cell.toString()));
							batchIdMatch = (Pattern.matches(batchIdPattern, cell.toString())||
										Pattern.matches(batchIdOtherPattern, cell.toString()));
							if ( numMatch )
							{
								String newTxCell = getCellReplacement(cell, txSubPattern, transactionNumber);
								cell.setCellValue(newTxCell);
							}
							if(dateMatch)
							{
								cell.setCellValue(yyyyMMdd);
							}
							if(batchIdMatch)
							{
								cell.setCellValue(batchIdNumber);
							}
							if (cell.toString().equals("ATTRIBUTE1"))
							{
								attribute1Column = j;
								attributeRow = i;
							}
							if (cell.toString().equals("ATTRIBUTE2"))
							{
								attribute2Column = j;
							}
						}
						if (j != 0 && j == attribute1Column && i > attributeRow )
						{
							cell = sheet.getRow(i).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							cell.setCellValue(transactionNumber+"_"+(i-3));
						}
						if (j != 0 && j == attribute2Column && i > attributeRow )
						{
							cell = sheet.getRow(i).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							cell.setCellValue((i-3));
						}
					}
				}
			}
		}
		inp1.close();

		OutputStream out1 = new FileOutputStream(template.toString());
		wb.write(out1);
		out1.close();
	}

	/**
	 * Handles the file writing step of converting excel files to
	 * csvs.
	 *
	 * @param items An array list containing all data from a row as pieces.
	 * @param csv The file object containing a path to the newly created csv in the
	 *            convertOracleTemplate method.
	 * @throws IOException
	 */
	private void writeToCSV(ArrayList<String[]> items, File csv) throws IOException {
		FileWriter csvWriter = new FileWriter(csv);
		int index = 0;
		for(String item[]: items) {
			if(item[0] == null)
				index = 1;
			for (int i = index; i < item.length; i++)
				csvWriter.write(item[i]);
			csvWriter.write(System.lineSeparator());
		}
		csvWriter.close();
	}

	/**
	 * Compress files into a .zip format. Method will keep original file in addition
	 * to newly created zip.
	 *
	 * @param oneFile The file object containing a path to the file being zipped.
	 * @param twoFile The second file object containing a path to the file being zipped.
	 * @param aZipFile Name of zip file to zip the object file into.
	 */
	public void zipFiles(File oneFile, File twoFile, File threeFile, File fourFile, File aZipFile) throws IOException
	{
		File theZip = aZipFile;
		ArrayList<String> csvFiles = new ArrayList<String>();
		csvFiles.add(oneFile.toString());
		if(twoFile.toString().contains(".csv"))
		{
			csvFiles.add(twoFile.toString());
		}
		if(threeFile.toString().contains(".csv"))
		{
			csvFiles.add(threeFile.toString());
		}
		if(fourFile.toString().contains(".csv"))
		{
			csvFiles.add(fourFile.toString());
		}
		byte[] bytes = new byte[1024];

		FileOutputStream fos = new FileOutputStream(theZip.toString());
		ZipOutputStream zipOut = new ZipOutputStream(fos);

		for ( int i=0; i<csvFiles.size(); i++)
		{
			File zipFile = new File(csvFiles.get(i));
			FileInputStream fis = new FileInputStream(zipFile);
			ZipEntry zipEntry = new ZipEntry(zipFile.getName());
			zipOut.putNextEntry(zipEntry);

			int length;
			while ((length = fis.read(bytes)) >= 0){
				zipOut.write(bytes, 0, length);
			}

			zipOut.closeEntry();
			fis.close();
		}
		zipOut.close();
		fos.close();
	}
}

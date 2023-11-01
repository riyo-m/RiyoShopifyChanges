package com.vertex.quality.connectors.oraclecloud.keywords.api;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.components.*;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleUtilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.transactionNumber;
import static com.vertex.quality.connectors.oraclecloud.common.utils.Template.batchIdNumber;
import static org.testng.Assert.fail;

/**
 * Keywords for retrieving data from the Oracle server.
 *
 */
public class DataKeywords
{
	public static String transactionPrefix;
	OracleUtilities utilities = new OracleUtilities();

	/**
	 * Retrieve the PTDE file generated on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: ArUsCan_Results
	 */
	public void getPtdeFile(String resultFileName){
		try {
			Thread.sleep(3000);
			SoapWS soapService = new SoapWS();
			soapService.getDocumentsForFilePrefix("GetDocumentsForFilePrefix.xml");
			soapService.writeCsvResponseToFile(resultFileName);
		}catch (Exception ie)
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from AR table.");
		}
	}

	/**
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: ArUsCan_Results
	 */
	public void getArDataFromTables(String resultFileName)
	{
		try
		{
			Thread.sleep(300000);
			SoapWS soapService = new SoapWS("AR");
			soapService.runReport("RunReport.xml");
			soapService.writeXmlResponseToFile(resultFileName);
			soapService.createCsv(resultFileName);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from AR table.");
		}
	}

	/**
	 * NOTE: This method is designed specifically for performance tests
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: ArUsCan_Results
	 */
	public void getArDataFromTablesPerformance(String resultFileName)
	{
		try
		{
			boolean dataCheck = false;
			int counter = 0;
			SoapWS soapService = new SoapWS("AR");
			long startTime = System.currentTimeMillis();
			while(!dataCheck && counter <= 600) {
				Thread.sleep(30000);
				soapService.runReport("RunReport_1000LinesAR.xml");
				soapService.writeXmlResponseToFile(resultFileName);
				soapService.createCsv(resultFileName);
				ResultFile result = new ResultFile(resultFileName + ".csv");
				dataCheck = result.checkData();
				counter++;
			}
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			long minutes = (totalTime/1000)/60;
			long seconds = (totalTime/1000)%60;
			long milliseconds = totalTime%1000;
			VertexLogger.log("Approximate time taken by Oracle to process the data: " +minutes+" Minutes "
					+seconds+" Seconds "+milliseconds+" Milliseconds",	VertexLogLevel.INFO);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from AR table.");
		}
	}

	/**
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: CanadaBu_Results
	 */
	public void getApDataFromTables(String resultFileName)
	{
		try
		{
			Thread.sleep(240000);
			SoapWS soapService = new SoapWS("AP");
			soapService.runReport("RunReport.xml");
			soapService.writeXmlResponseToFile(resultFileName);
			soapService.createCsv(resultFileName);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from AP table.");
		}
	}

	/**
	 * NOTE: This method is designed specifically for performance tests
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: CanadaBu_Results
	 */
	public void getApDataFromTablesPerformance(String resultFileName)
	{
		try
		{
			boolean dataCheck = false;
			int counter = 0;
			SoapWS soapService = new SoapWS("AP");
			long startTime = System.currentTimeMillis();
			while(!dataCheck && counter <= 600) {
				Thread.sleep(30000);
				soapService.runReport("RunReport.xml");
				soapService.writeXmlResponseToFile(resultFileName);
				soapService.createCsv(resultFileName);
				ResultFile result = new ResultFile(resultFileName + ".csv");
				dataCheck = result.checkData();
				counter++;
			}
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			long minutes = (totalTime/1000)/60;
			long seconds = (totalTime/1000)%60;
			long milliseconds = totalTime%1000;
			VertexLogger.log("Approximate time taken by Oracle to process the data: " +minutes+" Minutes "
					+seconds+" Seconds "+milliseconds+" Milliseconds",	VertexLogLevel.INFO);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from AP table.");
		}
	}

	/**
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: CanadaBu_Results
	 */
	public void getApErrorDataFromTables(String resultFileName)
	{
		try
		{
			Thread.sleep(240000);
			SoapWS soapService = new SoapWS("AP");
			soapService.runReport("RunErrorReport.xml");
			soapService.writeXmlResponseToFile(resultFileName);
			soapService.createCsv(resultFileName);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from AP table.");
		}
	}

	/**
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: OmBatch_Results
	 */
	public void getOmDataFromTables(String resultFileName)
	{
		try
		{
			Thread.sleep(240000);
			SoapWS soapService = new SoapWS("OM");
			soapService.runReport("RunOmReport.xml");
			soapService.writeXmlResponseToFile(resultFileName);
			soapService.createCsv(resultFileName);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from OM table.");
		}
	}

	/**
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: OmBatch_Results
	 */
	public String getOmDataFromTables(String resultFileName, String orderNum)
	{
		String transaction_id = "";
		try
		{
			Thread.sleep(240000);
			SoapWS soapService = new SoapWS("OM_OrderNum");
			soapService.runReport("RunOmReport.xml");
			soapService.writeXmlResponseToFile(resultFileName);
			soapService.createCsv(resultFileName);
			File csvFile = utilities.getFile(resultFileName+".csv","csv");
			List<String> selfData = Files.readAllLines(csvFile.toPath());
			if (selfData.get(0).endsWith("DATA_DS")){
				fail("Oracle didn't return any data");
			}
			transaction_id = selfData.get(1).substring(0, selfData.get(1).indexOf(","));
		}
		catch (InterruptedException | IOException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from OM table.");

		}
		return transaction_id;
	}

	/**
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: PoUsCanVatBatch_Results
	 */
	public void getPoDataFromTables(String resultFileName)
	{
		try
		{
			Thread.sleep(240);
			SoapWS soapService = new SoapWS("PO");
			soapService.runReport("RunPoReport.xml");
			soapService.writeXmlResponseToFile(resultFileName);
			soapService.createCsv(resultFileName);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from PO table.");
		}
	}

	/**
	 * NOTE: This method is designed specifically for performance tests
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: CanadaBu_Results
	 */
	public void getPoDataFromTablesPerformance(String resultFileName)
	{
		try
		{
			boolean dataCheck = false;
			int counter = 0;
			SoapWS soapService = new SoapWS("PO");
			long startTime = System.currentTimeMillis();
			while(!dataCheck && counter <= 600) {
				Thread.sleep(30000);
				soapService.runReport("RunPoReport.xml");
				soapService.writeXmlResponseToFile(resultFileName);
				soapService.createCsv(resultFileName);
				ResultFile result = new ResultFile(resultFileName + ".csv");
				dataCheck = result.checkData();
				counter++;
			}
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			long minutes = (totalTime/1000)/60;
			long seconds = (totalTime/1000)%60;
			long milliseconds = totalTime%1000;
			VertexLogger.log("Approximate time taken by Oracle to process the data: " +minutes+" Minutes "
					+seconds+" Seconds "+milliseconds+" Milliseconds",	VertexLogLevel.INFO);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from PO table.");
		}
	}

	/**
	 * Retrieve data from the standard tables on the Oracle server.
	 *
	 * @param resultFileName Name for two output files: the file to store the
	 *                       XML response, and the csv generated using the
	 *                       received transaction data. NOTE Use the following
	 *                       naming convention for this parameter:
	 *                       <<name of test>>_Results
	 *                       Example: RequisitionBatch_Results
	 */
	public void getRequisitionDataFromTables(String resultFileName)
	{
		try
		{
			Thread.sleep(240);
			SoapWS soapService = new SoapWS("REQUISITION");
			soapService.runReport("RunRequisitionReport.xml");
			soapService.writeXmlResponseToFile(resultFileName);
			soapService.createCsv(resultFileName);
		}
		catch ( InterruptedException ie )
		{
			ie.printStackTrace();
			fail("Interrupted exception thrown when waiting to get data from Requisition table.");
		}
	}
	/**
	 * Generate a prefix for template transaction numbers. The prefix is always two randomly
	 * generated letters followed by the current date in mm/dd/yy format.
	 *
	 */
	public void generateTransactionNumberPrefix() {
		OracleUtilities utility = new OracleUtilities();
		String firstInitial = utility.getRandomLetter();
		String lastInitial = utility.getRandomLetter();
		String txDate = utility.getTodaysDate("MMddyy");
		transactionNumber = firstInitial+lastInitial+txDate;
	}

	/**
	 * Generate 10 character numeric String used for PO batch IDs. The generated number
	 * is today's current date in yyyyMMdd format followed by 2 random numbers.
	 *
	 */
	public void generateBatchIdNumber() {
		OracleUtilities utility = new OracleUtilities();
		String firstRand = utility.getRandomDigit();
		String secondRand = utility.getRandomDigit();
		String txDate = utility.getTodaysDate("yyyyMMdd");
		batchIdNumber = txDate+firstRand+secondRand;
	}
}

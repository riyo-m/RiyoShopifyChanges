package com.vertex.quality.connectors.oraclecloud.common.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.utils.OracleUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.fail;
import org.testng.asserts.SoftAssert;

public class ResultFile extends OracleUtilities
{
	public String file;
	public String type = "";
	private HashMap<String, String> lineDifferences = new HashMap<>();

	public ResultFile(String fileObj) {
		this.file = fileObj;
	}
	public ResultFile(String fileObj, String finType){
		this.file = fileObj;
		this.type = finType;
	}

	public void compare(String otherFile)
	{
		try
		{
			String deltaName = file.replace("_Results", "_Delta");
			File self = getFile(file, "csv");
			File other = getFile(otherFile, "csv");
			File delta = getFile(deltaName, "csv");

			List<String> selfData = Files.readAllLines(self.toPath());
			List<String> otherData = Files.readAllLines(other.toPath());
			List<String> differences = new ArrayList<>();
			boolean match = false;
			boolean diff = false;

			HashMap<String, String> currSelf = new HashMap<>();
			HashMap<String, String> currOther = new HashMap<>();
			String[] keys = selfData.get(0).split(",");

			for (int k = 0; k <= keys.length-1; k++) {
				currSelf.put(keys[k], "");
				currOther.put(keys[k], "");
			}

			ArrayList<String> primaryKeys = new ArrayList<>();
			if(type.equals("OM"))
			{
				primaryKeys.add("COMMENTS");
			}
			else if(type.equals("REQUISITION") || type.equals("PO"))
			{
				primaryKeys.add("ATTRIBUTE2");
			}
			else
			{
				primaryKeys.add("LINE_NUMBER");
				primaryKeys.add("TAX_REGIME_CODE");
				primaryKeys.add("TAX");
				primaryKeys.add("TAX_JURISDICTION_CODE");
				primaryKeys.add("ATTRIBUTE11");
				primaryKeys.add("ATTRIBUTE13");
			}
			String currentSelfLine = "";
			String currentOtherLine = "";

			for (int i = 1; i < selfData.size(); i++) {
				match = false;
				currentSelfLine = selfData.get(i);
				String[] selfSplit = currentSelfLine.split(",");
				for(int l = 0; l <= selfSplit.length-1; l++) {
					currSelf.put(keys[l], selfSplit[l]);
				}
				for (int j = 1; j < otherData.size(); j++) {
					currentOtherLine = otherData.get(j);
					String[] otherSplit = currentOtherLine.split(",");
					for(int p = 0; p <= otherSplit.length-1; p++) {
						currOther.put(keys[p], otherSplit[p]);
					}
					if(keysMatch(currSelf, currOther, primaryKeys)) {
						if(type.equals("OM") || type.equals("REQUISITION") || type.equals("PO"))
							diff = compareColumn(currSelf, currOther);
						else
							diff = compareLine(currSelf,currOther);
						if(!diff) {
							match = true;
						}
						break;
					}
				}
				if(!match) {
					differences.add("Expected: "+currentOtherLine+"\nActual: "+currentSelfLine+
									"\n"+lineDifferences.values().toString()+"\n");
					lineDifferences.clear();
				}
			}
			if (!checkData()){
				VertexLogger.log("Oracle did not return any data. It may still be calculating taxes. " +
						"\n Please check to ensure all import and processing jobs completed successfully in the ERP.",
						VertexLogLevel.ERROR);
				FileWriter writer = new FileWriter(delta.getPath());
				for(String item : differences) {
					writer.write(item+"\n");
				}
				writer.close();
				fail("Test failed baseline comparison. No data in result file.");
			}
			else if(differences.isEmpty()) {
				VertexLogger.log("No differences between "+self.getName()+
								  " and "+other.getName(), VertexLogLevel.INFO);
				delta.delete();
			}
			else {
				VertexLogger.log(differences.size()+" differences found between "+self.getName()+
								 " and "+other.getName(), VertexLogLevel.ERROR);
				FileWriter writer = new FileWriter(delta.getPath());
				for(String item : differences) {
					writer.write(item+"\n");
				}
				writer.close();
				fail("Test failed baseline comparison.");
			}
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
			fail("Test failed when opening files during comparison.");
		}
	}

	/**
	 * Verifies that Oracle returned errors for negative scenarios that
	 * result in holds being applied to invalid invoices.
	 *
	 * @param otherFile
	 */
	public void compareErrors(String otherFile)
	{
		try
		{
			File self = getFile(file, "csv");
			File other = getFile(otherFile, "csv");

			List<String> selfData = Files.readAllLines(self.toPath());
			List<String> otherData = Files.readAllLines(other.toPath());

			SoftAssert softAssert = new SoftAssert();

			int expectedTrxHeaderAmtCount = 0;
			int expectedBadAddressCount = 0;
			int expectedAssessableValueCount = 0;

			for(int i=0; i<otherData.size(); i++)
			{
				if(otherData.get(i).contains("Large number detected for TrxHeaderAmt"))
					expectedTrxHeaderAmtCount++;
				if(otherData.get(i).contains("No tax areas were found during the lookup"))
					expectedBadAddressCount++;
				if(otherData.get(i).contains("Large number detected for AssessableValue"))
					expectedAssessableValueCount++;
			}

			int actualTrxHeaderAmtCount = 0;
			int actualBadAddressCount = 0;
			int actualAssessableValueCount = 0;

			for(int i=0; i<selfData.size(); i++)
			{
				if(selfData.get(i).contains("Large number detected for TrxHeaderAmt"))
					actualTrxHeaderAmtCount++;
				if(selfData.get(i).contains("No tax areas were found during the lookup"))
					actualBadAddressCount++;
				if(selfData.get(i).contains("Large number detected for AssessableValue"))
					actualAssessableValueCount++;
			}

			if (selfData.get(selfData.size()-1).endsWith("%") ){
				VertexLogger.log("Oracle did not return any errors." +
								"\n Please check to ensure all jobs have completed successfully in the ERP.",
						VertexLogLevel.ERROR);
				fail("Test failed error comparison.");
			}
			else if (selfData.size() != otherData.size()){
				VertexLogger.log("There is a difference between the number of expected errors and actual errors"
								+ "\n Please check to ensure correct template is used for the test",
						VertexLogLevel.ERROR);

				if(selfData.size() > otherData.size())
					VertexLogger.log("More errors were found than expected", VertexLogLevel.ERROR);
				else
					VertexLogger.log("Less errors were found than expected", VertexLogLevel.ERROR);

				VertexLogger.log("A total of "+expectedTrxHeaderAmtCount+ " TrxHeaderAmt Errors, "
								+expectedAssessableValueCount+ " AssessableValue Errors, "
								+expectedBadAddressCount+ "Bad Address Errors are expected", VertexLogLevel.INFO);
				VertexLogger.log("A total of "+actualTrxHeaderAmtCount+ " TrxHeaderAmt Errors, "
						+actualAssessableValueCount+ " AssessableValue Errors, "
						+actualBadAddressCount+ "Bad Address Errors were found", VertexLogLevel.ERROR);

				softAssert.assertTrue(false,"Test failed error comparison.");
			}
			else {
				VertexLogger.log("A total of "+expectedTrxHeaderAmtCount+ " TrxHeaderAmt Errors, "
						+expectedAssessableValueCount+ " AssessableValue Errors, "
						+expectedBadAddressCount+ "Bad Address Errors are expected", VertexLogLevel.INFO);
				VertexLogger.log("A total of "+actualTrxHeaderAmtCount+ " TrxHeaderAmt Errors, "
						+actualAssessableValueCount+ " AssessableValue Errors, "
						+actualBadAddressCount+ "Bad Address Errors were found", VertexLogLevel.INFO);
				if(expectedTrxHeaderAmtCount == actualTrxHeaderAmtCount &&
				   expectedAssessableValueCount == actualAssessableValueCount &&
				   expectedBadAddressCount == actualBadAddressCount)
					VertexLogger.log("Expected errors were found", VertexLogLevel.INFO);
				else {
					VertexLogger.log("Errors found are different than expected", VertexLogLevel.ERROR);
					softAssert.assertTrue(false,"Test failed error comparison.");
				}
			}
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
			fail("Test failed when opening error files during comparison.");
		}
	}

	public boolean keysMatch(HashMap<String, String> currSelf, HashMap<String, String> currOther, ArrayList<String> primaryKeys)
	{
		boolean diff = false;
		boolean matchFound = true;
		Boolean[] matches = new Boolean[primaryKeys.size()];

		for(int i = 0; i < primaryKeys.size(); i++)
		{
			matches[i] = false;
			for(int j= 0; j < primaryKeys.size(); j++)
			{
				if(currSelf.get(primaryKeys.get(i)).equals(currOther.get(primaryKeys.get(j))))
				{
						matches[i] = true;
				}
			}
		}

		for(int b = 0; b < matches.length; b++)
		{
			if (matches[b] == false)
				matchFound = false;
		}

		return matchFound;
	}

	private boolean compareColumn(HashMap<String, String> self, HashMap<String, String> other) {
		String txnum = "TRX_NUMBER";
		String difference = "";
		boolean differenceFound = false;
		HashMap<String, String> comparisonKeys = new HashMap<>();
		if(type.equals("OM"))
		{
			comparisonKeys.put("COMMENTS", "");
			comparisonKeys.put("TOTAL_AMOUNT", "");
		}
		else if (type.equals("REQUISITION") || type.equals("PO"))
		{
			comparisonKeys.put("ATTRIBUTE2", "");
			comparisonKeys.put("NONRECOVERABLE_TAX", "");
		}
		if(self.size() > other.size())
		{
			for( String key : comparisonKeys.keySet()) {
				if(!key.equals(txnum))    // Ignore specified keys
				{
					if ( self
						.get(key)
						.equals(other.get(key)) )
					{
						lineDifferences.put(key, "");
					}
					else
					{
						difference = "Baseline: " + other.get(key) + " Result: " + self.get(key);
						lineDifferences.put(key, difference);
						differenceFound = true;
					}
				}
			}
		}
		else {
			for(String key : comparisonKeys.keySet()) {
				if(!key.contains(txnum))    // Ignore specified keys
				{
					if ( other
						.get(key)
						.equals(self.get(key)))
					{
						lineDifferences.put(key, "");
					}
					else
					{
						difference = "Baseline: " + other.get(key) + " Result: " + self.get(key);
						lineDifferences.put(key, difference);
						differenceFound = true;
					}
				}
			}
		}
		lineDifferences.put(txnum,"");
		return differenceFound;
	}

	private boolean compareLine(HashMap<String, String> self, HashMap<String, String> other) {
		String txnum = "TRX_NUMBER";
		String difference = "";
		boolean differenceFound = false;

		if(self.size() > other.size())
		{
			for( String key : self.keySet()) {
				if(!key.equals(txnum))    // Ignore specified keys
				{
					if ( self
						.get(key)
						.equals(other.get(key)) )
					{
						lineDifferences.put(key, "");
					}
					else
					{
						difference = "Baseline: " + other.get(key) + " Result: " + self.get(key);
						lineDifferences.put(key, difference);
						differenceFound = true;
					}
				}
			}
		}
		else {
			for(String key : other.keySet()) {
				if(!key.contains(txnum))    // Ignore specified keys
				{
					if ( other
						.get(key)
						.equals(self.get(key)))
					{
						lineDifferences.put(key, "");
					}
					else
					{
						difference = "Baseline: " + other.get(key) + " Result: " + self.get(key);
						lineDifferences.put(key, difference);
						differenceFound = true;
					}
				}
			}
		}
		lineDifferences.put(txnum,"");
		return differenceFound;
	}

	/**
	 * Checks whether Oracle returns data or DATA_DS (no data).
	 *
	 * @return whether data exists or not
	 */
	public boolean checkData()
	{
		boolean dataPresent = true;
		try {
			File self = getFile(file, "csv");
			List<String> selfData = Files.readAllLines(self.toPath());
			if (selfData.get(0).endsWith("DATA_DS")){
				dataPresent = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail("Test failed when opening files during comparison.");
		}
		return dataPresent;
	}
}

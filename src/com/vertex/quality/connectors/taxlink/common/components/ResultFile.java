package com.vertex.quality.connectors.taxlink.common.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.common.utils.TaxlinkAPIUtilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * A container for methods that handles comparing CSV files
 *
 * @author Aman Jain
 */
public class ResultFile extends TaxlinkAPIUtilities
{
	public String resultFile;
	public String type = "";
	private HashMap<String, String> lineDifferences = new HashMap<>();

	public ResultFile( String fileObj )
	{
		this.resultFile = fileObj;
	}

	/**
	 * Compare the data present in csv file for a transaction against expected csv file
	 *
	 * @param baselineFile baselineFileName
	 */
	public void compareData( String baselineFile )
	{
		try
		{
			VertexLogger.log("Initiating CSV file comparison");
			String deltaFileName = resultFile.replace("_Results", "_Delta");
			File self = getFile(resultFile, "csv");
			File other = getFile(baselineFile, "csv");
			File delta = getFile(deltaFileName, "csv");

			List<String> resultFileData = Files.readAllLines(self.toPath());
			List<String> baselineFileData = Files.readAllLines(other.toPath());

			List<String> differences = new ArrayList<>();
			boolean match = false;
			boolean diff = false;

			HashMap<String, String> currSelf = new HashMap<>();
			HashMap<String, String> currOther = new HashMap<>();

			String[] keys = resultFileData
				.get(0)
				.split(",");

			for ( int k = 0 ; k <= keys.length - 1 ; k++ )
			{
				currSelf.put(keys[k], "");
				currOther.put(keys[k], "");
			}

			ArrayList<String> primaryKeys = new ArrayList<>();
			if ( type.equals("OM") )
			{
				primaryKeys.add("COMMENTS");
			}
			else if ( type.equals("REQUISITION") || type.equals("PO") )
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

			for ( int i = 1 ; i < resultFileData.size() ; i++ )
			{
				match = false;
				currentSelfLine = resultFileData.get(i);
				String[] selfSplit = currentSelfLine.split(",");
				for ( int l = 0 ; l <= selfSplit.length - 1 ; l++ )
				{
					currSelf.put(keys[l], selfSplit[l]);
				}
				for ( int j = 1 ; j < baselineFileData.size() ; j++ )
				{
					currentOtherLine = baselineFileData.get(j);
					String[] otherSplit = currentOtherLine.split(",");
					for ( int p = 0 ; p <= otherSplit.length - 1 ; p++ )
					{
						currOther.put(keys[p], otherSplit[p]);
					}
					if ( keysMatch(currSelf, currOther, primaryKeys) )
					{
						if ( type.equals("OM") || type.equals("REQUISITION") || type.equals("PO") )
						{
							diff = compareColumn(currSelf, currOther);
						}
						else
						{
							diff = compareLine(currSelf, currOther, primaryKeys);
						}
						if ( !diff )
						{
							match = true;
						}
						break;
					}
				}
				if ( !match )
				{
					differences.add("Expected: " + currentOtherLine + "\nActual: " + currentSelfLine + "\n" +
									lineDifferences
										.values()
										.toString() + "\n");
					lineDifferences.clear();
				}
			}
			if ( !checkData() )
			{
				VertexLogger.log("Application did not return any data. It may still be calculating taxes. " +
								 "\n Please check to ensure all import and processing jobs completed successfully in the ERP.",
					VertexLogLevel.ERROR);
				FileWriter writer = new FileWriter(delta.getPath());
				for ( String item : differences )
				{
					writer.write(item + "\n");
				}
				writer.close();
				fail("Test failed baseline comparison. No data in result file.");
			}
			else if ( differences.isEmpty() )
			{
				VertexLogger.log("No differences between " + self.getName() + " and " + other.getName(),
					VertexLogLevel.INFO);
				delta.delete();
			}
			else
			{
				VertexLogger.log(
					differences.size() + " differences found between " + self.getName() + " and " + other.getName(),
					VertexLogLevel.ERROR);
				FileWriter writer = new FileWriter(delta.getPath());
				for ( String item : differences )
				{
					writer.write(item + "\n");
				}
				writer.close();
				fail("Test failed baseline comparison.");
			}
		}
		catch ( IOException ioe )
		{
			ioe.printStackTrace();
			fail("Test failed when opening files during comparison.");
		}
	}

	/**
	 * Matches the data of respective CSV files
	 *
	 * @param currSelf
	 * @param currOther
	 * @param primaryKeys
	 *
	 * @return matchFound
	 */
	public boolean keysMatch( HashMap<String, String> currSelf, HashMap<String, String> currOther,
		ArrayList<String> primaryKeys )
	{
		boolean matchFound = true;
		Boolean[] matches = new Boolean[primaryKeys.size()];

		for ( int i = 0 ; i < primaryKeys.size() ; i++ )
		{
			matches[i] = false;
			for ( int j = 0 ; j < primaryKeys.size() ; j++ )
			{
				if ( currSelf
					.get(primaryKeys.get(i))
					.equals(currOther.get(primaryKeys.get(j))) )
				{
					matches[i] = true;
				}
			}
		}

		for ( int b = 0 ; b < matches.length ; b++ )
		{
			if ( matches[b] == false )
			{
				matchFound = false;
			}
		}

		return matchFound;
	}

	/**
	 * Compares the data of respective columns and captures the differences found
	 *
	 * @param self
	 * @param other
	 *
	 * @return differenceFound flag
	 */
	private boolean compareColumn( HashMap<String, String> self, HashMap<String, String> other )
	{
		String txnum = "TRX_NUMBER";
		String difference = "";
		boolean differenceFound = false;
		HashMap<String, String> comparisonKeys = new HashMap<>();
		if ( type.equals("OM") )
		{
			comparisonKeys.put("COMMENTS", "");
			comparisonKeys.put("TOTAL_AMOUNT", "");
		}
		else if ( type.equals("REQUISITION") || type.equals("PO") )
		{
			comparisonKeys.put("ATTRIBUTE2", "");
			comparisonKeys.put("NONRECOVERABLE_TAX", "");
		}
		if ( self.size() > other.size() )
		{
			for ( String key : comparisonKeys.keySet() )
			{
				if ( !key.equals(txnum) )    // Ignore specified keys
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
		else
		{
			for ( String key : comparisonKeys.keySet() )
			{
				if ( !key.contains(txnum) )    // Ignore specified keys
				{
					if ( other
						.get(key)
						.equals(self.get(key)) )
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
		lineDifferences.put(txnum, "");
		return differenceFound;
	}

	/**
	 * Compares the data of respective lines and captures the differences found
	 *
	 * @param self
	 * @param other
	 * @param primaryKeys
	 *
	 * @return differenceFound flag
	 */
	private boolean compareLine( HashMap<String, String> self, HashMap<String, String> other,
		ArrayList<String> primaryKeys )
	{
		String txnum = "TRX_NUMBER";
		String difference = "";
		boolean differenceFound = false;

		if ( self.size() > other.size() )
		{
			for ( String key : self.keySet() )
			{
				if ( !key.equals(txnum) )    // Ignore specified keys
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
		else
		{
			for ( String key : other.keySet() )
			{
				if ( !key.contains(txnum) )    // Ignore specified keys
				{
					if ( other
						.get(key)
						.equals(self.get(key)) )
					{
						lineDifferences.put(key, "");
					}
					else
					{
						String trxNumSuffixOther = other
							.get(txnum)
							.substring(8);

						String trxNumSuffixSelf = self
							.get(txnum)
							.substring(8);

						if ( primaryKeys.contains(key) && trxNumSuffixSelf.equalsIgnoreCase(trxNumSuffixOther) )
						{
							difference = "Baseline: " + other.get(key) + " Result: " + self.get(key);
							lineDifferences.put(key, difference);
							differenceFound = true;
						}
					}
				}
			}
		}
		lineDifferences.put(txnum, "");
		return differenceFound;
	}

	/**
	 * Checks whether Oracle returns data or DATA_DS (no data).
	 *
	 * @return whether data exists or not
	 */
	public boolean checkData( )
	{
		boolean dataPresent = true;
		try
		{
			File self = getFile(resultFile, "csv");
			List<String> selfData = Files.readAllLines(self.toPath());
			if ( selfData
				.get(0)
				.endsWith("DATA_DS") )
			{
				dataPresent = false;
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			fail("Test failed when opening files during comparison.");
		}
		return dataPresent;
	}
}

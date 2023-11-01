package com.vertex.quality.connectors.taxlink.api.keywords;

import com.vertex.quality.connectors.taxlink.common.components.ResultFile;

/**
 * Keywords for interacting with testing csv files.
 *
 * @author Aman.Jain
 */
public class CsvHandlerKeywords
{

	/**
	 * Compare the results of transaction processing against
	 * an expected baseline file. Both files are in csv format.
	 *
	 * @param baselineCsvFileName Name of expected CSV file in resource directory.
	 * @param resultCsvFileName   Name of actual CSV file produced from test.
	 */
	public void compareCSVFileData( String resultCsvFileName, String baselineCsvFileName )
	{
		ResultFile resultFile = new ResultFile(resultCsvFileName);
		resultFile.compareData(baselineCsvFileName);
	}
}

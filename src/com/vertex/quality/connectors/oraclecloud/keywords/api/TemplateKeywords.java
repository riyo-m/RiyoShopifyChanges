package com.vertex.quality.connectors.oraclecloud.keywords.api;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.common.components.ResultFile;
import com.vertex.quality.connectors.oraclecloud.common.utils.Template;

import java.io.IOException;

import static org.testng.Assert.fail;

/**
 * Keywords for interacting with testing templates.
 */
public class TemplateKeywords
{
	/**
	 * Create a csv file using an existing Excel template.
	 * @throws Exception
	 */
	public void generateCsvFromTemplate(String templateFile, String csvName, String csv2Name, String csv3Name, String csv4Name, String csvZipName,
										String templateType, String businessUnit) {
		try
		{
			Template template = new Template(templateFile, csvName, csv2Name, csv3Name, csv4Name, csvZipName,
					templateType, businessUnit);
			template.generateCsvFromExcel();
		} catch ( Exception e) {
			VertexLogger.log("There was an issue when generating a csv from "+
							 "the template. Please check the resource file supplied is "+
							 "in the resource directory, and if the file is valid.", VertexLogLevel.ERROR);
			e.printStackTrace();
			fail("Test failed due to exception.");
		}
	}

	/**
	 * Compare the results of AR transaction processing against an
	 * expected baseline file. Both files are in csv format.
	 *
	 * @param expected Name of expected CSV file in resource directory.
	 * @param actual Name of actual CSV file produced from test.
	 */
	public void compareArResults(String expected, String actual) {
		ResultFile result = new ResultFile(actual);
		result.compare(expected);
	}

	/**
	 * Compare the results of AP transaction processing against
	 * an expected baseline file. Both files are in csv format.
	 *
	 * @param expected Name of expected CSV file in resource directory.
	 * @param actual Name of actual CSV file produced from test.
	 */
	public void compareApResults(String expected, String actual) {
		ResultFile result = new ResultFile(actual);
		result.compare(expected);
	}

	/**
	 * Compare the results of AP transaction processing against
	 * an expected baseline file. Both files are in csv format.
	 *
	 * @param expected Name of expected CSV file in resource directory.
	 * @param actual Name of actual CSV file produced from test.
	 */
	public void compareApErrorResults(String expected, String actual) {
		ResultFile result = new ResultFile(actual);
		result.compareErrors(expected);
	}

	/**
	 * Compare the results of OM transaction processing against
	 * an expected baseline file. Both files are in csv format.
	 *
	 * @param expected Name of expected CSV file in resource directory.
	 * @param actual Name of actual CSV file produced from test.
	 */
	public void compareOmResults(String expected, String actual) {
		ResultFile result = new ResultFile(actual, "OM");
		result.compare(expected);
	}

	/**
	 * Compare the results of REQUISITION transaction processing against
	 * an expected baseline file. Both files are in csv format.
	 *
	 * @param expected Name of expected CSV file in resource directory.
	 * @param actual Name of actual CSV file produced from test.
	 */
	public void comparePoResults(String expected, String actual) {
		ResultFile result = new ResultFile(actual, "PO");
		result.compare(expected);
	}

	/**
	 * Compare the results of REQUISITION transaction processing against
	 * an expected baseline file. Both files are in csv format.
	 *
	 * @param expected Name of expected CSV file in resource directory.
	 * @param actual Name of actual CSV file produced from test.
	 */
	public void compareRequisitionResults(String expected, String actual) {
		ResultFile result = new ResultFile(actual, "REQUISITION");
		result.compare(expected);
	}

}

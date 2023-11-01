package com.vertex.quality.connectors.dummyconnector;

import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import org.testng.annotations.Test;


public class dummyUITests extends VertexUIBaseTest
{

	protected String url = "https://www.google.com/";
	
	/**
	 * loads google page
	 */
	protected void openGoogle( )
	{
		VertexLogger.log("start open Google");
		driver.get(url);

		VertexLogger.log(driver.getTitle());
		VertexLogger.log("end open Google");
	}
	
	@Test(groups = { "dummyUITest" })
	public void dummyUITestOne( )
	{
		openGoogle();
	}
}
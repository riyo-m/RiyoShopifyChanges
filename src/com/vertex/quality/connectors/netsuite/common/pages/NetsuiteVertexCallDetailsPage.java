package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteTransactions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the Vertex Call Details page
 *
 * @author hho
 */
public class NetsuiteVertexCallDetailsPage extends NetsuitePage
{
	protected By backButtonLocator = By.id("_back");
	protected String vertexRequestLabelId = "custrecord_request_vt_fs_lbl";
	protected String vertexResponseLabelId = "custrecord_response_vt_fs_lbl";
	protected By parentXMLContainerLocator = By.className("uir-long-text");

	public NetsuiteVertexCallDetailsPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Goes back to the previous page
	 *
	 * @return the previous page
	 */
	public <T extends NetsuiteTransactions> T goBack( )
	{
		click.clickElement(backButtonLocator);
		return initializePageObject(getPageClass(getPageTitle()));
	}

	/**
	 * Checks if the request contains a certain value
	 *
	 * @param value the value to check for
	 *
	 * @return if the request contains a certain value
	 */
	public boolean doesVertexRequestContains( String value )
	{
		return doesXMLContain(vertexRequestLabelId, value);
	}

	/**
	 * Checks if the response contains a certain value
	 *
	 * @param value the value to check for
	 *
	 * @return if the response contains a certain value
	 */
	public boolean doesVertexResponseContains( String value )
	{
		return doesXMLContain(vertexResponseLabelId, value);
	}

	/**
	 * Checks if the request or response contains a value
	 *
	 * @param xmlLabelId the specified request or response label id locator
	 * @param value      the value to check for
	 *
	 * @return if it contains the value
	 */
	private boolean doesXMLContain( String xmlLabelId, String value )
	{
		WebElement xmlElement = getTextUnderLabel(parentXMLContainerLocator, xmlLabelId);
		String xmlText = xmlElement.getText();
		boolean doesXMLContains = (xmlText != null) && xmlText.contains(value);
		return doesXMLContains;
	}
}

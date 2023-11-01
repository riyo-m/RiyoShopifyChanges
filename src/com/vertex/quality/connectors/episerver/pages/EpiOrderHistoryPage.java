package com.vertex.quality.connectors.episerver.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EpiOrderHistoryPage extends VertexPage
{
	public EpiOrderHistoryPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to Validate the Presence of OrderNumber on Order History
	 * Page
	 */
	public void validateOrderNumberPresence( String ordernumber )
	{
		By ORDER_ID_LOC = By.xpath(
			String.format("//*[@class='row section-box']/*[h3[text()='Order ID: '%s'']]", ordernumber));
		if ( element.isElementDisplayed(ORDER_ID_LOC) )
		{
			VertexLogger.log("OrderID is displayed in Order History page");
		}
		else
		{
			VertexLogger.log("OrderID is NOT displayed in Order History page", VertexLogLevel.ERROR);
		}
	}

	/**
	 * This method is used to get the price of the OrderNumber on Order History Page
	 */
	public String getPrice( String ordernumber )
	{
		By ORDER_ID_LOC = By.xpath(
			String.format("//*[@class='row section-box']/*[h3[text()='Order ID: '%s'']]", ordernumber));
		String price = attribute.getElementAttribute(ORDER_ID_LOC, "text");
		return price;
	}
}

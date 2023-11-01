package com.vertex.quality.connectors.hybris.pages.backoffice;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents to validate the Tax, Total values on Orders page against the order placed from
 * Electronics Store page
 *
 * @author Nagaraju Gampa
 */
public class HybrisBOOrdersPage extends HybrisBasePage
{
	public HybrisBOOrdersPage( WebDriver driver )
	{
		super(driver);
	}

	protected By ORDERNO_SEARCH = By.cssSelector(".yw-textsearch-searchbox.z-bandbox>input");
	protected By SEARCH_BUTTON = By.cssSelector("[class*='searchbutton']");
	//	protected By POSITIONSPRICES_TAB = By.xpath("//span[contains(text(),'Positions and Prices')]");
	protected By ORDER_TABS_CONTAINER = By.cssSelector("yw-editorarea-tabbox-tabs z-tabs");
	protected By ORDER_TAB_NAMES = By.cssSelector("[class='yw-editorarea-tabbox-tabs-tab z-tab']");
	protected By INCLTAX_VALUES_STATE = By.xpath("//*[contains(text(),'STATE')]");
	protected By INCLTAX_VALUES_COUNTY = By.xpath("//*[contains(text(),'COUNTY')]");
	protected By INCLTAX_VALUES_DISTRICT = By.xpath("//*[contains(text(),'DISTRICT')]");
	protected By INCL_TAX = By.xpath("//td[*[*[text()='Incl. Tax']]]//input[@type='text']");
	protected By TOTAL_PRICE = By.xpath("//td[*[*[text()='Total Price']]]//input[@type='text']");
	protected By DELIVERY_COST = By.xpath("//td[*[*[text()='Delivery Cost']]]//input[@type='text']");
	protected By ORDER_RESULT_CONTAINER = By.cssSelector("[class*='yw-coll-browser-hyperlink']");
	protected By PROMOTION_CODE = By.cssSelector("[class='yrb-label-break-all z-label']");
	protected By PROMOTION_NAME = By.id("oLxP4p1-chdextr");
	protected By PROMOTION_DESCRIPTION = By.id("oLxP5p1-chdextr");
	protected By APPLIED_DISCOUNT_VALUE = By.id("oLxP6p1-chdextr");

	/***
	 * Method to Search for Order Number on Orders Page
	 *
	 * @param orderNumber
	 */
	public void orderNumberSearch( String orderNumber )
	{
		wait.waitForElementEnabled(ORDERNO_SEARCH);
		text.enterText(ORDERNO_SEARCH, orderNumber);
		wait.waitForElementEnabled(SEARCH_BUTTON);
		click.clickElement(SEARCH_BUTTON);
		hybrisWaitForPageLoad();
		wait.waitForElementPresent(ORDER_RESULT_CONTAINER);
	}

	/***
	 * Method to Select Order from Search Results
	 */
	public void selectOrderNumberFromSearchResults( )
	{
		hybrisWaitForPageLoad();
		wait.waitForElementPresent(ORDER_RESULT_CONTAINER);
		final WebElement searchResultContainer = driver.findElement(ORDER_RESULT_CONTAINER);
		wait.waitForElementEnabled(searchResultContainer);
		click.clickElement(searchResultContainer, PageScrollDestination.VERT_CENTER);
		hybrisWaitForPageLoad();
	}

	/***
	 * Method to Navigate to Given Tab from Orders Page
	 */
	public void navigateToTabOnOrderPage( String tabName )
	{
		wait.waitForElementDisplayed(ORDER_TABS_CONTAINER);
		WebElement orderTabsContainer = driver.findElement(ORDER_TABS_CONTAINER);
		List<WebElement> orderTabNames = orderTabsContainer.findElements(ORDER_TAB_NAMES);
		WebElement ordertabName = null;
		for ( int i = 0 ; i < orderTabNames.size() ; i++ )
		{
			ordertabName = orderTabNames.get(i);
			if ( ordertabName
				.getText()
				.equalsIgnoreCase(tabName) )
			{
				scroll.scrollElementIntoView(ordertabName);
				ordertabName.click();
			}
		}
		hybrisWaitForPageLoad();
	}

	/***
	 * Method to Get Incl. Tax Values - STATE, COUNTRY, DISTRICT from PositionsPricesTab
	 *
	 * @return Incl.Tax values of State, Country, District as a MAP
	 */
	public Map<String, String> getInclTaxValues( )
	{
		final String incltaxstate = attribute.getElementAttribute(INCLTAX_VALUES_STATE, "text");
		final String incltaxcountry = attribute.getElementAttribute(INCLTAX_VALUES_COUNTY, "text");
		final String incltaxdistrict = attribute.getElementAttribute(INCLTAX_VALUES_DISTRICT, "text");
		final Map<String, String> incltaxvaluesMap = new HashMap<String, String>();
		incltaxvaluesMap.put("INCL.TAXSTATE", incltaxstate);
		incltaxvaluesMap.put("INCL.TAXCOUNTRY", incltaxcountry);
		incltaxvaluesMap.put("INCL.TAXDISTRICT", incltaxdistrict);
		return incltaxvaluesMap;
	}

	/***
	 * Method to Get Delivery Cost from Orders Page
	 *
	 * @return Delivery Cost
	 */
	public String getDeliveryCost( )
	{
		final String deliveryCost = attribute.getElementAttribute(DELIVERY_COST, "value");
		return deliveryCost;
	}

	/***
	 * Method to Get Total Price
	 *
	 * @return Total Price
	 */
	public String getTotalPrice( )
	{
		final String totalPrice = attribute.getElementAttribute(TOTAL_PRICE, "value");
		return totalPrice;
	}

	/***
	 * Method to Get Incl. Tax
	 *
	 * @return Incl. Tax
	 */
	public String getInclTax( )
	{
		final String inclTax = attribute.getElementAttribute(INCL_TAX, "value");
		return inclTax;
	}

	/***
	 * Method to Get PromotionCode
	 *
	 * @return promotionCode from PromotionEngineResults Tab
	 */
	public String getPromotionCode( )
	{
		final String promotionCode = attribute.getElementAttribute(PROMOTION_CODE, "text");
		return promotionCode;
	}

	/***
	 * Method to Get PromotionName
	 *
	 * @return promotionName from PromotionEngineResults Tab
	 */
	public String getPromotionName( )
	{
		final String promotionName = attribute.getElementAttribute(PROMOTION_NAME, "text");
		return promotionName;
	}

	/***
	 * Method to Get PromotionDescription
	 *
	 * @return promotionDescription from PromotionEngineResults Tab
	 */
	public String getPromotionDescription( )
	{
		final String promotionDescription = attribute.getElementAttribute(PROMOTION_DESCRIPTION, "text");
		return promotionDescription;
	}

	/***
	 * Method to Get Applied Discount Value
	 *
	 * @return appliedDiscount from PromotionEngineResults Tab
	 */
	public String getAppliedDiscount( )
	{
		final String appliedDiscount = attribute.getElementAttribute(APPLIED_DISCOUNT_VALUE, "text");
		return appliedDiscount;
	}
}

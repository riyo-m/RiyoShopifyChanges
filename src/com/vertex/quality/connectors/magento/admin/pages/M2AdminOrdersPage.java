package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * M2AdminOrdersPage where customers manage and input orders
 *
 * @author alewis
 */
public class M2AdminOrdersPage extends MagentoAdminPage
{
	protected By newOrderButtonID = By.id("add");
	protected By dataCellClass = By.className("data-grid-cell-content");
	protected By maskChangeClass = By.className("message-notice");
	protected By spinnerClass = By.className("admin__data-grid-loading-mask");
	protected String innerHTML = MagentoData.innerHTML.data;
	protected By maskCSS = By.cssSelector("div[data-role='sticky-el-root']");
	By maskClass = By.className("loading-mask");
	By orderTable =By.xpath("//div[@class='admin__data-grid-wrap']//table[@class='data-grid data-grid-draggable']//tbody//tr[@class='data-row']");

	protected By orderRowClass = By.className("data-row");
	protected By orderCellsTag = By.tagName("td");

	public M2AdminOrdersPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * clicks the New Order Button
	 *
	 * @return the Customers Order Page
	 */
	public M2AdminOrderCustomerPage clickNewOrderButton( )
	{
		waitForSpinnerToBeDisappeared();
		WebElement vertexServicesButton = findNewOrderButton();

		if ( vertexServicesButton != null )
		{
			click.moveToElementAndClick(vertexServicesButton);
		}
		else
		{
			String errorMsg = "New Order button not found";
			throw new RuntimeException(errorMsg);
		}

		return initializePageObject(M2AdminOrderCustomerPage.class);
	}

	/**
	 * locates and returns the New Order Button
	 *
	 * @return the New Order Button
	 */
	private WebElement findNewOrderButton( )
	{
		WebElement newOrderButton = wait.waitForElementPresent(newOrderButtonID);

		return newOrderButton;
	}

	/**
	 * clicks the order that was just put in in storefront
	 *
	 * @return the order view info page
	 */
	public M2AdminOrderViewInfoPage clickOrder( String orderNumber )
	{
		//Writing with David
		wait.waitForElementNotDisplayed(maskClass);
		wait.waitForElementDisplayed(orderTable,10);
		waitForMaskGone(spinnerClass);
		waitForPageLoad();
		wait.waitForElementNotDisplayed(spinnerClass, 10);
		wait.waitForElementPresent(maskCSS);
		wait.waitForElementNotDisplayed(spinnerClass, 10);
		WebElement dataCell = element.selectElementByText(dataCellClass, orderNumber);
		if ( dataCell != null )
		{
			click.clickElement(dataCell);
		}
		waitForPageLoad();
		return initializePageObject(M2AdminOrderViewInfoPage.class);
	}

	/**
	 * verifies whether an order with a specific number is present
	 *
	 * @param orderNumber
	 *
	 * @return whether the order is present or not
	 */
	public boolean isOrderPresent( String orderNumber )
	{
		boolean present = false;

		WebElement dataCell = element.selectElementByText(dataCellClass, orderNumber);
		if ( dataCell != null )
		{
			present = true;
		}

		return present;
	}
}
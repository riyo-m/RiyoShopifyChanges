package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Generic representation of Commerce tools Order page
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsOrderPage extends CommerceToolsBasePage
{

	public CommerceToolsOrderPage( final WebDriver driver )
	{
		super(driver);
	}


	protected final By CT_OrdersIcon=By.xpath("//div[text()='Orders']");
	protected final By CT_OrderList=By.xpath("//a[text()='Order list']");
	protected final By CT_Order_CustomField=By.xpath("//a[text()='Custom Fields']");
	protected final By CT_CustomFieldSelect=By.xpath("//div[@data-testid='order-details-custom-fields-type-select']");
	protected final By CT_CustomFieldDropDown=By.xpath("//input[@id='order-details-custom-fields-type-select']");
	protected final By CT_OrderCancellationProcessed=By.xpath("//input[@id='custom.fields.cancellationProcessed']");
	protected final By CT_SaveButton=By.xpath("//div[text()='Save']");
	protected final By CT_GeneralTab=By.xpath("//a[text()='General']");
	protected final By CT_OrderState=By.xpath("//input[@id='order-state']");

	/**
	 * Click on order icon on commercetools dashboard
	 */
	public void clickOnOrderIcon()
	{
		WebElement orderIconField=wait.waitForElementDisplayed(CT_OrdersIcon);
		click.moveToElementAndClick(orderIconField);
	}

	/**
	 * click on order list on commercetools.
	 */
	public void clickOnOrderList()
	{
		WebElement orderListField=wait.waitForElementDisplayed(CT_OrderList);
		click.moveToElementAndClick(orderListField);
	}

	/**
	 * click on custom fields on selected order
	 */
	public void clickCustomFieldsTab()
	{
		WebElement customFieldTab=wait.waitForElementDisplayed(CT_Order_CustomField);
		click.moveToElementAndClick(customFieldTab);
	}

	/**
	 * click and select custom fields on commercetools
	 *
	 */
	public void selectCustomFields(String customFieldValue)
	{
		WebElement customField=wait.waitForElementDisplayed(CT_CustomFieldDropDown);

		text.enterText(customField,customFieldValue);
	}

	/**
	 * click and select order cancellation reason.
	 * @param cancellationReason
	 */
	public void selectOrderCancellationReason(String cancellationReason)
	{
		WebElement cancelReasonField=wait.waitForElementDisplayed(CT_OrderCancellationProcessed);
		dropdown.selectDropdownByDisplayName(cancelReasonField,cancellationReason);
	}

	/**
	 * Click on general tab of current order
	 */
	public void clickOnGeneralTab()
	{
		WebElement generalTabField=wait.waitForElementDisplayed(CT_GeneralTab);
		click.moveToElementAndClick(generalTabField);
	}
	public void selectOrderState(final String orderState)
	{
		WebElement orderStateField=wait.waitForElementDisplayed(CT_OrderState);
		text.enterText(orderStateField,orderState);
	}

	/**
	 * click on save button
	 */
	public void clickOnSaveButton()
	{
		WebElement saveButtonField=wait.waitForElementEnabled(CT_SaveButton);
		click.moveToElementAndClick(saveButtonField);
	}

}

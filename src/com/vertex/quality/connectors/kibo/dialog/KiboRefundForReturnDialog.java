package com.vertex.quality.connectors.kibo.dialog;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the dialog for refunding a return
 * contains all the methods necessary to interact with the different webElements on the dialog
 *
 * @author osabha
 */
public class KiboRefundForReturnDialog extends VertexComponent
{
	public KiboRefundForReturnDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By refundAmountFieldTag = By.tagName("input");
	protected By refundAmountFieldLoc = By.cssSelector(
		"#application-mount > div > div:nth-child(2) > div > div.mozu-c-modal.mozu-c-modal--default.mozu-c-modal--global.mozu-c-modal--huge.mozu-is-active.mozu-is-animation.mozu-qa-modal > div.mozu-c-modal__body.mozu-c-modal__body--default.mozu-c-modal__body--global.mozu-c-modal__body--huge.mozu-is-active.mozu-is-animation.mozu-qa-modal > article > div:nth-child(1) > div > div.react-grid-table-container > table > tbody > tr > td:nth-child(10)");
	protected By issueRefundButtonLoc = By.className("mozu-c-btn__cross--primary");
	protected By checkNumberFieldLoc = By.cssSelector("input[placeholder='Check Number']");
	protected By newCheckValueField = By.cssSelector(
		"#application-mount > div > div:nth-child(2) > div > div.mozu-c-modal.mozu-c-modal--default.mozu-c-modal--global.mozu-c-modal--huge.mozu-is-active.mozu-is-animation.mozu-qa-modal > div.mozu-c-modal__body.mozu-c-modal__body--default.mozu-c-modal__body--global.mozu-c-modal__body--huge.mozu-is-active.mozu-is-animation.mozu-qa-modal > article > div.mozu-g-row > div.mozu-g-col.mozu-g-col--sm-12.mozu-g-col--lg-10.mozu-g-col--flex-column > div > div > div.react-grid-table-container > table > tbody > tr:nth-child(2) > td:nth-child(5) > span > div > div > input");
	protected By newStoreCreditField = By.cssSelector(
		"#application-mount > div > div:nth-child(2) > div > div.mozu-c-modal.mozu-c-modal--default.mozu-c-modal--global.mozu-c-modal--huge.mozu-is-active.mozu-is-animation.mozu-qa-modal > div.mozu-c-modal__body.mozu-c-modal__body--default.mozu-c-modal__body--global.mozu-c-modal__body--huge.mozu-is-active.mozu-is-animation.mozu-qa-modal > article > div.mozu-g-row > div.mozu-g-col.mozu-g-col--sm-12.mozu-g-col--lg-10.mozu-g-col--flex-column > div > div > div.react-grid-table-container > table > tbody > tr:nth-child(1) > td:nth-child(5) > span > div > div > input");
	protected By itemCheckBoxContainerLoc = By.cssSelector(
		"#application-mount > div > div:nth-child(2) > div > div.mozu-c-modal.mozu-c-modal--default.mozu-c-modal--global.mozu-c-modal--huge.mozu-is-active.mozu-is-animation.mozu-qa-modal > div.mozu-c-modal__body.mozu-c-modal__body--default.mozu-c-modal__body--global.mozu-c-modal__body--huge.mozu-is-active.mozu-is-animation.mozu-qa-modal > article > div:nth-child(1) > div > div.react-grid-header-fixed-container > table > thead > tr > th.react-grid-checkbox-container > input");
	protected By ShippingHandlingCheckBox = By.className("mozu-c-input__check-box-image");
	String checkNumber = "123456789";

	/**
	 * locates the check number field WebElement
	 *
	 * @return check number field WebElement
	 */
	protected WebElement getCheckNumberField( )
	{
		WebElement checkNumberField = wait.waitForElementDisplayed(checkNumberFieldLoc);

		return checkNumberField;
	}

	/**
	 * uses the getter method to locate the check number field and then clears it and then types in
	 * a random check number
	 */
	public void enterCheckNumber( )
	{
		WebElement checkNumberField = getCheckNumberField();
		text.enterText(checkNumberField, checkNumber);
	}

	/**
	 * locates and clicks the item ( wanted to be returned )check box from the return line
	 */
	public void clickReturnedItemCheckBox( )
	{
		WebElement returnedItemCheckBox = wait.waitForElementPresent(itemCheckBoxContainerLoc);
		returnedItemCheckBox.click();
	}

	/**
	 * gets and clicks the shipping and handling check box WebElement
	 */
	public void uncheckShippingHandling( )
	{
		WebElement shippingHandlingCheckbox = wait.waitForElementPresent(ShippingHandlingCheckBox);

		shippingHandlingCheckbox.click();
	}

	/**
	 * locates the check value field and types in the check amount to refund
	 *
	 * @param refundAmount String
	 */
	public void enterNewCheckValue( String refundAmount )
	{
		WebElement refundAmountField = wait.waitForElementDisplayed(newCheckValueField);
		text.enterText(refundAmountField, refundAmount);
	}

	/**
	 * gets the new store credit field and types in the refund amount in it
	 *
	 * @param refundAmount
	 */
	public void enterNewStoreCreditValue( String refundAmount )
	{
		WebElement refundAmountField = wait.waitForElementPresent(newStoreCreditField);
		text.enterText(refundAmountField, refundAmount);
	}

	/**
	 * gets and clicks the issue refund button from the refund dialog
	 */
	public void clickIssueRefundButton( )
	{
		WebElement issueRefundButton = wait.waitForElementPresent(issueRefundButtonLoc);

		issueRefundButton.click();
	}

	/**
	 * to locate and type in the amount to refund, some cases only partial return happens
	 */
	public void enterRefundAmount( String amountToRefund )
	{
		WebElement refundAmountFieldContainer = wait.waitForElementPresent(refundAmountFieldLoc);
		WebElement refundAmountField = wait.waitForElementPresent(refundAmountFieldTag, refundAmountFieldContainer);
		text.enterText(refundAmountField, amountToRefund);
	}
}

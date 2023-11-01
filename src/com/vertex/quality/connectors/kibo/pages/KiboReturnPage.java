package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.dialog.KiboRefundForReturnDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the return page
 * contains all the methods necessary to interact with the page
 * extends component classes
 *
 * @author osabha
 */
public class KiboReturnPage extends VertexPage
{
	public KiboRefundForReturnDialog refundDialog;

	public KiboReturnPage( WebDriver driver, VertexPage parent )
	{
		super(driver);
		this.refundDialog = new KiboRefundForReturnDialog(driver, parent);
	}

	protected By refundStatusContainer = By.className("mozu-c-card--sticky-nav");
	protected By issueRefundButtonLoc = By.className("mozu-c-btn");
	protected By refundStatusLoc = By.className("mozu-c-badge--good");
	protected By elemToChange = By.className("mozu-c-card__title--sticky-nav");

	/**
	 * getter method to locate the issue refund button
	 *
	 * @return refund button WebElement
	 */
	protected WebElement getIssueRefundButton( )
	{
		WebElement issueRefundButton = null;
		final String expectedText = "Issue Refund";

		List<WebElement> issueRefundContainers = wait.waitForAllElementsPresent(issueRefundButtonLoc);
		issueRefundButton = element.selectElementByText(issueRefundContainers, expectedText);

		return issueRefundButton;
	}

	/**
	 * uses the getter method to locate the refund button and then clicks on it
	 */
	public void clickIssueRefundButton( )
	{
		WebElement issueRefundButton = getIssueRefundButton();

		issueRefundButton.click();
	}

	/**
	 * boolean method to verify that we are refunding an expected amount
	 *
	 * @return true or false based on the results
	 */
	public boolean verifyRefunded( )
	{
		boolean isRefunded = false;

		final String expectedText = "Refunded";
		WebElement refundedElemContainer = wait.waitForElementPresent(refundStatusContainer);
		WebElement refundedElem = wait.waitForElementPresent(refundStatusLoc, refundedElemContainer);
		String refundedText = refundedElem.getText();

		if ( expectedText.equals(refundedText) )
		{
			isRefunded = true;
		}

		return isRefunded;
	}
}

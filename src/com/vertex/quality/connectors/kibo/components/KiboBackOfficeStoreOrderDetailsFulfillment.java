package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This class represents the Fulfillment tab in the order detail section inside the Maxine store
 * Back Office
 *
 * @author osabha
 */
public class KiboBackOfficeStoreOrderDetailsFulfillment extends VertexComponent
{
	public KiboBackOfficeStoreOrderDetailsFulfillment( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected final By fulfilledElemContLoc = By.cssSelector(
		".x-header-text.x-panel-header-text.x-panel-header-text-subform");
	protected final By fulfillmentMoveTo = By.className("fulfillment-move-to");
	protected final By loadMaskLoc = By.className("taco-loadmask");
	protected final By paymentFulfillmentContainer = By.className("taco-form-nav");
	protected final By markAsFulfilledLabelLoc = By.className("x-btn-inner-center");
	protected final By markAsShippedLabelLoc = By.className("x-btn-inner-center");
	protected final By newFulfillment = By.className("x-menu-item-text");
	protected final By fulfilledLoc = By.className("x-column-content-pill");
	protected final By fulfillmentContLoc = By.className("taco-order-fulfillment");
	protected final By markAsShippedClass = By.className("x-btn-action-medium-noicon");
	protected final By fulfillmentButtonLoc = By.className("taco-link-button");

	/**
	 * This method is a getter of the fulfillment tab to click on
	 * uses a wait for load condition local method
	 *
	 * @return fulfillment Button WebElement
	 */
	protected WebElement getFulfillmentButton( )
	{
		WebElement fulfillmentButton;
		String expectedText = "Fulfillment";
		WebElement fulfillmentButtonContainer = wait.waitForElementDisplayed(paymentFulfillmentContainer);
		List<WebElement> menuClasses = wait.waitForAllElementsDisplayed(fulfillmentButtonLoc,
			fulfillmentButtonContainer);
		fulfillmentButton = element.selectElementByText(menuClasses, expectedText);

		return fulfillmentButton;
	}

	/**
	 * Uses the getter method to locate the fulfillment tab and then clicks on it
	 */
	public void clickFulfillmentButton( )
	{
		WebElement fulfillmentButton = getFulfillmentButton();
		if ( fulfillmentButton == null )
		{
			fulfillmentButton = getFulfillmentButton();
			if ( fulfillmentButton == null )
			{
				fulfillmentButton = getFulfillmentButton();
			}
		}
		fulfillmentButton.click();
	}

	/**
	 * Locates the move to button from the fulfillment tab
	 *
	 * @return move to  button element
	 */
	protected WebElement getMoveToButton( )
	{
		WebElement moveToButton = wait.waitForElementPresent(fulfillmentMoveTo);

		return moveToButton;
	}

	/**
	 * uses the getter method of the move to button
	 * and then clicks on it
	 */
	public void clickMoveToButton( )
	{
		WebElement moveToButton = getMoveToButton();
		moveToButton.click();
	}

	/**
	 * gets the new package button
	 *
	 * @return new package button element
	 */
	protected WebElement getNewPackageButton( )
	{
		String expectedText = "New Package";
		WebElement packageButton = element.selectElementByText(newFulfillment, expectedText);
		return packageButton;
	}

	/**
	 * uses the getter method to locate the new package button and then clicks on it
	 */
	public void selectNewPackage( )
	{
		WebElement packageButton = getNewPackageButton();
		packageButton.click();
		WebElement markAsShippedButton = getMarkAsShippedButton();
		wait.waitForElementDisplayed(markAsShippedButton);
	}

	/**
	 * locates the new pick up button and waits until its present then retrieves it
	 *
	 * @return new pick up button element
	 */
	protected WebElement getNewPickupButton( )
	{
		final String expectedText = "New Pickup";
		WebElement pickup = element.selectElementByText(newFulfillment, expectedText);
		return pickup;
	}

	/**
	 * uses the getter method to locate the new package button element and then clicks on it
	 */
	public void selectNewPickup( )
	{
		WebElement packageButton = getNewPickupButton();
		packageButton.click();
	}

	/**
	 * uses nested for loops to locate the mark as shipped button and waits until it's clickable
	 *
	 * @return the mark as shipped button element
	 */
	protected WebElement getMarkAsShippedButton( )
	{
		String expectedText = "Mark as Shipped";
		List<WebElement> markAsShippedButtons = wait.waitForAllElementsPresent(markAsShippedClass);
		WebElement markAsShippedButton = element.selectElementByNestedLabel(markAsShippedButtons, markAsShippedLabelLoc,
			expectedText);
		return markAsShippedButton;
	}

	/**
	 * uses the getter method to locate the mark as shipped button and then clicks on it
	 * this method also has another local method that waits until all the load masks triggered after
	 * the click are gone
	 */
	public void clickMarkAsShippedButton( )
	{
		WebElement markAsShippedButton = getMarkAsShippedButton();
		click.clickElement(markAsShippedButton);

		List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
		for ( WebElement mask : masks )
		{
			try
			{
				wait.waitForElementNotDisplayed(mask);
			}
			catch ( Exception e )
			{
				System.out.println("Load Mask isn't present");
			}
		}
	}

	/**
	 * getter method using nester for loops to located the mark as fulfilled button element and
	 * waits until it's clickable
	 *
	 * @return mark as fulfilled button
	 */
	protected WebElement getMarkAsFulfilledButton( )
	{
		String expectedText = "Mark as Fulfilled";
		List<WebElement> markAsFulfilledButtons = wait.waitForAllElementsPresent(markAsShippedClass);
		WebElement markAsFulfilledButton = element.selectElementByNestedLabel(markAsFulfilledButtons,
			markAsFulfilledLabelLoc, expectedText);
		return markAsFulfilledButton;
	}

	/**
	 * uses the getter method to locate the mark as fulfilled button element and then clicks on it
	 * this method also has a local method to wait until all the load masks triggered by the click
	 * to be gone
	 */
	public void clickMarkAsFulfilledButton( )
	{
		WebElement markAsShippedButton = getMarkAsFulfilledButton();
		markAsShippedButton.click();

		List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
		for ( WebElement mask : masks )
		{
			try
			{
				wait.waitForElementNotDisplayed(mask);
			}
			catch ( Exception e )
			{
				System.out.println("Load Mask isn't present");
			}
		}
	}

	/**
	 * boolean method to verify the order has been fulfilled after shipping it or it has been picked
	 * up
	 *
	 * @return true if order was fulfilled
	 */
	public boolean checkIfFulfilled( )
	{
		boolean ifFulfilled = false;
		String expectedFulfillment = "Fulfilled";
		WebElement fulfillmentParentCont = wait.waitForElementPresent(fulfillmentContLoc);
		WebElement fulfillmentCont = wait.waitForElementDisplayed(fulfilledElemContLoc, fulfillmentParentCont);
		WebElement fulfillmentElem = wait.waitForElementDisplayed(fulfilledLoc, fulfillmentCont);
		String fulfillmentText = text.getElementText(fulfillmentElem);
		if ( expectedFulfillment.equals(fulfillmentText) )
		{
			ifFulfilled = true;
		}
		return ifFulfilled;
	}
}

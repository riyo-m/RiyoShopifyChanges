package com.vertex.quality.connectors.sitecore.pages.store.checkout;

import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents payment info tab in sitecore checkout page
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecorePaymentInfoTab extends SitecoreBasePage
{

	protected By creditCardType = By.id("CreditCardType");
	protected By cardHolderName = By.id("CardholderName");
	protected By cardNumber = By.id("CardNumber");
	protected By expirationMonth = By.id("ExpireMonth");
	protected By expireYear = By.id("ExpireYear");
	protected By cardCode = By.id("CardCode");
	protected By nextButton = By.className("btn-success");

	public SitecorePaymentInfoTab( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to select the credit card type
	 *
	 * @param cardType type of card to select
	 */
	public void selectCreditCardType( final String cardType )
	{
		dropdown.selectDropdownByDisplayName(creditCardType, cardType);
	}

	/**
	 * This method is used to set the credit card holder name
	 *
	 * @param name of cardholder to set
	 */
	public void setCardHolderName( final String name )
	{
		text.enterText(cardHolderName, name);
	}

	/**
	 * This method is used to set the Credit Card Number
	 *
	 * @param number of card to set
	 */
	public void setCardNumber( final String number )
	{
		text.enterText(cardNumber, number);
	}

	/**
	 * This method is used to select the Credit Card expiration month
	 *
	 * @param month of card expiration date
	 */
	public void selectCreditCardExpirationMonth( final String month )
	{
		dropdown.selectDropdownByDisplayName(expirationMonth, month);
	}

	/**
	 * This method is used to select the Credit Card expiration year
	 *
	 * @param year of card expiration date
	 */
	public void selectCreditCardExpirationYear( final String year )
	{
		dropdown.selectDropdownByDisplayName(expireYear, year);
	}

	/**
	 * This method is used to set the Credit card code/CVV
	 *
	 * @param cardCodeText cvv of credit card
	 */
	public void setCardCode( final String cardCodeText )
	{
		text.enterText(cardCode, cardCodeText);
	}

	//TODO make params enum

	/**
	 * This method is used to fill the credit card details.
	 *
	 * @param type        type of card to select
	 * @param name        of cardholder to set
	 * @param number      of card to set
	 * @param expireMonth of card expiration date to set
	 * @param expireYear  of card expiration date to set
	 * @param code        cvv of credit card
	 */
	public void fillCreditCardDetails( final String type, final String name, final String number,
		final String expireMonth, final String expireYear, final String code )
	{

		selectCreditCardType(type);
		setCardHolderName(name);
		setCardNumber(number);
		selectCreditCardExpirationMonth(expireMonth);
		selectCreditCardExpirationYear(expireYear);
		setCardCode(code);
		clickNextButton();
	}

	/**
	 * This method is used to click the next method. This is common across all the
	 * different tabs available in this page.
	 */
	public void clickNextButton( )
	{
		WebElement nextBtnElement = element.getWebElement(nextButton);
		scroll.scrollElementIntoView(nextBtnElement);
		click.clickElement(nextBtnElement);
		final String loadingText = "Loading...";
		List<WebElement> buttons = wait.waitForAnyElementsDisplayed(By.tagName("button"));
		WebElement loadingButton = null;
		//search for leading button
		for ( WebElement btn : buttons )
		{
			try
			{
				String btnText = text.getElementText(btn);
				if ( btnText.equals(loadingText) )
				{
					loadingButton = btn;
					break;
				}
			}
			catch ( StaleElementReferenceException e )
			{
				//Loading element disappeared in time it took looking for loading button so loading button
				//webelment is stale and caused error
				break;
				//we just wanted to wait for the loading button to disappear so just break from loop and ignore exception
			}
		}
		//loading button has been found
		if ( loadingButton != null )
		{
			try
			{
				wait.waitForElementNotDisplayed(loadingButton);
			}
			catch ( StaleElementReferenceException e )
			{
				//loading button element became stale and is no longer on page
				//ignore exception
			}
		}
	}
}

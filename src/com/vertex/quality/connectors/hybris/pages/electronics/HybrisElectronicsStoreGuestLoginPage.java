package com.vertex.quality.connectors.hybris.pages.electronics;

import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Guest Login Page of the Electronics Store page
 *
 * @author Nagaraju Gampa
 */
public class HybrisElectronicsStoreGuestLoginPage extends HybrisBasePage
{
	public HybrisElectronicsStoreGuestLoginPage( WebDriver driver )
	{
		super(driver);
	}

	protected By GUEST_EMAIL = By.id("guest.email");
	protected By GUEST_CONFIRM_EMAIL = By.id("guest.confirm.email");
	protected By CHECK_OUT_AS_GUEST_BUTTON = By.cssSelector("[class$='guestCheckoutBtn']");

	/***
	 * Method to enter input text of Guest email
	 *
	 * @param email
	 *            - Enter GuestEmail in email field
	 */
	public void enterGuestEmail( String email )
	{
		wait.waitForElementDisplayed(GUEST_EMAIL);
		text.enterText(GUEST_EMAIL, email);
	}

	/***
	 * Method to enter the confirmation Guest mail
	 *
	 * @param email
	 *            - Enter GuestEmail in confirm email field
	 */
	public void confirmGuestEmail( String email )
	{
		wait.waitForElementDisplayed(GUEST_CONFIRM_EMAIL);
		text.enterText(GUEST_CONFIRM_EMAIL, email);
	}

	/***
	 * Method to click checkout as a guest
	 */
	public HybrisElectronicsStoreCheckOutPage checkoutAsGuest( )
	{
		click.clickElement(CHECK_OUT_AS_GUEST_BUTTON);
		final HybrisElectronicsStoreCheckOutPage checkoutpage = initializePageObject(
			HybrisElectronicsStoreCheckOutPage.class);
		return checkoutpage;
	}
}

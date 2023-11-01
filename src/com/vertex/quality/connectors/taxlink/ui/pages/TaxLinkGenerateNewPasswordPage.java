package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import com.vertex.quality.connectors.taxlink.ui.enums.GenerateNewPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.ArrayList;

/**
 * this class represents all the locators and methods for Generate new password page
 * in Taxlink UI.
 *
 * @author mgaikwad
 */

public class TaxLinkGenerateNewPasswordPage extends TaxLinkBasePage
{
	public TaxLinkGenerateNewPasswordPage( final WebDriver driver ) throws IOException
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[contains(text(),'Generate New Password')]")
	private WebElement generatePwdButton;

	@FindBy(xpath = "//a[@data-cy='customerOnboarding-module']")
	private WebElement onboardingDashboardButton;

	@FindBy(xpath = "//h1[contains(text(),'Users')]/parent::div/parent::div/parent::div")
	private WebElement syncUsersPopup;

	@FindBy(xpath = "//input[@name='TaxLink URL']")
	private WebElement acceleratorURL;

	@FindBy(xpath = "//input[@name='TaxLink User Name']")
	private WebElement acceleratorUserName;

	@FindBy(xpath = "//input[@name='TaxLink Password']")
	private WebElement acceleratorPassword;

	@FindBy(xpath = "(//h6[contains(text(),'Copy')])[last()]")
	private WebElement copyLinkURL;

	@FindBy(xpath = "//div[@class='Modal modalShow react-draggable']/div/p")
	private WebElement confirmationPopUp;

	@FindBy(xpath = "//div[@class='Modal modalShow react-draggable']/p")
	private WebElement instructionsPopUp;

	@FindBy(xpath = "//button[@data-cy='btn-yes']")
	private WebElement yesOnPopUp;

	@FindBy(xpath = "//div[@class='Modal modalShow react-draggable']/span")
	private WebElement closeConfirmationPopUp;

	@FindBy(xpath = "(//div[@col-id='description'])[last()]")
	private WebElement instructionsPresentation;

	@FindBy(xpath = "//button[contains(text(),'Download')]")
	private WebElement downloadButtonPresentation;

	/**
	 * Verify title of Generate new password Page in Taxlink application
	 *
	 * @return boolean
	 */
	public boolean verifyTitleGenerateNewPasswordPage( )
	{
		boolean flag;
		wait.waitForElementDisplayed(addViewEditPageTitle);
		String headerGenNewPwdTitle = addViewEditPageTitle.getText();
		boolean verifyFlag = headerGenNewPwdTitle.equalsIgnoreCase(
			GenerateNewPassword.GENERATE_NEW_PASSWORD_DETAILS.headerGenNewPwdPage);

		if ( verifyFlag )
		{
			flag = true;
		}
		else
		{
			flag = false;
		}
		return flag;
	}

	/**
	 * Verify generate new password functionality
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean verifySyncedPassword( )
	{
		boolean flagMatchedOriginal = false, flagVerifiedInstructions = false, flagMatchedNew = false, finalFlag
			= false;

		navigateToInstancePage();
		wait.waitForElementDisplayed(editButton, 10);
		editButton.click();
		wait.waitForElementDisplayed(addViewEditPageTitle);

		String urlFromDashboard = acceleratorURL.getAttribute("value");
		VertexLogger.log("urlFromDashboard : " + urlFromDashboard);
		String usernameFromDashboard = acceleratorUserName.getAttribute("value");
		VertexLogger.log("usernameFromDashboard : " + usernameFromDashboard);
		String passwordFromDashboard = acceleratorPassword.getAttribute("value");
		VertexLogger.log("passwordFromDashboard : " + passwordFromDashboard);

		jsWaiter.sleep(15000);

		navigateToGenerateNewPasswordTab();

		if ( verifyTitleGenerateNewPasswordPage() )
		{
			if ( urlFromDashboard.equals(acceleratorURL.getAttribute("value")) && usernameFromDashboard.equals(
				acceleratorUserName.getAttribute("value")) && passwordFromDashboard.equals(
				acceleratorPassword.getAttribute("value")) )
			{
				VertexLogger.log("Verified original details from dashboard screen. Matches!! ");
				flagMatchedOriginal = true;
			}

			click.clickElement(generatePwdButton);
			if ( confirmationPopUp
				.getText()
				.contains(GenerateNewPassword.GENERATE_NEW_PASSWORD_DETAILS.confirmOnPopUpPage) )
			{
				click.clickElement(yesOnPopUp);
				jsWaiter.sleep(5000);
				if ( instructionsPopUp
					.getText()
					.contains(GenerateNewPassword.GENERATE_NEW_PASSWORD_DETAILS.infoOnPopUpPage) )
				{
					click.clickElement(closeConfirmationPopUp);
					if ( instructionsPresentation
						.getText()
						.equals(GenerateNewPassword.GENERATE_NEW_PASSWORD_DETAILS.instructionsHeading) )
					{
						click.clickElement(downloadButtonPresentation);
						ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
						jsWaiter.sleep(1000);
						String winHandle1 = driver.getWindowHandle();
						VertexLogger.log(winHandle1);
						driver
							.switchTo()
							.window(tabs.get(1));
						String winHandle2 = driver.getWindowHandle();
						VertexLogger.log(winHandle2);

						if ( !winHandle1.equals(winHandle2) )
						{
							VertexLogger.log("Instructions present!! ");
							flagVerifiedInstructions = true;
						}
						driver
							.switchTo()
							.window(tabs.get(0));
					}
				}
			}
			if ( verifyNewPwdOnDashboard() )
			{
				flagMatchedNew = true;
				VertexLogger.log("Verified new password in dashboard screen. Matches!! ");
			}

			if ( flagMatchedOriginal && flagVerifiedInstructions && flagMatchedNew )
			{
				finalFlag = true;
			}
		}
		return finalFlag;
	}

	/**
	 * Verify newly generated password in dashboard
	 * in Taxlink Application
	 *
	 * @return boolean
	 */
	public boolean verifyNewPwdOnDashboard( )
	{
		boolean flagMatchedNew = false;
		String newGeneratedPwd = acceleratorPassword.getAttribute("value");
		navigateToInstancePage();
		editButton.click();
		wait.waitForElementDisplayed(addViewEditPageTitle);
		String newPasswordFromDashboard = acceleratorPassword.getAttribute("value");
		VertexLogger.log("newPasswordFromDashboard : " + newPasswordFromDashboard);
		if ( newPasswordFromDashboard.equals(newGeneratedPwd) )
		{
			flagMatchedNew = true;
		}
		return flagMatchedNew;
	}

	/**
	 * Method to verify disabled Generate new password tab
	 * when Instance does not have FI_ADMIN roles assigned in Taxlink UI
	 *
	 * @param user
	 * @param instance return boolean
	 */
	public boolean verifyDisabledGenNewPwdTab( String user, String instance )
	{
		boolean finalFlag = false;
		navigateToUsersTab();
		jsWaiter.sleep(15000);
		if ( selectUser(user) )
		{
			jsWaiter.sleep(500);
			if ( removeRoleFromAnInstance(instance) )
			{
				if ( checkDisabledGenNewPwdButton() )
				{
					VertexLogger.log("Generate new password button is disabled!!");
					finalFlag = true;
				}
			}
		}
		return finalFlag;
	}

	/**
	 * Method to verify disabled Generate new password tab
	 * when Instance does not have FI_ADMIN roles assigned in Taxlink UI
	 *
	 * return boolean
	 */
	public boolean checkDisabledGenNewPwdButton( )
	{
		jsWaiter.sleep(5000);
		String toolTipText = generateNewPwdLink.getAttribute("title");
		boolean buttonDisabledFlag = toolTipText.equalsIgnoreCase("FI Admin Role not assigned");
		return buttonDisabledFlag;
	}
}
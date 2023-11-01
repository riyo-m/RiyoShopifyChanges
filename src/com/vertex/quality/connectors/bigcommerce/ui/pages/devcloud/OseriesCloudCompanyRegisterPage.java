package com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represent the dev cloud registration and contain all the methods to register new company
 * in vertex dev cloud.
 *
 * @author rohit.mogane
 */
public class OseriesCloudCompanyRegisterPage extends OseriesCloudBasePage
{

	public OseriesCloudCompanyRegisterPage( WebDriver driver )
	{
		super(driver);
	}

	protected final By firstNameText = By.xpath("//*[@name='firstName']");
	protected final By lastNameText = By.xpath("//*[@name='lastName']");
	protected final By phoneText = By.xpath("//*[@name='userPhone']");
	protected final By userNameText = By.xpath("//input[@id='user-name']");
	protected final By passwordText = By.xpath("//input[@id='password']");
	protected final By confirmPasswordText = By.xpath("//input[@id='confirm-password']");
	protected final By finishAndSaveButton = By.xpath("//button[@type='submit']");

	/**
	 * locates the company name field and then clears it and types in the company name
	 *
	 * @param companyName the company name string which will be entered in the register company page.
	 */
	public void enterCompanyUserName( final String companyName )
	{
		WebElement userNameField = wait.waitForElementDisplayed(userNameText);
		text.enterText(userNameField, companyName);
	}

	/**
	 * locates the company password field and then clears it and types in the company password
	 *
	 * @param companyPassword the company password string which will be entered in the register company page
	 */
	public void enterCompanyPassword( final String companyPassword )
	{
		WebElement passwordField = wait.waitForElementEnabled(passwordText);
		text.enterText(passwordField, companyPassword);
	}

	/**
	 * locates the company confirm password field and send the company confirm password
	 *
	 * @param confirmPassword the company confirm password string which will be entered in the register company page
	 */
	public void enterCompanyConfirmPassword( final String confirmPassword )
	{
		WebElement confirmPasswordField = wait.waitForElementDisplayed(confirmPasswordText);
		text.enterText(confirmPasswordField, confirmPassword);
	}

	/**
	 * this method is to enter first name on register company page
	 *
	 * @param firstName string first name
	 */
	public void enterFirstName( String firstName )
	{
		WebElement firstNameField = wait.waitForElementDisplayed(firstNameText);
		text.enterText(firstNameField, firstName);
	}

	/**
	 * this method is to enter last name on register company page
	 *
	 * @param lastName string last name
	 */
	public void enterLastName( String lastName )
	{
		WebElement lastNameField = wait.waitForElementDisplayed(lastNameText);
		text.enterText(lastNameField, lastName);
	}

	/**
	 * this method is to enter phone on register company page
	 *
	 * @param phone string last name
	 */
	public void enterPhone( String phone )
	{
		WebElement phoneField = wait.waitForElementDisplayed(phoneText);
		text.clickElementAndEnterText(phoneField, phone);
	}

	/**
	 * locates and click on the save and finish button.
	 */
	public void clickOnSaveAndFinishButton( )
	{
		WebElement saveButton = wait.waitForElementDisplayed(finishAndSaveButton);
		click.clickElement(saveButton);
	}
}

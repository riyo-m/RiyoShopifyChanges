package com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the invite new user page and all related methods
 *
 * @author rohit.mogane
 */
public class OseriesCloudInviteNewUserPage extends OseriesCloudBasePage
{

	public OseriesCloudInviteNewUserPage( final WebDriver driver )
	{
		super(driver);
	}

	protected final By roleDropDown = By.xpath("//select[@name='Role']");
	protected final By returnToUserButton = By.xpath("//*[contains(text(),'Return to Users')]");
	protected final By accountTypeDropDown = By.xpath("//select[@name='clientSubTypeName']");
	protected final By franchiseNameDropDown = By.xpath("//select[@name='resellerName']");
	protected final By oraclePartyNumberText = By.xpath("//input[@name='OraclePartyNumber']");
	protected final By clientNameText = By.xpath("//input[@name='ClientName']");
	protected final By firstnameText = By.xpath("//input[@name='FirstName']");
	protected final By lastNameText = By.xpath("//input[@name='LastName']");
	protected final By emailText = By.xpath("//input[@name='Email']");
	protected final By countryDropDown = By.xpath("//select[@name='Country']");
	protected final By phoneText = By.xpath("//input[@id='txtPhone']");
	protected final By streetAddressText = By.xpath("//input[@id='txtStreetAddress1']");
	protected final By streetAddress2Text = By.xpath("//input[@id='txtStreetAddress2']");
	protected final By cityText = By.xpath("//input[@id='txtCity']");
	protected final By stateDropDown = By.xpath("//select[@name='StateCode']");
	protected final By zipCodeText = By.xpath("//input[@id='txtZip']");
	protected final By subscriptionDropDown = By.xpath("//select[@name='subscriptionId']");
	protected final By optionsDropDown = By.xpath("//select[@name='ProfessionalSubscriptionFeatures']");
	protected final By reservePodCheckBox = By.xpath("//*[@name='isReservedPodChecked']");
	protected final By reservePod = By.xpath("//*[@name='reservedPod']");
	protected final By sendInviteText = By.xpath("//button[@id='btnInvite']");
	protected final By newUserLink = By.partialLinkText("https://devportal.vertexsmb.com/Registration/Complete");

	/**
	 * locates and selects the role from drop down
	 *
	 * @param displayName select the display name from the drop down
	 */
	public void selectRole( final String displayName )
	{
		wait.waitForElementDisplayed(roleDropDown, 120);
		dropdown.selectDropdownByDisplayName(roleDropDown, displayName);
	}

	/**
	 * locates and selects the account display name
	 *
	 * @param accountDisplayName select the account display name from drop down
	 */
	public void selectAccountType( final String accountDisplayName )
	{
		WebElement accountType = wait.waitForElementDisplayed(accountTypeDropDown);
		dropdown.selectDropdownByDisplayName(accountType, accountDisplayName);
	}

	/**
	 * locates and selects the franchiseName
	 *
	 * @param franchiseName select the franchiseName from drop down
	 */
	public void selectFranchiseName( final String franchiseName )
	{
		WebElement franchiseNameField = wait.waitForElementDisplayed(franchiseNameDropDown);
		dropdown.selectDropdownByDisplayName(franchiseNameField, franchiseName);
	}

	/**
	 * locates and enter the partyNumber
	 *
	 * @param partyNumber partynumber which will be entered in the new user invite page
	 */
	public void enterOraclePartyNumber( final String partyNumber )
	{
		WebElement oraclePartyNumnber = wait.waitForElementDisplayed(oraclePartyNumberText);
		text.enterText(oraclePartyNumnber, partyNumber);
	}

	/**
	 * locates and enter the clientName
	 *
	 * @param clientName clientName which will be entered in the new user invite page
	 */
	public void enterClientName( final String clientName )
	{
		WebElement clientNameField = wait.waitForElementDisplayed(clientNameText);
		text.enterText(clientNameField, clientName);
	}

	/**
	 * locates and enter the firstName
	 *
	 * @param firstName firstName which will be entered in the new user invite page
	 */
	public void enterFirstName( final String firstName )
	{
		WebElement firstNameField = wait.waitForElementDisplayed(firstnameText);
		text.enterText(firstNameField, firstName);
	}

	/**
	 * locates and enter the lastName
	 *
	 * @param lastName lastName which will be entered in the new user invite page
	 */
	public void enterLastName( final String lastName )
	{
		WebElement lastNameField = wait.waitForElementDisplayed(lastNameText);
		text.enterText(lastNameField, lastName);
	}

	/**
	 * locates and enter the emailID
	 *
	 * @param emailID emailID which will be entered in the new user invite page
	 */
	public void enterEmailAddress( final String emailID )
	{
		WebElement emailIDField = wait.waitForElementDisplayed(emailText);
		text.enterText(emailIDField, emailID);
	}

	/**
	 * locates and select the countryName
	 *
	 * @param countryName countryName which will be selected in the new user invite page
	 */
	public void selectCountry( final String countryName )
	{
		WebElement countryField = wait.waitForElementDisplayed(countryDropDown);
		dropdown.selectDropdownByDisplayName(countryField, countryName);
	}

	/**
	 * locates and enter the phoneNumber
	 *
	 * @param phoneNumber phoneNumber which will be entered in the new user invite page
	 */
	public void enterPhoneNumber( final String phoneNumber )
	{
		WebElement phoneNumberField = wait.waitForElementDisplayed(phoneText);
		text.enterText(phoneNumberField, phoneNumber);
	}

	/**
	 * locates and enter the streetAddress
	 *
	 * @param streetAddress streetAddress which will be entered in the new user invite page
	 */
	public void enterStreetAddress( final String streetAddress )
	{
		WebElement streetAddressField = wait.waitForElementDisplayed(streetAddressText);
		text.enterText(streetAddressField, streetAddress);
	}

	/**
	 * locates and enter the streetAddress2
	 *
	 * @param streetAddress2 streetAddress2 which will be entered in the new user invite page
	 */
	public void enterStreetAddress2( final String streetAddress2 )
	{
		WebElement streetAddress2Field = wait.waitForElementDisplayed(streetAddress2Text);
		text.enterText(streetAddress2Field, streetAddress2);
	}

	/**
	 * locates and enter the cityName
	 *
	 * @param cityName cityName which will be entered in the new user invite page
	 */
	public void enterCity( final String cityName )
	{
		WebElement cityNameField = wait.waitForElementDisplayed(cityText);
		text.enterText(cityNameField, cityName);
	}

	/**
	 * locates and select the state
	 *
	 * @param state state which will be selected in the new user invite page
	 */
	public void selectState( final String state )
	{
		WebElement stateField = wait.waitForElementDisplayed(stateDropDown);
		dropdown.selectDropdownByDisplayName(stateField, state);
	}

	/**
	 * locates and enter the zipCode
	 *
	 * @param zipCode zipCode which will be entered in the new user invite page
	 */
	public void enterZipCode( final String zipCode )
	{
		WebElement zipCodeField = wait.waitForElementDisplayed(zipCodeText);
		text.clearText(zipCodeField);
		text.clickElementAndEnterText(zipCodeField, zipCode);
	}

	/**
	 * locates and select the subscription
	 *
	 * @param subscription subscription which will be selected in the new user invite page
	 */
	public void selectSubscription( final String subscription )
	{
		WebElement subscriptionField = wait.waitForElementDisplayed(subscriptionDropDown);
		dropdown.selectDropdownByDisplayName(subscriptionField, subscription);
	}

	/**
	 * locates and select the option
	 *
	 * @param option option which will be selected in the new user invite page
	 */
	public void selectOption( final String option )
	{
		WebElement optionField = wait.waitForElementDisplayed(optionsDropDown);
		dropdown.selectDropdownByDisplayName(optionField, option);
	}

	/**
	 * this method is to select reserve pod check box to select pod
	 */
	public void selectReservePodCheckBox( )
	{
		WebElement reservePodCheckField = wait.waitForElementDisplayed(reservePodCheckBox);
		click.javascriptClick(reservePodCheckField);
	}

	/**
	 * this method is to select pod from drop down
	 *
	 * @param podValue String pod value to select
	 */
	public void selectReservePod( String podValue )
	{
		WebElement reservePodField = wait.waitForElementDisplayed(reservePod);
		dropdown.selectDropdownByDisplayName(reservePodField, podValue);
	}

	/**
	 * locates and clicks the invite new user button
	 *
	 * @return new instance of the new user
	 */
	public OseriesCloudInviteNewUserPage clickSendInviteButton( )
	{
		WebElement inviteButtonField = wait.waitForElementDisplayed(sendInviteText);
		click.clickElement(inviteButtonField);
		OseriesCloudInviteNewUserPage newUserPage = initializePageObject(OseriesCloudInviteNewUserPage.class);
		return newUserPage;
	}

	/**
	 * locates and clicks the invite new user button
	 */
	public void clickNewInviteLink( )
	{
		WebElement newInviteLink = wait.waitForElementDisplayed(newUserLink);
		click.clickElement(newInviteLink);
	}
}

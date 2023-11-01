package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.common.utils.selenium.VertexTextUtilities;
import com.vertex.quality.connectors.shopify.base.ShopifyUIBaseTest;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class ShopifyNewLoginPage extends ShopifyPage
{

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ShopifyNewLoginPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By loginNav = By.xpath("//a[text()='Log in' and contains(@class,'marketing-nav')]");
	protected By emailField = By.id("account_email");
	protected By continueWithEmailBtn = By.name("commit");
	protected By passText = By.id("account_password");
	protected By loginBtn = By.name("commit");
	protected By partnerAcc = By.xpath("//span[text()='Vertex Inc.']");
	protected By searchInputField = By.xpath("//input[@id='PolarisTextField1']");

	protected By searchFirstResult = By.xpath("//div[@class='Polaris-ResourceItem']//following-sibling::div//h3");

	protected By settingsButton = By.xpath("//span[text()='Settings']");

	protected By btnTaxAndDuties = By.xpath("//span[text()='Taxes and duties']");

	protected By textActiveStatus = By.xpath("//span[text()='Active']");

	protected By btnClosePopUp = By.xpath("//div[@class='gvh9k']//button[@aria-label='Close']");

	protected By btnOrders = By.xpath("//span[text()='Orders']");

	protected By btnCreateOrders = By.xpath("//span[text()='Create order']");

	protected By btnBrowse = By.xpath("//span[text()='Browse']");

	protected By btnAllOrders = By.xpath("//div[text()='Popular products']");

	protected By firstPopularProduct = By.xpath("//div[@class='txPIe']//span[text()='The 3p Fulfilled Snowboard']");

	protected By btnAddProduct = By.xpath("//span[text()='Add']");





	public void loginNavigation( )
	{
		waitForPageLoad();
		click.clickElement(loginNav);
	}

	public void enterEmailField( String email )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(emailField), email);
	}

	public void clickContinueWithEmailBtn( )
	{
		waitForPageLoad();
		click.clickElement(continueWithEmailBtn);
	}

	public void enterPassField( String pass )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(passText), pass);
	}

	public void clickLoginbtn( )
	{
		waitForPageLoad();
		click.clickElement(loginBtn);
	}

	public void connectPartnerAcc( )
	{
		waitForPageLoad();
		click.clickElement(partnerAcc);
	}

	public void searchField( String store )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(searchInputField), store);
	}

	public void selectVtxQa(){

		waitForPageLoad();
		click.clickElement(searchFirstResult);
	}

	public void clickSetting(){
	waitForPageLoad();
	click.clickElement(settingsButton);

	}
	public void clickTaxAndDuties(){
		waitForPageLoad();
		click.clickElement(btnTaxAndDuties);
	}

}



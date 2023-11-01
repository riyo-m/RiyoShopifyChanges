package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.enums.AcumaticaAddressCleansingActions;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This page is for common actions in acumatica application.
 *
 * @author saidulu kodadala
 */
public class AcumaticaCommonPage extends AcumaticaPostSignOnPage
{
	protected By DELETE_BUTTON = By.cssSelector("[data-cmd='Delete'] div[icon='Remove']");
	protected By WAREHOUSE_ID = By.id("ctl00_phG_pnlCreateShipment_formCreateShipment_edSiteID_text");
	protected By OK_BUTTON = By.id("ctl00_phG_PXSmartPanel13_PXButton11");

	protected By popupContainer = By.className("SmartPanelCN");
	protected By popupButtonsContainer = By.className("buttonPanel");
	protected String popupOkButtonText = "OK";

	protected By ACTION_DROP_DROPDOWN = By.cssSelector("label[text='Action:']+div div[class='editorCont']");
	protected By ADMIN = By.cssSelector("[data-cmd='userName'] div");
	protected By SIGN_OUT = By.cssSelector("[icon='Logout']");
	protected By LOGIN_BUTTON = By.id("btnLogin");
	protected By VERTEXPOSTALADDRESS = By.id("ctl00_phG_PXSmartPanel13_cap");
	protected static final String ACTIONS_DROP_DOWN = "//div[contains(text(),'%s')]";
	protected static final String ADDRESS_CLEASING_OPTION_FROM_ACTIONS
		= "//div[contains(text(),'%s')][@class='menuSpacer']";
	protected By CLOSE_VERTEX_POSTAL_ADDRESS_POPUP = By.cssSelector("td[id='ctl00_phG_PXSmartPanel13_cap'] div");
	String confirm = AcumaticaAddressCleansingActions.CONFIRM.getValue();
	String ignore = AcumaticaAddressCleansingActions.IGNORE.getValue();

	public AcumaticaCommonPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Switch to default content (This method should return to parent/main page)
	 */
	public void switchToParentPage( )
	{
		window.switchToDefaultContent();
	}

	//TODO figure out how to distribute this among the relevant pages, as well as other buttons which are only in some pages???
	//	maybe have enum of such buttons, then each menu page has component (menuControlPane?) which has code for all such buttons
	//	and also has an enumset of which of those buttons are actually on that page

	/**
	 * Click on delete button (This action for delete,created record)
	 */
	public void clickDeleteButton( )
	{
		waitForPageLoad();
		click.clickElementCarefully(DELETE_BUTTON, true);
	}

	/***
	 * Select address cleansing option
	 * @param dropDown
	 * @param option
	 */
	public void selectAddressCleansing( final String dropDown, final String option )
	{
		By DROP_DOWN = By.xpath(String.format(ACTIONS_DROP_DOWN, dropDown));
		By OPTION = By.xpath(String.format(ADDRESS_CLEASING_OPTION_FROM_ACTIONS, option));
		click.clickElementCarefully(DROP_DOWN);
		click.clickElementCarefully(OPTION, true);
	}

	/***
	 * Verify address cleansing option state
	 * @param dropDown
	 * @param option
	 * @return
	 */
	public boolean isEnabledAddressCleansingOption( final String dropDown, final String option )
	{
		boolean status;
		waitForPageLoad();
		By DROP_DOWN = By.xpath(String.format(ACTIONS_DROP_DOWN, dropDown));
		By OPTION = By.xpath(String.format(ADDRESS_CLEASING_OPTION_FROM_ACTIONS, option));
		click.clickElementCarefully(DROP_DOWN);
		status = element.isElementDisplayed(OPTION);
		return status;
	}

	/***
	 * Get warehouse id
	 * @return
	 */
	public String getWarehouseId( )
	{
		wait.waitForElementDisplayed(WAREHOUSE_ID);
		String warehouseId = attribute.getElementAttribute(WAREHOUSE_ID, "value");
		return warehouseId;
	}

	/***
	 * click on acumatica sign out option(it should return to acumatica login page)
	 */
	public void signOutFromAcumatica( )
	{
		click.clickElementCarefully(ADMIN);
		click.clickElementCarefully(SIGN_OUT);
		wait.waitForElementDisplayed(LOGIN_BUTTON);
	}

	/***
	 * Get vertex postal address pop up
	 * @param columnName
	 * @return
	 */
	public String getVertexPostalAddressPopup( final String columnName )
	{
		wait.waitForElementDisplayed(VERTEXPOSTALADDRESS);
		By locator = By.xpath(
			"//div[@id='ctl00_phG_PXSmartPanel13_PXGrid10_headerDiv']/../../following-sibling::tr[1]//tr[@id='ctl00_phG_PXSmartPanel13_PXGrid10_row_0']//td[position() = count(//div[contains(text(), '" +
			columnName + "')] /../../preceding-sibling::td)+1]");
		String vertex_postal_address = attribute.getElementAttribute(locator, "value");
		return vertex_postal_address;
	}

	/***
	 * Select option from action drop down
	 * @param option
	 */
	public void selectOptionFromActionDropDown( final String option )
	{
		click.clickElementCarefully(ACTION_DROP_DROPDOWN);
		if ( option.equalsIgnoreCase(confirm) )
		{
			By option_locator = By.xpath("(//td[text()='" + option + "'])[2]");
			click.clickElementCarefully(option_locator);
		}
		else if ( option.equalsIgnoreCase(ignore) )
		{
			By option_locator = By.xpath("(//td[text()='" + option + "'])[1]");
			click.clickElementCarefully(option_locator);
		}
	}

	/***
	 * Click on 'OK' button
	 */
	public void clickOkButton( )
	{
		click.clickElementCarefully(OK_BUTTON);
	}

	/**
	 * clicks on the 'ok' button in the currently-displayed popup to dismiss the popup
	 */
	public void clickPopupOkButton( )
	{
		WebElement okButton = getPopupOkButton();

		click.clickElementCarefully(okButton);
	}

	/**
	 * fetches the 'ok' button in the currently-displayed popup
	 *
	 * @return the 'ok' button in the currently-displayed popup
	 */
	protected WebElement getPopupOkButton( )
	{
		WebElement okButton = null;

		List<WebElement> popups = wait.waitForAnyElementsDisplayed(popupContainer);

		buttonSearch:
		for ( WebElement popup : popups )
		{
			if ( element.isElementDisplayed(popup) )
			{
				List<WebElement> buttonPanels = wait.waitForAllElementsPresent(popupButtonsContainer, popup);

				for ( WebElement panel : buttonPanels )
				{
					List<WebElement> buttons = wait.waitForAnyElementsDisplayed(By.tagName("button"), panel);
					okButton = element.selectElementByText(buttons, popupOkButtonText);
					if ( okButton != null )
					{
						break buttonSearch;
					}
				}
			}
		}

		return okButton;
	}

	/**
	 * Close address cleansing pop up
	 */
	public void closeAddressCleansingPopUp( )
	{
		click.clickElementCarefully(CLOSE_VERTEX_POSTAL_ADDRESS_POPUP);
	}

	/**
	 * Switch to window (switching window based on 'page name')
	 */
	public void switchToParentWindow( String pageName )
	{
		switchToParentWindow(pageName);
	}
}

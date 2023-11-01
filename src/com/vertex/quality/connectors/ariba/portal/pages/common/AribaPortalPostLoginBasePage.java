package com.vertex.quality.connectors.ariba.portal.pages.common;

import com.vertex.quality.connectors.ariba.portal.dialogs.requisition.AribaPortalEditLineItemContactDialog;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalDashboardTab;
import com.vertex.quality.connectors.ariba.portal.enums.AribaPortalManageListPage;
import com.vertex.quality.connectors.ariba.portal.interfaces.AribaPortalTextField;
import com.vertex.quality.connectors.ariba.portal.pages.catalog.AribaPortalCatalogHomePage;
import com.vertex.quality.connectors.ariba.portal.pages.invoice.AribaPortalInvoiceCreationPage;
import org.openqa.selenium.*;

import java.util.List;

/**
 * generic representation of a webpage on Ariba's Portal website other than the sign-in page
 * encapsulates features common to all 'pages' (other than login) on Ariba's Portal site
 *
 * @author ssalisbury, osabha
 * also some imported legacy code from the person who started automating Ariba Portal in python
 */
public abstract class AribaPortalPostLoginBasePage extends AribaPortalBasePage
{
	protected final By dialogClass = By.className("w-dlg-dialog");
	protected final By recentSearchBlocksLoc = By.className("cat_section_title");
	protected final By searchFieldLoc = By.cssSelector("input[aria-label='Search for a specific value']");
	protected final By searchButtonLoc = By.cssSelector("button[title='Search for a specific value in the list']");
	protected final By inputFieldSection = By.cssSelector("tr[bh='ROV']");
	protected final By tableDataLabelContainer = By.className("ffl");
	protected final By updatePopup = By.className("awwaitAlertDiv");
    protected final By createButtonLoc = By.cssSelector("div[class='a-mastCmd-create-button aw7_a-mastCmd-create-button']");
	protected final By headerNavBarContainer = By.className("w-navigation-bar-separator");
	protected final By createOptionsListContLoc = By.id("Create");
	protected final By catalogPageTitle = By.xpath("//*/table//*/h1[contains(text(),'Catalog Home')]");
	protected final By userNameListLoc = By.id("Preferences");
	protected final By usernameLinkLoc = By.className("w-header-username-link");
	protected final By manageButtonContLoc = By.className("a-nav-bar-cmds");
	protected final By manageListContLoc = By.id("Manage");
	public AribaPortalEditLineItemContactDialog contactDialog;

	public AribaPortalPostLoginBasePage( WebDriver driver )
	{

		super(driver);
		this.contactDialog = new AribaPortalEditLineItemContactDialog(driver, this);
	}

	/**
	 * waits for the page to finish loading
	 * Note- this is rather slower than the normal waitForPageLoad() because (some or all of) ariba
	 * portal's pages will sometimes load and reload part or all of the page repeatedly, so
	 * additional checks are necessary to avoid stale element exceptions
	 *
	 * @author ssalisbury
	 */
	@Override
	public void waitForPageLoad( )
	{
		super.waitForPageLoad();
		wait.waitForElementNotDisplayed(updatePopup);
	}

	/**
	 * waits for a short time for an 'update' popup to be displayed, then waits for it to disappear
	 *
	 * @author ssalisbury
	 */
	public void waitForUpdate( )
	{
		try
		{
			waitForPageLoad();
			wait.waitForElementDisplayed(updatePopup, THREE_SECOND_TIMEOUT);
		}
		catch ( Exception e )
		{
		}

		wait.waitForElementNotDisplayed(updatePopup);
	}

	/**
	 * Open the Catalog page
	 *
	 * @return the resulting catalog page
	 */
	public AribaPortalCatalogHomePage openCatalog( )
	{
		AribaPortalCatalogHomePage catalogPage;
		try
		{
			wait.waitForAllElementsPresent(recentSearchBlocksLoc, FIVE_SECOND_TIMEOUT);
		}
		catch ( TimeoutException e )
		{
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		/* Clicks the Catalog tab in the header to navigate to the Catalog */
		openHeaderTabs(AribaPortalDashboardTab.CATALOG.tabText);

		wait.waitForElementDisplayed(catalogPageTitle);
		catalogPage = initializePageObject(AribaPortalCatalogHomePage.class);
		return catalogPage;
	}

	/**
	 * Opens a tab in the header
	 *
	 * @param tabLabel the specific tab to open
	 */
	public void openHeaderTabs( final String tabLabel )
	{
		this.refreshPage();

		try
		{
			tryOpenHeaderTab(tabLabel);
		}
		catch ( StaleElementReferenceException e )
		{
			this.refreshPage();
			tryOpenHeaderTab(tabLabel);
		}

		// ariba does weird stuff when switching tabs, hard to pinpoint root cause,
		// don't have time to debug - Dave G.
		try
		{
			Thread.sleep(1000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Helper method to help open header tab, created to avoid code duplication
	 *
	 * @param tabLabel
	 */
	public void tryOpenHeaderTab( String tabLabel )
	{
		WebElement targetElement = null;
		WebElement header = wait.waitForElementDisplayed(headerNavBarContainer);
		List<WebElement> tabs = wait.waitForAllElementsPresent(By.tagName("a"), header);
		for ( WebElement tabElement : tabs )
		{
			if ( tabElement.isDisplayed() )
			{
				String rawText = text.getElementText(tabElement);
				if ( rawText != null )
				{
					String cleanText = text.cleanseWhitespace(rawText);
					if ( cleanText.contains(tabLabel) )
					{
						targetElement = tabElement;
						break;
					}
				}
			}
		}

		if ( targetElement != null )
		{
			click.clickElement(targetElement);
		}
	}

	/**
	 * locates the WebElement of Create button from the top right tabs
	 */
	public WebElement findCreateButton( )
	{
		WebElement createButton = wait.waitForElementPresent(createButtonLoc);
		wait.waitForElementEnabled(createButton);
		return createButton;
	}

	/**
	 * locates and clicks on create button and then selects invoice from the list of options
	 *
	 * @return new instance of the invoice creation page.
	 */
	public AribaPortalInvoiceCreationPage startNewInvoice( )
	{
		WebElement invoiceOption = null;

		try
		{
			WebElement createButton = findCreateButton();
			click.clickElement(createButton);
			waitForPageLoad();
		}
		catch ( StaleElementReferenceException e )
		{
			waitForPageLoad();
			WebElement createButton = findCreateButton();
			click.clickElement(createButton);
			waitForPageLoad();
		}

		WebElement optionsListCont = wait.waitForElementPresent(createOptionsListContLoc);
		if ( !optionsListCont.isDisplayed() )
		{
			WebElement createButton = findCreateButton();
			click.clickElement(createButton);
			waitForPageLoad();
		}

		List<WebElement> options = wait.waitForAnyElementsDisplayed(By.tagName("a"), optionsListCont);
		invoiceOption = element.selectElementByText(options, "Invoice");
		click.clickElementCarefully(invoiceOption);
		return this.initializePageObject(AribaPortalInvoiceCreationPage.class);
	}

	/**
	 * clears a text input field and 'types' a new string value into it
	 * has to handle a dialog that sometimes pops up when trying to edit a subset of
	 * text input fields that are also combo-boxes
	 *
	 * @param inputText the new string to be entered into the field
	 * @param field     the field whose contents will be replaced with the param text
	 *
	 * @author ssalisbury osabha
	 */
	public void writeTextField( String inputText, AribaPortalTextField field )
	{
		if ( field.isCombobox() )
		{
			WebElement fieldElem = findTextField(field);
			fieldElem.sendKeys(Keys.ENTER);

			WebElement dialog = wait.waitForElementDisplayed(dialogClass);
			WebElement searchField = wait.waitForElementEnabled(searchFieldLoc, dialog);
			text.enterText(searchField, inputText);
			WebElement searchButton = wait.waitForElementEnabled(searchButtonLoc, dialog);
			click.clickElement(searchButton);
			contactDialog.locateAndClickSelectButton();
		}
		else
		{
			try
			{
				WebElement fieldElem = findTextField(field);
				text.setTextFieldCarefully(fieldElem, inputText);
			}
			catch ( StaleElementReferenceException e )
			{
				waitForPageLoad();
				WebElement fieldElem = findTextField(field);
				text.setTextFieldCarefully(fieldElem, inputText);
			}
		}
	}

	/**
	 * finds a text input element on the page
	 *
	 * @param field description of the text input field whose WebElement this is finding
	 *
	 * @return that text input element
	 *
	 * @author ssalisbury
	 */
	protected WebElement findTextField( AribaPortalTextField field )
	{
		waitForUpdate();
		String expectedLabel = field.getLabel();
		WebElement expectedFieldLabelElement = null;
		WebElement fieldElem = null;

		List<WebElement> inputFieldSections = wait.waitForAllElementsPresent(inputFieldSection);

		search:
		for ( WebElement inputFieldSection : inputFieldSections )
		{
			List<WebElement> labelContainers = inputFieldSection.findElements(tableDataLabelContainer);

			for ( WebElement labelContainer : labelContainers )
			{
				List<WebElement> labels = labelContainer.findElements(By.tagName("label"));

				expectedFieldLabelElement = element.selectElementByText(labels, expectedLabel);
				if ( expectedFieldLabelElement != null )
				{
					break search;
				}
			}
		}

		if ( expectedFieldLabelElement != null )
		{
			String fieldId = attribute.getElementAttribute(expectedFieldLabelElement, "for");
			if ( fieldId != null )
			{
				fieldElem = wait.waitForElementPresent(By.id(fieldId));
			}
		}

		return fieldElem;
	}

	/**
	 * locates he Manage button from the navigation bar
	 *
	 * @return manage button WebElement
	 */
	public WebElement findManageButton( )
	{
		WebElement manageButtonCont = wait.waitForElementPresent(manageButtonContLoc);
		List<WebElement> manageButtonTags = wait.waitForAllElementsPresent(By.tagName("a"), manageButtonCont);
		WebElement manageButton = element.selectElementByText(manageButtonTags, "Manage");
		wait.waitForElementEnabled(manageButton);
		return manageButton;
	}

	/**
	 * does the navigation to the any of the manage list options pages
	 *
	 * @return new instance of that page
	 */
	public <T extends AribaPortalPostLoginBasePage> T navigateToManageListPages( AribaPortalManageListPage page )
	{
		waitForUpdate();
		WebElement manageButton = findManageButton();
		click.clickElementCarefully(manageButton);
		WebElement optionsListCont = wait.waitForElementPresent(manageListContLoc);
		if ( !optionsListCont.isDisplayed() )
		{
			this.refreshPage();
			WebElement button = findManageButton();
			click.clickElementCarefully(button);
		}
		List<WebElement> listOptions = wait.waitForAllElementsDisplayed(By.tagName("a"), optionsListCont);
		WebElement coreAdministration = element.selectElementByText(listOptions, page.getPageName());
		click.clickElementCarefully(coreAdministration);
		Class pageClass = page.getPageClass();
		return initializePageObject(pageClass);
	}

	/**
	 * locates and clicks on the user name link and then clicks logout
	 * to log out of the portal.
	 *
	 * @return new instance of the Ariba Portal login Page
	 */
	public AribaPortalLoginPage logout( )
	{
		waitForUpdate();
		WebElement usernameLink = wait.waitForElementPresent(usernameLinkLoc);
		scroll.scrollElementIntoView(usernameLink);
		click.clickElementCarefully(usernameLink);
		WebElement listCont = wait.waitForElementPresent(userNameListLoc);
		List<WebElement> aTags = wait.waitForAllElementsPresent(By.tagName("a"), listCont);
		WebElement logoutButton = element.selectElementByText(aTags, "Logout");
		click.clickElementCarefully(logoutButton);
		return initializePageObject(AribaPortalLoginPage.class);
	}
}

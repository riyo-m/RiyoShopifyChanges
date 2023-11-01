package com.vertex.quality.connectors.netsuite.common.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteNavigationMenuTitles;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuitePageTitles;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteGlobalSearchResultsPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteRolePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.tests.base.NetsuiteBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the navigation bar on every page of the Netsuite website. This
 * navigation pane contains all the elements that allow a user to navigate to
 * different parts of the website
 *
 * @author hho
 */
public class NetsuiteNavigationPane extends NetsuiteComponent
{
	protected final By mainNavigationBarLocator = By.id("ns-header-menu-main");
	protected final String hoverableMenuItemClass = "ns-menuitem";
	protected final String roleMenuText = "spn_cRR_d1";
	protected final By roleMenu = By.id(roleMenuText);
	protected final By defaultRoleId = By.id("ns-role-company");

	protected final By singleCompany = By.xpath("//div[@id='spn_cRR_d1']//span[contains(text(), 'Vertex QA - Single Company')]");
	protected final String oneWorld ="Vertex QA - OneWorld (TSTDRV1505402 )  -  Administrator";
	protected final By OW = By.xpath("//div[@id='spn_cRR_d1']//span[contains(text(),'Vertex QA - OneWorld')]");
	protected final By NetsuiteAPI = By.xpath("//div[@id='spn_cRR_d1']//span[contains(text(), 'Vertex SuiteTax QA (TSTDRV1847016)')]");
	protected final By suiteTaxLeading = By.xpath("//div[@id='spn_cRR_d1']//span[contains(text(), 'Vertex Suitetax Leading TSTDRV2480179')]");

	protected final By scrollDownButtonLocator = By.className("ns-scroll-button-bottom");
	protected final By searchFieldLocator = By.id("_searchstring");
	protected final By singleCompanyRP = By.xpath("//div[@id='spn_cRR_d1']//span[contains(text(), 'Vertex QA - Single Company - RP  -  Administrator')]");
	protected final By oneWorldRP = By.xpath("//div[@id='spn_cRR_d1']//span[contains(text(),'Vertex QA - OneWorld (TSTDRV1505402 ) - RP  -  Administrator')]");
	public final By newInstallEnv= By.xpath("//div[@id='spn_cRR_d1']//span[contains(text(),'VERTEX QA OW TRAILING New Install')]");
	protected final By oneWorldUpgrade = By.xpath("//div[@id='spn_cRR_d1']//span[contains(text(), 'OneWorld Bundle Upgrade')]");
	protected final By viewRole = By.id("ns-header-menu-userrole-item1");


	public NetsuiteNavigationPane( final WebDriver driver, final NetsuitePage parent )
	{
		super(driver, parent);
	}

	/**
	 * Uses the dropdowns in Netsuite to get to pages
	 * @param navigationMenu
	 * @param <T>
	 * @return
	 */
	public <T extends NetsuitePage> T navigateThrough( NetsuiteNavigationMenus navigationMenu )
	{
		List<NetsuiteNavigationMenuTitles> navigationMenuTitles = navigationMenu.getMenus();
		WebElement mainNavigationBar = wait.waitForElementDisplayed(mainNavigationBarLocator, 5);
		WebElement parentNavigationMenu = null;
		String parentNavigationMenuTitle = null;

			int index = 0;
			for(NetsuiteNavigationMenuTitles title: navigationMenuTitles) {
				index++;
				//Get parent element
				parentNavigationMenuTitle = title.getTitle();
				By locator = By.xpath("//li[@data-title='"+parentNavigationMenuTitle+"']");
				WebElement menuButton = element.getWebElement(locator);

				wait.waitForElementDisplayed(locator);
				//Check if we are at final element
				if(index < navigationMenuTitles.size() ) {
					hover.hoverOverElement(menuButton);
				}else {
					click.clickElement(menuButton);
				}
			}

		// Wait 3 seconds for Alert Popup
		if ( alert.waitForAlertPresent(1) )
		{
			alert.acceptAlert();
		}

		return initializePageObject(NetsuiteBaseTest.pageFactory.getPageClass());
	}

	/**
	 * Navigate directly to the page you're looking for
	 * Enter name of the page? and it returns a page object like the original method
	 * PAGE is a String that references the enum?
	 * Use this method for bulk of api navigation
	 * This saves time by directly navigating to the link instead of using the in page menus
	 * The enum should contain the most commonly used page endpoints
	 * Still a work in progress...
	 * @param Page
	 * @param <T>
	 * @return
	 */
	public <T extends NetsuitePage> T navigateTo( String Page ){

		driver.navigate().to(buildUrl(Page));
		waitForPageLoad();
		return initializePageObject(NetsuiteBaseTest.pageFactory.getPageClass());
	}

	/**
	 * Add arguments that will get concatenated to the Url String
	 * Useful for navigating directly to specific records
	 * @param Page
	 * @param args
	 * @param <T>
	 * @return
	 */
	public <T extends NetsuitePage> T navigateTo( String Page, String args ){
		String url = buildUrl(Page);
		driver.navigate().to(url+args);
		waitForPageLoad();
		return initializePageObject(NetsuiteBaseTest.pageFactory.getPageClass());
	}

	/**
	 * Build page url using current session id and page endpoint
	 * @param Page
	 * @return
	 */
	private String buildUrl(String Page){
		String url = "https://" + getSessionId() + "/app" + getPageEndpoint(Page);
		return url;
	}

	/**
	 * Get the session id **it's not really the session id
	 * @return
	 */
	private String getSessionId(){
		String id = driver.getCurrentUrl().replaceFirst("https://", "").split("/app*")[0];
		return id;
	}

	/**
	 * Get the page endpoint from the page Enum
	 * @param input
	 * @return
	 */
	private String getPageEndpoint(String input){
		String url = com.vertex.quality.connectors.netsuite.common.enums.NetsuitePage.valueOf(input).getUrl();
		return  url;
	}

	/**
	 * Gets the specific navigation menu on the main navigation bar
	 *
	 * @param parentNavigationMenu the navigation menu
	 *
	 * @return the specific navigation menu on the main navigation bar
	 */
	private WebElement getParentNavigationMenu( WebElement navigationMenuButton,
		NetsuiteNavigationMenuTitles parentNavigationMenu )
	{
		String parentNavigationMenuTitle = parentNavigationMenu.getTitle();
		WebElement mainNavigationBar = wait.waitForElementDisplayed(navigationMenuButton,3);
		WebElement specificParentNavigationMenu = null;

		while ( specificParentNavigationMenu == null )
		{
			List<WebElement> navigationMenus = wait.waitForAllElementsPresent(By.tagName("li"), mainNavigationBar,5);

			search:
			for ( WebElement navigationMenu : navigationMenus )
			{
				String navigationMenuClass = attribute.getElementAttribute(navigationMenu, "class");
				String navigationMenuTitle = attribute.getElementAttribute(navigationMenu, "data-title");
				if ( parentNavigationMenuTitle.equals(navigationMenuTitle) )
				{
					specificParentNavigationMenu = navigationMenu;
					break search;
				}
				if ( navigationMenuClass != null && navigationMenuClass.contains(hoverableMenuItemClass) )
				{
					if ( element.isElementDisplayed(navigationMenu) )
					{
						hover.hoverOverElement(navigationMenu);
					}
				}
			}

			if ( specificParentNavigationMenu == null )
			{
				scrollDownInNavigationMenu();
			}
		}

		return specificParentNavigationMenu;
	}

	/**
	 * Scrolls down in the navigation menu
	 */
	private void scrollDownInNavigationMenu( )
	{
		if ( element.isElementDisplayed(scrollDownButtonLocator) )
		{
			hover.hoverOverElement(scrollDownButtonLocator);
		}
	}

	/**
	 * Gets the page title enum
	 *
	 * @return the page title enum
	 */
	private NetsuitePageTitles getPage( )
	{
		NetsuitePageTitles page = null;
		String pageTitle = parent.getPageTitle();

		for ( NetsuitePageTitles pageTitles : NetsuitePageTitles.values() )
		{
			if ( pageTitles
					.getPageTitle()
					.equals(pageTitle) )
			{
				page = pageTitles;
			}
		}

		return page;
	}

	/**
	 * Sign in as any role
	 * Use input from the cmd line to specify your desired user role
	 * [inputRole] is passed into the test run from -DnetsuiteEnvironment
	 * [inputRole] should correspond to one of NetsuiteNavigationPane's fields, which are listed above
	 * @return the home page for specified user role
	 */
	public NetsuiteHomepage signInAs(String desiredRole)
	{
		Field targetUserRole;
		WebElement defaultUserRoleElement;
		WebElement UserRoleElement;
		By selectedUserRole = OW;

		try {
			if (desiredRole != null)
			{
				targetUserRole = NetsuiteNavigationPane.class.getDeclaredField(desiredRole);
				VertexLogger.log("Target Environment: " + targetUserRole, VertexLogLevel.INFO);
			}

			WebElement roleMenuElement = wait.waitForElementDisplayed(roleMenu);

			hover.hoverOverElement(roleMenuElement);
			//Get environment input from cmd line
			if (desiredRole != null) {
				VertexLogger.log("Target Environment: " + desiredRole, VertexLogLevel.INFO);
				for (Field e : NetsuiteNavigationPane.class.getDeclaredFields()) {
					if (e.getName().equals(desiredRole ) ) {
						selectedUserRole = (By) e.get(this);
						UserRoleElement = element.getWebElement(selectedUserRole);
						VertexLogger.log("Target Environment Retrieved: " + selectedUserRole, VertexLogLevel.INFO);
						if(UserRoleElement.getAttribute("class").contains("ns-role-menuitem") )
						{
							wait.waitForElementDisplayed(selectedUserRole);
							click.clickElement(selectedUserRole);
						}
					}
				}
			}
		}
		catch(Exception exception){
			VertexLogger.log(exception.toString(), VertexLogLevel.ERROR);
		}
		return initializePageObject(NetsuiteHomepage.class);
	}

	/**
	 * Signs in as the single company role
	 *
	 * @return the home page for single company
	 */
	public NetsuiteHomepage signInAsSingleCompany( )
	{
		WebElement roleMenuElement = wait.waitForElementDisplayed(roleMenu);
		hover.hoverOverElement(roleMenuElement);
		wait.waitForElementDisplayed(singleCompany);
		click.clickElement(singleCompany);
		return initializePageObject(NetsuiteHomepage.class);
	}

	/**
	 * Signs in as the single company role
	 *
	 * @return the home page for single company
	 */
	public NetsuiteHomepage signInAsSingleCompanyRP( )
	{
		WebElement roleMenuElement = wait.waitForElementDisplayed(roleMenu);
		hover.hoverOverElement(roleMenuElement);
		//Wait till results are populated
		jsWaiter.sleep(250);
		//if RP role exist
		if(element.isElementPresent(singleCompanyRP)){
			// use it
			wait.waitForElementDisplayed(singleCompanyRP);
			click.clickElement(singleCompanyRP);
			return initializePageObject(NetsuiteHomepage.class);
		}
			//else find Normal role
			signInAsSingleCompany();

			return initializePageObject(NetsuiteHomepage.class);
	}

	/**
	 * Signs in as the one world role
	 *
	 * @return the home page for one world
	 */
	public NetsuiteHomepage signInAsOneWorld( )
	{
		//WebElement menuRoleButton = getMenuRoleButton(oneWorld);

		//wait.waitForElementDisplayed(menuRoleButton, 5);
		//click.clickElement(menuRoleButton);

		return initializePageObject(NetsuiteHomepage.class);
	}

	/**
	 * Signs in as the one world role
	 *
	 * @return the home page for one world
	 */
	public NetsuiteHomepage signInAsNetsuiteAPI( )
	{
		try {
			By env = NetsuiteAPI;
			WebElement roleMenuElement = wait.waitForElementDisplayed(roleMenu);
			hover.hoverOverElement(roleMenuElement);

			if (System.getProperty("netsuiteEnvironment") != null) {
				VertexLogger.log("Target Environment: " + System.getProperty("netsuiteEnvironment"), VertexLogLevel.INFO);
				for (Field e : NetsuiteNavigationPane.class.getDeclaredFields()) {
					if (e.getName().equals(System.getProperty("netsuiteEnvironment") ) ) {
						env = (By) e.get(this);
					}
				}
				VertexLogger.log("Target Environment Retrieved: " + env, VertexLogLevel.INFO);
			}
			wait.waitForElementDisplayed(env);
			click.clickElement(env);
		}
		catch(Exception exception){
			VertexLogger.log(exception.toString(), VertexLogLevel.ERROR);
		}
		return initializePageObject(NetsuiteHomepage.class);
	}

	/**
	 * Signs in as the one world role
	 *
	 * @return the home page for one world
	 */
	public NetsuiteHomepage signInAsOneWorldRP( )
	{
		WebElement roleMenuElement = wait.waitForElementDisplayed(roleMenu);
		hover.hoverOverElement(roleMenuElement);
		//Wait till results are populated
		jsWaiter.sleep(250);
		//if RP role exist
		if(element.isElementPresent(oneWorldRP)){
			// use it
			wait.waitForElementDisplayed(oneWorldRP);
			click.clickElement(oneWorldRP);
			return initializePageObject(NetsuiteHomepage.class);
		}
			//else find Normal role
			signInAsOneWorld();

			return initializePageObject(NetsuiteHomepage.class);
	}

	/**
	 * Gets the menu role button
	 *
	 * @param roleTitle the role's title
	 *
	 * @return the menu role button
	 */
	private WebElement getMenuRoleButton( String roleTitle )
	{
		WebElement roleMenuElement = wait.waitForElementDisplayed(roleMenu);
		hover.hoverOverElement(roleMenuElement);
		WebElement specificMenuRoleButton = null;

		specificMenuRoleButton = element.getWebElement(By.xpath("//div[@id='spn_cRR_d1']//li//span[contains(text(),'"+roleTitle+"')]") );

		List<WebElement> menuRoleButtons = wait.waitForAllElementsDisplayed(By.xpath("//li//span[contains(text(),'"+roleTitle+"')]"), roleMenuElement);
		for ( WebElement menuRoleButton : menuRoleButtons )
		{
			List<WebElement> menuRoleButtonSpans = menuRoleButton.findElements(By.tagName("span"));
			List<String> text = new ArrayList<>();
			if ( menuRoleButtonSpans.size() == 1 )
			{
				for (WebElement role: menuRoleButtonSpans )
				{
					Optional<String> it = Optional.of( role.getAttribute("innerHTML") );
					it.ifPresent((i) -> text.add(role.getAttribute("innerHTML") ) );
				}
					specificMenuRoleButton = element.selectElementByText(menuRoleButtonSpans, roleTitle);
				if (specificMenuRoleButton != null )
				{
					break;
				}
			}
		}
			return specificMenuRoleButton;
	}

	/**
	 * Searches for certain pages using the search bar in the navigation bar
	 * Use only if the navigateThrough would not work in certain situations (scrolling down the submenu)
	 *
	 * @param navigationMenuTitles the search criteria
	 *
	 * @return the global search results page
	 */
	public NetsuiteGlobalSearchResultsPage search( NetsuiteNavigationMenuTitles navigationMenuTitles )
	{
		text.enterText(searchFieldLocator, navigationMenuTitles.getTitle());
		text.pressEnter(searchFieldLocator);
		return initializePageObject(NetsuiteGlobalSearchResultsPage.class);
	}

	/**
	 * Visits the view role page
	 *
	 * @return the View Roles page
	 */
	public NetsuiteRolePage viewRolePage( )
	{
		WebElement roleMenuElement = wait.waitForElementDisplayed(roleMenu);
		hover.hoverOverElement(roleMenuElement);
		jsWaiter.sleep(250);
		click.clickElement(viewRole);
		return initializePageObject(NetsuiteRolePage.class);
	}

}

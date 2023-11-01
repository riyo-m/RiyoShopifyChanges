package com.vertex.quality.connectors.magentoTap.admin.components;

import com.vertex.quality.connectors.magentoTap.admin.pages.*;
import com.vertex.quality.connectors.magentoTap.common.components.MagentoComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Navigation Panel on left side of page
 *
 * @author alewis
 */
public class M2AdminNavigationPanel extends MagentoComponent {
    protected By storesButtonID = By.id("menu-magento-backend-stores");
    protected By salesButtonID = By.id("menu-magento-sales-sales");
    protected By customersButtonID = By.id("menu-magento-customer-customer");

    protected By catalogButtonID = By.id("menu-magento-catalog-catalog");
    protected By inventoryButtonClass = By.className("item-inventory");
    protected By productsButtonClass = By.className("item-catalog-products");

    protected By loggedInHeaderTag = By.className("submenu-title");

    protected By marketingButtonID = By.id("menu-magento-backend-marketing");

    protected By configButtonClass = By.className("item-system-config");
    protected By taxRulesButtonClass = By.className("item-sales-tax-rules");
    protected By ordersButtonClass = By.className("item-sales-order");
    protected By allCustomersButtonClass = By.className("item-customer-manage");
    protected By customerGroupsButtonClass = By.className("item-customer-group");

    protected By cartPriceRulesClass = By.className("item-promo-quote");

    protected By systemButtonID = By.id("menu-magento-backend-system");
    protected By submenuClass = By.className("submenu");
    protected By submenuColumns = By.className("column");
    protected By toolsClass = By.className("item-system-tools");
    protected By toolsSubmenuClass = By.className("submenu");
    protected By cacheClass = By.className("item-system-cache");
    By productTable = By.xpath("//div[@class='admin__data-grid-wrap']//table[@class='data-grid data-grid-draggable']//tbody//tr[@class='data-row']");
    By productTableNotDisplayed = By.xpath("//div[@class='admin__data-grid-wrap']//tbody//tr//td[contains(text(),\"We couldn't find any records.\")]");
    By maskClass = By.className("loading-mask");
    By spinner = By.className("spinner");

    protected By partnersID = By.id("menu-magento-marketplace-partners");

    protected By footLegalContainer = By.className("footer-legal");

    protected String innerHMTL = "innerHTML";

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminNavigationPanel(WebDriver driver) {
        super(driver);
    }

    /**
     * checks to see if the Stores Button is visible on M2AdminNavigationPanel
     *
     * @return boolean telling if Stores Button is present
     */
    public boolean isStoresButtonVisible() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement storesButton = findStoresButton();

        boolean isButtonPresent = storesButton != null && storesButton.isDisplayed();

        return isButtonPresent;
    }

    /**
     * test to see if the SalesButton is visible on M2AdminNavigationPanel
     *
     * @return boolean telling if Sales Button is present
     */
    public boolean isSalesButtonVisible() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement salesButton = findSalesButton();

        boolean isSalesButtonPresent = salesButton != null && salesButton.isDisplayed();

        return isSalesButtonPresent;
    }

    /**
     * test to see if the CustomersButton is visible on M2AdminNavigationPanel
     *
     * @return boolean telling if Customers Button is present
     */
    public boolean isCustomersButtonVisible() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement customersButton = findCustomersButton();

        boolean isCustomersButtonPresent = customersButton != null && customersButton.isDisplayed();

        return isCustomersButtonPresent;
    }

    /**
     * clicks the Stores Button on navigation panel
     *
     * @return M2AdminHomepage with Stores tab of Navigation Panel open
     */
    public M2AdminHomepage clickStoresButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(findStoresButton());
        WebElement vertexServicesButton = findStoresButton();

        if (vertexServicesButton != null) {
            click.clickElementCarefully(vertexServicesButton);
        } else {
            String errorMsg = "Stores button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminHomepage.class);
    }

    /**
     * clicks the Sales Button on navigation panel
     *
     * @return M2AdminHomepage with Sales tab of Navigation Panel open
     */
    public M2AdminHomepage clickSalesButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement vertexServicesButton = findSalesButton();

        if (vertexServicesButton != null) {
            vertexServicesButton.click();
        } else {
            String errorMsg = "Sales button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminHomepage.class);
    }

    /**
     * clicks the CustomersButton on navigation panel
     *
     * @return M2AdminHomepage with Customers tab of Navigation Panel open
     */
    public M2AdminHomepage clickCustomersButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(findCustomersButton());
        WebElement vertexServicesButton = findCustomersButton();

        if (vertexServicesButton != null) {
            click.clickElement(vertexServicesButton);
        } else {
            String errorMsg = "Customers button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminHomepage.class);
    }

    /**
     * clicks the System Button on navigation panel
     *
     * @return M2AdminHomepage with System tab of Navigation Panel open
     */
    public M2AdminHomepage clickSystemButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement vertexServicesButton = findSystemButton();

        if (vertexServicesButton != null) {
            click.clickElementCarefully(vertexServicesButton);
        } else {
            String errorMsg = "System button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminHomepage.class);
    }

    /**
     * clicks the Catalog Button on navigation panel
     *
     * @return M2AdminHomepage with Catalog tab of Navigation Panel open
     */
    public M2AdminHomepage clickCatalogButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(findCatalogButton());
        WebElement vertexServicesButton = findCatalogButton();

        if (vertexServicesButton != null) {
            click.clickElement(vertexServicesButton);
        } else {
            String errorMsg = "Catalog button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminHomepage.class);
    }

    /**
     * clicks the Marketing Button on navigation panel
     *
     * @return M2AdminHomepage with Marketing tab of Navigation Panel open
     */
    public M2AdminHomepage clickMarketingButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement marketingButton = wait.waitForElementPresent(marketingButtonID);

        marketingButton.click();

        return initializePageObject(M2AdminHomepage.class);
    }

    /**
     * clicks the Config Button on navigation panel
     *
     * @return the configuration page
     */
    public M2AdminConfigPage clickConfigButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(findConfigButton());
        WebElement vertexConfigButton = findConfigButton();

        if (vertexConfigButton != null) {
            click.clickElement(vertexConfigButton);
        } else {
            String errorMsg = "Config button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminConfigPage.class);
    }

    /**
     * clicks the Tax Rules button on the navigation panel
     *
     * @return the tax rules page
     */
    public M2AdminTaxRulesPage clickTaxRulesButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(findTaxRulesButton());
        WebElement vertexConfigButton = findTaxRulesButton();

        if (vertexConfigButton != null) {
            click.clickElement(vertexConfigButton);
        } else {
            String errorMsg = "Tax Rules button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminTaxRulesPage.class);
    }

    /**
     * clicks the Orders Button on navigation panel
     *
     * @return the Orders Page
     */
    public M2AdminOrdersPage clickOrdersButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement vertexOrdersButton = findOrdersButton();

        if (vertexOrdersButton != null) {
            wait.waitForElementDisplayed(vertexOrdersButton);
            click.clickElement(vertexOrdersButton);
        } else {
            String errorMsg = "Orders button not found";
            throw new RuntimeException(errorMsg);
        }
        wait.waitForElementNotDisplayed(maskClass);
        return initializePageObject(M2AdminOrdersPage.class);
    }

    /**
     * clicks the allCustomers Button on navigation panel
     *
     * @return the All Customers Page
     */
    public M2AdminCustomersPage clickAllCustomersButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement vertexOrdersButton = findAllCustomersButton();

        if (vertexOrdersButton != null) {
            vertexOrdersButton.click();
        } else {
            String errorMsg = "All Customers button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminCustomersPage.class);
    }

    /**
     * clicks the customerGroups Button on navigation panel
     *
     * @return the Customer Groups Page
     */
    public M2AdminCustomerGroupsPage clickCustomerGroupsButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement vertexOrdersButton = findCustomerGroupsButton();

        if (vertexOrdersButton != null) {
            vertexOrdersButton.click();
        } else {
            String errorMsg = "Customer Groups button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminCustomerGroupsPage.class);
    }

    /**
     * clicks the Cache Management Button on navigation panel
     *
     * @return the Cache Management Page
     */
    public M2AdminCacheMgmt clickCacheManagementButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement vertexOrdersButton = findCacheManagementButton();

        if (vertexOrdersButton != null) {
            vertexOrdersButton.click();
        } else {
            String errorMsg = "Cache Management button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminCacheMgmt.class);
    }

    /**
     * clicks the Products Button on navigation panel
     *
     * @return the Products Page
     */
    public M2AdminProductsPage clickProductsButton() {
        M2AdminProductsPage productsPage = new M2AdminProductsPage(driver);
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(findProductsButton());
        WebElement vertexProductsButton = findProductsButton();

        if (vertexProductsButton != null) {
            vertexProductsButton.click();
        } else {
            String errorMsg = "Products button not found";
            throw new RuntimeException(errorMsg);
        }
        waitForPageLoad();
        jsWaiter.waitForLoadAll();
        wait.waitForElementNotDisplayed(maskClass);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
        if (element.isElementDisplayed(spinner)) {
            productsPage.refreshPage();
            waitForPageLoad();
        }
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
        if (productsPage.verifiesProductTable()) {
            wait.waitForElementDisplayed(productTable, DEFAULT_TIMEOUT);
        }
        productsPage.clickClearAllButton();
        return initializePageObject(M2AdminProductsPage.class);
    }

    /**
     * Clicks on card price rule button
     *
     * @return M2AdminCartPriceRulesPage
     */
    public M2AdminCartPriceRulesPage clickCardPriceRulesButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement vertexProductsButton = findCartPriceRulesButton();

        if (vertexProductsButton != null) {
            vertexProductsButton.click();
        } else {
            String errorMsg = "Card Price Rules button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminCartPriceRulesPage.class);
    }

    /**
     * clicks the Partners Button on navigation panel
     *
     * @return the Partners Page
     */
    public M2AdminPartnersPage clickPartnersButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement partnersButton = findPartnersButton();

        if (partnersButton != null) {
            WebElement legalFooter = wait.waitForElementPresent(footLegalContainer);
            scroll.scrollElementIntoView(legalFooter);
            partnersButton.click();
        } else {
            String errorMsg = "Partners button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2AdminPartnersPage.class);
    }

    /**
     * Locates the Stores Button on the navigation panel
     *
     * @return the Stores button
     */
    private WebElement findStoresButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement storesButton = wait.waitForElementDisplayed(storesButtonID);

        return storesButton;
    }

    /**
     * Locates the Sales Button on the navigation panel
     *
     * @return the Sales Button
     */
    private WebElement findSalesButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement salesButton = wait.waitForElementPresent(salesButtonID);

        return salesButton;
    }

    /**
     * Locates the Customers Button on the navigation panel
     *
     * @return the Customers Button
     */
    private WebElement findCustomersButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement customersButton = wait.waitForElementDisplayed(customersButtonID);

        return customersButton;
    }

    /**
     * Locates the System Button on the navigation panel
     *
     * @return System Button
     */
    private WebElement findSystemButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement systemButton = wait.waitForElementPresent(systemButtonID);

        return systemButton;
    }

    /**
     * Locates the Catalog Button on the navigation panel
     *
     * @return the Catalog Button
     */
    private WebElement findCatalogButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(catalogButtonID);
        WebElement catalogButton = wait.waitForElementPresent(catalogButtonID);

        return catalogButton;
    }

    /**
     * Locates the Config Button on the navigation panel
     *
     * @return Config Button
     */
    private WebElement findConfigButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement configButton = wait.waitForElementPresent(configButtonClass);

        return configButton;
    }

    /**
     * Locates the Tax Rules Button on the navigation panel
     *
     * @return Tax Rules Button
     */
    private WebElement findTaxRulesButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(taxRulesButtonClass);
        WebElement taxRulesButton = wait.waitForElementPresent(taxRulesButtonClass);

        return taxRulesButton;
    }

    /**
     * Locates the Orders Button on the navigation panel
     *
     * @return the Orders Button
     */
    private WebElement findOrdersButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement ordersButton = wait.waitForElementPresent(ordersButtonClass);

        return ordersButton;
    }

    /**
     * Locates the allCustomers Button on the navigation panel
     *
     * @return All Customers Button
     */
    private WebElement findAllCustomersButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement allCustomersButton = wait.waitForElementPresent(allCustomersButtonClass);

        return allCustomersButton;
    }

    /**
     * Locates the customerGroups Button on the navigation panel
     *
     * @return the Customer Groups Button
     */
    private WebElement findCustomerGroupsButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement customerGroupsButton = wait.waitForElementPresent(customerGroupsButtonClass);

        return customerGroupsButton;
    }

    /**
     * Locates the Cache Management Button on the navigation panel
     *
     * @return the Cache Management Button
     */
    private WebElement findCacheManagementButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement cacheManageID = wait.waitForElementPresent(systemButtonID);
        WebElement submenuButton = cacheManageID.findElement(submenuClass);
        List<WebElement> columns = submenuButton.findElements(submenuColumns);
        WebElement cacheButton = null;

        for (int i = 0; i < columns.size() && cacheButton == null; i++) {
            WebElement button = columns.get(i);
            WebElement toolsTag = button.findElement(toolsClass);

            WebElement toolsSubmenu = toolsTag.findElement(toolsSubmenuClass);
            WebElement cacheClassButton = toolsSubmenu.findElement(cacheClass);
            cacheButton = cacheClassButton;
        }
        return cacheButton;
    }

    /**
     * Locates the Products Button on the navigation panel
     *
     * @return the Products Button
     */
    private WebElement findProductsButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement catalogID = driver.findElement(catalogButtonID);
        WebElement submenuButton = catalogID.findElement(submenuClass);
        WebElement inventoryButton = submenuButton.findElement(inventoryButtonClass);
        WebElement submenuButton2 = inventoryButton.findElement(submenuClass);
        WebElement productsButton = submenuButton2.findElement(productsButtonClass);
        return productsButton;
    }

    /**
     * Locates the Partners Button
     *
     * @return the Partners Button
     */
    private WebElement findPartnersButton() {
        WebElement partnersButton = wait.waitForElementPresent(partnersID);
        //attribute.waitForElementAttributeChange(partnersButton, innerHMTL, attributeChangeTimeout);
        partnersButton = wait.waitForElementPresent(partnersID);

        WebElement partnersLink = wait.waitForElementPresent(By.tagName("a"), partnersButton);

        return partnersLink;
    }

    private WebElement findCartPriceRulesButton() {
        WebElement priceCarTules = wait.waitForElementDisplayed(cartPriceRulesClass);
        return priceCarTules;
    }

    /**
     * Locates the Products Button on the navigation panel
     *
     * @return String of Title
     */
    public String getPanelTitle() {
        wait.waitForElementNotDisplayed(maskClass);
        String loggedInHeaderText = null;
        waitForPageLoad();
        List<WebElement> headers = driver.findElements(loggedInHeaderTag);

        for (WebElement header : headers) {
            String headerText = header.getText();

            if (headerText != null) {
                loggedInHeaderText = headerText;
                break;
            }
        }

        if (loggedInHeaderText == null) {
            String errorMsg = "Banner of text for having just logged in is not found";
            throw new RuntimeException(errorMsg);
        }

        return loggedInHeaderText;
    }
}
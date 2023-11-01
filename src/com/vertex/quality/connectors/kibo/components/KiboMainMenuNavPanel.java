package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the main menu with the two main tabs ( main and system)
 * contains all the methods necessary to interact with the buttons to navigate to different pages
 *
 * @author osabha
 */
public class KiboMainMenuNavPanel extends VertexComponent {
    public KiboMainMenuNavPanel(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    protected By mainMenuTabsContainerLoc = By.className("taco-primary-menu-ct");
    protected By mainMenuPanel = By.xpath(".//*[@class='x-container x-fit-item x-container-default x-box-layout-ct'][1]");
    protected By mainMenuMainTab = By.xpath(".//a[text()='Main']");
    protected By SiteBuilderButtonLoc = By.cssSelector("a[href='website']");
    protected By orderButtonLoc = By.className("taco-primary-menu-item-link");
    protected By mainMenuTabsLoc = By.className("tab");
    protected By applicationsButtonLoc = By.linkText("Applications");
    protected By locationsButtonLoc = By.cssSelector("a[href='locations']");
    protected By customerAttributesButtonLoc = By.cssSelector(
            "a[href='https://t25858.sandbox.mozu.com/Admin/t-25858/CustomerAttributes']");
    protected By customersButtonLoc = By.cssSelector(
            "a[href='https://t25858.sandbox.mozu.com/Admin/t-25858/Customers']");
    protected By customerButton = By.xpath("//a[contains(text(),'Customers')]");
    protected By customerPageDetails = By.xpath("(.//*[text()='Customers'])[1]//following::table//tr");
    protected By firstName = By.xpath("//*[@class='react-grid-row']");
    protected String beforeXpath = "//table/tbody/tr[";
    protected String afterXpath = "]/td[3]";
    protected By editCustomerButtonClass = By.xpath("//tbody/tr[1]/td[16]/span/ul/li[1]");
    protected By vertexCustomerCode = By.xpath("//input[@name='attributes-Vertex~vertex-customer-code']");
    protected By saveButtonId = By.xpath("//span[contains(text(),'Save')]");
    protected By vertexCustomerClass = By.xpath("(.//label[normalize-space(.)='Vertex Customer Class']//preceding-sibling::div)[1]//div[1]");
    protected By dialogFieldsLoc = By.xpath("//a[@class='mozu-c-selector__item mozu-c-selector__item--dropdown mozu-c-selector__item--flyout mozu-is-dropdown mozu-is-flyout']");
    protected By productButton = By.xpath("//a[contains(text(),'Products')]");
    protected By createNewProductButton = By.xpath("//span[contains(text(),'Create New Product')]");
    protected By createProductTitle = By.xpath("//input[@name='productName']");
    protected By productCodeInput = By.xpath("//input[@name='productCode']");
    protected By priceAmount = By.xpath("//input[@name='price']");
    protected By productTypeId = By.xpath("//input[@name='productTypeId']");
    protected By productUsageButton = By.xpath("//input[@name='productUsage']");
    protected By standardProd = By.xpath("//li[contains(text(),'Configurable Product With Options')]");
    protected By productDropDown = By.xpath("//li[contains(text(),'Bike')]");
    protected By customerPageSummary = By.xpath(".//section[@data-nav-title='Summary']");
    protected By customerPageGeneral = By.xpath(".//section[@data-nav-title='General']");
    protected By customerPageContactInfo = By.xpath(".//section[@data-nav-title='Contact Information']");
    protected By customerPagePaymentInfo = By.xpath(".//section[@data-nav-title='Payment Information']");
    protected By customerPageOrders = By.xpath(".//section[@data-nav-title='Order History']");
    protected By customerPageAudit = By.xpath(".//section[@data-nav-title='Audit Log']");
    protected By customerPageAttribute = By.xpath(".//section[@data-nav-title='Customer Attributes']");
    protected By customerPageGifts = By.xpath(".//section[@data-nav-title='Gift Card & Store Credits']");
    protected By customerPageWishlist = By.xpath(".//section[@data-nav-title='Wishlists']");
    protected By searchProduct = By.xpath(".//input[@placeholder='Search']");
    protected By systemTab = By.xpath(".//a[text()='System']");
    protected String productXpath = ".//div[text()='<<text_replace>>']";

    /**
     * locates the system tab from the main menu
     *
     * @return WebElement of the system tab
     */
    protected WebElement getSystemTab() {
        WebElement systemTab = null;
        String expectedText = "System";
        WebElement mainMenuTabsContainer = wait.waitForElementDisplayed(mainMenuTabsContainerLoc);
        List<WebElement> tabsContainers = wait.waitForAllElementsPresent(mainMenuTabsLoc, mainMenuTabsContainer);
        systemTab = element.selectElementByText(tabsContainers, expectedText);

        return systemTab;
    }

    /**
     * uses the getter method to locate the system tab element and then clicks on it
     */
    public void clickSystemTab() {
        if (!wait.waitForElementPresent(mainMenuPanel, 10).isDisplayed()
                && wait.waitForElementPresent(mainMenuTabsLoc).isDisplayed()) {
            WebElement systemTab = getSystemTab();
            systemTab.click();
        }
    }

    /**
     * Clicks on system tab & go to options on system tab.
     */
    public void gotoSystemTab() {
        WebElement sysTab = wait.waitForElementDisplayed(systemTab);
        click.moveToElementAndClick(sysTab);
    }

    /**
     * gets the main tab from main menu button element
     *
     * @return the main tab from the main menu as WebElement
     */
    protected WebElement getMainTab() {
        WebElement mainTab = null;
        String expectedText = "Main";
        WebElement mainMenuTabsContainer = wait.waitForElementDisplayed(mainMenuTabsContainerLoc);
        List<WebElement> tabsContainers = wait.waitForAllElementsPresent(mainMenuTabsLoc, mainMenuTabsContainer);
        mainTab = element.selectElementByText(tabsContainers, expectedText);

        return mainTab;
    }

    /**
     * uses the getter method to locate the main tab button and then clicks on it
     */
    public void clickMainTab() {
        waitForPageLoad();
        WebElement mainTab = getMainTab();

        mainTab.click();
    }

    /**
     * locates the applications button from the system menu and clicks on it
     *
     * @return new instance of the applicationsPage class
     */
    public KiboApplicationsPage goToApplicationsPage() {
        WebElement applicationsButton = wait.waitForElementPresent(applicationsButtonLoc);

        applicationsButton.click();
        waitForPageLoad();
        return initializePageObject(KiboApplicationsPage.class);
    }

    /**
     * Getter method to locate the html element for the Order Button in the main menu
     *
     * @return the element or orderButton
     */
    protected WebElement getOrdersButton() {
        WebElement orderButton = null;
        String expectedText = "Orders";
        orderButton = element.selectElementByText(orderButtonLoc, expectedText);

        return orderButton;
    }

    /**
     * uses getter method to locate the order button on the main tab from the main menu
     * and then clicks on it
     *
     * @return new instance of the order page class
     */
    public KiboOrdersPage goToOrderPage() {
        WebElement orderButton = getOrdersButton();

        orderButton.click();
        waitForPageLoad();

        return initializePageObject(KiboOrdersPage.class);
    }

    /**
     * Search product
     *
     * @param product pass name which should be searched.
     */
    public void searchProduct(String product) {
        waitForPageLoad();
        WebElement search = wait.waitForElementPresent(searchProduct);
        click.moveToElementAndClick(search);
        text.enterText(search, product);
        text.pressEnter(search);
        waitForPageLoad();
    }

    /**
     * Verify whether product is added to the system or not
     *
     * @param productName pass name which product's availability should be checked
     * @return true or false based on condition.
     */
    public boolean checkProductAvailability(String productName) {
        searchProduct(productName);
        waitForPageLoad();
        WebElement availableProduct = wait.waitForElementPresent(By.xpath(productXpath.replace("<<text_replace>>", productName)));
        return element.isElementDisplayed(availableProduct);
    }

    /**
     * Edit product's attributes
     *
     * @param product pass name which should be edited.
     */
    public void editProduct(String product) {
        searchProduct(product);
        waitForPageLoad();
        WebElement prod = wait.waitForElementPresent(By.xpath(productXpath.replace("<<text_replace>>", product)));
        click.moveToElementAndClick(prod);
        waitForPageLoad();
        wait.waitForElementEnabled(createProductTitle);
    }

    /**
     * uses getter method to locate the create new Product button on the main tab from the main menu
     * and then clicks on it
     */
    public void createNewProduct() {
        WebElement orderButton = wait.waitForElementPresent(createNewProductButton);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", orderButton);
        waitForPageLoad();
    }

    /**
     * getter method to locate the locations button  ( from the main tab in main menu )WebElement.
     *
     * @return the WebElement of the Locations button
     */
    protected WebElement findLocationsButton() {
        WebElement LocationsButton = wait.waitForElementPresent(locationsButtonLoc);

        return LocationsButton;
    }

    /**
     * uses the getter method to locate the location button in the main tab from the main menu and
     * then clicks on it
     *
     * @return new instance of the locations page class
     */
    public KiboLocationsPage goToLocationsPage() {
        WebElement locationsButton = findLocationsButton();

        locationsButton.click();

        return initializePageObject(KiboLocationsPage.class);
    }

    /**
     * locates and clicks on the customer attribute button in the main tab in main menu
     *
     * @return new instance of the customer attribute page class
     */
    public KiboCustomerAttributesPage goToCustomerAttributesPage() {
        WebElement customerAttributesButton = wait.waitForElementPresent(customerAttributesButtonLoc);
        customerAttributesButton.click();
        waitForPageLoad();

        return initializePageObject(KiboCustomerAttributesPage.class);
    }

    /**
     * locates and clicks on the Customers button from the main tab in main menu
     *
     * @return new instance of the Customers page class
     */
    public KiboCustomersPage goToCustomersPage() {
        WebElement customersButton = wait.waitForElementPresent(customerButton);
        customersButton.click();
        waitForPageLoad();
        wait.waitForAllElementsPresent(customerPageDetails);
        return initializePageObject(KiboCustomersPage.class);
    }

    /**
     * locates and clicks on the Products button from the main tab in main menu
     *
     * @return new instance of the Products page class
     */
    public KiboCustomersPage goToProductsPage() {
        WebElement customersButton = wait.waitForElementPresent(productButton);
        customersButton.click();
        waitForPageLoad();
        return initializePageObject(KiboCustomersPage.class);
    }

    /**
     * locates and clicks on the site builder editor button from the main tab in main menu
     *
     * @return new instance of the site builder editor page class
     */
    public KiboSiteBuilderEditorPage goToSiteBuilderEditorPage() {
        WebElement siteBuilderEditorButton = wait.waitForElementPresent(SiteBuilderButtonLoc);

        siteBuilderEditorButton.click();
        return initializePageObject(KiboSiteBuilderEditorPage.class);
    }

    /**
     * getter method to locate the customer ( from the main tab in main menu )WebElement.
     *
     * @param Name
     */
    protected void findCustomerByName(String Name) {
        wait.waitForElementEnabled(firstName);
        List<WebElement> rows = driver.findElements(firstName);
        int rowCount = rows.size();
        for (int i = 1; i <= rowCount; i++) {
            String actualPath = beforeXpath + i + afterXpath;
            WebElement keyName = driver.findElement(By.xpath(actualPath));
            String keyValue = keyName.getText();
            if (Name.equals(keyValue)) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", keyName);
                break;
            }
        }
    }

    /**
     * locates and edit the Customer from the main tab in main menu
     *
     * @param customerNames pass customer names which should be edited
     */
    public void editCustomerByName(String customerNames) {
        waitForPageLoad();
        findCustomerByName(customerNames);
        waitForPageLoad();
        wait.waitForElementPresent(customerPageSummary);
        wait.waitForElementPresent(customerPageGeneral);
        wait.waitForElementPresent(customerPageContactInfo);
        wait.waitForElementPresent(customerPagePaymentInfo);
        wait.waitForElementPresent(customerPageOrders);
        wait.waitForElementPresent(customerPageAudit);
        wait.waitForElementPresent(customerPageAttribute);
        wait.waitForElementPresent(customerPageGifts);
        wait.waitForElementPresent(customerPageWishlist);
    }

    /**
     * Removes value of customer class
     */
    public void removeVertexCustomerCode() {
        WebElement organizationNameField = wait.waitForElementDisplayed(vertexCustomerCode);
        click.moveToElementAndClick(organizationNameField);
        text.clearText(organizationNameField);
    }

    /**
     * enter customer code
     *
     * @param customerCode
     */
    public void enterVertexCustomerCode(String customerCode) {
        WebElement organizationNameField = wait.waitForElementDisplayed(vertexCustomerCode);
        click.moveToElementAndClick(organizationNameField);
        text.enterText(organizationNameField, customerCode);
    }

    /**
     * enter product title
     *
     * @param productTitle
     */
    public void enterProductTitle(String productTitle) {
        WebElement organizationNameField = wait.waitForElementDisplayed(createProductTitle);
        click.moveToElementAndClick(organizationNameField);
        text.enterText(organizationNameField, productTitle);
    }

    /**
     * enter product Code
     *
     * @param productCode
     */
    public void enterProductCode(String productCode) {
        WebElement organizationNameField = wait.waitForElementDisplayed(productCodeInput);
        click.moveToElementAndClick(organizationNameField);
        text.enterText(organizationNameField, productCode);
    }

    /**
     * enter Price
     *
     * @param price
     */
    public void enterPrice(String price) {
        WebElement organizationNameField = wait.waitForElementDisplayed(priceAmount);
        click.moveToElementAndClick(organizationNameField);
        text.enterText(organizationNameField, price);
    }

    /**
     * enter customer class
     *
     * @param customerClass pass value of customer class which should be applied.
     */
    public void selectVertexCustomerClass(String customerClass) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(customerPageDetails);
        String selectCustomerClass = ".//a[contains(@class,'item--dropdown')][text()='<<text_replace>>']";
        WebElement organizationNameField = wait.waitForElementPresent(vertexCustomerClass);
        if (!text.getElementText(organizationNameField).equalsIgnoreCase(customerClass)) {
            click.moveToElementAndClick(organizationNameField);
            click.moveToElementAndClick(By.xpath(selectCustomerClass.replace("<<text_replace>>", customerClass)));
        }
    }

    /**
     * De-selects selected customer-class.
     */
    public void deSelectVertexCustomerClass() {
        By selectedCustomerCode = By.xpath(".//a[contains(@class,'mozu-is-selected')]");
        WebElement organizationNameField = wait.waitForElementPresent(vertexCustomerClass);
        click.moveToElementAndClick(organizationNameField);
        if (element.isElementDisplayed(selectedCustomerCode)) {
            click.moveToElementAndClick(selectedCustomerCode);
        }
    }

    /**
     * click on save customer button
     */
    public void clickSaveButton() {
        waitForPageLoad();
        WebElement saveButton = wait.waitForElementEnabled(saveButtonId);
        click.javascriptClick(saveButton);
        waitForPageLoad();
    }

    /**
     * enter product Type
     *
     * @param productType
     */
    public void enterProductType(String productType) {
        WebElement organizationNameField = wait.waitForElementDisplayed(productTypeId);
        text.enterText(organizationNameField, productType);
        WebElement productTypes = wait.waitForElementDisplayed(productDropDown);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", productTypes);
    }

    /**
     * enter product Usage
     */
    public void enterProductUsage() {
        WebElement organizationNameField = wait.waitForElementDisplayed(productUsageButton);
        click.moveToElementAndClick(organizationNameField);
        WebElement standardProduct = wait.waitForElementDisplayed(standardProd);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", standardProduct);
    }
}
package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Representation of the Products Page
 *
 * @author alewis
 */
public class M2AdminProductsPage extends MagentoAdminPage {
    By addNewProdID = By.id("add_new_product");
    By addProductID = By.id("add_new_product-button");
    By prodOptionClass = By.className("action-toggle");
    By dropDownClass = By.className("dropdown-menu");
    By itemClass = By.className("item");
    By cellClass = By.className("data-grid-cell-content");
    By editClass = By.className("data-grid-actions-cell");
    By editLinkClass = By.className("action-menu-item");
    By spinner = By.className("spinner");

    protected By productCheckboxClass = By.className("admin__control-checkbox");
    protected By productRowClass = By.className("data-row");
    protected By productCellsTag = By.tagName("td");

    protected By headerClass = By.className("sticky-header");
    protected By actionsMenuClass = By.xpath("(//div[@class='action-select-wrap'])[1]");
    protected By actionMenuItemsClass = By.className("action-menu");
    protected By actionItemTag = By.tagName("li");

    protected By popupButtonsClass = By.className("modal-footer");
    protected By okButtonClass = By.className("action-accept");
    protected By searchField = By.xpath("(//input[@class='admin__control-text data-grid-search-control'])[1]");
    protected By searchButton = By.xpath("(//button[@class='action-submit'])[2]");
    protected By clearAll = By.xpath("(//div[@class='admin__data-grid-filters-current _show']//div//button[contains(text(),'Clear all')])[1]");

    By maskClass = By.className("loading-mask");
    By productTable = By.xpath("//div[@class='admin__data-grid-wrap']//table[@class='data-grid data-grid-draggable']//tbody//tr[@class='data-row']");
    By productTableNotDisplayed = By.xpath("//div[@class='admin__data-grid-wrap']//tbody//tr//td[contains(text(),\"We couldn't find any records.\")]");
    private By commodityCodeOption = By.xpath("//*[contains(text(),'Commodity Code')]");
    private By codeInput = By.xpath("//*[contains(text(),'Commodity Code')]//following::input[1]");
    private By codeType = By.xpath("//*[contains(text(),'Commodity Code')]//following::select[1]");

    protected By backButton = By.xpath(".//button[@id='back']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminProductsPage(WebDriver driver) {
        super(driver);
    }


    /**
     * search for product in filters Admin Page
     */
    public void setSearchField(String searchValue) {
        jsWaiter.waitForLoadAll();
        wait.waitForElementNotDisplayed(maskClass);
        if (verifiesProductTable()) {
            wait.waitForElementDisplayed(productTable, 10);
            wait.waitForElementNotDisplayed(maskClass);
        } else {
            wait.waitForElementDisplayed(productTableNotDisplayed, 10);
            wait.waitForElementNotDisplayed(maskClass);
        }
        WebElement search = wait.waitForElementDisplayed(searchField);
        text.enterText(search, searchValue);
    }

    /**
     * click search button in product admin Page
     */
    public void clickSearchButton() {
        jsWaiter.waitForLoadAll();
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        if (verifiesProductTable()) {
            wait.waitForElementDisplayed(productTable, 10);
            wait.waitForElementNotDisplayed(maskClass);
        } else {
            wait.waitForElementDisplayed(productTableNotDisplayed, 10);
            wait.waitForElementNotDisplayed(maskClass);
        }
        scroll.scrollElementIntoView(searchButton, PageScrollDestination.VERT_CENTER);
        WebElement clickSearchButton = wait.waitForElementDisplayed(searchButton);
        click.moveToElementAndClick(clickSearchButton);
    }

    /**
     * verifies clearAll button
     *
     * @return True Or False
     */
    public boolean verifyClearAllButton() {
        boolean clear = element.isElementDisplayed(clearAll);
        return clear;
    }

    /**
     * verifies product table not displayed
     *
     * @return True Or False
     */
    public boolean verifyProductTableIsNotDisplayed() {
        boolean product = element.isElementDisplayed(productTableNotDisplayed);
        return product;
    }

    /**
     * click search button in filter Admin Page
     */
    public void clickClearAllButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
        if (verifyClearAllButton()) {
            if (verifiesProductTable()) {
                wait.waitForElementDisplayed(productTable);
            } else {
                wait.waitForElementDisplayed(productTableNotDisplayed);
            }
            wait.waitForElementNotDisplayed(maskClass);
            new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
            scroll.scrollElementIntoView(clearAll, PageScrollDestination.VERT_CENTER);
            WebElement clickClearAllButton = wait.waitForElementDisplayed(clearAll);
            click.javascriptClick(clickClearAllButton, PageScrollDestination.VERT_CENTER);
            wait.waitForElementNotDisplayed(maskClass);
            wait.waitForElementDisplayed(productTable);
        }
    }

    /**
     * clicks the add product button
     */
    public M2AdminNewProductPage clickAddProductButton() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        WebElement addProductButton = wait.waitForElementDisplayed(addProductID);
        click.clickElement(addProductButton);
        wait.waitForElementNotDisplayed(maskClass);

        return initializePageObject(M2AdminNewProductPage.class);
    }

    /**
     * Clicks add product pick type button
     *
     * @param prodType Product type
     * @return New product page.
     */
    public M2AdminNewProductPage clickAddProductButtonPickType(String prodType) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(productTable, 10);
        WebElement addProductButton = wait.waitForElementDisplayed(addNewProdID);
        WebElement choiceButton = wait.waitForElementDisplayed(prodOptionClass, addProductButton);
        click.clickElementCarefully(choiceButton);

        WebElement prodDropDown = wait.waitForElementDisplayed(dropDownClass, addProductButton);
        List<WebElement> items = wait.waitForAllElementsDisplayed(itemClass, prodDropDown);

        for (WebElement item : items) {
            try {
                String optionText = text.getElementText(item);
                if (optionText.equals(prodType)) {
                    click.clickElementCarefully(item);
                }
            } catch (Exception e) {
            }
        }

        waitForPageLoad();

        return initializePageObject(M2AdminNewProductPage.class);
    }

    /**
     * verifies the product table is displayed or not after search product
     *
     * @return table
     */
    public boolean verifiesProductTable() {
        waitForPageLoad();
        boolean table = element.isAnyElementDisplayed(productTable);
        return table;
    }

    /**
     * enter the product in search field and click search button in admin products page
     *
     * @param productName product's name
     * @return M2AdminNewProductPage
     */
    public M2AdminNewProductPage searchProduct(String productName) {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        setSearchField(productName);
        clickSearchButton();
        wait.waitForElementNotDisplayed(maskClass);
        if (verifiesProductTable()) {
            wait.waitForElementDisplayed(productTable, DEFAULT_TIMEOUT);
        } else {
            wait.waitForElementDisplayed(productTableNotDisplayed, DEFAULT_TIMEOUT);
        }
        wait.waitForElementNotDisplayed(maskClass);
        return initializePageObject(M2AdminNewProductPage.class);
    }

    /**
     * edit the product which is getting from the search result in admin product page
     *
     * @param productName product's name
     * @return M2AdminNewProductPage
     */
    public M2AdminNewProductPage editSelectProduct(String productName) {
        searchProduct(productName);
        WebElement cell = wait.waitForElementDisplayed(editClass);
        WebElement edit = wait.waitForElementDisplayed(editLinkClass, cell);
        click.javascriptClick(edit);
        wait.waitForElementNotDisplayed(maskClass);

        return initializePageObject(M2AdminNewProductPage.class);
    }

    /**
     * find the product by name in the admin product page
     *
     * @param productName product's name
     * @return productRow
     */
    public WebElement findProductByName(String productName) {
        jsWaiter.waitForLoadAll();
        WebElement productRow = null;
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        if (verifiesProductTable()) {
            wait.waitForElementPresent(productTable, 10);
            List<WebElement> rowList = wait.waitForAllElementsPresent(productRowClass);
            for (WebElement row : rowList) {
                List<WebElement> cellList = wait.waitForAllElementsPresent(productCellsTag, row);
                WebElement nameCell = cellList.get(3);
                String nameFound = nameCell.getText();

                if (productName.equals(nameFound)) {
                    productRow = row;
                    break;
                }
            }
        }
        return productRow;
    }

    /**
     * selects the checkbox by the product name in admin product page
     *
     * @param name product's name
     * @return if product is exists returns true and select the product or else returns false
     */
    public boolean selectProductCheckboxByName(String name) {
        boolean verifyProduct;
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        String productName = String.format("%s", name);
        WebElement product = findProductByName(productName);
        if (!(product == null)) {
            WebElement checkbox = wait.waitForElementEnabled(productCheckboxClass, product);
            click.clickElementCarefully(checkbox);
            verifyProduct = true;
        } else {
            verifyProduct = false;
        }

        return verifyProduct;
    }

    /**
     * deletes selected product in admin product page
     */
    public void selectDeleteAction() {
        wait.waitForElementNotDisplayed(maskClass);

        scroll.scrollElementIntoView(addProductID);

        WebElement actionMenu = wait.waitForElementDisplayed(actionsMenuClass);

        actionMenu.click();

        WebElement actionItems = wait.waitForElementDisplayed(actionMenuItemsClass);

        List<WebElement> itemsList = wait.waitForAllElementsEnabled(actionItemTag, actionItems);

        WebElement delete = element.selectElementByText(itemsList, "Delete");

        delete.click();

        WebElement popup = wait.waitForElementDisplayed(popupButtonsClass);
        WebElement okButton = wait.waitForElementEnabled(okButtonClass, popup);

        click.clickElementCarefully(okButton);

        if (verifiesProductTable()) {
            wait.waitForElementDisplayed(productTable, 10);
        } else {
            wait.waitForElementDisplayed(productTableNotDisplayed, 10);
        }
    }

    /**
     * select commodity code
     *
     * @return M2AdminNewProductPage
     */
    public M2AdminNewProductPage selectCommodityCode() {
        waitForPageLoad();
        WebElement commodityCodeElement = wait.waitForElementPresent(commodityCodeOption);
        click.moveToElementAndClick(commodityCodeElement);
        text.enterText(codeInput, "23153200");
        dropdown.selectDropdownByDisplayName(codeType, "UNSPSC");
        return initializePageObject(M2AdminNewProductPage.class);
    }

    /**
     * Goes back from commodity code
     */
    public void goBackFromCommodityCode() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(backButton));
        waitForPageLoad();
    }

    /**
     * Removes commodity code from the product
     *
     * @return M2AdminNewProductPage
     */
    public M2AdminNewProductPage removeCommodityCode() {
        waitForPageLoad();
        WebElement commodityCodeElement = wait.waitForElementPresent(commodityCodeOption);
        click.moveToElementAndClick(commodityCodeElement);
        text.clearText(codeInput);
        dropdown.selectDropdownByDisplayName(codeType, "Please Select.");
        return new M2AdminNewProductPage(driver);
    }
}
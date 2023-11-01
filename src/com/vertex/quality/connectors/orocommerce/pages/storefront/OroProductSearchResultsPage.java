package com.vertex.quality.connectors.orocommerce.pages.storefront;

import com.vertex.quality.connectors.orocommerce.pages.base.OroStoreFrontBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author alewis
 */
public class OroProductSearchResultsPage extends OroStoreFrontBasePage {
    public OroProductSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    By shoppingListHoverLoc = By.className("cart-widget-counter");
    By parentListContLoc = By.className("product-list");
    By productContLoc = By.className("product-item");
    By addToShoppingListButtonLoc = By.xpath("(//a[contains(text(),'Add to Shopping List')])[1]");
    By productQuantity = By.xpath("(//input[@name='product_qty'])[2]");
    protected String beforeXpath = "(//input[@name='product_qty'])[";
    protected String afterXpath = "]";
    By deleteProduct = By.xpath("//button[@title='Remove Physician’s 5-Pocket Lab Coat from Shopping List']");
    By yesDelete = By.xpath(".//button[text()='Yes, Delete']");
    By cancel = By.xpath(".//button[text()='Cancel']");
    String productName = ".//*[@class='product-item product-item--in-cart'][contains(normalize-space(.),'<<text_replace>>')]/following-sibling::div//button[@class='item-remove btn btn--plain']";


    /**
     * get parent list container
     *
     * @return parentContainer for storefront screen.
     */
    public WebElement getListParentContainer() {
        WebElement parentContainer;
        parentContainer = wait.waitForElementDisplayed(parentListContLoc);
        return parentContainer;
    }

    /**
     * enter quantity for for line item.
     *
     * @param qnt quantity of product to be purchased.
     */
    public void enterProductQuantity(String qnt, String lineItem) {
        String actualPath = beforeXpath + lineItem + afterXpath;
        WebElement productQnt = wait.waitForElementDisplayed(By.xpath(actualPath));
        text.enterText(productQnt, qnt);
        click.moveToElementAndClick(By.xpath(".//*[@id='shopping_list_view_container_content']//div[contains(normalize-space(.),'Customer')]"));
        jsWaiter.sleep(500);
        waitForPageLoad();
    }

    /**
     * enter quantity for for line item.
     */
    public void deleteProduct() {
        WebElement product = wait.waitForElementDisplayed(deleteProduct);
        click.moveToElementAndClick(deleteProduct);
        wait.waitForElementDisplayed(yesDelete);
        click.moveToElementAndClick(yesDelete);
    }

    /**
     * Pass product name as parameter and this method will delete matched product
     * @param product
     */
    public void deleteProductByName(String product) {
        WebElement deleteProd = wait.waitForElementDisplayed(By.xpath(productName.replace("<<text_replace>>", product)));
        click.javascriptClick(deleteProd);
        WebElement confirmDelete = wait.waitForElementDisplayed(yesDelete);
        click.javascriptClick(confirmDelete);
    }

    /**
     * get target product container
     *
     * @param productName of the product container.
     * @return targetProductCont count of target product.
     */
    public WebElement getTargetProductContainer(String productName) {
        WebElement targetProductCont = null;
        WebElement listCont = getListParentContainer();
        List<WebElement> searchResultsList = wait.waitForAllElementsDisplayed(productContLoc, listCont);
        String productCSSLocatorPath = String.format("a[title='%s']", productName);
        By productTitleLoc = By.cssSelector(productCSSLocatorPath);
        for (WebElement potentialElement : searchResultsList) {
            if (element.isElementPresent(productTitleLoc, potentialElement)) {
                targetProductCont = potentialElement;
                break;
            }
        }
        return targetProductCont;
    }

    /**
     * select product from list to be purchased.
     *
     * @param productName to be purchased.
     */
    public void selectProductFromList(String productName) {
        jsWaiter.sleep(2000);
        waitForPageLoad();
        wait.waitForElementEnabled(addToShoppingListButtonLoc);
        click.javascriptClick(addToShoppingListButtonLoc);
        WebElement targetProductCont = getTargetProductContainer(productName);
        waitForPageLoad();
    }

    /**
     * goto shipping list page to add product in the cart.
     *
     * @return shoppingListPage
     */
    public OroShoppingListPage goToShoppingListPage() {
        OroShoppingListPage shoppingListPage;
        hover.hoverOverElement(shoppingListHoverLoc);
        WebElement viewDetailsButton = wait.waitForElementPresent(By.className("shopping-list-dropdown__info"));
        click.javascriptClick(viewDetailsButton);
        shoppingListPage = initializePageObject(OroShoppingListPage.class);
        return shoppingListPage;
    }
}

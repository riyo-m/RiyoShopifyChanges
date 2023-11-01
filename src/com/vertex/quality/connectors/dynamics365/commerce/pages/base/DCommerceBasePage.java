package com.vertex.quality.connectors.dynamics365.commerce.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceCartPage;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceCheckoutPage;
import com.vertex.quality.connectors.dynamics365.commerce.pages.DCommerceHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DCommerceBasePage extends VertexPage {

    protected Actions actions = new Actions(driver);

    protected By LOGO = By.cssSelector("[title='Fabrikam']");

    protected By MENS_TAB = By.xpath("//div[@class='ms-header__desktop-view']//button[text()='Menswear']");
    protected By ACCESSORIES_TAB = By.xpath("//div[@class='ms-header__desktop-view']//button[text()='Accessories']");
    protected By WOMENS_TAB = By.xpath("//div[@class='ms-header__desktop-view']//button[text()='Womenswear']");

    protected By SEARCH_BUTTON = By.xpath("//button[@aria-label='Search']");
    protected By SEARCH_INPUT = By.xpath("//input[@aria-label='Search']");
    protected By ACCOUNT_TAB = By.cssSelector("[class='ms-header__account-info account-desktop']");
    protected By SIGN_OUT_BUTTON = By.cssSelector("[aria-label='Sign out']");
    protected By CART_ICON = By.cssSelector("[class='ms-cart-icon']");

    protected By SIZE_DROPDOWN = By.cssSelector("[aria-label='Size']");
    protected By QUANTITY_INPUT = By.cssSelector("[class='quantity-input']");
    protected By ADD_TO_BAG_BUTTON = By.cssSelector("[aria-label='Add to bag']");
    protected By MODAL_CLOSE_ICON = By.xpath("//div[contains(@style, 'visibility: visible')]//button[@aria-label='Close']");
    public DCommerceBasePage(WebDriver driver) {
        super(driver);
    }

    /**
     * return the name of the account currently signed in
     * @return name
     */
    public String getAccountName() {
        WebElement accountTab = wait.waitForElementDisplayed(ACCOUNT_TAB);
        String name = accountTab.getText();

        return name;
    }

    /**
     * sign out of account
     */
    public void clickSignOut() {
        wait.waitForElementDisplayed(ACCOUNT_TAB);
        click.clickElementIgnoreExceptionAndRetry(ACCOUNT_TAB);

        wait.waitForElementDisplayed(SIGN_OUT_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SIGN_OUT_BUTTON);
    }

    /**
     * clicks tab at top and then category in the dropdown
     * @param tabName
     * @param categoryName
     */
    public void navigateToCategory(String tabName, String categoryName) {
        By tabLoc = By.xpath(String.format("//div[@class='ms-header__desktop-view']//button[text()='%s']", tabName));

        wait.waitForElementDisplayed(tabLoc);
        click.clickElementIgnoreExceptionAndRetry(tabLoc);

        By categoryLoc = By.xpath(String.format("//a[@class='ms-nav__list__item__link' and text()='%s']", categoryName));
        wait.waitForElementDisplayed(categoryLoc);
        click.clickElementIgnoreExceptionAndRetry(categoryLoc);

        waitForPageLoad();
    }

    /**
     * Clicks search button at top
     */
    public void clickSearchButton() {
        WebElement searchButton = wait.waitForElementDisplayed(SEARCH_BUTTON);
        click.clickElementCarefully(searchButton);
    }

    /**
     * Click search button at top, click on input field, enter product name and press enter
     * @param productName
     */
    public void searchForProduct(String productName) {
        wait.waitForElementDisplayed(SEARCH_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SEARCH_BUTTON);

        waitForPageLoad();

        WebElement searchInput = wait.waitForElementDisplayed(SEARCH_INPUT);
        click.clickElementIgnoreExceptionAndRetry(searchInput);
        text.selectAllAndInputText(searchInput, productName);
        text.pressEnter(searchInput);

        waitForPageLoad();
    }

    public void clickProduct(String productName) {
        waitForPageLoad();

        By productLoc = By.xpath(String.format("//a[contains(@aria-label, '%s')]", productName));
        WebElement productItem = wait.waitForElementPresent(productLoc);
        actions.moveToElement(productItem).click().perform();

        waitForPageLoad();
    }

    /**
     * Set the quantity for a product
     * @param quantity
     */
    public void setQuantity(String quantity) {
        waitForPageLoad();
        WebElement quantityField = wait.waitForElementEnabled(QUANTITY_INPUT);
        text.selectAllAndInputText(quantityField, quantity);
    }

    /**
     * Select the size for a product (shoe or clothing)
     * @param size
     */
    public void selectSize(String size) {
        WebElement sizeDropdown = wait.waitForElementDisplayed(SIZE_DROPDOWN);
        click.clickElementCarefully(sizeDropdown);

        By optionLoc = By.xpath(String.format("//option[text()='%s']", size));

        WebElement option = wait.waitForElementDisplayed(optionLoc);
        click.clickElementIgnoreExceptionAndRetry(option);
        text.pressTab(By.cssSelector("body"));

        waitForPageLoad();
    }

    /**
     * Click 'Add to bag' button for a product
     */
    public void clickAddToBagButton() {
        waitForPageLoad();
        jsWaiter.sleep(2500);
        WebElement addToBagButton = wait.waitForElementDisplayed(ADD_TO_BAG_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(addToBagButton);
        closeModal();
    }

    /**
     * close modal on screen
     */
    public void closeModal() {
        waitForPageLoad();
        jsWaiter.sleep(2500);
        WebElement closeButton = wait.waitForElementDisplayed(MODAL_CLOSE_ICON);
        click.clickElementIgnoreExceptionAndRetry(closeButton);
    }

    /**
     * Click on store logo and return instance of DCommerceHomePage
     * @return homePage
     */
    public DCommerceHomePage navigateToHomePage() {
        wait.waitForElementPresent(LOGO);
        click.clickElementIgnoreExceptionAndRetry(LOGO);

        DCommerceHomePage homePage = new DCommerceHomePage(driver);
        return homePage;
    }

    /**
     * Double click on shopping bag icon and return instance of DCommerceCartPage
     * @return cartPage
     */
    public DCommerceCartPage navigateToCartPage() {
        WebElement cartButton = wait.waitForElementDisplayed(CART_ICON);
        actions.moveToElement(cartButton).doubleClick().perform();

        waitForPageLoad();

        DCommerceCartPage cartPage = new DCommerceCartPage(driver);
        return cartPage;
    }

    /**
     * Remove currency sign from string and return numeric value
     * @param amount
     * @return parsedAmount
     */
    public String parseNumericAmount(String amount) {
        String parsedAmount = amount.trim();

        Matcher m = Pattern.compile("[\\d,]+[.]\\d+").matcher(parsedAmount);
        if (m.find()) {
            parsedAmount = m.group(0);
        }

        return parsedAmount;
    }
}

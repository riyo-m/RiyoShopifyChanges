package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SalesForceB2CBuyerPage extends SalesForceBasePage {

    protected By NOT_A_MEMBER_LINK = By.xpath(".//a[contains(@class, 'self-register') and text() = 'Not a member?']");
    protected By FIRST_NAME_INPUT = By.xpath(".//label[text() = 'First Name']/following-sibling::div/input");
    protected By LAST_NAME_INPUT = By.xpath(".//label[text() = 'Last Name']/following-sibling::div/input");
    protected By EMAIL_INPUT = By.xpath(".//label[text() = 'Email']/following-sibling::div/input");
    protected By CREATE_PASSWORD_INPUT = By.xpath(".//label[text() = 'Create Password']/following-sibling::div/input");
    protected By CONFIRM_PASSWORD_INPUT = By.xpath(".//label[text() = 'Confirm Password']/following-sibling::div/input");
    protected By SIGN_UP_BUTTON = By.xpath(".//button[contains(@class, 'self-register')]");

    protected By PRODUCT_SEARCH_INPUT = By.xpath(".//div/input[@class='search-input-with-button']");
    protected By PRODUCT_QUANTITY_INPUT = By.xpath(".//input[contains(@id, 'quantity')]");
    protected By PRODUCT_INCREASE_QUANTITY_BUTTON = By.xpath(".//*[@data-key='add']");
    protected By PRODUCT_ADD_TO_CART_BUTTON = By.xpath(".//button[text() = 'ADD TO CART']");
    protected By PRODUCT_VIEW_CART_BUTTON = By.xpath(".//button[text()='View Cart']");
    protected By PRODUCT_CONTINUE_SHOPPING_BUTTON = By.xpath(".//button[contains(text(),'Continue Shopping')]");
    protected By PRODUCT_SKU = By.xpath(".//p[text()='SKU#']");

    protected By VIEW_CART_BUTTON = By.xpath(".//span[contains(@title, 'Cart:')]/*/*");
    protected By PROFILE_BUTTON = By.xpath(".//button[@title='Log In']/*/*");
    protected By CART_ITEMS = By.xpath("//li[@class='cart-line-item']");
    protected By REMOVE_CART_ITEM = By.xpath("//div[@class='remove']/a");
    protected By MEMBER_USERNAME_INPUT = By.xpath(".//lightning-input[contains(@class, 'username-input')]/div/input");
    protected By MEMBER_PASSWORD_INPUT = By.xpath(".//lightning-input[contains(@class, 'password-input')]/div/input");
    protected By MEMBER_LOG_IN_BUTTON = By.xpath(".//button[contains(@class, 'login-button')]");

    public SalesForceB2CBuyerPage(WebDriver driver) {super(driver);}

    /**
     * Go to B2C storefront
     */
    public void navigateToB2CStorefront(){
        driver.get("https://qalightninglb2b-developer-edition.ap26.force.com/alpine/s");
    }

    /**
     * Click Not a Member link on login page
     */
    public void clickNotAMemberLink(){
        wait.waitForElementDisplayed(NOT_A_MEMBER_LINK);
        click.clickElement(NOT_A_MEMBER_LINK);
    }

    /**
     * Sign up for B2C storefront account
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
    public void createB2CAccount(String firstName, String lastName, String email, String password){
        clickNotAMemberLink();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setCreatePassword(password);
        setConfirmPassword(password);
        clickSignUpButton();
    }

    /**
     * Set first name for account
     *
     * @param firstName
     */
    public void setFirstName(String firstName){
        wait.waitForElementDisplayed(FIRST_NAME_INPUT);
        text.enterText(FIRST_NAME_INPUT, firstName);
    }

    /**
     * Set last name for account
     *
     * @param lastName
     */
    public void setLastName(String lastName){
        wait.waitForElementDisplayed(LAST_NAME_INPUT);
        text.enterText(LAST_NAME_INPUT, lastName);
    }

    /**
     * Set email for account
     *
     * @param email
     */
    public void setEmail(String email){
        wait.waitForElementDisplayed(EMAIL_INPUT);
        text.enterText(EMAIL_INPUT, email);
    }

    /**
     * Set create password field for account
     *
     * @param password
     */
    public void setCreatePassword(String password){
        wait.waitForElementDisplayed(CREATE_PASSWORD_INPUT);
        text.enterText(CREATE_PASSWORD_INPUT, password);
    }

    /**
     * Set confirm password field for account
     *
     * @param password
     */
    public void setConfirmPassword(String password){
        wait.waitForElementDisplayed(CONFIRM_PASSWORD_INPUT);
        text.enterText(CONFIRM_PASSWORD_INPUT, password);
    }

    /**
     * Click Sign Up on account creation page
     */
    public void clickSignUpButton(){
        wait.waitForElementDisplayed(SIGN_UP_BUTTON);
        click.clickElement(SIGN_UP_BUTTON);
    }

    /**
     * Login as existing member to B2C storefront
     *
     * @param username
     * @param password
     */
    public void loginAsExistingMember(String username, String password){
        clickProfileIcon();
        setExistingMemberUsername(username);
        setExistingMemberPassword(password);
        clickMemberLoginButton();
        waitForSalesForceLoaded();
    }

    /**
     * Click profile icon to login as member to B2C storefront
     */
    public void clickProfileIcon(){
        wait.waitForElementDisplayed(PROFILE_BUTTON);
        click.clickElement(PROFILE_BUTTON);
    }

    /**
     * Set existing member username
     *
     * @param username
     */
    public void setExistingMemberUsername(String username){
        wait.waitForElementDisplayed(MEMBER_USERNAME_INPUT);
        text.enterText(MEMBER_USERNAME_INPUT, username);
    }

    /**
     * Set existing member password
     *
     * @param password
     */
    public void setExistingMemberPassword(String password){
        wait.waitForElementDisplayed(MEMBER_PASSWORD_INPUT);
        text.enterText(MEMBER_PASSWORD_INPUT, password);
    }

    /**
     * Click member login button
     */
    public void clickMemberLoginButton(){
        wait.waitForElementDisplayed(MEMBER_LOG_IN_BUTTON);
        click.javascriptClick(MEMBER_LOG_IN_BUTTON);
    }

    /**
     * Navigate to B2C menu item
     *
     * @param menuItem
     */
    public void navigateToMenuItem(String menuItem){
        String pathString = String.format(".//a[@role='menuitem']/span[@data-automation = '%s']", menuItem);
        By path = By.xpath(pathString);
        wait.waitForElementDisplayed(path);
        click.clickElement(path);
    }

    /**
     * Select product from list of products
     *
     * @param productName name of product to be added to cart
     */
    public void selectProduct(String productName){
        String pathString = String.format(".//*[@title='%s']", productName, productName);
        By path = By.xpath(pathString);
        wait.waitForElementDisplayed(path);
        click.clickElement(path);
    }

    /**
     * Set product quantity to be added to cart
     *
     * @param quantity amount of item to be added to cart
     */
    public void setProductQuantity(int quantity){
        wait.waitForElementEnabled(PRODUCT_QUANTITY_INPUT);
        text.clearText(PRODUCT_QUANTITY_INPUT);
        waitForSalesForceLoaded(3000);
        text.enterText(PRODUCT_QUANTITY_INPUT, Integer.toString(quantity));
        // gets rid of 0 quantity error by clicking elsewhere on page
        click.clickElement(PRODUCT_SEARCH_INPUT);
        waitForSalesForceLoaded(3000);
    }

    public void searchStoreFrontProduct(String productName){
    	click.clickElementCarefully(PRODUCT_SEARCH_INPUT);
		text.enterText(PRODUCT_SEARCH_INPUT,productName);
		text.pressEnter(PRODUCT_SEARCH_INPUT);
	}

    /**
     * Add product to cart
     */
    public void clickAddToCartButton(){
        waitForPageLoad();
        wait.waitForElementDisplayed(PRODUCT_ADD_TO_CART_BUTTON);
        click.clickElement(PRODUCT_ADD_TO_CART_BUTTON);
    }

    /**
     * Add selected product to cart
     *
     * @param productName name of product to be added to cart
     * @param quantity quantity of product to be added to cart
     */
    public void addProductToCart(String productName, int quantity){
    	searchStoreFrontProduct(productName);
    	jsWaiter.sleep(3000);
        selectProduct(productName);
        setProductQuantity(quantity);
        clickAddToCartButton();
    }

    /**
     * Click view cart button or continue shopping button
     *
     * @param viewCart determines whether to view cart or continue shopping
     */
    public void clickViewCartOrContinueShoppingButton(boolean viewCart)
    {
        if(viewCart){
            try{
                Thread.sleep(3000);
                wait.waitForElementDisplayed(PRODUCT_VIEW_CART_BUTTON, 3);
                click.clickElement(PRODUCT_VIEW_CART_BUTTON);
            }
            catch (Exception ex) {
                navigateToCart();
            }
        }
        else{
            try{
                wait.waitForElementDisplayed(PRODUCT_CONTINUE_SHOPPING_BUTTON, 3);
                click.clickElement(PRODUCT_CONTINUE_SHOPPING_BUTTON);
            }
            catch (Exception ignored) {}
        }
    }

    /**
     * Click cart symbol to view the cart
     */
    public void navigateToCart(){
        wait.waitForElementDisplayed(VIEW_CART_BUTTON);
        click.clickElement(VIEW_CART_BUTTON);
    }
    /**
     * Clear Cart
     */
    public void clearCart() {
        jsWaiter.sleep(3000);
        List<WebElement> cartItems = driver.findElements(CART_ITEMS);
        int cartItemsCount = cartItems.size();
        if (cartItemsCount > 0) {
            for (int i = 0; i < cartItemsCount; i++) {
                wait.waitForElementDisplayed(REMOVE_CART_ITEM);
                click.clickElement(REMOVE_CART_ITEM);
                waitForSalesForceLoaded(3000);
            }
        }
    }
}

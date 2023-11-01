package com.vertex.quality.connectors.episerver.pages.v324x;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Episerver v3.2.4 Commerce -> Marketing page
 *
 * @author Shivam.Soni
 */
public class EpiCommerceMarketingPage extends EpiServer324Page {

    protected By LOADING_DISCOUNTS = By.xpath(".//div[text()='Loading discounts']");
    protected By TREE_LOADER = By.xpath(".//span[contains(@class,'dijitTreeExpandoLoading')]");
    protected By MAIN_GRID_OVERLAY = By.className("epi-editor-overlay");
    protected By COLLAPSED_MAIN_GRID = By.xpath(".//div[contains(@class,'triangle-1-e')]");
    protected By EXPANDED_MAIN_GRID = By.xpath(".//div[contains(@class,'triangle-1-se')]");
    protected By MAIN_GRID_ALL_ROW = By.xpath(".//div[@class='epi-editor-overlay']//table[@class='dgrid-row-table']//tr");
    protected String EXISTING_COUPON_NAME = ".//table[@class='dgrid-row-table']//h3[text()='<<text_replace>>']";
    protected String LEFT_PANEL_OPTION = ".//span[text()='<<text_replace>>']";
    protected String COLLAPSED_LEFT_PANEL_OPTION = ".//span[normalize-space(.)='<<text_replace>>']/parent::span[@aria-pressed='true']";
    protected String EXPANDED_LEFT_PANEL_OPTION = ".//span[normalize-space(.)='<<text_replace>>']/parent::span[@aria-pressed='false']";
    protected By CREATE_NEW_BUTTON = By.xpath(".//span[text()='Create New...']");
    protected By CREATE_CONTENT_DISCOUNT_OPTION = By.xpath(".//td[text()='Discount']");
    protected By SELECT_ITEM_LABEL = By.xpath(".//span[text()='Select Item']");
    protected By MARKET_NAME = By.xpath("(.//span[text()='Marketing'])[3]");
    protected By QUICK_SILVER_CAMPAIGN = By.xpath("(.//span[text()='QuickSilver'])[2]");
    protected By SELECT_CAMPAIGN_BUTTON = By.xpath(".//span[text()='Select']");
    protected By ITEM_DISCOUNT_CATEGORY = By.xpath(".//h2[text()='Item Discount']/following-sibling::ul//h3[text()='Buy Products for Discount from Other Selection']");
    protected By ORDER_DISCOUNT_CATEGORY = By.xpath(".//h2[text()='Order Discount']/following-sibling::ul//h3[text()='Buy Products and Get Discount on Order']");
    protected By SHIPPING_DISCOUNT_CATEGORY = By.xpath(".//h2[text()='Shipping Discount']/following-sibling::ul//h3[text()='Buy Products for Discount on Shipping Cost']");
    protected By ADD_DISCOUNT_MODAL_TITLE = By.xpath(".//div[@title='Please Note']");
    protected By ADD_DISCOUNT_MODAL_COUPON_NAME = By.xpath("((.//label[text()='Name'])[2]/following-sibling::div//input)[2]");
    protected By MODAL_OK_BUTTON = By.xpath(".//span[text()='OK']");
    protected By PROMOTION_CODE_BOX = By.xpath("(.//label[normalize-space(.)='Promotion code']/parent::div/following-sibling::div//input)[2]");
    protected By SAME_AS_CAMPAIGN_DATE_RADIO_BUTTON = By.xpath(".//label[text()='Same as the campaign']/preceding-sibling::div/input");
    protected By ACTIVE_DISCOUNT_CHECKBOX = By.xpath(".//label[text()='Active']/preceding-sibling::div//input");
    protected By X_ITEMS_BOX = By.xpath("(.//label[text()='X items']/parent::div/following-sibling::div//input)[5]");
    protected By BUY_PRODUCT_SELECT_CONTENT = By.xpath(".//*[text()='Buy at least...']//following-sibling::ul//span[text()='Select Content']");
    protected By SELECT_CONTENT_LABEL = By.xpath(".//div[@title='Select Content']");
    protected By CATALOG_NAME_FASHION = By.xpath("(.//span[normalize-space(.)='Fashion'])[2]");
    protected By EXPAND_CAMPAIGN = By.xpath("(.//span[contains(@class,'dijitTreeExpandoClosed')])[4]");
    protected By GET_PRODUCT_SELECT_CONTENT = By.xpath(".//*[text()='Get...']//following-sibling::ul//span[text()='Select Content']");
    protected By PERCENTAGE_OFF_RADIO_BUTTON = By.xpath(".//label[text()='Percentage off']/preceding-sibling::div//input");
    protected By PERCENTAGE_OFF_BOX = By.xpath("(.//label[text()='Percentage off']/following-sibling::div//input)[5]");
    protected By AMOUNT_OFF_RADIO_BUTTON = By.xpath(".//label[text()='Amount off']/preceding-sibling::div//input");
    protected By AMOUNT_OFF_USD_BOX = By.xpath("(.//td[normalize-space(.)='USD']/following-sibling::td//div//input)[2]");
    protected By CONTENT_SAVE_BUTTON = By.xpath("(.//span[text()='Save'])[1]");
    protected By ALL_US_MARKET_SHIP_OPTION_LABELS = By.xpath(".//label[contains(text(),'Markets: SWE, US')]");
    protected String ALL_US_MARKET_SHIP_OPTION_CHECKBOX = "(.//label[contains(text(),'Markets: SWE, US')]/preceding-sibling::div//input)[<<text_replace>>]";

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiCommerceMarketingPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Waits till tree node gets loaded
     */
    public void waitUntilTreeNodesLoading() {
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(TREE_LOADER));
    }

    /**
     * Waits until discounts gets loaded.
     */
    public void waitUntilDiscountsLoading() {
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(LOADING_DISCOUNTS));
    }

    /**
     * Expands main grid
     */
    public void expandMainGrid() {
        waitForSpinnerToBeDisappeared();
        waitUntilDiscountsLoading();
        wait.waitForElementPresent(MAIN_GRID_OVERLAY);
        if (element.isElementPresent(COLLAPSED_MAIN_GRID)) {
            click.moveToElementAndClick(COLLAPSED_MAIN_GRID);
            wait.waitForElementPresent(EXPANDED_MAIN_GRID);
            waitUntilDiscountsLoading();
            VertexLogger.log("Expanded main grid");
        }
    }

    /**
     * This method helps to navigate to left panel option & nested option
     *
     * @param submenu      Left panel's option
     * @param nestedOption Left panel's nested option
     */
    public void selectLeftPanelOption(String submenu, String nestedOption) {
        waitForSpinnerToBeDisappeared();
        WebElement node = wait.waitForElementPresent(By.xpath(LEFT_PANEL_OPTION.replace("<<text_replace>>", submenu)));
        click.moveToElementAndClick(node);
        waitForSpinnerToBeDisappeared();
        if (element.isElementPresent(By.xpath(COLLAPSED_LEFT_PANEL_OPTION.replace("<<text_replace>>", submenu)))) {
            click.moveToElementAndClick(node);
            wait.waitForElementPresent(By.xpath(EXPANDED_LEFT_PANEL_OPTION.replace("<<text_replace>>", submenu)));
        }
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(LEFT_PANEL_OPTION.replace("<<text_replace>>", nestedOption))));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Selected option of submenu: " + submenu + " and nested option: " + nestedOption);
    }

    /**
     * Checks whether coupon code is already present or not?
     *
     * @param couponCode Coupon Code which is to be checked
     * @return true or false based on condition check
     */
    public boolean checkIfCouponExists(String couponCode) {
        VertexLogger.log("Checking " + couponCode + " in existing coupons' list");
        waitForSpinnerToBeDisappeared();
        waitUntilDiscountsLoading();
        wait.waitForElementPresent(MAIN_GRID_OVERLAY);
        wait.waitForAllElementsPresent(MAIN_GRID_ALL_ROW);
        if (element.isElementPresent(By.xpath(EXISTING_COUPON_NAME.replace("<<text_replace>>", couponCode)))) {
            VertexLogger.log("The coupon code: " + couponCode + " is already existed");
            return false;
        } else {
            VertexLogger.log("The coupon code: " + couponCode + " is not found");
            return true;
        }
    }

    /**
     * Clicks on create content button & opens new content creation page
     */
    public void clickCreateDiscount() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(CREATE_NEW_BUTTON));
        click.moveToElementAndClick(wait.waitForElementPresent(CREATE_CONTENT_DISCOUNT_OPTION));
        wait.waitForElementPresent(SELECT_ITEM_LABEL);
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Going to create new discount");
    }

    /**
     * Selects default campaign
     */
    public void selectDefaultCampaign() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(SELECT_ITEM_LABEL);
        click.moveToElementAndClick(wait.waitForElementPresent(QUICK_SILVER_CAMPAIGN));
        click.moveToElementAndClick(wait.waitForElementEnabled(SELECT_CAMPAIGN_BUTTON));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Selected default campaign for creation of discount");
    }

    /**
     * Selects default category for item based discounts
     */
    public void selectDefaultCategoryForItemBasedDiscount() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(ITEM_DISCOUNT_CATEGORY));
        VertexLogger.log("Default category is selected for Item based discount");
    }

    /**
     * Selects default category for order based discounts
     */
    public void selectDefaultCategoryForOrderBasedDiscount() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(ORDER_DISCOUNT_CATEGORY));
        VertexLogger.log("Default category is selected for Order based discount");
    }

    /**
     * Selects default category for shipping based discounts
     */
    public void selectDefaultCategoryForShippingBasedDiscount() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(SHIPPING_DISCOUNT_CATEGORY));
        VertexLogger.log("Default category is selected for Shipping based discount");
    }

    /**
     * Enters discount name
     *
     * @param discountName Discount Name
     */
    public void enterDiscountName(String discountName) {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(ADD_DISCOUNT_MODAL_TITLE);
        WebElement name = wait.waitForElementPresent(ADD_DISCOUNT_MODAL_COUPON_NAME);
        click.moveToElementAndClick(name);
        text.enterText(name, Keys.chord(Keys.CONTROL, "a"));
        text.enterText(name, discountName, false);
        click.moveToElementAndClick(MODAL_OK_BUTTON);
        VertexLogger.log("Entered discount name: " + discountName);
    }

    /**
     * Enter promotional code
     *
     * @param code promotional code
     */
    public void enterPromotionCode(String code) {
        waitForSpinnerToBeDisappeared();
        text.enterText(wait.waitForElementPresent(PROMOTION_CODE_BOX), code);
        VertexLogger.log("Entered promotion code: " + code);
    }

    /**
     * Sets default start date for discount
     */
    public void selectSameAsCampaignDate() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(SAME_AS_CAMPAIGN_DATE_RADIO_BUTTON));
        VertexLogger.log("Selected Same date as campaign for the discount");
    }

    /**
     * Activate the discount
     */
    public void makeActiveDiscount() {
        waitForSpinnerToBeDisappeared();
        WebElement active = wait.waitForElementPresent(ACTIVE_DISCOUNT_CHECKBOX);
        if (!checkbox.isCheckboxChecked(active)) {
            click.moveToElementAndClick(active);
        }
        VertexLogger.log("Discount is marked as active");
    }

    /**
     * Enter at least X item's value
     *
     * @param xItem value of X Items
     */
    public void enterXItems(String xItem) {
        waitForSpinnerToBeDisappeared();
        text.enterText(wait.waitForElementPresent(X_ITEMS_BOX), xItem);
        VertexLogger.log("Entered X Items: " + xItem);
    }

    /**
     * Selects default catalog for Buy at least products
     */
    public void selectBuyDefaultCatalog() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(SELECT_CONTENT_LABEL);
        waitUntilTreeNodesLoading();
        if (!element.isElementPresent(CATALOG_NAME_FASHION)) {
            click.moveToElementAndClick(wait.waitForElementPresent(EXPAND_CAMPAIGN));
            waitUntilTreeNodesLoading();
        }
        click.moveToElementAndClick(wait.waitForElementPresent(CATALOG_NAME_FASHION));
        click.moveToElementAndClick(wait.waitForElementPresent(MODAL_OK_BUTTON));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Selected default catalog for Buy at least product");
    }

    /**
     * Select Buy at least x item's content type
     */
    public void selectBuyContentType() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(BUY_PRODUCT_SELECT_CONTENT));
        waitUntilTreeNodesLoading();
        waitForSpinnerToBeDisappeared();
        selectBuyDefaultCatalog();
        VertexLogger.log("Selected default Buy at least products for the discount");
    }

    /**
     * Selects default catalog for Get products
     */
    public void selectGetDefaultCatalog() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(SELECT_CONTENT_LABEL);
        waitUntilTreeNodesLoading();
        if (!element.isElementPresent(CATALOG_NAME_FASHION)) {
            click.moveToElementAndClick(wait.waitForElementPresent(EXPAND_CAMPAIGN));
            waitUntilTreeNodesLoading();
        }
        click.clickElementIgnoreExceptionAndRetry(wait.waitForElementPresent(CATALOG_NAME_FASHION));
        click.moveToElementAndClick(wait.waitForElementPresent(MODAL_OK_BUTTON));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Selected default catalog for Get product");
    }

    /**
     * Select get product content type
     */
    public void selectGetContentType() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(GET_PRODUCT_SELECT_CONTENT));
        waitUntilTreeNodesLoading();
        waitForSpinnerToBeDisappeared();
        selectGetDefaultCatalog();
        VertexLogger.log("Selected default Get products for the discount");
    }

    /**
     * Enter discount percentage
     *
     * @param percent discount percent
     */
    public void enterDiscountPercentage(String percent) {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(PERCENTAGE_OFF_RADIO_BUTTON));
        text.enterText(wait.waitForElementPresent(PERCENTAGE_OFF_BOX), percent);
        VertexLogger.log("Entered discount percentage: " + percent + "%");
    }

    /**
     * Enter discount amount
     *
     * @param usdAmount discount amount
     */
    public void enterDiscountAmountInUSD(String usdAmount) {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(AMOUNT_OFF_RADIO_BUTTON));
        text.enterText(wait.waitForElementPresent(AMOUNT_OFF_USD_BOX), usdAmount);
        VertexLogger.log("Entered discount amount: $" + usdAmount);
    }

    /**
     * Saves filled details on create content page (Discount or Campaign)
     */
    public void saveContentDetails() {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(CONTENT_SAVE_BUTTON));
        waitForSpinnerToBeDisappeared();
        VertexLogger.log("Saved all the content details");
    }

    /**
     * Creates Item based discount coupon
     *
     * @param isPercentBase   pass true if you want to create percent based discount & false to create amount based discount
     * @param couponCode      name of coupon code
     * @param percentOrAmount discount percent or discount amount in USD
     */
    public void createItemBasedDiscountCoupon(boolean isPercentBase, String couponCode, String percentOrAmount) {
        clickCreateDiscount();
        selectDefaultCampaign();
        selectDefaultCategoryForItemBasedDiscount();
        enterDiscountName(couponCode);
        enterPromotionCode(couponCode);
        selectSameAsCampaignDate();
        makeActiveDiscount();
        enterXItems(EpiDataCommon.CouponAmountOrPercent.VALUE_ONE.text);
        selectBuyContentType();
        selectGetContentType();
        if (isPercentBase) {
            enterDiscountPercentage(percentOrAmount);
        } else {
            enterDiscountAmountInUSD(percentOrAmount);
        }
        saveContentDetails();
        VertexLogger.log("The item based coupon code: " + couponCode + " is created successfully");
    }

    /**
     * Selects all Shipping options for US Market
     */
    public void selectAllUSMarketsForShipping() {
        waitForSpinnerToBeDisappeared();
        wait.waitForAllElementsPresent(ALL_US_MARKET_SHIP_OPTION_LABELS);
        int size = element.getWebElements(ALL_US_MARKET_SHIP_OPTION_LABELS).size();
        for (int i = 1; i <= size; i++) {
            WebElement option = wait.waitForElementPresent(By.xpath(ALL_US_MARKET_SHIP_OPTION_CHECKBOX.replace("<<text_replace>>", String.valueOf(i))));
            if (!checkbox.isCheckboxChecked(option)) {
                click.moveToElementAndClick(option);
            }
        }
        VertexLogger.log("Selected all US markets for shipping discount");
    }

    /**
     * Creates Shipping based discount coupon
     *
     * @param isPercentBase   pass true if you want to create percent based discount & false to create amount based discount
     * @param couponCode      name of coupon code
     * @param percentOrAmount discount percent or discount amount in USD
     */
    public void createShippingBasedDiscountCoupon(boolean isPercentBase, String couponCode, String percentOrAmount) {
        clickCreateDiscount();
        selectDefaultCampaign();
        selectDefaultCategoryForShippingBasedDiscount();
        enterDiscountName(couponCode);
        enterPromotionCode(couponCode);
        selectSameAsCampaignDate();
        makeActiveDiscount();
        enterXItems(EpiDataCommon.CouponAmountOrPercent.VALUE_ONE.text);
        selectBuyContentType();
        selectAllUSMarketsForShipping();
        if (isPercentBase) {
            enterDiscountPercentage(percentOrAmount);
        } else {
            enterDiscountAmountInUSD(percentOrAmount);
        }
        saveContentDetails();
        VertexLogger.log("The shipping based coupon code: " + couponCode + " is created successfully");
    }

    /**
     * Creates Order based discount coupon
     *
     * @param isPercentBase   pass true if you want to create percent based discount & false to create amount based discount
     * @param couponCode      name of coupon code
     * @param percentOrAmount discount percent or discount amount in USD
     */
    public void createOrderBasedDiscountCoupon(boolean isPercentBase, String couponCode, String percentOrAmount) {
        clickCreateDiscount();
        selectDefaultCampaign();
        selectDefaultCategoryForOrderBasedDiscount();
        enterDiscountName(couponCode);
        enterPromotionCode(couponCode);
        selectSameAsCampaignDate();
        makeActiveDiscount();
        enterXItems(EpiDataCommon.CouponAmountOrPercent.VALUE_ONE.text);
        selectBuyContentType();
        if (isPercentBase) {
            enterDiscountPercentage(percentOrAmount);
        } else {
            enterDiscountAmountInUSD(percentOrAmount);
        }
        saveContentDetails();
        VertexLogger.log("The order based coupon code: " + couponCode + " is created successfully");
    }
}

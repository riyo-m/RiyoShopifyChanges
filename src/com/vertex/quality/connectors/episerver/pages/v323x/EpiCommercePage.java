package com.vertex.quality.connectors.episerver.pages.v323x;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This page helps to interact with the options under Commerce Page of Epi-commerce store
 *
 * @author Shivam.Soni
 */
public class EpiCommercePage extends VertexPage {

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiCommercePage(WebDriver driver) {
        super(driver);
    }

    protected String COLLAPSED_LEFT_PANEL_OPTION = ".//span[text()='<<text_replace>>']/parent::span[@aria-pressed='false']";
    protected String EXPANDED_LEFT_PANEL_OPTION = ".//span[text()='<<text_replace>>']/parent::span[@aria-pressed='true']";
    protected String LEFT_PANEL_OPTION = ".//span[text()='<<text_replace>>']";
    protected By MAIN_GRID_OVERLAY = By.className("epi-editor-overlay");
    protected By COLLAPSED_MAIN_GRID = By.cssSelector(".ui-icon-triangle-1-e");
    protected By EXPANDED_MAIN_GRID = By.cssSelector(".ui-icon-triangle-1-se");
    protected By MAIN_GRID_ALL_ROW = By.xpath(".//table[@class='dgrid-row-table']//tr");
    protected String EXISTING_COUPON_NAME = ".//table[@class='dgrid-row-table']//h3[text()='<<text_replace>>']";
    protected By CREATE_CONTENT_BUTTON = By.xpath(".//span[@title='Create content']");
    protected By CREATE_CONTENT_DISCOUNT_OPTION = By.xpath(".//td[text()='Discount']");
    protected By SELECT_ITEM_LABEL = By.xpath(".//span[text()='Select Item']");
    protected By CAMPAIGNS_LIST = By.xpath(".//span[text()='Campaigns']");
    protected By EXPAND_CAMPAIGN = By.xpath("(.//span[@class='dijitTreeContent'])[4]/span[1]");
    protected By QUICK_SILVER_CAMPAIGN = By.xpath(".//span[text()='QuickSilver']");
    protected By SELECT_CAMPAIGN_BUTTON = By.xpath(".//span[text()='Select']");
    protected By ITEM_DISCOUNT_CATEGORY = By.xpath(".//h2[text()='Item Discount']/following-sibling::ul//h3[text()='Buy Products for Discount from Other Selection']");
    protected By ORDER_DISCOUNT_CATEGORY = By.xpath(".//h2[text()='Order Discount']/following-sibling::ul//h3[text()='Buy Products and Get Discount on Order']");
    protected By SHIPPING_DISCOUNT_CATEGORY = By.xpath(".//h2[text()='Shipping Discount']/following-sibling::ul//h3[text()='Buy Products for Discount on Shipping Cost']");
    protected By ADD_DISCOUNT_MODAL_TITLE = By.xpath(".//div[@title='Please Note']");
    protected By ADD_DISCOUNT_MODAL_COUPON_NAME = By.xpath("((.//label[text()='Name'])[2]/following-sibling::div//input)[2]");
    protected By ADD_DISCOUNT_MODAL_OK_BUTTON = By.xpath(".//span[text()='OK']");
    protected By SAME_AS_CAMPAIGN_DATE_RADIO_BUTTON = By.xpath(".//label[text()='Same as the campaign']/preceding-sibling::div/input");
    protected By ACTIVE_DISCOUNT_CHECKBOX = By.xpath(".//label[text()='Active']/preceding-sibling::div//input");
    protected By PROMOTION_CODE_BOX = By.xpath("(.//label[text()='Promotion code']/following-sibling::div//input)[2]");
    protected By X_ITEMS_BOX = By.xpath("(.//label[text()='X items']/following-sibling::div//input)[5]");
    protected By BUY_PRODUCT_BROWSE_LINK = By.xpath(".//*[text()='Buy at least...']//following-sibling::ul//a[text()='Browse...']");
    protected By SELECT_CONTENT_LABEL = By.xpath(".//span[text()='Select Content']");
    protected By CATALOG_NAME_FASHION_BUY = By.xpath("(.//span[text()='Fashion'])[2]");
    protected By CATALOG_NAME_FASHION_GET = By.xpath("(.//span[text()='Fashion'])[3]");
    protected By CATALOG_ADD_BUTTON = By.xpath(".//span[text()='Add']");
    protected By GET_PRODUCT_BROWSE_LINK = By.xpath(".//*[text()='Get...']//following-sibling::ul//a[text()='Browse...']");
    protected By PERCENTAGE_OFF_RADIO_BUTTON = By.xpath(".//label[text()='Percentage off']/preceding-sibling::div//input");
    protected By PERCENTAGE_OFF_BOX = By.xpath("(.//label[text()='Percentage off']/following-sibling::div//input)[5]");
    protected By AMOUNT_OFF_RADIO_BUTTON = By.xpath(".//label[text()='Amount off']/preceding-sibling::div//input");
    protected By AMOUNT_OFF_USD_BOX = By.xpath("(.//td[normalize-space(.)='USD']/following-sibling::td//div//input)[2]");
    protected By CONTENT_SAVE_BUTTON = By.xpath(".//span[text()='Save']");
    protected By ALL_US_MARKET_SHIP_OPTION_LABELS = By.xpath(".//label[contains(text(),'Markets: SWE, US')]");
    protected String ALL_US_MARKET_SHIP_OPTION_CHECKBOX = "(.//label[contains(text(),'Markets: SWE, US')]/preceding-sibling::div//input)[<<text_replace>>]";

    /**
     * This method helps to navigate to left panel option & nested option
     *
     * @param submenu      Left panel's option
     * @param nestedOption Left panel's nested option
     */
    public void selectLeftPanelOption(String submenu, String nestedOption) {
        waitForPageLoad();
        WebElement node = wait.waitForElementPresent(By.xpath(LEFT_PANEL_OPTION.replace("<<text_replace>>", submenu)));
        if (element.isElementPresent(By.xpath(COLLAPSED_LEFT_PANEL_OPTION.replace("<<text_replace>>", submenu)))) {
            click.moveToElementAndClick(node);
            wait.waitForElementPresent(By.xpath(EXPANDED_LEFT_PANEL_OPTION.replace("<<text_replace>>", submenu)));
        }
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(LEFT_PANEL_OPTION.replace("<<text_replace>>", nestedOption))));
        waitForPageLoad();
        VertexLogger.log("Selected option of submenu: " + submenu + " and nested option: " + nestedOption);
    }

    /**
     * Expands main grid
     */
    public void expandMainGrid() {
        waitForPageLoad();
        wait.waitForElementPresent(MAIN_GRID_OVERLAY);
        if (element.isElementPresent(COLLAPSED_MAIN_GRID)) {
            click.moveToElementAndClick(COLLAPSED_MAIN_GRID);
            wait.waitForElementPresent(EXPANDED_MAIN_GRID);
            VertexLogger.log("Expanded main grid");
        }
    }

    /**
     * Checks whether coupon code is already present or not?
     *
     * @param couponCode Coupon Code which is to be checked
     * @return true or false based on condition check
     */
    public boolean checkIfCouponExists(String couponCode) {
        VertexLogger.log("Checking " + couponCode + " in existing coupons' list");
        waitForPageLoad();
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
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(CREATE_CONTENT_BUTTON));
        click.moveToElementAndClick(wait.waitForElementPresent(CREATE_CONTENT_DISCOUNT_OPTION));
        wait.waitForElementPresent(SELECT_ITEM_LABEL);
        wait.waitForElementPresent(CAMPAIGNS_LIST);
        VertexLogger.log("Going to create new discount");
    }

    /**
     * Selects default campaign
     */
    public void selectDefaultCampaign() {
        waitForPageLoad();
        wait.waitForElementPresent(SELECT_ITEM_LABEL);
        click.moveToElementAndClick(wait.waitForElementPresent(QUICK_SILVER_CAMPAIGN));
        click.moveToElementAndClick(wait.waitForElementEnabled(SELECT_CAMPAIGN_BUTTON));
        waitForPageLoad();
        VertexLogger.log("Selected default campaign for creation of discount");
    }

    /**
     * Selects default category for item based discounts
     */
    public void selectDefaultCategoryForItemBasedDiscount() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(ITEM_DISCOUNT_CATEGORY));
        VertexLogger.log("Default category is selected for Item based discount");
    }

    /**
     * Selects default category for order based discounts
     */
    public void selectDefaultCategoryForOrderBasedDiscount() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(ORDER_DISCOUNT_CATEGORY));
        VertexLogger.log("Default category is selected for Order based discount");
    }

    /**
     * Selects default category for shipping based discounts
     */
    public void selectDefaultCategoryForShippingBasedDiscount() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(SHIPPING_DISCOUNT_CATEGORY));
        VertexLogger.log("Default category is selected for Shipping based discount");
    }

    /**
     * Enters discount name
     *
     * @param discountName Discount Name
     */
    public void enterDiscountName(String discountName) {
        waitForPageLoad();
        wait.waitForElementPresent(ADD_DISCOUNT_MODAL_TITLE);
        WebElement name = wait.waitForElementPresent(ADD_DISCOUNT_MODAL_COUPON_NAME);
        click.moveToElementAndClick(name);
        text.enterText(name, Keys.chord(Keys.CONTROL, "a"));
        text.enterText(name, discountName, false);
        click.moveToElementAndClick(ADD_DISCOUNT_MODAL_OK_BUTTON);
        VertexLogger.log("Entered discount name: " + discountName);
    }

    /**
     * Enter promotional code
     *
     * @param code promotional code
     */
    public void enterPromotionCode(String code) {
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(PROMOTION_CODE_BOX), code);
        VertexLogger.log("Entered promotion code: " + code);
    }

    /**
     * Sets default start date for discount
     */
    public void selectSameAsCampaignDate() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(SAME_AS_CAMPAIGN_DATE_RADIO_BUTTON));
        VertexLogger.log("Selected Same date as campaign for the discount");
    }

    /**
     * Activate the discount
     */
    public void makeActiveDiscount() {
        waitForPageLoad();
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
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(X_ITEMS_BOX), xItem);
        VertexLogger.log("Entered X Items: " + xItem);
    }

    /**
     * Selects default catalog for Buy at least products
     */
    public void selectBuyDefaultCatalog() {
        waitForPageLoad();
        wait.waitForElementPresent(SELECT_CONTENT_LABEL);
        if (!element.isElementPresent(CATALOG_NAME_FASHION_BUY)) {
            click.moveToElementAndClick(wait.waitForElementPresent(EXPAND_CAMPAIGN));
        }
        click.moveToElementAndClick(wait.waitForElementPresent(CATALOG_NAME_FASHION_BUY));
        click.moveToElementAndClick(wait.waitForElementEnabled(CATALOG_ADD_BUTTON));
        waitForPageLoad();
        VertexLogger.log("Selected default catalog for Buy at least product");
    }

    /**
     * Selects default catalog for Get products
     */
    public void selectGetDefaultCatalog() {
        waitForPageLoad();
        wait.waitForElementPresent(SELECT_CONTENT_LABEL);
        if (!element.isElementPresent(CATALOG_NAME_FASHION_BUY)) {
            click.moveToElementAndClick(wait.waitForElementPresent(EXPAND_CAMPAIGN));
        }
        click.moveToElementAndClick(wait.waitForElementPresent(CATALOG_NAME_FASHION_GET));
        click.moveToElementAndClick(wait.waitForElementEnabled(CATALOG_ADD_BUTTON));
        waitForPageLoad();
        VertexLogger.log("Selected default catalog for Get product");
    }

    /**
     * Select Buy at least x item's content type
     */
    public void selectBuyContentType() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(BUY_PRODUCT_BROWSE_LINK));
        waitForPageLoad();
        selectBuyDefaultCatalog();
        VertexLogger.log("Selected default Buy at least products for the discount");
    }

    /**
     * Select get product content type
     */
    public void selectGetContentType() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(GET_PRODUCT_BROWSE_LINK));
        waitForPageLoad();
        selectGetDefaultCatalog();
        VertexLogger.log("Selected default Get products for the discount");
    }

    /**
     * Enter discount percentage
     *
     * @param percent discount percent
     */
    public void enterDiscountPercentage(String percent) {
        waitForPageLoad();
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
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(AMOUNT_OFF_RADIO_BUTTON));
        text.enterText(wait.waitForElementPresent(AMOUNT_OFF_USD_BOX), usdAmount);
        VertexLogger.log("Entered discount amount: $" + usdAmount);
    }

    /**
     * Selects all Shipping options for US Market
     */
    public void selectAllUSMarketsForShipping() {
        waitForPageLoad();
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
     * Saves filled details on create content page (Discount or Campaign)
     */
    public void saveContentDetails() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(CONTENT_SAVE_BUTTON));
        waitForPageLoad();
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
}

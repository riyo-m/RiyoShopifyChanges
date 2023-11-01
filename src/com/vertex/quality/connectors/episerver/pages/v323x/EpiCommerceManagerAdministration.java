package com.vertex.quality.connectors.episerver.pages.v323x;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiCommerceManagerHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Locale;

/**
 * A java class that contains locators & helper methods of Administrator page of Episerver's Commerce Manager
 *
 * @author Shivam.Soni
 */
public class EpiCommerceManagerAdministration extends EpiCommerceManagerHomePage {

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiCommerceManagerAdministration(WebDriver driver) {
        super(driver);
    }

    protected String WAREHOUSE_NAME = ".//tr//a[normalize-space(.)='<<text_replace>>']";
    protected By WAREHOUSE_OVERVIEW_TAB = By.xpath(".//span[@class='ajax__tab_tab'][text()='Overview']");
    protected By WAREHOUSE_ADDRESS_TAB = By.xpath(".//span[@class='ajax__tab_tab'][text()='Address']");
    protected By WAREHOUSE_FIRST_NAME = By.xpath(".//td[normalize-space(.)='First Name:']/following-sibling::td//input");
    protected By WAREHOUSE_LAST_NAME = By.xpath(".//td[normalize-space(.)='Last Name:']/following-sibling::td//input");
    protected By WAREHOUSE_ORGANIZATION = By.xpath(".//td[normalize-space(.)='Organization:']/following-sibling::td//input");
    protected By WAREHOUSE_LINE1 = By.xpath(".//td[normalize-space(.)='Line 1:']/following-sibling::td//input");
    protected By WAREHOUSE_CITY = By.xpath(".//td[normalize-space(.)='City:']/following-sibling::td//input");
    protected By WAREHOUSE_STATE = By.xpath(".//td[normalize-space(.)='State:']/following-sibling::td//input");
    protected By WAREHOUSE_COUNTRY_CODE = By.xpath(".//td[normalize-space(.)='Country Code:']/following-sibling::td//input");
    protected By WAREHOUSE_COUNTRY_NAME = By.xpath(".//td[normalize-space(.)='Country Name:']/following-sibling::td//input");
    protected By WAREHOUSE_POSTAL_CODE = By.xpath(".//td[normalize-space(.)='Postal Code:']/following-sibling::td//input");
    protected By WAREHOUSE_REGION_CODE = By.xpath(".//td[normalize-space(.)='Region Code:']/following-sibling::td//input");
    protected By WAREHOUSE_REGION_NAME = By.xpath(".//td[normalize-space(.)='Region Name:']/following-sibling::td//input");
    protected By WAREHOUSE_DAY_PHONE = By.xpath(".//td[normalize-space(.)='Day Phone:']/following-sibling::td//input");
    protected By WAREHOUSE_EMAIL = By.xpath(".//td[normalize-space(.)='Email:']/following-sibling::td//input");
    protected By WAREHOUSE_OK_BUTTON = By.xpath(".//input[@value='OK']");

    /**
     * Selects warehouse
     *
     * @param warehouse warehouse name
     */
    public void selectWarehouse(String warehouse) {
        waitForPageLoad();
        switchToRightIframe();
        WebElement whName = wait.waitForElementPresent(By.xpath(WAREHOUSE_NAME.replace("<<text_replace>>", warehouse)));
        click.moveToElementAndClick(whName);
        VertexLogger.log("Selected " + warehouse);
        waitForPageLoad();
        wait.waitForElementPresent(WAREHOUSE_OVERVIEW_TAB);
        wait.waitForElementPresent(WAREHOUSE_ADDRESS_TAB);
        window.switchToDefaultContent();
    }

    /**
     * Helps to set Warehouse address
     * There are 7 mandatory parameters on UI so only 7 parameters should be passed to set warehouse address
     * While passing warehouse address please must follow mentioned sequence:-
     * Address Line1, City, State, Country Code, Country Name, Postal Code, Region Code
     * example: commerceManagerHomePage.setWarehouseAddress("542 W. 27th Street", "New York", "New York", "US", "United States", "10001", "NY");
     *
     * @param address address of warehouse
     */
    public void setWarehouseAddress(String... address) {
        if (address.length != 7) {
            Assert.fail("Failed due to wrong parameters");
            VertexLogger.log("Set Warehouse address has some mis-match in the parameters, kindly check JavaDoc for parameters guide");
        }
        waitForPageLoad();
        switchToRightIframe();
        click.moveToElementAndClick(wait.waitForElementPresent(WAREHOUSE_ADDRESS_TAB));
        text.enterText(wait.waitForElementPresent(WAREHOUSE_FIRST_NAME), EpiDataCommon.DefaultContactDetails.WAREHOUSE_FIRST_NAME.text);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_LAST_NAME), EpiDataCommon.DefaultContactDetails.WAREHOUSE_LAST_NAME.text);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_ORGANIZATION), EpiDataCommon.DefaultContactDetails.WAREHOUSE_ORGANIZATION.text);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_LINE1), address[0]);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_CITY), address[1]);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_STATE), address[2]);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_COUNTRY_CODE), address[3]);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_COUNTRY_NAME), address[4]);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_POSTAL_CODE), address[5]);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_REGION_CODE), address[6]);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_REGION_NAME), address[2]);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_DAY_PHONE), EpiDataCommon.DefaultContactDetails.WAREHOUSE_DEFAULT_CONTACT.text);
        text.enterText(wait.waitForElementPresent(WAREHOUSE_EMAIL), address[1].toLowerCase(Locale.ROOT).replaceAll(" ", "") + EpiDataCommon.DefaultContactDetails.WAREHOUSE_EMAIL_DOMAIN.text);
        VertexLogger.log("Warehouse address is set for: " + Arrays.toString(address));
        click.moveToElementAndClick(wait.waitForElementPresent(WAREHOUSE_OK_BUTTON));
        waitForPageLoad();
        window.switchToDefaultContent();
    }

    /**
     * Sets default addresses of warehouse or shipping stores
     *
     * @param whName warehouse or store name
     */
    public void setDefaultAddressOfWarehouse(String whName) {
        String[] newYorkStore = {"542 W. 27th Street", "New York", "New York", "US", "United States", "10001", "NY"};
        String[] londonStore = {"32 Russell Square", "London", "", "GB", "UNITED KINGDOM", "HP19 3EQ", "Buckinghamshire"};
        String[] hanoiStore = {"6 Dinh Tien Hoang", "Hanoi", "", "VN", "Vietnam", "10000", "Hoàn Kiếm"};
        String[] stockholmStore = {"Tegeluddsvägen 1", "Stockholm", "", "SE", "Sweden", "115 41", "Stockholm"};
        String[] sydneyStore = {"456 Kent Street", "Sydney", "Sydney", "AU", "Australia", "NSW 2020", "Sydney"};
        String[] tokyoStore = {"2002-10-01", "東京都", "千代田区", "JP", "Japan", "600-8994", "有楽町"};

        selectWarehouse(whName);
        switch (whName.toLowerCase(Locale.ROOT)) {
            case "new york store":
                setWarehouseAddress(newYorkStore);
                break;
            case "london store":
                setWarehouseAddress(londonStore);
                break;
            case "hanoi store":
                setWarehouseAddress(hanoiStore);
                break;
            case "stockholm store":
                setWarehouseAddress(stockholmStore);
                break;
            case "sydney store":
                setWarehouseAddress(sydneyStore);
                break;
            case "tokyo store":
                setWarehouseAddress(tokyoStore);
                break;
            default:
                VertexLogger.log("Warehouse name differs in script & on UI kindly check & correct.");
                Assert.fail("Warehouse name not found!");
        }
    }
}

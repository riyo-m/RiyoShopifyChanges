package com.vertex.quality.connectors.episerver.pages.v324x;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Locale;

/**
 * Warehouse page to update warehouse settings
 *
 * @author Shivam.Soni
 */
public class EpiWarehousePage extends EpiAdministrationHomePage {

    protected String WAREHOUSE_NAME = ".//td//a[normalize-space(.)='<<text_replace>>']";
    protected By WAREHOUSE_OVERVIEW_TAB = By.xpath(".//div[normalize-space(.)='Overview']");
    protected By WAREHOUSE_ADDRESS_TAB = By.xpath(".//div[normalize-space(.)='Address']");
    protected By WAREHOUSE_FIRST_NAME = By.xpath(".//input[@name='contactInformation.firstName']");
    protected By WAREHOUSE_LAST_NAME = By.xpath(".//input[@name='contactInformation.lastName']");
    protected By WAREHOUSE_ORGANIZATION = By.xpath(".//input[@name='contactInformation.organization']");
    protected By WAREHOUSE_LINE1 = By.xpath(".//input[@name='contactInformation.line1']");
    protected By WAREHOUSE_CITY = By.xpath(".//input[@name='contactInformation.city']");
    protected By WAREHOUSE_STATE = By.xpath(".//input[@name='contactInformation.state']");
    protected By WAREHOUSE_COUNTRY_CODE = By.xpath(".//input[@name='contactInformation.countryCode']");
    protected By WAREHOUSE_COUNTRY_NAME = By.xpath(".//input[@name='contactInformation.countryName']");
    protected By WAREHOUSE_POSTAL_CODE = By.xpath(".//input[@name='contactInformation.postalCode']");
    protected By WAREHOUSE_REGION_CODE = By.xpath(".//input[@name='contactInformation.regionCode']");
    protected By WAREHOUSE_REGION_NAME = By.xpath(".//input[@name='contactInformation.regionName']");
    protected By WAREHOUSE_DAY_PHONE = By.xpath(".//input[@name='contactInformation.daytimePhoneNumber']");
    protected By WAREHOUSE_EMAIL = By.xpath(".//input[@name='contactInformation.email']");
    protected By WAREHOUSE_SAVE_BUTTON = By.xpath(".//button//span[text()='Save']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiWarehousePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects warehouse
     *
     * @param warehouse warehouse name
     */
    public void selectWarehouse(String warehouse) {
        waitForSpinnerToBeDisappeared();
        WebElement whName = wait.waitForElementPresent(By.xpath(WAREHOUSE_NAME.replace("<<text_replace>>", warehouse)));
        click.moveToElementAndClick(whName);
        VertexLogger.log("Selected " + warehouse);
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(WAREHOUSE_OVERVIEW_TAB);
        wait.waitForElementPresent(WAREHOUSE_ADDRESS_TAB);
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
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(WAREHOUSE_ADDRESS_TAB));

        WebElement fName = wait.waitForElementPresent(WAREHOUSE_FIRST_NAME);
        text.selectAllAndInputText(fName, EpiDataCommon.DefaultContactDetails.WAREHOUSE_FIRST_NAME.text);

        WebElement lName = wait.waitForElementPresent(WAREHOUSE_LAST_NAME);
        text.selectAllAndInputText(lName, EpiDataCommon.DefaultContactDetails.WAREHOUSE_LAST_NAME.text);

        WebElement org = wait.waitForElementPresent(WAREHOUSE_ORGANIZATION);
        text.selectAllAndInputText(org, EpiDataCommon.DefaultContactDetails.WAREHOUSE_ORGANIZATION.text);

        WebElement line1 = wait.waitForElementPresent(WAREHOUSE_LINE1);
        text.selectAllAndInputText(line1, address[0]);

        WebElement city = wait.waitForElementPresent(WAREHOUSE_CITY);
        text.selectAllAndInputText(city, address[1]);

        WebElement state = wait.waitForElementPresent(WAREHOUSE_STATE);
        text.selectAllAndInputText(state, address[2]);

        WebElement cCode = wait.waitForElementPresent(WAREHOUSE_COUNTRY_CODE);
        text.selectAllAndInputText(cCode, address[3]);

        WebElement cName = wait.waitForElementPresent(WAREHOUSE_COUNTRY_NAME);
        text.selectAllAndInputText(cName, address[4]);

        WebElement postal = wait.waitForElementPresent(WAREHOUSE_POSTAL_CODE);
        text.selectAllAndInputText(postal, address[5]);

        WebElement rCode = wait.waitForElementPresent(WAREHOUSE_REGION_CODE);
        text.selectAllAndInputText(rCode, address[6]);

        WebElement rName = wait.waitForElementPresent(WAREHOUSE_REGION_NAME);
        text.selectAllAndInputText(rName, address[2]);

        WebElement dayPH = wait.waitForElementPresent(WAREHOUSE_DAY_PHONE);
        text.selectAllAndInputText(dayPH, EpiDataCommon.DefaultContactDetails.WAREHOUSE_DEFAULT_CONTACT.text);

        WebElement email = wait.waitForElementPresent(WAREHOUSE_EMAIL);
        text.selectAllAndInputText(email, address[1].toLowerCase(Locale.ROOT).replaceAll(" ", "") + EpiDataCommon.DefaultContactDetails.WAREHOUSE_EMAIL_DOMAIN.text);
        text.pressTab(email);
        VertexLogger.log("Warehouse address is set for: " + Arrays.toString(address));
        click.javascriptClick(wait.waitForElementEnabled(WAREHOUSE_SAVE_BUTTON));
        waitForSpinnerToBeDisappeared();
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

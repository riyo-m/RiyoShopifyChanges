package com.vertex.quality.connectors.dynamics365.retail.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DRetailShippingPage extends DFinanceBasePage {

    protected By STANDARD_BUTTON = By.xpath("//div[text()= 'Standard']");
    protected By STANDARD_OVERNIGHT_BUTTON = By.xpath("//div[contains(text(), 'Standard overnight')]");
    protected By SHIP_TO_ADDRESSES_BUTTON = By.xpath("//div[contains(@data-ax-bubble, 'viewAllCustomerAddressesButton')]/button");
    protected By SHIP_OK_BUTTON = By.xpath("//button[@data-ax-bubble='messageDialog_okButtonClick']");

    protected By DATE_DAY = By.xpath("//select[@aria-label='Week days drop down']");
    protected By DATE_MONTH = By.xpath("//select[@aria-label='Months drop down']");

    public DRetailShippingPage( WebDriver driver )
    {
        super(driver);
    }


    /**
     * Clicks the standard shipping button and selects date
     */
    public void shipStandard() {
        wait.waitForElementDisplayed(STANDARD_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(STANDARD_BUTTON);
        waitForPageLoad();

        selectDate();
    }

    /**
     * Clicks the standard overnight shipping button and selects date
     */
    public void shipStandardOvernight() {
        wait.waitForElementDisplayed(STANDARD_OVERNIGHT_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(STANDARD_OVERNIGHT_BUTTON);
        waitForPageLoad();

        selectDate();
    }

    /**
     * Selects the given ship-to address
     * @param addressName
     */
    public void selectShipToAddress(String addressName) {
        wait.waitForElementDisplayed(SHIP_TO_ADDRESSES_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SHIP_TO_ADDRESSES_BUTTON);

        By addressLoc = By.xpath(String.format("//a[text()='%s']/../following-sibling::div", addressName));
        wait.waitForElementDisplayed(addressLoc);
        click.clickElementIgnoreExceptionAndRetry(addressLoc);
    }

    /**
     * Selects the date to pickup or ship
     */
    public void selectDate(){
        WebElement dateEle = wait.waitForElementDisplayed(DATE_DAY);
        Select s = new Select(dateEle);
        String Day = s.getFirstSelectedOption().getText();
        VertexLogger.log(Day);

        Date dt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DATE, 1);
        dt = calendar.getTime();

        String tommorowsDateMonth = new SimpleDateFormat("M").format(dt);
        String tommorowsDateDay = new SimpleDateFormat("d").format(dt);

        if (tommorowsDateMonth != new SimpleDateFormat("M").format(LocalDate.now().getDayOfMonth()))
        {
            WebElement dateMonthEle = wait.waitForElementDisplayed(DATE_MONTH);
            Select sMonth = new Select(dateMonthEle);
            String Month = sMonth.getFirstSelectedOption().getText();
            VertexLogger.log(Month);
            sMonth.selectByValue(String.valueOf(Integer.valueOf(tommorowsDateMonth)-1));
        }
        s.selectByValue(tommorowsDateDay);
        wait.waitForElementDisplayed(SHIP_OK_BUTTON);
        click.clickElementCarefully(SHIP_OK_BUTTON);
    }
}

package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the workday Business Process page
 * contains all the methods necessary to interact with the page
 * @author dpatel
 */
public class WorkdayBusinessProcessPage extends VertexPage {

    public WorkdayBusinessProcessPage(WebDriver driver) { super(driver); }

    protected By suplInvSpectre = By.cssSelector("div[title='Supplier Invoice Event for Spectre, Inc.']");
    protected By suplInvGPS = By.cssSelector("div[title='Supplier Invoice Event for Green Planet Solutions, Inc. (USA)']");
    protected By custInvSpectre = By.cssSelector("div[title='Customer Invoice Event for Spectre, Inc.']");
    protected By custInvGMS = By.xpath("//*[contains(text(),'Customer Invoice Event for Global Modern Services, Ltd (Canada)')]");
    protected By custInvGPS = By.xpath("//*[contains(text(),'Customer Invoice Event for Green Planet Solutions, Inc.')]");
    protected By custInvVertex = By.xpath("//*[contains(text(),'Customer Invoice Event (Vertex Companies)')]");
    protected By secondIntegrationCust = By.xpath("(//span[contains(text(),'Configure VTX-')])[1]");
    protected By secondIntegrationSup = By.xpath("//span[text()='Configure VTX-SupplierInvoiceBP']");
    protected By thirdIntegrationCust = By.xpath("(//span[contains(text(),'Configure VTX-')])[2]");
    protected By ok = By.xpath("//span[@title='OK']");
    protected By postOption = By.xpath("(//span[@data-uxi-widget-type='selectinputicon'])[4]");
    protected By distribute = By.xpath("//div[@data-automation-label='DISTRIBUTE']");
    protected By post = By.xpath("//div[@data-automation-label='POST']");
    protected By quote = By.xpath("//div[@data-automation-label='QUOTE']");
    protected By title = By.xpath("//span[@title='View Workflow Step']");
    protected By delete = By.xpath("(//div[@data-automation-id='DELETE_charm'])[3]");
    protected By custInvDef = By.xpath("//div[@title='Customer Invoice Event (Default Definition)']");
    protected By waitForSearchPage = By.xpath("//aside[@class='css-1aigrd2-leftColumn']");

    /**
     * This method gets the Supplier Invoice BP page for given company
     *
     * @param company Name of the company
     */
    public void getSupplierInvoiceBPPage(String company) {

        WorkdayHomePage homePage = initializePageObject(WorkdayHomePage.class);
        homePage.searchFromHomePage("bp: Supplier invoice event ", false);
        clickOnSearchResults(company, false);

    }

    /**
     * This method gets the Customer Invoice BP page for given company
     *
     * @param company Name of the company
     */
    public void getCustomerInvoiceBPPage(String company) {
        waitForPageLoad();
        WorkdayHomePage homePage = initializePageObject(WorkdayHomePage.class);
        homePage.searchFromHomePage("bp: Customer invoice event ", false);
        try {
            wait.waitForElementDisplayed(waitForSearchPage, 5);
        } catch (TimeoutException e) {
            homePage.searchFromHomePage("bp: Customer invoice event ", false);
        }
        clickOnSearchResults(company, true);
    }


    /**
     * This method clicks on the search results
     *
     * @param searchName name of the search results
     */
    public void clickOnSearchResults(String searchName, boolean isCustomer) {

        if (searchName.contains("spectre")) {
            if (isCustomer) {
                click.javascriptClick(custInvSpectre);
            } else {
                click.javascriptClick(suplInvSpectre);
            }
        } else if (searchName.contains("GMS")) {
            wait.waitForElementPresent(custInvGMS);
            click.javascriptClick(custInvGMS);
        } else if (searchName.contains("GPS")) {
            if (isCustomer) {
                click.javascriptClick(custInvGPS);
            } else {
                click.javascriptClick(suplInvGPS);
            }
        } else {
            if (searchName.contains("Vertex")) {
                if (isCustomer) {
                    wait.waitForElementDisplayed(custInvVertex);
                    click.javascriptClick(custInvVertex);
                } else {
                    click.javascriptClick(custInvVertex); // vertex companies, but needs to be changed for supplier
                }
            }
        }
        waitForPageLoad();
    }

    /**
     * This method Change post option for the spectreBP assuming it first quote and then either Post/Distribute
     *
     * @param option PostOption
     */
    public void changeBPForSpectreSupplierInvoice(String option) {
        getSupplierInvoiceBPPage("spectre");
        click.clickElementCarefully(secondIntegrationSup);
        waitForPageLoad();
        click.clickElementCarefully(ok);
        click.clickElementCarefully(delete);
        WebElement postOptionEle = wait.waitForElementDisplayed(postOption);
        scroll.scrollElementIntoView(postOptionEle);
        postOptionEle.click();
        if (option.equalsIgnoreCase("distribute")) {
            click.clickElementCarefully(distribute);
        } else {
            click.clickElementCarefully(post);
        }
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(title);
    }

    /**
     * This method Change post option for the GPS BP assuming it only does Quote/Post
     *
     * @param option PostOption
     */
    public void changeBPForGPSSupplierInvoice(String option) {
        getSupplierInvoiceBPPage("GPS");
        click.clickElementCarefully(secondIntegrationSup);
        waitForPageLoad();
        click.clickElementCarefully(ok);
        click.clickElementCarefully(delete);
        WebElement postOptionEle = wait.waitForElementDisplayed(postOption);
        scroll.scrollElementIntoView(postOptionEle);
        postOptionEle.click();
        if (option.equalsIgnoreCase("post")) {
            click.clickElementCarefully(post);
        } else {
            click.clickElementCarefully(quote);
        }
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(title);
    }

    /**
     * This method Change post option for the spectreBP assuming it first quote and then either Quote/Post
     *
     * @param option PostOption
     */
    public void changeBPForSpectreCustomerInvoice(String option) {
        getCustomerInvoiceBPPage("spectre");
        click.clickElementCarefully(secondIntegrationCust);
        waitForPageLoad();
        click.clickElementCarefully(ok);
        click.clickElementCarefully(delete);
        WebElement postOptionEle = wait.waitForElementDisplayed(postOption);
        scroll.scrollElementIntoView(postOptionEle);
        postOptionEle.click();
        if (option.equalsIgnoreCase("post")) {
            click.clickElementCarefully(post);
        } else {
            click.clickElementCarefully(quote);
        }
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(title);
    }

    /**
     * This method Change post option for the GPS BP assuming it only does Quote-Post or Quote-Distribute
     *
     * @param option PostOption
     */
    public void changeBPForGPSCustomerInvoice(String option) {
        getCustomerInvoiceBPPage("GPS");
        click.clickElementCarefully(thirdIntegrationCust);
        waitForPageLoad();
        click.clickElementCarefully(ok);
        click.clickElementCarefully(delete);
        WebElement postOptionEle = wait.waitForElementDisplayed(postOption);
        scroll.scrollElementIntoView(postOptionEle);
        postOptionEle.click();
        if (option.equalsIgnoreCase("distribute")) {
            click.clickElementCarefully(distribute);
        } else {
            click.clickElementCarefully(post);
        }
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(title);
    }

    /**
     * This method Change post option for the GMS Canada assuming it first quote and then either Quote/Post
     *
     * @param option PostOption
     */
    public void changeBPForGMSCanadaCustomerInvoice(String option) {
        getCustomerInvoiceBPPage("GMS Canada");
        click.clickElementCarefully(thirdIntegrationCust);
        waitForPageLoad();
        click.clickElementCarefully(ok);
        click.clickElementCarefully(delete);
        WebElement postOptionEle = wait.waitForElementDisplayed(postOption);
        scroll.scrollElementIntoView(postOptionEle);
        postOptionEle.click();
        if (option.equalsIgnoreCase("post")) {
            click.clickElementCarefully(post);
        } else {
            click.clickElementCarefully(distribute);
        }
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(title);
    }

    /**
     * This method Change post option for the GMS Canada assuming it first quote and then either Quote/Post
     *
     * @param option PostOption
     * @author Vishwa
     */

    public void changeBusinessProcessForCustomerInvoice(String company, String option) {

        getCustomerInvoiceBPPage(company);
        click.clickElementCarefully(thirdIntegrationCust);
        waitForPageLoad();
        click.clickElementCarefully(ok);
        click.clickElementCarefully(delete);
        WebElement postOptionEle = wait.waitForElementDisplayed(postOption);
        scroll.scrollElementIntoView(postOptionEle);
        postOptionEle.click();
        if (option.equalsIgnoreCase("post")) {
            click.clickElementCarefully(post);
        } else {
            click.clickElementCarefully(distribute);
        }
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(title);
    }
}

package com.vertex.quality.connectors.orocommerce.components.storefront;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.orocommerce.enums.OroAddresses;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroStoreFrontHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author alewis
 */
public class checkoutBillingInfo extends VertexComponent {
    public checkoutBillingInfo(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    By firstNameFieldLoc = By.xpath(".//label[text()='First name']/following-sibling::input");
    By lastNameFieldLoc = By.xpath(".//label[text()='Last name']/following-sibling::input");
    By organizationFieldLoc = By.cssSelector("input[id*='billing_address_organization']");
    By zipCodeFieldLoc = By.xpath(".//label[text()='Zip/Postal Code']/following-sibling::input");
    By cityFieldLoc = By.xpath(".//label[text()='City']/following-sibling::input");
    By streetFieldLoc = By.xpath(".//label[text()='Street']/following-sibling::input");
    By vatRegLoc = By.cssSelector("input[id*='billing_address_vertex_registration']");
    By couponCodeButtonClass = By.xpath(".//span[text()='I have a Coupon Code']");
    By couponCodeFieldClass = By.className("coupon-container__form");
    By buttonClass = By.className("btn");
    By inputClass = By.className("input");
    By trashClass = By.className("fa-trash-o");
    By continueButtonLink = By.xpath("//button[contains(text(),'Continue')]");
    By shipToAddressCheckbox = By.xpath(".//input[contains(@id, 'oro_workflow_transition_ship_to_bill')]");

    /**
     * fill all address details for providing billing address while checkout.
     *
     * @param address billing address while checkout.
     */
    public void fillAddressFields(OroAddresses address) {
        String FirstName = "TesterFirstName";
        WebElement firstNameField = wait.waitForElementDisplayed(firstNameFieldLoc);
        OroStoreFrontHomePage homepage = new OroStoreFrontHomePage(driver);
        homepage.enterJavascriptText(FirstName, firstNameField);
        text.enterText(lastNameFieldLoc, "TesterLastName");
        //		text.enterText(organizationFieldLoc, "TesterOrganization");
        text.enterText(streetFieldLoc, address.getLine1());
        text.enterText(cityFieldLoc, address.getCity());
        selectCountry(address);
        jsWaiter.sleep(1500);
        selectState(address);
        jsWaiter.sleep(50);
        text.enterText(zipCodeFieldLoc, address.getZip());
    }

    /**
     * fill all address details for providing billing address while checkout.
     *
     * @param address billing address while checkout.
     */
    public void fillAddressFieldsCarefully(OroAddresses address) {
        text.enterText(firstNameFieldLoc, "TesterFirstName");
        text.enterText(lastNameFieldLoc, "TesterLastName");
        text.enterText(organizationFieldLoc, "TesterOrganization");
        text.enterText(streetFieldLoc, address.getLine1());
        text.enterText(cityFieldLoc, address.getCity());
        selectCountryCarefully(address);
        selectState(address);
        text.enterText(zipCodeFieldLoc, address.getZip());
    }

    /**
     * fill all address details for providing billing address for EU while checkout.
     *
     * @param address billing address while checkout.
     * @param vatReg  atReg vatReg for EU countries.
     */
    public void fillAddressFieldsEU(OroAddresses address, String vatReg) {
        text.enterText(firstNameFieldLoc, "TesterFirstName");
        text.enterText(lastNameFieldLoc, "TesterLastName");
        text.enterText(organizationFieldLoc, "TesterOrganization");
        text.enterText(streetFieldLoc, address.getLine1());
        text.enterText(cityFieldLoc, address.getCity());
        selectCountryCarefully(address);
        selectState(address);
        text.enterText(zipCodeFieldLoc, address.getZip());
        if (element.isElementPresent(vatRegLoc)) {
            text.enterText(vatRegLoc, vatReg);
        }
    }

    /**
     * select billing country while checkout.
     *
     * @param address billing country address while checkout.
     */
    public void selectCountry(OroAddresses address) {
        WebElement countyField = wait.waitForElementDisplayed(By.cssSelector("div[id*='billing_address_country']"));
        click.clickElement(countyField);

        WebElement countryListInputField = wait.waitForElementPresent(By.xpath("/html/body/div[8]/div/input"));
        text.enterText(countryListInputField, address.getCountry());
        wait.waitForElementPresent(By.cssSelector("ul[class='select2-results']"));
        text.pressEnter(countryListInputField);
    }

    /**
     * select billing country while checkout.
     *
     * @param address billing country address while checkout.
     */
    public void selectCountryCarefully(OroAddresses address) {
        WebElement countyField = wait.waitForElementDisplayed(By.cssSelector("div[id*='billing_address_country']"));
        click.clickElement(countyField);

        WebElement countryListInputField = wait.waitForElementPresent(By.xpath("/html/body/div[8]/div/input"));
        text.enterText(countryListInputField, address.getCountry());
        WebElement countriesListCont = wait.waitForElementPresent(By.cssSelector("ul[class='select2-results']"));
        String xpath = String.format("//span[contains(text(),'%s')]", address.getCountry());
        WebElement input = wait.waitForElementPresent(By.xpath("//*[@id=\"select2-drop\"]/div/input"));
        text.pressEnter(input);
        //WebElement desiredOption = wait.waitForElementPresent(By.className("select2-chosen"), countriesListCont);
        //click.clickElement(desiredOption);
    }

    /**
     * select billing state while checkout.
     *
     * @param address billing state address while checkout.
     */
    public void selectState(OroAddresses address) {
        WebElement stateFieldCont = wait.waitForElementDisplayed(By.cssSelector("div[id*='billing_address_region']"));
        click.clickElement(stateFieldCont);

        WebElement stateListInputField = wait.waitForElementPresent(By.xpath("/html/body/div[9]/div/input"));
        text.enterText(stateListInputField, address.getState());
        WebElement statesListCont = wait.waitForElementPresent(By.cssSelector("ul[class='select2-results']"));
        String xpath = String.format("//span[contains(text(),'%s')]", address.getState());
        WebElement desiredOption = wait.waitForElementPresent(By.xpath(xpath), statesListCont);
        click.clickElement(desiredOption);
    }

    /**
     * select shipTo Billing address for same billing and shipping address while checkout.
     * <p>
     * address shipping state address while checkout.
     */
    public void shipToBillingAddress() {
        WebElement checkBoxCont = wait.waitForElementPresent(By.cssSelector("label[for*='ship_to_billing_address']"));
        WebElement checkBoxElem = wait.waitForElementPresent(By.cssSelector("span[class='custom-checkbox__icon']"),
                checkBoxCont);
        click.clickElement(checkBoxElem);
    }

    /**
     * Enters the discount coupon code
     *
     * @param couponCode for applying discounts.
     */
    public void enterCoupon(String couponCode) {
        jsWaiter.sleep(1500);
        WebElement couponCodeButton = wait.waitForElementPresent(couponCodeButtonClass);
        click.clickElementCarefully(couponCodeButton);

        WebElement couponCodeField = wait.waitForElementPresent(couponCodeFieldClass);
        WebElement innerCouponField = wait.waitForElementDisplayed(inputClass, couponCodeField);
        //WebElement couponCodeInnerField = wait.waitForElementDisplayed();
        text.enterText(innerCouponField, couponCode);

        jsWaiter.sleep(500);
        WebElement button = wait.waitForElementEnabled(buttonClass, couponCodeField);
        click.javascriptClick(button);
        jsWaiter.sleep(1500);
        waitForPageLoad();
    }

    /**
     * Enters a second coupon code
     *
     * @param couponCode for applying second discounts.
     */
    public void enterSecondCoupon(String couponCode) {
        jsWaiter.sleep(1500);
        WebElement couponCodeField = wait.waitForElementPresent(couponCodeFieldClass);
        WebElement innerCouponField = wait.waitForElementDisplayed(inputClass, couponCodeField);
        //WebElement couponCodeInnerField = wait.waitForElementDisplayed();
        text.enterText(innerCouponField, couponCode);

        jsWaiter.sleep(500);
        WebElement button = wait.waitForElementPresent(buttonClass, couponCodeField);
        click.javascriptClick(button);
        jsWaiter.sleep(1500);
        waitForPageLoad();
    }

    /**
     * clicks on continue button after entering billing address while checkout.
     */
    public void clickContinue() {
        jsWaiter.sleep(1500);
        WebElement continueButton = wait.waitForElementPresent(continueButtonLink);
        click.javascriptClick(continueButton);
        this.waitForPageLoad();
    }

    /**
     * Enters a third coupon code
     *
     * @param couponCode for third discount.
     */
    public void enterThirdCoupon(String couponCode) {
        jsWaiter.sleep(1500);
        WebElement couponCodeField = wait.waitForElementPresent(couponCodeFieldClass);
        WebElement innerCouponField = wait.waitForElementDisplayed(inputClass, couponCodeField);
        //WebElement couponCodeInnerField = wait.waitForElementDisplayed();
        text.enterText(innerCouponField, couponCode);

        jsWaiter.sleep(500);
        WebElement button = wait.waitForElementPresent(buttonClass, couponCodeField);
        click.javascriptClick(button);
        jsWaiter.sleep(1500);
        waitForPageLoad();
    }

    /**
     * delete discount coupon code
     */
    public void deleteCoupon() {
        WebElement pressDelete = wait.waitForElementPresent(trashClass);
        click.clickElementCarefully(pressDelete);
    }
}

package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

/**
 * a generic representation of a CommerceTools add Customer Page
 *
 * @author vivek-kumar
 */
public class CommerceToolsAddCustomerPage extends CommerceToolsBasePage
{

    public CommerceToolsAddCustomerPage( WebDriver driver )
    {

        super(driver);
    }

    protected final By customersLink = By.xpath("//div[contains(text(),'Customers')]");
    protected final By addCustomersLink = By.xpath("//*[contains(text(),'Add customer')]");
    protected final By customerList = By.xpath("//a[contains(text(),'Customer list')]");
    protected final By emailLists =By.xpath("//*[@id='app']//thead/tr[1]/th[6]/button[1]");
    protected final By salutationName = By.xpath("//input[@name='salutation']");
    protected final By title = By.xpath("//input[@name='title']");
    protected final By firstName = By.xpath("//input[@name='firstName']");
    protected final By middleName = By.xpath("//input[@name='middleName']");
    protected final By lastName = By.xpath("//input[@name='lastName']");
    protected final By email = By.xpath("//input[@id='email']");
    protected final By yearValue = By.xpath("//input[@name='year-selector']");
    protected final By monthValue = By.xpath("//input[@name='month-selector']");
    protected final By dayValue = By.xpath("//input[@name='day-selector']");
    protected final By customerNumberField = By.xpath("//input[@name='customerNumber']");
    protected final By externalIdField = By.xpath("//input[@name='externalId']");
    protected final By customerGroupField = By.xpath("//div[preceding-sibling::div=\"Select a Customer group\"]//input");
    protected final By accountRestrictedField = By.xpath("//div[preceding-sibling::div=\"Not restricted to any store\"]//input");
    protected final By customerPasswordField = By.xpath("//input[@name='password']");
    protected final By confirmPasswordField = By.xpath("//input[@name='confirmedPassword']");
    protected final By companyNameField = By.xpath("//input[@name='companyName']");
    protected final By vatIdField = By.xpath("//input[@name='vatId']");
    protected final By saveButton = By.xpath("//div[contains(text(),'Save')]");
    protected final By Email =By.xpath("//*[@id=\"app\"]//tbody/tr");
    String beforeXpath = "//*[@id=\"app\"]//tbody/tr[";
    String afterXpath = "]/td[6]";

    /**
     * click on Customers Link in commercetools
     */
    public void clickCustomersLink( )
    {
        WebElement addCustomerField = wait.waitForElementEnabled(customersLink);
        click.moveToElementAndClick(addCustomerField);
    }
    /**
     * click on Customers Link in commercetools
     * @return value
     * @param value
     */
    public String getCustomerEmailList(String value)
    {
        WebElement emailListField = wait.waitForElementEnabled(emailLists);
        List<WebElement> rows = driver.findElements(Email);
        int rowCount = rows.size();

        for(int i=1; i<=rowCount;i++)
        {
          String actualPath = beforeXpath +i + afterXpath;
          WebElement element = driver.findElement(By.xpath(actualPath));

          if(element.getText().equals(value)) {
              Random random = new Random();
              String Value = String.valueOf(random.nextInt(100));
              value = value.concat("xyz");
          }
          }
        return value;
    }

    /**
     * click on Add Customer Link in commercetools
     */
    public void clickAddCustomersLink( )
    {
        WebElement addCustomerField = wait.waitForElementEnabled(addCustomersLink);
        click.moveToElementAndClick(addCustomerField);
    }
    /**
     * click on Add Customer Link in commercetools
     */
    public void clickCustomersListIcon( )
    {
        WebElement addCustomerField = wait.waitForElementEnabled(customerList);
        click.moveToElementAndClick(addCustomerField);
    }

    /**
     * enter salutation Name to be selected in commercetools.
     *
     * @param salutation
     */
    public void enterSalutationName( String salutation )
    {
        WebElement salutationNameField = wait.waitForElementDisplayed(salutationName);
        text.enterText(salutationNameField, salutation);
    }

    /**
     * enter customer First Name in commercetools.
     *
     * @param firstname
     */
    public void enterFirstName( final String firstname )
    {
        WebElement firstNameField = wait.waitForElementDisplayed(firstName);
        text.enterText(firstNameField, firstname);
    }

    /**
     * enter customer Middle Name in commercetools.
     *
     * @param middlename
     */
    public void enterMiddleName( final String middlename )
    {
        WebElement middleNameField = wait.waitForElementDisplayed(middleName);
        text.enterText(middleNameField, middlename);
    }

    /**
     * enter Last Name in commercetools.
     *
     * @param lastname
     */
    public void enterLastName( final String lastname )
    {
        WebElement lastNameField = wait.waitForElementDisplayed(lastName);
        text.enterText(lastNameField, lastname);
    }

    /**
     * enter email Id in commercetools.
     *
     * @param emailEntry
     */
    public void enterEmail( String emailEntry )
    {
        WebElement emailField = wait.waitForElementDisplayed(email);
        text.enterText(emailField, emailEntry);
    }

    /**
     * enter customer title in commercetools.
     *
     * @param titleName
     */
    public void enterTitle( final String titleName )
    {
        WebElement titleField = wait.waitForElementDisplayed(title);
        text.enterText(titleField, titleName);
    }

    /**
     * enter customer DOB Year Value in commercetools.
     *
     * @param year
     */
    public void enterYearValue( final String year )
    {
        WebElement yearField = wait.waitForElementDisplayed(yearValue);
        text.enterText(yearField, year);
    }

    /**
     * enter customer DOB Month Value in commercetools.
     *
     * @param month
     */
    public void enterMonthValue( final String month )
    {
        WebElement monthField = wait.waitForElementDisplayed(monthValue);
        text.enterText(monthField, month);
    }

    /**
     * enter customer DOB Day Value in commercetools.
     *
     * @param day
     */
    public void enterDayValue( final String day )
    {
        WebElement dayField = wait.waitForElementDisplayed(dayValue);
        text.enterText(dayField, day);
    }

    /**
     * enter customer Number Field in commercetools.
     *
     * @param customerNumber
     */
    public void enterCustomerNumberField( final String customerNumber )
    {
        WebElement numberField = wait.waitForElementDisplayed(customerNumberField);
        text.enterText(numberField, customerNumber);
    }

    /**
     * enter customer External Id in commercetools.
     *
     * @param externalId
     */
    public void enterExternalIdField( final String externalId )
    {
        WebElement external = wait.waitForElementDisplayed(externalIdField);
        text.enterText(external, externalId);
    }

    /**
     * enter customer Group Field in commercetools.
     *
     * @param customerGroup
     */
    public void enterCustomerGroupField( final String customerGroup )
    {
        WebElement customerGrp = wait.waitForElementDisplayed(customerGroupField);
        text.enterText(customerGrp, customerGroup);
        text.pressEnter(customerGrp);
    }

    /**
     * enter customer Account Restricted Field in commercetools.
     *
     * @param accountRestricted
     */
    public void enterAccountRestrictedField( final String accountRestricted )
    {
        WebElement accountRestrict = wait.waitForElementDisplayed(accountRestrictedField);
        text.enterText(accountRestrict, accountRestricted);
    }

    /**
     * enter customer Password Field in commercetools.
     *
     * @param customerPassword
     */
    public void enterCustomerPasswordField( final String customerPassword )
    {
        WebElement customerPwd = wait.waitForElementDisplayed(customerPasswordField);
        text.enterText(customerPwd, customerPassword);
    }

    /**
     * enter customer Confirm Password Field in commercetools.
     *
     * @param confirmPassword
     */
    public void enterConfirmPasswordField( final String confirmPassword )
    {
        WebElement confirmPwd = wait.waitForElementDisplayed(confirmPasswordField);
        text.enterText(confirmPwd, confirmPassword);
    }

    /**
     * enter Customer Name Field in commercetools
     *
     * @param companyName
     */
    public void enterCompanyNameField( final String companyName )
    {
        WebElement company = wait.waitForElementDisplayed(companyNameField);
        text.enterText(company, companyName);
    }

    /**
     * enter Customer Vat Id field in commercetools.
     *
     * @param vatId
     */
    public void enterVatIdField( final String vatId )
    {
        WebElement vat = wait.waitForElementDisplayed(vatIdField);
        text.enterText(vat, vatId);
    }

    /**
     * click to save Button after adding Group details in commercetools
     */
    public void clickSaveButton( )
    {
        WebElement saveButtonField = wait.waitForElementDisplayed(saveButton);
        click.moveToElementAndClick(saveButtonField);
    }
}

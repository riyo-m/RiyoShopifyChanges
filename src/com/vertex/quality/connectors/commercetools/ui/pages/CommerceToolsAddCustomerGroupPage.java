package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

/**
 * a generic representation of a CommerceTools Add Customer Group Page
 *
 * @author vivek-kumar
 */
public class CommerceToolsAddCustomerGroupPage extends CommerceToolsBasePage
{

    public CommerceToolsAddCustomerGroupPage( WebDriver driver )
    {

        super(driver);
    }

    protected final By customersLink = By.xpath("//div[contains(text(),'Customers')]");
    protected final By addCustomerGroupLink = By.xpath("//a[contains(text(),'Add customer group')]");
    protected final By customerGroupName = By.xpath("//input[@name='name']");
    protected final By customerKey = By.xpath("//input[@name='key']");
    protected final By saveButton = By.xpath("//div[contains(text(),'Save')]");
    protected final By groupList = By.xpath("//div[contains(text(),'Group name')]");
    protected final By groupListIcon = By.xpath("//a[contains(text(),'Customer group list')]");
    protected final By groupName =By.xpath("//*[@id='app']//tbody/tr[1]/td[1]");
    String beforeXpath = "//*[@id='app']//tbody/tr[";
    String afterXpath = "]/td[1]";

    /**
     * click on Customer Link in commercetools
     */
    public void clickCustomersLink( )
    {
        WebElement customerLnk = wait.waitForElementEnabled(customersLink);
        click.moveToElementAndClick(customerLnk);
    }
    /**
     * click on Add Customer Link in commercetools
     */
    public void clickCustomerGroupListIcon( )
    {
        WebElement addCustomerField = wait.waitForElementEnabled(groupListIcon);
        click.moveToElementAndClick(addCustomerField);
    }
    /**
     * click on Customers Link in commercetools
     * @return value
     * @param value
     */
    public String getCustomerGroupList(String value)
    {
        WebElement groupListField = wait.waitForElementEnabled(groupList);
        List<WebElement> rows = driver.findElements(groupName);
        int rowCount = rows.size();

        for(int i=1; i<=rowCount;i++)
        {
            String actualPath = beforeXpath +i + afterXpath;
            WebElement element = driver.findElement(By.xpath(actualPath));

            if(element.getText().equals(value)) {
                Random random = new Random();
                String randomValue = String.valueOf(random.nextInt(100));
                value = value.concat(randomValue);
            }
        }
        return value;
    }

    /**
     * click on Add Customer Group Link in commercetools
     */
    public void clickAddCustomerGroupLink( )
    {
        WebElement addCustomerGroup = wait.waitForElementEnabled(addCustomerGroupLink);
        click.moveToElementAndClick(addCustomerGroup);
    }

    /**
     * enter customer Group Name in commercetools.
     *
     * @param customerGroup
     */
    public void enterCustomerGroupName( String customerGroup )
    {
        WebElement customerGroupField = wait.waitForElementDisplayed(customerGroupName);
        text.enterText(customerGroupField, customerGroup);
    }

    /**
     * enter customer key Name in commercetools
     *
     * @param customerKeyName
     */
    public void enterCustomerKeyName( final String customerKeyName )
    {
        WebElement customerKeyField = wait.waitForElementDisplayed(customerKey);
        text.enterText(customerKeyField, customerKeyName);
    }

    /**
     * click to save Button after adding customer Group details in commercetools
     */
    public void clickSaveButton( )
    {
        WebElement saveButtonField = wait.waitForElementDisplayed(saveButton);
        click.moveToElementAndClick(saveButtonField);
    }
}

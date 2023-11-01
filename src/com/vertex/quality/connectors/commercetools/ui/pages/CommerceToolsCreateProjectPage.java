package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

/**
 * generic representation of Commercetools Add Project page
 *
 * @author Mayur.Kumbhar
 */
public class CommerceToolsCreateProjectPage extends CommerceToolsBasePage {
    public CommerceToolsCreateProjectPage(final WebDriver driver) {
        super(driver);
    }

    protected final By userIcon = By.xpath("//img[@class='css-1k7yarl']");
    protected final By manageProject = By.xpath("//*[contains(text(),'Manage projects')]");
    protected final By addProjectButton = By.xpath("//button[@label='Add project']");
    protected final By organizationNameTextBox = By.xpath("//input[@id='select-field-1']");
    protected final By key = By.xpath("//div[contains(text(),'Key')]");
    protected final By projectNameTextBox = By.xpath("//*[@name='name']");
    protected final By projectKeyTextBox = By.xpath("//*[@name='key']");
    protected final By nextPage = By.xpath("//button[@label='Next page']");
    protected final By cancelButton = By.xpath("//*[@label='Cancel']");
    protected final By createProjectButton = By.xpath("//*[@label='Create']");
    protected final By backToProjectLink = By.xpath("//*[contains(text(),'Back to project')]");
    protected final By accessProjectLink = By.xpath("//*[contains(text(),'Access your project')]");
    protected final By rowNumber = By.xpath("//*[@id=\"app\"]//tbody/tr");
    protected final By projectKeyField = By.xpath("//input[@name='key']");
    protected final By deleteProjectButton = By.xpath("//button[@label='Delete project']");
    String beforeXpath = "//*[@id=\"app\"]//tbody/tr[";
    String afterXpath = "]/td[2]";
    String beforeXpathProjectName = "//*[@id=\"app\"]//tbody/tr[";
    String afterXpathProjectName = "]/td[1]";
    String beforeDeleteXpath = "//button[@data-testid='project-remove-button-";


    /**
     * click on user icon on merchant center
     */
    public void clickOnUserIcon() {
        WebElement userIconField = wait.waitForElementDisplayed(userIcon);
        click.moveToElementAndClick(userIconField);
    }


    /**
     * clean all project lists in commercetools one by one.
     *
     * @param projectKey
     */
    public void cleanProjectsList(List<String> projectKey) {
        wait.waitForElementEnabled(key);
        List<WebElement> rows = driver.findElements(rowNumber);
        int rowCount = rows.size();
        int count = 1;
        while (driver.findElement(By.xpath("//button[@label='Next page']")).isEnabled() || count == 1) {
            for (int i = 1; i <= rowCount; i++) {
                try {
                    count--;
                    String actualPath = beforeXpath + i + afterXpath;
                    wait.waitForElementEnabled(key);
                    WebElement keyName = driver.findElement(By.xpath(actualPath));
                    String keyValue = keyName.getText();

                    if (projectKey.contains(keyValue)) {
                        i--;
                        String actualDeletePath = beforeDeleteXpath + keyName.getText() + "']";
                        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("close-bold_react_svg__shape")));
                        WebElement deleteButton = wait.waitForElementDisplayed(driver.findElement(By.xpath(actualDeletePath)));
                        click.moveToElementAndClick(deleteButton);
                        WebElement projectNameField = wait.waitForElementDisplayed(projectKeyField);

                        text.enterText(projectNameField, keyValue);
                        WebElement deleteProject = driver.findElement(deleteProjectButton);
                        click.moveToElementAndClick(deleteProject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            click.moveToElementAndClick(nextPage);
            try {
                new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(nextPage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.quit();
    }


    /**
     * get all project lists in commercetools
     *
     * @param value
     * @return value
     */
    public String getProjectList(String value) {
        WebElement keyDisplay = wait.waitForElementEnabled(key);
        List<WebElement> rows = driver.findElements(rowNumber);
        int rowCount = rows.size();


        for (int i = 1; i <= rowCount; i++) {
            String actualPath = beforeXpath + i + afterXpath;
            WebElement element = driver.findElement(By.xpath(actualPath));

            if (element.getText().equals(value)) {
                Random random = new Random();
                String concatenateValue = String.valueOf(random.nextInt(9999));
                value = value.concat(concatenateValue);
            }
        }
        return value;
    }

    /**
     * click on manage project
     */
    public void clickOnManageProject() {
        WebElement manageProjectField = wait.waitForElementDisplayed(manageProject);
        click.moveToElementAndClick(manageProjectField);
    }

    /**
     * click on add project button
     */
    public void clickOnAddProjectButton() {
        WebElement addProjectButtonField = wait.waitForElementDisplayed(addProjectButton);
        click.moveToElementAndClick(addProjectButtonField);
    }

    /**
     * enter organization name
     *
     * @param organizationName
     */
    public void enterOrganizationName(String organizationName) {
        WebElement organizationNameField = wait.waitForElementDisplayed(organizationNameTextBox);
        click.moveToElementAndClick(organizationNameField);
        text.enterText(organizationNameField, organizationName);
    }

    /**
     * enter project name
     *
     * @param projectName
     */
    public void enterProjectName(String projectName) {
        WebElement projectNameField = wait.waitForElementDisplayed(projectNameTextBox);
        text.enterText(projectNameField, projectName);
    }

    /**
     * enter project key
     *
     * @param projectKey
     */
    public void enterProjectKey(String projectKey) {
        WebElement projectKeyField = wait.waitForElementDisplayed(projectKeyTextBox);
        text.enterText(projectKeyField, projectKey);
    }

    /**
     * click on cancel button
     */
    public void clickCancelButton() {
        WebElement cancelButtonField = wait.waitForElementDisplayed(cancelButton);
        click.moveToElementAndClick(cancelButtonField);
    }

    /**
     * click on create project button
     */
    public void clickCreateProjectButton() {
        WebElement createProjectField = wait.waitForElementDisplayed(createProjectButton);
        click.moveToElementAndClick(createProjectField);
    }

    /**
     * click on back to project link.
     */
    public void clickBackToProject() {
        WebElement backToProjectField = wait.waitForElementDisplayed(backToProjectLink);
        click.moveToElementAndClick(backToProjectField);

    }

    /**
     * click on access project link
     */
    public void clickOnAccessProject() {
        WebElement accessProjectField = wait.waitForElementDisplayed(accessProjectLink);
        click.moveToElementAndClick(accessProjectField);

    }

}

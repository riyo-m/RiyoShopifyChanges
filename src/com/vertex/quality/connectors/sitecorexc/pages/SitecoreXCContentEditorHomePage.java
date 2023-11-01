package com.vertex.quality.connectors.sitecorexc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Commerce - Content Editor's Home page
 *
 * @author Shivam.Soni
 */
public class SitecoreXCContentEditorHomePage extends SitecoreXCPage {

    protected String headerNavigationOption = ".//div[@class='scRibbonNavigatorButtonsGroupButtons']//a[normalize-space(.)='<<text_replace>>']";
    protected By sidePanelAllNodes = By.xpath(".//div[@class='scFlexContentWithoutFlexie']//a");
    protected String sidePanelExpandedNode = "(.//div[@class='scFlexContentWithoutFlexie']//a[normalize-space(.)='<<text_replace>>']//preceding-sibling::img[contains(@src,'expanded')])";
    protected String sidePanelCollapsedNode = "(.//div[@class='scFlexContentWithoutFlexie']//a[normalize-space(.)='<<text_replace>>']//preceding-sibling::img[contains(@src,'collapsed')])";
    protected String sidePanelOption = ".//div[@class='scFlexContentWithoutFlexie']//a[normalize-space(.)='<<text_replace>>']";
    protected By addressLine1Box = By.xpath(".//div[normalize-space(.)='Address1:']/following-sibling::div//input");
    protected By cityBox = By.xpath(".//div[normalize-space(.)='City:']/following-sibling::div//input");
    protected By regionBox = By.xpath(".//div[normalize-space(.)='Region:']/following-sibling::div//input");
    protected By postalCodeBox = By.xpath(".//div[normalize-space(.)='PostalCode:']/following-sibling::div//input");
    protected By countryBox = By.xpath(".//div[normalize-space(.)='Country:']/following-sibling::div//input");
    protected By isoCountryCodeBox = By.xpath(".//div[normalize-space(.)='ISOCountryCode:']/following-sibling::div//input");
    protected By saveButton = By.xpath(".//a[@title='Save any changes. (Ctrl+S)']");
    protected By rightClickPopupDialog = By.xpath(".//div[@class='scPopup']");
    protected String rightClickPopupOption = ".//td[normalize-space(.)='<<text_replace>>']";
    protected By publishItemPopup = By.xpath(".//div[@class='scStretch']");
    protected By smartPublishRadioButton = By.xpath(".//input[@id='SmartPublish']");
    protected By publishRelatedItemsCheckbox = By.xpath(".//input[@id='PublishRelatedItems']");
    protected By englishLanguageCheckbox = By.xpath(".//label[normalize-space(.)='English']/preceding-sibling::input[1]");
    protected By publishButton = By.xpath(".//button[@id='NextButton']");
    protected By publishItemConfirmationDialog = By.xpath(".//div[@class='scFlexColumnContainer scStretch']");
    protected By okButton = By.xpath(".//button[@id='OK']");
    protected By closePublishItemsPopupButton = By.xpath(".//button[contains(@class,'close')]");
    protected By jQueryIframe = By.id("jqueryModalDialogsFrame");
    protected By publishPopupIframe = By.id("scContentIframeId0");
    protected By publishConfirmIframe = By.id("scContentIframeId1");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCContentEditorHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to Header navigation option
     *
     * @param navigationOption navigation option's name
     */
    public void selectHeaderNavigationOption(String navigationOption) {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(headerNavigationOption.replace("<<text_replace>>", navigationOption))));
        waitForPageLoad();
    }

    /**
     * Expands the node from the side-left panel
     *
     * @param nodeName Node name which is to be expanded
     */
    public void expandNodeFromLeftPanel(String nodeName) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(sidePanelAllNodes);
        String modifiedXpathOfNode;
        if (!element.isElementDisplayed(By.xpath(sidePanelExpandedNode.replace("<<text_replace>>", nodeName)))) {
            click.moveToElementAndClick(By.xpath(sidePanelCollapsedNode.replace("<<text_replace>>", nodeName)));
        }
        waitForPageLoad();
    }

    /**
     * Expands the node from the side-left panel
     *
     * @param isFirstNode There are few same name 2 nodes so pass true to expand 1st node & false to expand 2nd node
     * @param nodeName    Node name which is to be expanded
     */
    public void expandNodeFromLeftPanel(boolean isFirstNode, String nodeName) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(sidePanelAllNodes);
        String modifiedExpandedNodeXpath;
        String modifiedCollapsedNodeXpath;
        if (isFirstNode) {
            modifiedExpandedNodeXpath = sidePanelExpandedNode.replace("<<text_replace>>", nodeName) + "[1]";
            modifiedCollapsedNodeXpath = sidePanelCollapsedNode.replace("<<text_replace>>", nodeName) + "[1]";
        } else {
            modifiedExpandedNodeXpath = sidePanelExpandedNode.replace("<<text_replace>>", nodeName) + "[2]";
            modifiedCollapsedNodeXpath = sidePanelCollapsedNode.replace("<<text_replace>>", nodeName) + "[2]";
        }
        if (!element.isElementDisplayed(By.xpath(modifiedExpandedNodeXpath))) {
            click.moveToElementAndClick(By.xpath(modifiedCollapsedNodeXpath));
        }
        waitForPageLoad();
    }

    /**
     * Selects option from the side-left panel
     *
     * @param option name of side-left option
     */
    public void selectLeftPanelOption(String option) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(sidePanelAllNodes);
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(sidePanelOption.replace("<<text_replace>>", option))));
        waitForPageLoad();
    }

    /**
     * Sets Warehouse address
     *
     * @param address address detail of Warehouse
     */
    public void setWarehouseAddress(String... address) {
        if (address.length != 6) {
            Assert.fail("Parameters mismatched kindly check JavaDoc.");
        }
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(addressLine1Box), address[0]);
        text.enterText(wait.waitForElementPresent(cityBox), address[1]);
        text.enterText(wait.waitForElementPresent(regionBox), address[2]);
        text.enterText(wait.waitForElementPresent(postalCodeBox), address[3]);
        text.enterText(wait.waitForElementPresent(countryBox), address[4]);
        text.enterText(wait.waitForElementPresent(isoCountryCodeBox), address[5]);
    }

    /**
     * Saves the applied changes
     */
    public void clickSave() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(saveButton));
        waitForPageLoad();
    }

    /**
     * Selects the option of the pop-up of mouse right click
     *
     * @param rightClickOption option name on which right click should be performed
     * @param popUpOption      option name of right click pop-up
     */
    public void selectRightClickPopupOption(String rightClickOption, String popUpOption) {
        waitForPageLoad();
        wait.waitForElementPresent(sidePanelAllNodes);
        click.performRightClick(wait.waitForElementPresent(By.xpath(sidePanelOption.replace("<<text_replace>>", rightClickOption))));
        wait.waitForElementPresent(rightClickPopupDialog);
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(rightClickPopupOption.replace("<<text_replace>>", popUpOption))));
    }

    /**
     * Ensures the default publish item's options are selected & if not selected then will select them
     */
    public void makeSureDefaultPublishOptionsSet() {
        waitForPageLoad();
        wait.waitForElementPresent(publishItemPopup);
        click.moveToElementAndClick(wait.waitForElementPresent(smartPublishRadioButton));
        checkbox.setCheckbox(wait.waitForElementPresent(publishRelatedItemsCheckbox), true);
        checkbox.setCheckbox(wait.waitForElementPresent(englishLanguageCheckbox), true);
    }

    /**
     * Closes Publish Items pop-up
     */
    public void closePublishItemDialog() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(closePublishItemsPopupButton));
        waitForPageLoad();
    }

    /**
     * Publishes the edited & saved settings to DB with default options
     *
     * @param rightClickOption option name on which right click should be performed
     * @param popUpOption      option name of right click pop-up
     */
    public void publishTheItemWithDefaultOptions(String rightClickOption, String popUpOption) {
        waitForPageLoad();
        selectRightClickPopupOption(rightClickOption, popUpOption);
        driver.switchTo().frame(wait.waitForElementPresent(jQueryIframe));
        driver.switchTo().frame(wait.waitForElementPresent(publishPopupIframe));
        makeSureDefaultPublishOptionsSet();
        click.moveToElementAndClick(wait.waitForElementPresent(publishButton));
        window.switchToDefaultContent();
        driver.switchTo().frame(wait.waitForElementPresent(jQueryIframe));
        WebElement cnfIframe = wait.waitForElementPresent(publishConfirmIframe);
        driver.switchTo().frame(cnfIframe);
        wait.waitForElementPresent(publishItemConfirmationDialog);
        click.moveToElementAndClick(wait.waitForElementPresent(okButton));
        waitForSpinnerToBeDisappeared();
        window.switchToDefaultContent();
        driver.switchTo().frame(wait.waitForElementPresent(jQueryIframe));
        closePublishItemDialog();
    }
}

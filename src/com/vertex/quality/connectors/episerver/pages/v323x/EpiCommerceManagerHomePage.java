package com.vertex.quality.connectors.episerver.pages.v323x;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Locators & helper methods to perform operations on Episerver's commerce manager home webpage
 *
 * @author Shivam.Soni
 */
public class EpiCommerceManagerHomePage extends VertexPage {
    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiCommerceManagerHomePage(WebDriver driver) {
        super(driver);
    }

    protected By LOGGED_IN_USER_MENU = By.xpath(".//button[contains(text(),'Welcome, ')]");
    protected By SIGN_OUT = By.xpath(".//a[text()='Sign Out']");
    protected String LEFT_PANEL_OPTION = ".//td[text()='<<text_replace>>']";
    protected String SELECTED_LEFT_PANEL_OPTION = ".//span[@class='x-panel-header-text'][text()='<<text_replace>>']";
    protected String LEFT_PANEL_SUB_OPTION = ".//span[text()='<<text_replace>>']";
    protected String COLLAPSED_NODE = ".//span[text()='<<text_replace>>']/parent::a/preceding-sibling::img[@class='x-tree-ec-icon x-tree-elbow-plus'] | .//span[text()='<<text_replace>>']/parent::a/preceding-sibling::img[@class='x-tree-ec-icon x-tree-elbow-end-plus']";
    protected String EXPANDED_NODE = ".//span[text()='<<text_replace>>']/parent::a/preceding-sibling::img[@class='x-tree-ec-icon x-tree-elbow-minus'] | .//span[text()='<<text_replace>>']/parent::a/preceding-sibling::img[@class='x-tree-ec-icon x-tree-elbow-end-minus']";
    protected By RIGHT_IFRAME = By.xpath(".//iframe[@id='right']");
    protected By PICK_LIST_POPUP_IFRAME = By.xpath(".//div[@class='modalPopupHeaderMove']/following-sibling::iframe");

    /**
     * Used to sign out of Episerver's Commerce Manager
     */
    public void signOutFromCommerceManager() {
        waitForPageLoad();
        String url1 = driver.getCurrentUrl();
        window.switchToDefaultContent();
        click.moveToElementAndClick(wait.waitForElementPresent(LOGGED_IN_USER_MENU));
        click.moveToElementAndClick(wait.waitForElementPresent(SIGN_OUT));
        waitForPageLoad();
        String url2 = driver.getCurrentUrl();
        if (url1.equalsIgnoreCase(url2)) {
            VertexLogger.log("User is not Signed Out of Commerce Manager");
        } else {
            VertexLogger.log("User is Signed out of Commerce Manager");
        }
    }

    /**
     * Navigates to left panel's option and expand if it's found collapsed
     *
     * @param option option name
     */
    public void navigateToLeftPanelOption(String option) {
        waitForPageLoad();
        WebElement panelOption = wait.waitForElementPresent(By.xpath(LEFT_PANEL_OPTION.replace("<<text_replace>>", option)));
        click.moveToElementAndClick(panelOption);
        waitForPageLoad();
        wait.waitForElementPresent(By.xpath(SELECTED_LEFT_PANEL_OPTION.replace("<<text_replace>>", option)));
        if (element.isElementPresent(By.xpath(COLLAPSED_NODE.replace("<<text_replace>>", option)))) {
            click.moveToElementAndClick(By.xpath(COLLAPSED_NODE.replace("<<text_replace>>", option)));
        }
        wait.waitForElementPresent(By.xpath(EXPANDED_NODE.replace("<<text_replace>>", option)));
        VertexLogger.log("Navigated to: " + option);
    }

    /**
     * Selects un-expandable submenu option
     *
     * @param option submenu option
     */
    public void selectSubmenuOption(String option) {
        waitForPageLoad();
        WebElement finalOption = wait.waitForElementPresent(By.xpath(LEFT_PANEL_SUB_OPTION.replace("<<text_replace>>", option)));
        click.moveToElementAndClick(finalOption);
        waitForPageLoad();
        VertexLogger.log("Selected " + option + " option from submenu");
    }

    /**
     * Expands submenu if it's collapsed & selects submenu option from left panel.
     *
     * @param submenu name of expandable submenu
     * @param option  submenu option
     */
    public void selectSubmenuOption(String submenu, String option) {
        waitForPageLoad();
        wait.waitForElementPresent(By.xpath(LEFT_PANEL_SUB_OPTION.replace("<<text_replace>>", submenu)));
        expandNode(submenu);
        selectSubmenuOption(option);
    }

    /**
     * Expands submenu if it's collapsed & selects submenu option from left panel.
     *
     * @param submenu         name of expandable submenu
     * @param expandableChild name of expandable child node
     * @param option          submenu option
     */
    public void selectSubmenuOption(String submenu, String expandableChild, String option) {
        waitForPageLoad();
        wait.waitForElementPresent(By.xpath(LEFT_PANEL_SUB_OPTION.replace("<<text_replace>>", submenu)));
        expandNode(submenu);
        expandNode(expandableChild);
        selectSubmenuOption(option);
    }

    /**
     * Expands the node if it's collapsed
     *
     * @param nodeName name of node which is to be expanded
     */
    public void expandNode(String nodeName) {
        wait.waitForElementPresent(By.xpath(LEFT_PANEL_SUB_OPTION.replace("<<text_replace>>", nodeName)));
        if (element.isElementPresent(By.xpath(COLLAPSED_NODE.replaceAll("<<text_replace>>", nodeName)))) {
            click.moveToElementAndClick(By.xpath(COLLAPSED_NODE.replaceAll("<<text_replace>>", nodeName)));
            wait.waitForElementPresent(By.xpath(EXPANDED_NODE.replaceAll("<<text_replace>>", nodeName)));
            VertexLogger.log("Expanded submenu options of " + nodeName);
        }
    }

    /**
     * Switches to Right iFrame to interact with elements.
     */
    public void switchToRightIframe() {
        waitForPageLoad();
        window.switchToFrame(RIGHT_IFRAME);
    }

    /**
     * Switched to Popup modal's iFrame to interact with elements.
     */
    public void switchToPopupIframe() {
        waitForPageLoad();
        WebElement popupFrame = wait.waitForElementPresent(PICK_LIST_POPUP_IFRAME);
        // Intentionally using selenium's method because this frame is inner & any of method in framework first switches to default content
        // & hence unable to switch to inner so used this selenium's default method
        driver.switchTo().frame(popupFrame);
    }
}

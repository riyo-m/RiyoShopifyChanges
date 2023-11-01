package com.vertex.quality.connectors.hybris.pages.backoffice;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Sets Vertex's customization
 *
 * @author Shivam.Soni
 */
public class HybrisBOVertexCustomizationPage extends HybrisBasePage {

    /**
     * Parameterized construction to access parent level values in the hierarchy.
     *
     * @param driver WebDriver
     */
    public HybrisBOVertexCustomizationPage(WebDriver driver) {
        super(driver);
    }

    protected By FLEX_FIELD_SECTION = By.xpath(".//div[text()='Flex Fields Section']");
    protected By COLLAPSED_FLEX_FIELDS_SECTION = By.className("yw-editorarea-tabbox-tabpanels-tabpanel-groupbox z-groupbox z-groupbox-collapsed");
    protected By FLEX_CODE_FIELD_LABEL = By.xpath(".//*[@title='flexCodeFields']");
    protected By FLEX_DATE_FIELD_LABEL = By.xpath(".//*[@title='flexDateFields']");
    protected By FLEX_NUMERIC_FIELD_LABEL = By.xpath(".//*[@title='flexNumericFields']");
    protected By FLEX_CODE_FIELDS = By.xpath(".//*[@title='flexCodeFields']//following-sibling::div//input");
    protected By CREATE_FLEX_CODE_FIELD = By.xpath(".//span[text()='Create new Flex code field']");
    protected By FLEX_DATE_FIELDS = By.xpath(".//*[@title='flexDateFields']//following-sibling::div//input");
    protected By CREATE_FLEX_DATE_FIELD = By.xpath(".//span[text()='Create new Flex date field']");
    protected By FLEX_NUMERIC_FIELDS = By.xpath(".//*[@title='flexNumericFields']//following-sibling::div//input");
    protected By CREATE_FLEX_NUMERIC_FIELD = By.xpath(".//span[text()='Create new Flex numeric field']");
    protected By CREATE_FLEX_MODAL = By.cssSelector(".yw-modal-configurableFlow");
    protected By FLEX_FIELD_ID = By.xpath(".//span[contains(text(),'ID (#1 - ')]//following-sibling::div//input");
    protected By FLEX_FIELD_ENCLOSING_MODEL = By.xpath(".//span[text()='Choose Enclosing Model:']//following-sibling::div//input");
    protected By FLEX_FIELD_MAPPING_NUMERIC_TYPE = By.xpath(".//span[text()='Choose Mapping Numeric Type:']//following-sibling::div//input");
    protected By FLEX_FIELD_ENCLOSING_TYPE = By.xpath(".//span[text()='Enclosing Type:']//following-sibling::div//input");
    protected By REFERENCE_SEARCH_MODAL = By.cssSelector(".yw-modal-referenceadvancedsearchgroup");
    protected By ENCLOSING_TYPE_MORE_ACTION = By.xpath(".//span[text()='Enclosing Type:']//following-sibling::div//i");
    protected By SELECT_BUTTON = By.xpath(".//button[starts-with(text(),'Select (')]");
    protected By DONE_BUTTON = By.xpath(".//button[text()='Done']");
    protected By MAPPING_NUMERIC_TYPE_DROPDOWN = By.xpath(".//span[text()='Choose Mapping Numeric Type:']//following-sibling::div//i");
    protected String SELECTED_FLEX_FIELDS = "(.//span[@title='flexNumericFields']/following-sibling::div//tr)[3]/td[@class='ye-actiondots z-listcell'] " +
            "| (.//span[@title='flexDateFields']/following-sibling::div//tr)[3]/td[@class='ye-actiondots z-listcell'] " +
            "| (.//span[@title='flexCodeFields']/following-sibling::div//tr)[3]/td[@class='ye-actiondots z-listcell']";
    protected By REMOVE_SELECTED_FLEX_FIELD = By.xpath(".//*[@class='z-menuitem-text'][text()='Remove']");
    protected String CREATE_FLEX_FIELDS_POPUP_ALL_ROW = "(.//div[@class='ye-default-reference-editor-bandpopup z-bandpopup']//tr)";
    protected String ENCLOSING_MODEL_VALUE = ".//div[@class='z-listcell-content']//span[text()='<<text_replace>>']";
    protected String ENCLOSING_TYPE = ".//span[text()='Use Enclosing Type:']//following-sibling::div//label[text()='<<text_replace>>']//preceding-sibling::input";
    protected String ENCLOSING_TYPE_VALUE = ".//span[text()='<<text_replace>>']";
    protected String MAPPING_NUMERIC_TYPE_VALUE = ".//span[@class='z-comboitem-text'][text()='<<text_replace>>']";
    protected By DELIVERY_TERM_BOX = By.xpath(".//div[normalize-space(.)='Vertex Delivery Term Code']//following-sibling::div//input");
    protected By DELIVERY_TERM_DROPDOWN = By.xpath(".//div[normalize-space(.)='Vertex Delivery Term Code']//following-sibling::div//a");
    protected By DELIVERY_TERM_CODE_POPUP = By.cssSelector(".z-combobox-popup");
    protected By ALL_DELIVERY_TERMS = By.xpath(".//ul[@class='z-combobox-content']//li");
    protected String DELIVERY_TERM_OPTION = ".//ul[@class='z-combobox-content']//li[contains(normalize-space(.),'<<text_replace>>')]";

    /**
     * Selects the delivery term defined by Vertex.
     *
     * @param term Term name (ex: SUP)
     */
    public void selectVertexDeliveryTerm(String term) {
        waitForPageLoad();
        hybrisWaitForPageLoad();
        WebElement vtxTerm = wait.waitForElementEnabled(DELIVERY_TERM_BOX);
        click.moveToElementAndClick(vtxTerm);
        text.enterTextIgnoreExceptionsAndRetry(DELIVERY_TERM_BOX, term);
        text.pressEnter(vtxTerm);
        hybrisWaitForPageLoad();
        waitForPageLoad();
    }

    /**
     * It expands the flex field section if it is collapsed
     */
    public void expandFlexFieldSection() {
        hybrisWaitForPageLoad();
        wait.waitForElementPresent(FLEX_FIELD_SECTION);
        if (element.isElementPresent(COLLAPSED_FLEX_FIELDS_SECTION)) {
            click.moveToElementAndClick(FLEX_FIELD_SECTION);
        }
    }

    /**
     * Waits for all flex-fields' presence
     */
    public void waitForAllFlexFieldsSection() {
        hybrisWaitForPageLoad();
        wait.waitForElementPresent(FLEX_CODE_FIELD_LABEL);
        wait.waitForElementPresent(FLEX_DATE_FIELD_LABEL);
        wait.waitForElementPresent(FLEX_NUMERIC_FIELD_LABEL);
    }

    /**
     * Checks whether same flex field is available or not?
     *
     * @param id                  value of ID field
     * @param enclosingModelValue value of Enclosing Modal
     * @param enclosingTypeValue  value of Enclosing Type
     * @return true or false based on condition
     */
    public boolean isFlexFieldAvailable(String id, String enclosingModelValue, String enclosingTypeValue) {
        boolean isAvailable = false;
        String textFromUI;
        expandFlexFieldSection();
        waitForAllFlexFieldsSection();
        wait.waitForAllElementsPresent(By.xpath(CREATE_FLEX_FIELDS_POPUP_ALL_ROW));
        int size = element.getWebElements(By.xpath(CREATE_FLEX_FIELDS_POPUP_ALL_ROW)).size();
        for (int i = 1; i < size; i++) {
            textFromUI = text.getElementText(By.xpath(CREATE_FLEX_FIELDS_POPUP_ALL_ROW + "[" + i + "]"));
            if (textFromUI.contains(id) && textFromUI.contains(enclosingModelValue) && textFromUI.contains(enclosingTypeValue)) {
                VertexLogger.log("The values which you are going to set are already in one of the Flex-Field");
                isAvailable = true;
                break;
            }
        }
        VertexLogger.log("Values are not found set so creating new flex-field with these values");
        return isAvailable;
    }

    /**
     * Selects already available flex-field with the data
     *
     * @param id                  value of ID field
     * @param enclosingModelValue value of Enclosing Modal
     * @param enclosingTypeValue  value of Enclosing Type
     */
    public void selectAvailableFlexField(String id, String enclosingModelValue, String enclosingTypeValue) {
        String textFromUI;
        expandFlexFieldSection();
        waitForAllFlexFieldsSection();
        wait.waitForAllElementsPresent(By.xpath(CREATE_FLEX_FIELDS_POPUP_ALL_ROW));
        int size = element.getWebElements(By.xpath(CREATE_FLEX_FIELDS_POPUP_ALL_ROW)).size();
        for (int i = 1; i < size; i++) {
            textFromUI = text.getElementText(By.xpath(CREATE_FLEX_FIELDS_POPUP_ALL_ROW + "[" + i + "]"));
            if (textFromUI.contains(id) && textFromUI.contains(enclosingModelValue) && textFromUI.contains(enclosingTypeValue)) {
                VertexLogger.log("Found available flex-field matched with the data");
                click.moveToElementAndClick(By.xpath(CREATE_FLEX_FIELDS_POPUP_ALL_ROW + "[" + i + "]"));
                break;
            }
        }
    }

    /**
     * Set Flex Code field(s)
     *
     * @param id                  value of ID field
     * @param useEnclosingValue   value of Use Enclosing Type
     * @param enclosingModelValue value of Enclosing Modal
     * @param enclosingTypeValue  value of Enclosing Type
     */
    public void setFlexCodeField(String id, String useEnclosingValue, String enclosingModelValue, String enclosingTypeValue) {
        expandFlexFieldSection();
        waitForAllFlexFieldsSection();
        WebElement codeInput = wait.waitForElementPresent(FLEX_CODE_FIELDS);
        click.moveToElementAndClick(codeInput);
        if (isFlexFieldAvailable(id, enclosingModelValue, enclosingTypeValue)) {
            selectAvailableFlexField(id, enclosingModelValue, enclosingTypeValue);
        } else {
            WebElement createCodeField = wait.waitForElementPresent(CREATE_FLEX_CODE_FIELD);
            click.moveToElementAndClick(createCodeField);
            hybrisWaitForPageLoad();
            setUseEnclosingValue(useEnclosingValue);
            enterFlexFieldsID(id);
            setEnclosingModelValue(enclosingModelValue);
            setFlexEnclosingType(enclosingTypeValue);
            clickDone();
        }
    }

    /**
     * Set Flex Date field(s)
     *
     * @param id                  value of ID field
     * @param useEnclosingValue   value of Use Enclosing Type
     * @param enclosingModelValue value of Enclosing Modal
     * @param enclosingTypeValue  value of Enclosing Type
     */
    public void setFlexDateField(String id, String useEnclosingValue, String enclosingModelValue, String enclosingTypeValue) {
        expandFlexFieldSection();
        waitForAllFlexFieldsSection();
        WebElement dateInput = wait.waitForElementPresent(FLEX_DATE_FIELDS);
        click.moveToElementAndClick(dateInput);
        if (isFlexFieldAvailable(id, enclosingModelValue, enclosingTypeValue)) {
            selectAvailableFlexField(id, enclosingModelValue, enclosingTypeValue);
        } else {
            isFlexFieldAvailable(id, enclosingModelValue, enclosingTypeValue);
            WebElement createDateField = wait.waitForElementPresent(CREATE_FLEX_DATE_FIELD);
            click.moveToElementAndClick(createDateField);
            hybrisWaitForPageLoad();
            setUseEnclosingValue(useEnclosingValue);
            enterFlexFieldsID(id);
            setEnclosingModelValue(enclosingModelValue);
            setFlexEnclosingType(enclosingTypeValue);
            clickDone();
        }
    }

    /**
     * Set Flex Numeric field(s)
     *
     * @param id                  value of ID field
     * @param useEnclosingValue   value of Use Enclosing Type
     * @param enclosingModelValue value of Enclosing Modal
     * @param mappingNumericValue value of Choose Mapping Numeric Type
     * @param enclosingTypeValue  value of Enclosing Type
     */
    public void setFlexNumericField(String id, String useEnclosingValue, String enclosingModelValue, String mappingNumericValue, String enclosingTypeValue) {
        expandFlexFieldSection();
        waitForAllFlexFieldsSection();
        WebElement numericInput = wait.waitForElementPresent(FLEX_NUMERIC_FIELDS);
        click.moveToElementAndClick(numericInput);
        if (isFlexFieldAvailable(id, enclosingModelValue, enclosingTypeValue)) {
            selectAvailableFlexField(id, enclosingModelValue, enclosingTypeValue);
        } else {
            isFlexFieldAvailable(id, enclosingModelValue, enclosingTypeValue);
            WebElement createNumericField = wait.waitForElementPresent(CREATE_FLEX_NUMERIC_FIELD);
            click.moveToElementAndClick(createNumericField);
            hybrisWaitForPageLoad();
            setUseEnclosingValue(useEnclosingValue);
            enterFlexFieldsID(id);
            setEnclosingModelValue(enclosingModelValue);
            setMappingNumericType(mappingNumericValue);
            setFlexEnclosingType(enclosingTypeValue);
            clickDone();
        }
    }

    /**
     * Enter ID for flex fields
     *
     * @param id value of ID field
     */
    public void enterFlexFieldsID(String id) {
        waitForAllFlexFieldsSection();
        wait.waitForElementPresent(CREATE_FLEX_MODAL);
        WebElement flexCodeID = wait.waitForElementPresent(FLEX_FIELD_ID);
        text.enterText(flexCodeID, id);
    }

    /**
     * Set Use Enclosing Type fields' value
     *
     * @param useEnclosingValue value of Use Enclosing Type
     */
    public void setUseEnclosingValue(String useEnclosingValue) {
        waitForAllFlexFieldsSection();
        wait.waitForElementPresent(CREATE_FLEX_MODAL);
        WebElement enclosingTrue = wait.waitForElementPresent(By.xpath(ENCLOSING_TYPE.replace("<<text_replace>>", useEnclosingValue)));
        click.moveToElementAndClick(enclosingTrue);
        hybrisWaitForPageLoad();
    }

    /**
     * Set Enclosing Modal fields' value
     *
     * @param enclosingModelValue value of Enclosing Modal
     */
    public void setEnclosingModelValue(String enclosingModelValue) {
        waitForAllFlexFieldsSection();
        wait.waitForElementPresent(CREATE_FLEX_MODAL);
        WebElement encloseModal = wait.waitForElementPresent(FLEX_FIELD_ENCLOSING_MODEL);
        click.moveToElementAndClick(encloseModal);
        WebElement encloseModelValue = wait.waitForElementPresent(By.xpath(ENCLOSING_MODEL_VALUE.replace("<<text_replace>>", enclosingModelValue)));
        click.moveToElementAndClick(encloseModelValue);
        hybrisWaitForPageLoad();
    }

    /**
     * Sets Mapping Numeric Type fields' value
     *
     * @param mappingNumericValue value of Choose Mapping Numeric Type
     */
    public void setMappingNumericType(String mappingNumericValue) {
        waitForAllFlexFieldsSection();
        wait.waitForElementPresent(CREATE_FLEX_MODAL);
        WebElement mappingNumericType = wait.waitForElementPresent(MAPPING_NUMERIC_TYPE_DROPDOWN);
        click.moveToElementAndClick(mappingNumericType);
        WebElement mappingValue = wait.waitForElementPresent(By.xpath(MAPPING_NUMERIC_TYPE_VALUE.replace("<<text_replace>>", mappingNumericValue)));
        click.moveToElementAndClick(mappingValue);
        hybrisWaitForPageLoad();
    }

    /**
     * Sets enclosing type fields' value
     *
     * @param enclosingTypeValue value of Enclosing Type
     */
    public void setFlexEnclosingType(String enclosingTypeValue) {
        waitForAllFlexFieldsSection();
        wait.waitForElementPresent(CREATE_FLEX_MODAL);
        WebElement enclosingMoreAction = wait.waitForElementPresent(ENCLOSING_TYPE_MORE_ACTION);
        click.moveToElementAndClick(enclosingMoreAction);
        hybrisWaitForPageLoad();
        wait.waitForElementPresent(REFERENCE_SEARCH_MODAL);
        WebElement enclosingValue = wait.waitForElementPresent(By.xpath(ENCLOSING_TYPE_VALUE.replace("<<text_replace>>", enclosingTypeValue)));
        click.moveToElementAndClick(enclosingValue);
        WebElement select = wait.waitForElementPresent(SELECT_BUTTON);
        click.moveToElementAndClick(select);
        hybrisWaitForPageLoad();
    }

    /**
     * Clicks on Done to set  flex-field
     */
    public void clickDone() {
        hybrisWaitForPageLoad();
        WebElement done = wait.waitForElementPresent(DONE_BUTTON);
        click.moveToElementAndClick(done);
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(CREATE_FLEX_MODAL));
        hybrisWaitForPageLoad();
    }

    /**
     * Removes all selected flex-fields from all flex-fields' section
     */
    public void removeAllSelectedFlexFields() {
        expandFlexFieldSection();
        waitForAllFlexFieldsSection();
        if (element.isElementPresent(By.xpath(SELECTED_FLEX_FIELDS))) {
            int size = element.getWebElements((By.xpath(SELECTED_FLEX_FIELDS))).size();
            for (int i = 1; i <= size; i++) {
                click.moveToElementAndClick(By.xpath(SELECTED_FLEX_FIELDS + "[" + i + "]"));
                WebElement removeFlexField = wait.waitForElementPresent(REMOVE_SELECTED_FLEX_FIELD);
                click.moveToElementAndClick(removeFlexField);
                waitForAllFlexFieldsSection();
            }
            VertexLogger.log("Removed all selected flex-fields");
        } else {
            VertexLogger.log("No any selected flex-field found!");
        }
    }
}

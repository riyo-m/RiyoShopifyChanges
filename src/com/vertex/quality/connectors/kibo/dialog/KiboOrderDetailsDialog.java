package com.vertex.quality.connectors.kibo.dialog;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.kibo.enums.KiboDiscounts;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * This class represents the pop up dialog after we click order details from the Maxine order page
 * it contains all the methods necessary to interact with the different buttons and fields to edit
 * the details of an order.
 *
 * @author osabha
 */
public class KiboOrderDetailsDialog extends VertexDialog {
    protected final By orderEditDetailsLoc = By.className("taco-orderform-editor");
    protected final By rowsContainerLoc = By.className("order-editable");
    protected final By deleteButtonLoc = By.className("taco-grid-row-action-trigger-remove");
    protected final By rowsContainerTag = By.tagName("tbody");
    protected final By rowsTag = By.tagName("tr");
    protected final By productTagLoc = By.tagName("span");
    protected final By itemDiscountContainerLoc = By.xpath(".//descendant::ul[@class='x-list-plain']//li");
    protected final By itemDiscountLoc = By.tagName("span");
    protected final By itemsSearchBarContLoc = By.className("x-form-trigger-wrap-focus");
    protected final By productSearchBox = By.xpath(".//input[@placeholder='Product Search (4 characters minimum)']");
    protected final By itemListArrowClass = By.xpath("/descendant::div[@class='x-trigger-index-0 x-form-trigger x-form-arrow-trigger x-form-trigger-first'][3]");
    protected final By productClass = By.className("product-name");
    protected final By shippingOriginLoc = By.className("fulfillment-method");
    protected final By addButtonClass = By.className("x-btn-action-medium-noicon");
    protected final By shippingMethodContainerClass = By.className("taco-orderform-editor");
    protected final By shippingMethodButtonClass = By.className("order-shipping-method");
    protected final By saveButtonClass = By.className("x-btn-action-primary-toolbar-medium");
    protected final By flatRateShippingLoc = By.className("taco-shippingmethod-price");
    protected final By discountListArrowContainerLoc = By.className("x-form-trigger-wrap");
    protected final By loadMaskLoc = By.className("taco-loadmask");
    protected final By quantityFieldLoc = By.className("x-field-default-form-focus");
    protected final By discountListArrowSubContainerLoc = By.className("x-form-field");
    protected final By discountListArrowLoc = By.className("x-form-arrow-trigger");
    protected final By yesButtonLoc = By.className("x-btn-button");
    protected final By errorMessageContainerLoc = By.className("taco-notifierbar-error");
    protected final By errorMessageElemLoc = By.className("message");
    protected final By shippingOriginListLoc = By.className("fulfillment-picker-menu");
    protected final By expandCollapseAdjustment = By.xpath(".//table[contains(@class,'editable')]//th[normalize-space(.)='Order Adjustments']//preceding-sibling::th");
    protected final By allOrderAdjustments = By.xpath(".//table[contains(@class,'editable')]//div[@class='order-action-icon discount-suppress']");
    protected final By orderDiscountLabel = By.xpath(".//table[contains(@class,'editable')]//div[contains(text(),'Order Discount:')]");
    // Below XPaths are relative, we need incremental values to verify tabular formatted things so to pass dynamic row no. we have divided in two parts.
    // And hence, it seems as like absolute Xpath, but we do concat & use it in the method name: removeAllAdjustments();
    protected String adjustmentCommonXpath = ".//table[contains(@class,'editable')]//tr[";
    protected String removeAdjustment = "]//div[@class='order-action-icon discount-suppress']";
    protected String removedAdjustment = "]//div[@class='order-action-icon discount-activate']";

    public KiboOrderDetailsDialog(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    /**
     * locates the dropdown  arrow for the ProductNames list
     *
     * @return WebElement of the item list arrow
     */
    protected WebElement findItemListArrow() {
        wait.waitForElementPresent(productSearchBox);
        return wait.waitForElementEnabled(itemListArrowClass);
    }

    /**
     * uses the getter method to locate the arrow and then clicks on it.
     * also contains a function to wait until all the load masks triggered by the click are gone
     */
    public void clickItemListArrow() {
        WebElement itemListArrow = findItemListArrow();
        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Load Mask wasn't present");
            }
        }
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", itemListArrow);
    }

    /**
     * getter method to locate the desired product from the list
     *
     * @param desiredProduct an enum class with ProductNames strings is available
     * @return WebElement of the ProductNames desired
     */
    protected WebElement getProduct(String desiredProduct) {
        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Load Mask wasn't present");
            }
        }
        WebElement product = element.selectElementByText(productClass, desiredProduct);
        return product;
    }

    /**
     * Search & select product on order details page
     *
     * @param product pass name which should be selected.
     */
    public void searchProductAndSelect(String product) {
        String productAutoComplete = ".//li//span[text()='<<text_replace>>']";
        WebElement searchBar = wait.waitForElementPresent(productSearchBox);
        click.moveToElementAndClick(searchBar);
        text.enterText(searchBar, product);
        waitForPageLoad();
        WebElement getProduct = wait.waitForElementPresent(By.xpath(productAutoComplete.replace("<<text_replace>>", product)));
        click.moveToElementAndClick(getProduct);
        text.pressEnter(searchBar);
    }

    /**
     * uses the getter method to locate the product desired and then clicks on it
     */
    public void selectProduct(KiboProductNames productName) {
        String product = productName.value;
        WebElement productSelected = getProduct(product);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", productSelected);
        waitForPageLoad();
        List<WebElement> masks = driver.findElements(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Load Mask isn't Present");
            }
        }
    }

    /**
     * getter method to locate the WebElement of the home base warehouse from the warehouse
     * fulfillment list
     * list is automatically dropped after a product is selected ( no need to click on any arrow for
     * this list)
     *
     * @return WebElement of the home base for shipping
     */
    protected WebElement getShippingBaseHomeBase() {
        WebElement homeBase = null;
        String expectedText = "Ship";
        WebElement listCont = wait.waitForElementDisplayed(shippingOriginListLoc);
        List<WebElement> shippingClasses = wait.waitForAllElementsDisplayed(shippingOriginLoc, listCont);
        //fixme scott refactored this to use selectElementByTest(), it used to wait for each element to be enabled, if this function breaks then try that first
        homeBase = element.selectElementByText(shippingClasses, expectedText);
        return homeBase;
    }

    /**
     * uses the getter method to locate the WebElement of the home base shipping warehouse and then
     * clicks on it
     */
    public void selectShippingBaseHomeBase() {
        WebElement baseSelected = getShippingBaseHomeBase();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", baseSelected);
    }

    /**
     * getter method uses to locate the WebElement for the pickup home base warehouse
     *
     * @return WebElement of the pickup home base selection
     */
    protected WebElement getPickupHomeBase() {
        WebElement homeBase;
        String expectedText = "Pickup";
        WebDriverWait shortWait = new WebDriverWait(driver, FIVE_SECOND_TIMEOUT);
        shortWait
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(shippingOriginLoc));
        List<WebElement> shippingClasses = driver.findElements(shippingOriginLoc);
        homeBase = element.selectElementByText(shippingClasses, expectedText);
        return homeBase;
    }

    /**
     * uses the getter method to locate the pick up home base WebElement and then clicks on it
     */
    public void selectPickupHomeBase() {
        WebElement baseSelected = getPickupHomeBase();

        baseSelected.click();
    }

    /**
     * locates the quantity field WebElement and then enters the desired
     * quantity
     *
     * @param number String to type in the quantity field ( number)
     */
    public void enterQuantity(String number) {
        wait.waitForElementEnabled(quantityFieldLoc);
        text.enterTextByIndividualCharacters(quantityFieldLoc, number);
    }

    /**
     * getter method to locate the WebElement of the add button
     *
     * @return add button WebElement
     */
    protected WebElement getAddButton() {
        String expectedText = "Add";
        WebElement selectedButton = element.selectElementByText(addButtonClass, expectedText);
        return selectedButton;
    }

    /**
     * uses the getter method to locate the add button WebElement and then clicks on it
     */
    public void clickAddButton() {
        WebElement addButton = getAddButton();

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", addButton);

        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Load Mask wasn't present");
            }
        }
    }

    /**
     * getter method to locate the shipping button WebElement
     *
     * @return shipping button WebElement
     */
    protected WebElement getShippingButton() {
        WebElement shippingButtonContainer = wait.waitForElementPresent(shippingMethodContainerClass);

        WebElement shippingButton = shippingButtonContainer.findElement(shippingMethodButtonClass);

        return shippingButton;
    }

    /**
     * uses the getter method to locate the shipping button WebElement and then clicks on it
     * which shows a drop list of different shipping methods
     */
    public void clickShippingMethodButton() {
        WebElement shippingButton = getShippingButton();
        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Load Mask wasn't present");
            }
        }
        shippingButton.click();

        waitForPageLoad();
    }

    /**
     * this method is to locate the WebElement of the flat rate shippin from the shipping list
     *
     * @return WebElement of the flat rate option
     */
    protected WebElement getFlatRateMethod() {
        String expectedText = "$15.00";
        WebElement flatRate = element.selectElementByText(flatRateShippingLoc, expectedText);
        return flatRate;
    }

    /**
     * uses the getter method to locate the flat rate shipping option and then clicks on it
     */
    public void clickFlatRate() {
        WebElement flatRateButton = getFlatRateMethod();
        flatRateButton.click();
        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Load Mask wasn't present");
            }
        }
    }

    /**
     * locates the WebElement of the discount list arrow
     * uses nested search with for loops to make the code more stable
     *
     * @return discount list arrow WebElement
     */
    protected WebElement getDiscountListArrow() {
        waitForPageLoad();
        WebElement discountListArrow = null;
        String expectedAttributeText = "Add Coupon";
        String attributeName = "placeholder";
        List<WebElement> discountListArrowContainers = wait.waitForAllElementsPresent(discountListArrowContainerLoc);
        for (WebElement thisDiscountListArrowContainer : discountListArrowContainers) {
            List<WebElement> discountArrowContainerClass = wait.waitForAllElementsPresent(
                    discountListArrowSubContainerLoc, thisDiscountListArrowContainer);

            for (WebElement thisDiscountArrowContainerClass : discountArrowContainerClass) {
                String thisContainerAssertingText = thisDiscountArrowContainerClass.getAttribute(attributeName);

                if (thisContainerAssertingText.equals(expectedAttributeText)) {
                    discountListArrow = wait.waitForElementPresent(discountListArrowLoc,
                            thisDiscountListArrowContainer);
                    break;
                }
            }
        }
        return discountListArrow;
    }

    /**
     * uses the getter method to locate the discount list arrow WenElement and then clicks on it
     */
    public void clickDiscountListArrow() {
        waitForPageLoad();
        WebElement discountListArrow = getDiscountListArrow();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", discountListArrow);
        waitForPageLoad();

        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Load mask isn't present");
            }
        }
    }

    /**
     * getter method to locate the desired discount from the Discounts list
     *
     * @param discount enum String which we have a class for
     * @return WebElement of the desired discount
     */
    protected WebElement getDiscount(String discount) {
        WebElement itemDiscount = null;
        List<WebElement> itemDiscountContainers = wait.waitForAllElementsPresent(itemDiscountContainerLoc);

        for (WebElement thisItemDiscountContainerClass : itemDiscountContainers) {
            List<WebElement> thisItemDiscountContainer = wait.waitForAllElementsPresent(itemDiscountLoc,
                    thisItemDiscountContainerClass);
            itemDiscount = element.selectElementByText(thisItemDiscountContainer, discount);
            if (itemDiscount != null) {
                break;
            }
        }
        return itemDiscount;
    }

    /**
     * uses the getter method to locate the  WebElement of the desired discount
     * and then clicks on it
     *
     * @param discountName enum string gets passes to the getter method
     */
    public void selectDiscount(KiboDiscounts discountName) {
        waitForPageLoad();
        String discount = discountName.value;
        WebElement itemDiscount = getDiscount(discount);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", itemDiscount);
        waitForPageLoad();
    }

    /**
     * Removes all pre applied order adjustments
     */
    public void removeAllOrderAdjustments() {
        waitForPageLoad();
        int size = element.getWebElements(allOrderAdjustments).size();
        wait.waitForElementPresent(expandCollapseAdjustment);
        if (!element.isElementDisplayed(orderDiscountLabel)) {
            wait.waitForElementPresent(expandCollapseAdjustment);
            click.moveToElementAndClick(expandCollapseAdjustment);
        }
        for (int i = 1; i <= size; i++) {
            wait.waitForElementPresent(By.xpath(adjustmentCommonXpath + i + removeAdjustment));
            click.moveToElementAndClick(By.xpath(adjustmentCommonXpath + i + removeAdjustment));
            wait.waitForElementPresent(By.xpath(adjustmentCommonXpath + i + removedAdjustment));
        }
        waitForPageLoad();
    }

    /**
     * apply coupon code while checking out to cart
     *
     * @param discountName pass enum value which contains coupon codes
     */
    public void applyCouponCode(KiboDiscounts discountName) {
        clickDiscountListArrow();
        selectDiscount(discountName);
    }

    /**
     * getter method to locate the save button WebElement
     *
     * @return order save Button WebElement
     */
    protected WebElement getOrderSaveButton() {
        String expectedText = "Save";
        WebElement saveButton = element.selectElementByText(saveButtonClass, expectedText);
        return saveButton;
    }

    /**
     * uses the getter method to locate the save button WebElement and then clicks on it
     */
    public void clickOrderSaveButton() {
        WebElement saveButton = getOrderSaveButton();
        click.clickElementCarefully(saveButton);
        wait.waitForElementNotDisplayed(orderEditDetailsLoc);
        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Timed out waiting for the load mask to disappear");
            }
        }
    }

    /**
     * uses the getter method to locate the save button WebElement and then clicks on it
     */
    public void clickOrdersSaveButton() {
        WebElement saveButton = getOrderSaveButton();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", saveButton);
        List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
        for (WebElement mask : masks) {
            try {
                wait.waitForElementNotDisplayed(mask);
            } catch (Exception e) {
                VertexLogger.log("Timed out waiting for the load mask to disappear");
            }
        }
    }

    /**
     * locates the product from the selected ProductNames list to delete
     *
     * @param productToDelete name of the product to delete from the list of added products.
     * @return WebElement for the delete button from the desired to delete product row
     */
    protected WebElement getDeleteButton(final String productToDelete) {
        WebElement deleteButton = null;
        WebElement rowsSupContainer = wait.waitForElementPresent(rowsContainerLoc);
        WebElement rowsContainer = wait.waitForElementPresent(rowsContainerTag, rowsSupContainer);
        List<WebElement> rows = wait.waitForAllElementsPresent(rowsTag, rowsContainer);
        WebElement currRow = element.selectElementByNestedLabel(rows, productTagLoc, productToDelete);
        if (currRow != null) {
            deleteButton = wait.waitForElementPresent(deleteButtonLoc, currRow);
        }
        return deleteButton;
    }

    /**
     * uses the getter method to locate the delete button for the ProductNames desired to delete and
     * then clicks it
     *
     * @param productToDelete String to pass to the locator method
     */
    public void deleteProduct(final KiboProductNames productToDelete) {
        String product = productToDelete.value;
        WebElement deleteButton = getDeleteButton(product);
        click.clickElement(deleteButton);
    }

    /**
     * clicks yes on the pop up message to confirm deletion of a product from the list.
     */
    public void clickYesToDeleteProduct() {
        final String expectedText = "Yes";
        WebElement yesButton = element.selectElementByText(yesButtonLoc, expectedText);
        click.clickElementCarefully(yesButton);
    }

    /**
     * to locate and verify against the alert error message
     * generated after wrong Credentials enters for the connector application
     *
     * @return true is the error message is present with an exact text.
     */
    public String getErrorMessage() {
        WebElement errorMessageContainer = wait.waitForElementPresent(errorMessageContainerLoc);
        WebElement errorMessageElem = wait.waitForElementPresent(errorMessageElemLoc, errorMessageContainer);
        String errorMessageText = errorMessageElem.getText();

        return errorMessageText;
    }

    /**
     * select product and quantity for order.
     *
     * @param product  name of product for the order.
     * @param quantity quantity of products for the order.
     */
    public void selectProductAndQuantity(KiboProductNames product, String quantity) {
        clickItemListArrow();
        searchProductAndSelect(product.value);
        selectShippingBaseHomeBase();
        enterQuantity(quantity);
        clickAddButton();
        clickShippingMethodButton();
        clickFlatRate();
        clickOrdersSaveButton();
        waitForPageLoad();
    }
}

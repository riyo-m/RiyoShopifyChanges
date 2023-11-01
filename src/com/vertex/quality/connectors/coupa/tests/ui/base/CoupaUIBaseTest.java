package com.vertex.quality.connectors.coupa.tests.ui.base;

import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.connectors.coupa.pages.*;
import com.vertex.quality.connectors.coupa.pages.CoupaSignInPage;
import com.vertex.quality.connectors.coupa.pages.VertexCoupaSignInPage;
import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import org.openqa.selenium.Dimension;

/**
 * Stores constants & utility functions that are used by many or
 * all ui tests
 *
 * @author alewis
 */
public class CoupaUIBaseTest extends VertexUIBaseTest<CoupaSignInPage> {

    protected String signInUsername;
    protected String signInPassword;
    protected String coupaUrl;
    protected String environmentURL;

    /**
     * gets sign on information such as username, password, and url from SQL server
     */
//    @Override
//    public CoupaSignInPage loadInitialTestPage() {
//        try {
//            EnvironmentInformation environmentInformation = SQLConnection.getEnvironmentInformation(
//                    DBConnectorNames.CONCUR, DBEnvironmentNames.QA, getEnvironmentDescriptor());
//            EnvironmentCredentials environmentCredentials = SQLConnection.getEnvironmentCredentials(
//                    environmentInformation);
//            coupaUrl = environmentInformation.getEnvironmentUrl();
//            signInUsername = environmentCredentials.getUsername();
//            signInPassword = environmentCredentials.getPassword();
//            environmentURL = environmentInformation.getEnvironmentUrl();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        CoupaSignInPage signOnPage = signInToCoupa();
//        return signOnPage;
//    }
//
    public void turnLoggingOff() {
        VertexCoupaSignInPage signInPage = new VertexCoupaSignInPage(driver);
        //login to vertex coupa
        signInPage.vertexCoupaLogin();
        //click on configuration
        signInPage.clickConfiguration();
        //click on the tenants
        signInPage.clickTenants();
        //Click on Add button
        signInPage.clickEditButton();
    }

    /**
     * submits and invoice with a single line item a laptop
     */
    public void submitInvoice(String taxAmount) {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        invoicePage.fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        invoicePage.addSupplierTaxRate(taxAmount);

        invoicePage.clickCalculateTax();
    }

    public String getVertexTax() {
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String tax = invoicePage.getVertexTaxValue();

        return tax;
    }

    public String getVertexTaxTwo() {
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String tax = invoicePage.getVertexTaxValueTwo();

        return tax;
    }


    /**
     * Gets first vertex tax value listed
     *
     * @return tax value
     */
    public String getFirstVertexTax() {
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String tax = invoicePage.getFirstVertexTaxValue();

        return tax;
    }

    public String getUserTax() {
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String tax = invoicePage.getUserTaxValue();

        return tax;
    }

    /**
     * Gets message when order is on hold due to tax difference out of tolerance range
     */
    public String getOnHoldMessage() {
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String message = invoicePage.getMessageOnApproval();

        return message;
    }

    /**
     * Submits a header level invoice
     * from a purchase order
     */
    public String submitHeaderLevelInvoiceFromPO() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);
        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        homePage.clickCart();
        homePage.addToCart();
        invoicePage.selectShippingAddress("gettysburg");
        invoicePage.selectBillingButton();
        homePage.approveCart();
        homePage.confirmRequistionItem();
        homePage.clickOrders();
        homePage.clickFirstPurchaseOrder();
        homePage.scrollToLines();
        homePage.clickInvoiceFromPOButton();

        invoicePage.addInvoiceNumber();
        invoicePage.clickCalculateTax();

        String vertexTax = invoicePage.getVertexTaxValue();

        return vertexTax;
    }
    /**
     * Submits a line level invoice
     * from a purchase order
     */
    public String submitLineLevelInvoiceFromPO() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);
        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        homePage.clickCart();
        homePage.addToCart();
        invoicePage.selectShippingAddress("gettysburg");
        invoicePage.selectBillingButton();
        homePage.approveCart();
        homePage.confirmRequistionItem();
        homePage.clickOrders();
        homePage.clickFirstPurchaseOrder();
        homePage.scrollToLines();
        homePage.clickInvoiceFromPOButton();

        invoicePage.addInvoiceNumber();
        invoicePage.setLineLevelTax();
        invoicePage.clickCalculateTax();

        String vertexTax = invoicePage.getVertexCalculatedInvoiceTax();

        return vertexTax;
    }

    /**
     * Submits an invoice without adding charged tax
     */
    public void submitInvoiceWithoutChargedTax() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        invoicePage.addLineItem();
        invoicePage.submitButton();
        invoicePage.bypassApproval();
        invoicePage.createNewCreditNoteFull();
        invoicePage.clickCalculateTax();

    }

    /**
     * Submits an invoice, then a credit note with header level tax
     */
    public void submitCreditNote() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        invoicePage.addSupplierTaxRate("6");
        invoicePage.addLineItem();
        invoicePage.submitButton();
        invoicePage.bypassApproval();
        invoicePage.createNewCreditNoteFull();
        invoicePage.enterCreditNoteNumber();
        invoicePage.clickCalculateTax();
    }

    /**
     * Submits an invoice, then a credit note with line level tax
     */
    public void submitCreditNoteLineLevel() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        if (driver.manage().window().getSize().width <= 1294 ) {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.enterSupplierLineLevel();
        invoicePage.addLineItem();
        invoicePage.clickCalculateTax();
        invoicePage.submitButton();
        invoicePage.bypassApproval();
        invoicePage.createNewCreditNoteFull();
        invoicePage.enterCreditNoteNumber();
    }

    /**
     * Submits an invoice, then a partial credit note with header level tax
     */
    public void submitPartialCreditNoteHeaderLevel() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        invoicePage.addSupplierTaxRate("6");
        invoicePage.addLineItem();
        invoicePage.submitButton();
        invoicePage.bypassApproval();
        invoicePage.createNewCreditNotePartial();
        invoicePage.removeLineItem();
        invoicePage.clickCalculateTax();
    }

    /**
     * Submits an invoice, then a partial credit note with line level tax
     */
    public void submitPartialCreditNoteLineLevel() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        invoicePage.addSupplierTaxRate("6");
        invoicePage.setLineLevelTax();
        invoicePage.addLineItem();
        invoicePage.submitButton();
        invoicePage.bypassApproval();
        invoicePage.createNewCreditNotePartial();
        invoicePage.removeLineItem();
        invoicePage.clickCalculateTax();
    }

    /**
     * Submits an invoice, with tax amount outside tolerance
     */
    public void submitOnHoldInvoice() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        if (driver.manage().window().getSize().width <= 1294 ) {
            driver.manage().window().setSize(new Dimension(1616, 876));
        }

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        invoicePage.addSupplierTaxRate("20");
        invoicePage.setLineLevelTax();
        invoicePage.addLineItem();
        invoicePage.submitButton();
    }

    /**
     * Submits invoice with commodity Code
     */
    public void commodityCodeInvoice() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        invoicePage.addSupplierTaxRate("6");
        invoicePage.addLineItem();
        invoicePage.addCommodity();
        invoicePage.submitButton();
        invoicePage.bypassApproval();
    }

    /**
     * Submits an invoice with no vendor tax
     *
     * @return the invoice number to be used later for tax confirmation
     */
    public String submitInvoiceNoVendorTax() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        invoicePage.fillInvoiceNoVendorTax(invoiceNumber);

        homePage.clickInvoiceName(invoiceNumber);

        return invoicePage.getVertexCalculatedInvoiceTax();

    }

    /**
     * Submits an invoice using a .csv file
     * then navigates back to the invoice that was submitted
     * in order to get the tax calculated by Vertex
     *
     * @return the tax calculated by Vertex
     */
    public String bulkInvoiceUpload() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.clickLoadFromFileButton();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.doBulkInvoiceUpload();
        homePage.clickInvoiceName("JOETEST11");

        return invoicePage.getVertexCalculatedInvoiceTax();

    }

    /**
     * Submits an invoice when summary charges are not taxable
     *
     * @return the calculated tax
     */
    public String submitInvoiceSummaryChargesNotTaxable() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.goToInvoicePresentationEdit();
        homePage.uncheckSummaryChargesBox();

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String calculatedTax = invoicePage.headerLevelWhenUserEntersTax();

        homePage.goToInvoicePresentationEdit();
        homePage.checkSummaryChargesBox();

        return calculatedTax;

    }

    /**
     * Submits an invoice when summary charges are taxable
     *
     * @return the calculated tax
     */
    public String submitInvoiceSummaryChargesTaxable() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.goToInvoicePresentationEdit();
        homePage.checkSummaryChargesBox();

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String calculatedTax = invoicePage.headerLevelWhenUserEntersTax();
        homePage.goToInvoicePresentationEdit();
        homePage.checkSummaryChargesBox();

        return calculatedTax;

    }

    /**
     * Submits a header level invoice with no vendor tax entered
     * but with a shipping, handling, and misc cost
     *
     * @return the calculated tax
     */
    public String submitInvoiceNoTaxButWithMiscCharges() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        String calculatedTax = invoicePage.headerLevelNoUserEnteredTaxWithMiscCosts();


        return calculatedTax;
    }

    /**
     * Submits a line level invoice with a header charge
     *
     * @return the calculated tax
     */
    public String submitLineLevelWithHeaderCharge() {
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        invoicePage.setLineLevelTax();
        invoicePage.enterHandlingCost("5");
        invoicePage.enterShippingCost("5");
        invoicePage.enterMiscCost("5");

        invoicePage.clickCalculateTax();

        return invoicePage.getFirstVertexTaxValue();
    }

    /**
     * Creates a line level invoice, then edits the price of the item
     * in the invoice to make sure the tax calc is correct
     *
     * @return the second calculated tax after the change in price
     * */
    public String recalcInvoicePrice(){
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);
        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);
        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        invoicePage.fillDefaultInvoice(invoiceNumber,"gettysburg");
        invoicePage.setLineLevelTaxNoUserTaxRate();
        invoicePage.clickCalculateTax();

        invoicePage.submitButton();
        invoicePage.bypassApproval();

        homePage.pressEditInvoice();
        invoicePage.clickCalculateTax();
        invoicePage.editItemPrice("2000");
        invoicePage.clickCalculateTax();
        String secondCalulatedTax = invoicePage.getFirstVertexTaxValue();

        return secondCalulatedTax;
    }

    /**
     * Creates a line level invoice, then edits the price of the item
     * in the invoice to make sure the tax calc is correct
     *
     * @return the second tax calculated after the change in quantity
     * */
    public String recalcInvoiceQuantity(){
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);
        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);
        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        invoicePage.fillDefaultInvoice(invoiceNumber,"gettysburg");
        invoicePage.setLineLevelTaxNoUserTaxRate();
        invoicePage.clickCalculateTax();

        invoicePage.submitButton();
        invoicePage.bypassApproval();

        homePage.pressEditInvoice();
        invoicePage.editQuantity("2");
        invoicePage.clickCalculateTax();
        String secondCalulatedTax = invoicePage.getFirstVertexTaxValue();

        return secondCalulatedTax;
    }

    /**
     * Creates a header level invoice, then edits the price of the item
     * in the invoice to make sure the tax calc is correct
     *
     * @return the second tax calculated after the changed price
     * */
    public String recalcInvoicePriceHeader(){
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);
        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);
        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        invoicePage.fillDefaultInvoice(invoiceNumber,"gettysburg");
        invoicePage.clickCalculateTax();

        invoicePage.submitButton();
        invoicePage.bypassApproval();

        homePage.pressEditInvoice();
        invoicePage.clickCalculateTax();
        invoicePage.editItemPrice("2000");
        invoicePage.clickCalculateTax();
        String secondCalulatedTax = invoicePage.getVertexTaxValue();

        return secondCalulatedTax;
    }

    /**
     * Creates a header level invoice, then edits the quantity of the item
     * in the invoice to make sure the tax calc is correct
     *
     * @return the calculated tax value
     * */
    public String recalcInvoiceQuantityHeader(){
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);
        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);
        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        invoicePage.fillDefaultInvoice(invoiceNumber,"gettysburg");
        invoicePage.clickCalculateTax();

        invoicePage.submitButton();
        invoicePage.bypassApproval();

        homePage.pressEditInvoice();
        invoicePage.editQuantity("2");
        invoicePage.clickCalculateTax();
        String secondCalulatedTax = invoicePage.getVertexTaxValue();

        return secondCalulatedTax;
    }

    /**
     * Creates a purchase order then creates an invoice from that order
     * adds a tax to the invoice to match the vertex calculated tax
     * and confirms that the invoice was approved
     *
     * @param invoiceNumber the number for this invoice to be used for confirmation
     * @return the approval message for this invoice
     * */
    public String confirmInvoiceMatchFromPO(String invoiceNumber){
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        homePage.clickCart();
        homePage.addToCart();
        invoicePage.selectShippingAddress("gettysburg");
        invoicePage.selectBillingButton();
        homePage.approveCart();
        homePage.confirmRequistionItem();
        homePage.clickOrders();
        homePage.clickFirstPurchaseOrder();
        homePage.scrollToLines();
        homePage.clickInvoiceFromPOButton();

        invoicePage.addInvoiceNumber(invoiceNumber);
        invoicePage.addSupplierTaxRate("6");
        invoicePage.clickCalculateTax();

        invoicePage.submitButton();

        return invoicePage.getConfirmationText();
    }

    /**
     * Creates a purchase order then creates an invoice from that order
     * but does not match the PO's calculated tax
     *
     * @param invoiceNumber the number for this invoice to be used for confirmation
     * @return the message denying this invoice
     * */
    public String confirmInvoiceDoesNotMatchFromPO(String invoiceNumber){
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);
        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);
        homePage.clickCart();
        homePage.addToCart();
        invoicePage.selectShippingAddress("gettysburg");
        invoicePage.selectBillingButton();
        homePage.approveCart();
        homePage.confirmRequistionItem();
        homePage.clickOrders();
        homePage.clickFirstPurchaseOrder();
        homePage.scrollToLines();
        homePage.clickInvoiceFromPOButton();

        invoicePage.addInvoiceNumber(invoiceNumber);
        invoicePage.clickCalculateTax();

        invoicePage.submitButton();

        return invoicePage.getMessageOnApproval();
    }

    /**
     * Submits a tax exempt invoice for tax
     * calculation
     *
     * @return The calculated Tax value
     * */
    public String taxExemptInvoice(){
        CoupaSignInPage signInPage = new CoupaSignInPage(driver);

        signInPage.verifyCoupaLogin();

        CoupaHomePage homePage = new CoupaHomePage(driver);

        homePage.clickInvoice();
        homePage.createNewInvoice();

        CoupaCreateInvoicePage invoicePage = new CoupaCreateInvoicePage(driver);

        invoicePage.enterInvoiceNumber(CoupaUtils.randomInvoiceNumber());
        invoicePage.enterInvoiceSupplierNoMenu("coupa test");
        invoicePage.selectShippingAddress("gettysburg");
        invoicePage.enterItemName("laptop");
        invoicePage.editQuantity("1");
        invoicePage.selectBillingButton();
        invoicePage.clickCalculateTax();

        String calculatedTax = invoicePage.getVertexTaxValue();
        return calculatedTax;
    }

}
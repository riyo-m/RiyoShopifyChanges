package com.vertex.quality.connectors.concur.tests.invoice;
import com.vertex.quality.connectors.concur.enums.ConcurHeaderTab;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurEnterInvoiceDetailsPage;
import com.vertex.quality.connectors.concur.pages.panelPages.ConcurInvoicePage;
import com.vertex.quality.connectors.concur.pages.settings.ConcurInvoiceSettingsPage;
import com.vertex.quality.connectors.concur.pojos.ConcurInvoiceExpense;
import com.vertex.quality.connectors.concur.pojos.ConcurInvoiceRow;
import com.vertex.quality.connectors.concur.tests.base.ConcurUIBaseTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static org.testng.Assert.assertEquals;

/**
 * tests functionality of creating the invoice with different ship to address and billing address
 * # CSAPCONC-397
 *
 * @author alewis
 */

public class ConcurSalesOrderDifferentShipAndBillTests extends ConcurUIBaseTest{
    protected final String vendorNameInput = "Ecentric";
    protected final String address1Input = "1270 York Road";
    protected final String address2Input = "";
    protected final String cityInput = "Gettysburg";
    protected final String stateInput = "PA";
    protected final String zipCodeInput = "17325";
    protected final String countryInput = "united states";
    protected final String currencyInput = "USD-US, Dollar";
    protected final String telephoneNumberInput = "8888888888";
    protected final String contactFirstNameInput = "Mark";

    protected final String expectedInvoiceName = "Concur Test2";
    protected final String expectedInvoiceDate = "05/08/2020";
    protected final String expectedCurreny = "USD-US, Dollar";
    protected final String totalInvoiceAmmountInput = "10";
    protected final String taxProviderCompanyInput = "Geek Squad";

    protected final String shipToLocationName = "Hyderabad";
    protected final String shipToLocationAddressCode = "HMT";
    protected final String shipToLocationAddress1 = "5950 Broadway";
    protected final String shipToLocationCity = "Los Angeles";
    protected final String shipToLocationPostalCode = "90030";
    protected final String shipToLocationCountry = "UNITED STATES";
    protected final String shipToLocationState = "";
    protected final String shipToAddressValue = "Hyderabad";

    protected final String billToLocationName = "Billing One";
    protected final String billToLocationAddressCode = "Test2";
    protected final String billToLocationAddress1 = "100 N Orange";
    protected final String billToLocationCity = "St Wilmington";
    protected final String billToLocationPostalCode = "19801";
    protected final String billToCountryInput = "UNITED STATES";

    protected final String invoiceNumber = "";

    protected final String expenseTypeInput = "Fuel";
    protected final String lineDescriptionInput = "87 octane";
    protected final String quantityInput = "1";
    protected final String unitPriceInput = "10";
    protected final String expectedTotalPay = "10";
    protected final String expectedApprovalStatus = "Pending Approval-\n" + "Approver, Approver";

    /**
     * tests the Sales order with different billing and shipping address
     */
    @Test
    public void checkTaxOnInvoicePATest() {

        String randomInvoiceNumber = getRandomIntegerBetweenRange(100000001.0, 999999999.0);
        String expectedInvoiceNumber = String.format("%s%s", invoiceNumber, randomInvoiceNumber);
        //navigate to invoice page
        ConcurInvoicePage invoicePage = navigateToHeaderTabPage(ConcurHeaderTab.INVOICE, testStartPage);

        //navigate to admin invoice page
        String invoiceHeaderTab = "Invoice";
        ConcurInvoiceSettingsPage invoiceAdminPage = invoicePage.clickAdmin(invoiceHeaderTab);
        //click on company locations
        invoicePage.createCompanyLocations();
        //click on new button
        invoicePage.newButton();
        //enter ship to details
        invoicePage.enterShipToLocationDetails(shipToLocationName, shipToLocationAddressCode, shipToLocationAddress1,
                shipToLocationCity, shipToLocationPostalCode, shipToLocationCountry, "","");
        invoicePage.shipToSaveButton();
        //click on bill to link
        invoicePage.billToButton();
        //click on new button
        invoicePage.billToNewButton();
        //enter bill to details
        invoicePage.enterBillToLocationDetails(billToLocationName, billToLocationAddressCode,
                billToLocationAddress1, billToLocationCity, billToLocationPostalCode, billToCountryInput);
        invoicePage.billToSaveButton();
        // click to navigate invoice page
        invoicePage.invoiceLink();
        //create new invoice
        invoicePage.createNewInvoice();
        //request new vendor
        ConcurEnterInvoiceDetailsPage enterDetailsPage = invoicePage.requestNewVendor(vendorNameInput, address1Input,
                address2Input, cityInput, stateInput, zipCodeInput, countryInput, currencyInput, telephoneNumberInput, contactFirstNameInput);
        //enter invoice details
        enterDetailsPage.enterInvoiceDetails(expectedInvoiceName, expectedInvoiceNumber, expectedInvoiceDate,
                expectedCurreny, totalInvoiceAmmountInput, taxProviderCompanyInput,shipToAddressValue);
        ArrayList<ConcurInvoiceExpense> exp = new ArrayList<>();
        exp.add(new ConcurInvoiceExpense(expenseTypeInput, lineDescriptionInput, quantityInput, unitPriceInput,
                expectedTotalPay, null, null));
        //add expenses
        ConcurInvoicePage concurInvoicePage = enterDetailsPage.handleAmountRemainingPopup(exp);

        ConcurInvoiceRow invoiceData = concurInvoicePage.validateInvoiceCreation(expectedInvoiceNumber);
        String actualInvoiceName = invoiceData.getInvoiceName();
        String actualVendorName = invoiceData.getVendorName();
        String actualInvoiceNumber = invoiceData.getInvoiceNumber();
        String actualInvoiceDate = invoiceData.getInvoiceDate();
        String actualApprovalStatus = invoiceData.getApprovalStatus();
        String actualPaymentStatus = invoiceData.getPaymentStatus();
        String actualTotalPay = invoiceData.getTotalPay();

        assertEquals(expectedInvoiceName, actualInvoiceName);
        assertEquals(vendorNameInput, actualVendorName);
        assertEquals(expectedInvoiceNumber, actualInvoiceNumber);
        assertEquals(expectedInvoiceDate, actualInvoiceDate);
        //assertEquals(expectedApprovalStatus, actualApprovalStatus);
        //assertEquals(expectedPaymentStatus, actualPaymentStatus);
        //assertEquals(expectedTotalPay, actualTotalPay);

        runBatchJobUI();
    }

    /**
     * helper function to generated random number in range. Used to make 'unique' invoice number
     *
     * @param min minimum number of range
     * @param max maximum number of range
     * @return random integer between min and max
     */
    public static String getRandomIntegerBetweenRange(double min, double max) {
        double x = (int) (Math.random() * ((max - min) + 1)) + min;
        int x1 = (int) x;
        String x2 = Integer.toString(x1);
        return x2;
    }
}
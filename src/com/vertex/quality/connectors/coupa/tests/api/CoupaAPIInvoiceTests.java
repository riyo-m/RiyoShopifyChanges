package com.vertex.quality.connectors.coupa.tests.api;

import com.vertex.quality.connectors.coupa.tests.api.base.CoupaMessageLogBaseTest;
import com.vertex.quality.connectors.coupa.tests.api.base.CoupaRequestMappingBaseTest;
import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaInvoiceKeywords;
import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaMessageLogKeywords;
import com.vertex.quality.connectors.coupa.tests.api.base.keywords.CoupaTenantKeywords;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Coupa API Invoice Tests
 *
 * @author alewis
 */
public class CoupaAPIInvoiceTests {
    CoupaInvoiceKeywords invoice = new CoupaInvoiceKeywords();
    CoupaMessageLogKeywords logs = new CoupaMessageLogKeywords();
    CoupaTenantKeywords tenant = new CoupaTenantKeywords();
    CoupaRequestMappingBaseTest mappings = new CoupaRequestMappingBaseTest();

    /**
     * Coupa API Invoice Test; post taxes
     *
     * CCOUPA-1708
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void caHeaderTaxTest() {
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/HeaderLevel/coupaBaseXML.xml");
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();

    }

    /**
     * Coupa API Invoice bad payload; Negative Test
     *
     * CCOUPA-1709
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void headerTaxBadPayloadTest() {
        invoice.postBadPayload();
    }

    /**
     * Coupa API Invoice; Negative test header tax auth error
     *
     * CCOUPA-1710
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void badHeaderTaxAuthTest() {
        invoice.postBadTaxAuth();
    }

    /**
     * Coupa API Invoice for PA; post taxes at header level
     *
     * CCOUPA-1706
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void paHeaderTaxTest() {
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/StateInvoices/coupaPAPayload.xml");
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Coupa API Invoice for Delaware; post taxes at header level, should be zero tax
     *
     * CCOUPA-1707
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void deHeaderTaxTest() {
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/StateInvoices/coupaDEPayload.xml");
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<CalculatedTax>0.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.0</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Submit an Invoice with a tenant where logs are turned off. Confirm that no logs show up when doing get logs
     *
     * CCOUPA-
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void submitInvoiceWithLogsOffTest() {
        String invoiceNumber = invoice.postTaxesDisabledTenant("resources/xmlfiles/coupa/StateInvoices/coupaPAPayload.xml");
        logs.getTaxFromLogsWhenOff(invoiceNumber);
        tenant.deleteTenant();
    }

    /**
     * Puts in invoice with taxes on line item with an allocation.
     * Checks to see if charged tax is listed under first line item only and not second
     *
     * CCOUPA-1712
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void invoiceAllocationTest() {
        String invoiceNumber = invoice.postAllocationInvoice();

        String payload = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"1");
        assertTrue(payload.contains("<ChargedTax>60.00</ChargedTax>\n" +
                "      <Quantity unitOfMeasure=\"EA\">0.5000</Quantity>\n" +
                "      <UnitPrice>500.00000</UnitPrice>\n" +
                "      <ExtendedPrice>500.00</ExtendedPrice>\n" +
                "      <LineItem lineItemNumber=\"1\""));
        assertTrue(payload.contains(" </Buyer>\n" +
                "      <ChargedTax>0</ChargedTax>\n" +
                "      <Quantity unitOfMeasure=\"EA\">0.5000</Quantity>\n" +
                "      <UnitPrice>500.00000</UnitPrice>\n" +
                "      <ExtendedPrice>500.00</ExtendedPrice>\n" +
                "      <LineItem lineItemNumber=\"1\" lineItemId=\"1-1900-1\">\n" +
                "        <Purchase purchaseClass=\"InvoiceShippingCharge\"/>\n" +
                "        <ExtendedPrice>0.0000</ExtendedPrice>\n" +
                "      </LineItem>\n" +
                "      <LineItem lineItemNumber=\"2\" lineItemId=\"1-1901-1\">"));
        assertTrue(payload.contains("</Buyer>\n" +
                "      <ChargedTax>24.00</ChargedTax>\n" +
                "      <Quantity unitOfMeasure=\"EA\">0.5000</Quantity>\n" +
                "      <UnitPrice>200.00000</UnitPrice>\n" +
                "      <ExtendedPrice>200.00</ExtendedPrice>\n" +
                "      <LineItem lineItemNumber=\"1\" lineItemId=\"1-1900-2\">\n" +
                "        <Purchase purchaseClass=\"InvoiceShippingCharge\"/>\n" +
                "        <ExtendedPrice>0.0000</ExtendedPrice>\n" +
                "      </LineItem>\n" +
                "      <LineItem lineItemNumber=\"2\" lineItemId=\"1-1901-2\">\n" +
                "        <Purchase purchaseClass=\"InvoiceHandlingCharge\"/>\n" +
                "        <ExtendedPrice>0.0000</ExtendedPrice>\n" +
                "      </LineItem>"));
        assertTrue(payload.contains("</Buyer>\n" +
                "      <ChargedTax>0</ChargedTax>\n" +
                "      <Quantity unitOfMeasure=\"EA\">0.5000</Quantity>\n" +
                "      <UnitPrice>200.00000</UnitPrice>\n" +
                "      <ExtendedPrice>200.00</ExtendedPrice>\n" +
                "      <LineItem lineItemNumber=\"1\" lineItemId=\"1-1900-3\">\n" +
                "        <Purchase purchaseClass=\"InvoiceShippingCharge\"/>\n" +
                "        <ExtendedPrice>0.0000</ExtendedPrice>\n" +
                "      </LineItem>"));
    }

    /**
     * Checks that tax appropriately is charged at line level for a line level invoice
     *
     * CCOUPA-1776
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void lineItemChargedTaxTest() {
        String invoiceNumber = invoice.postChargedTaxLineLevel();

        String payload = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"1");
        assertTrue(payload.contains("<ChargedTax>24.00</ChargedTax>\n" +
                "      <Quantity unitOfMeasure=\"EA\">1.0</Quantity>\n" +
                "      <UnitPrice>400.00</UnitPrice>\n" +
                "      <ExtendedPrice>400.00</ExtendedPrice>\n" +
                "      <LineItem lineItemNumber=\"1\""));
    }

    /**
     *
     * CCOUPA-1737
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void totalTaxAtHeaderLevelTest() {
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/StateInvoices/coupaPAPayload.xml");

        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Tests whether the default taxpayer populates correctly in the logs after an invoice
     *
     * CCOUPA-1677
     */
    @Test(groups = { "coupa", "coupa_api"})
    public void companyCodeDefaultTest() {
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/StateInvoices/coupaPAPayload.xml");

        String verificationRequest = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"test");
        String verificationResponse = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(verificationRequest.contains("<LineItem lineItemNumber=\"1\" lineItemId=\"0-2609\">\n" +
                "      <Buyer>\n" +
                "        <Company>connector-coupa-qa</Company>\n" +
                "      </Buyer>"));
        assertTrue(verificationResponse.contains("<Company>connector-coupa-qa</Company>"));
        tenant.deleteTenant();
    }

    /**
     * Tests populating Division and Department Vertex fields as Account Segment-19, and Account Segment-20
     * And checking for the field in the invoice request and response
     *
     * CCOUPA-1739
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void divisionAndDepartmentMappingTest() {
        String id = mappings.createRequestMapping("DIVISION","ACCOUNT_SEGMENT_20");
        String idTwo = mappings.createRequestMapping("DEPARTMENT","ACCOUNT_SEGMENT_19");
        String invoiceNumber = invoice.postDepartmentDivisionInvoice("1");

        String verificationRequest = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"1");
        String verificationResponse = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"1");
        assertTrue(verificationRequest.contains("<Buyer>\n" +
                "        <Company>connector-coupa-dev</Company>\n" +
                "        <Division>Aaron-Division</Division>\n" +
                "        <Department>Aaron-Department</Department>\n" +
                "      </Buyer>"));
        assertTrue(verificationResponse.contains("<Buyer>\n" +
                "        <Company>connector-coupa-dev</Company>\n" +
                "        <Division>Aaron-Division</Division>\n" +
                "        <Department>Aaron-Department</Department>"));

        mappings.deleteRequestMapping(id);
        mappings.deleteRequestMapping(idTwo);
    }

    /**
     * Test division and department map correctly when use default division and department mapping
     *
     * CCOUPA-1747
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void defaultDivisionDepartmentMappingTest() {
        String id = mappings.createRequestMapping("DIVISION","ACCOUNT_SEGMENT_20");
        String idTwo = mappings.createRequestMapping("DEPARTMENT","ACCOUNT_SEGMENT_19");
        String invoiceNumber = invoice.postDepartmentDivisionInvoice("1");

        String verificationRequest = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"1");
        String verificationResponse = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"1");
        assertTrue(verificationRequest.contains(" <Buyer>\n" +
                "        <Company>connector-coupa-dev</Company>\n" +
                "        <Division>Aaron-Division</Division>\n" +
                "        <Department>Aaron-Department</Department>\n" +
                "      </Buyer>"));
        assertTrue(verificationResponse.contains(" <Buyer>\n" +
                "        <Company>connector-coupa-dev</Company>\n" +
                "        <Division>Aaron-Division</Division>\n" +
                "        <Department>Aaron-Department</Department>\n" +
                "      </Buyer>"));
        mappings.deleteRequestMapping(id);
        mappings.deleteRequestMapping(idTwo);
    }

    /**
     * Tests a Non PO backed multi line invoice with header level taxes without allocations
     *
     * CCOUPA-
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void coupaNonPOMultiLineHeadInvoiceTest() {
        String invoiceNumber = invoice.postCoupaNonPOMultiLineHeadInvoice();

        String payload = logs.getTaxFromLogs(invoiceNumber);
        assertTrue(payload.contains("<tax-rate>6.0</tax-rate>"));
    }

    /**
     * Tests a Non PO backed multi line invoice with line level taxes without allocations
     *
     * CCOUPA-
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void coupaNonPOMultiLineLineInvoiceTest() {
        String invoiceNumber = invoice.postCoupaNonPOMultiLineLineInvoice();

        String payload = logs.getTaxFromLogs(invoiceNumber);
        assertTrue(payload.contains("<line-level-taxation>true</line-level-taxation>"));
        assertTrue(payload.contains(" <tax-lines>\n" +
                "        <tax-line>\n" +
                "          <amount>6.00</amount>\n" +
                "          <amount-engine>60.0</amount-engine>"));
        assertTrue(payload.contains("<tax-lines>\n" +
                "        <tax-line>\n" +
                "          <amount>6.00</amount>\n" +
                "          <amount-engine>24.0</amount-engine>"));
    }

    /**
     * Tests a Non PO backed multi line invoice with header level taxes with allocations
     *
     * CCOUPA-
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void coupaNonPOMultiLineHeadInvoiceAllocationTest() {
        String invoiceNumber = invoice.coupaNonPOMultiLineHeadInvoiceAllocation();

        String payload = logs.getTaxFromLogs(invoiceNumber);
        assertTrue(payload.contains("<tax-rate>6.0</tax-rate>"));
        assertTrue(payload.contains("  <line-level-taxation>false</line-level-taxation>\n"));
    }

    /**
     * Non PO backed multi line invoice with line level taxes with allocations
     *
     * CCOUPA-
     */
    @Test
    public void coupaNonPOMultiLineLineInvoiceAllocationTest() {
        String invoiceNumber = invoice.coupaNonPOMultiLineLineInvoiceAllocation();

        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"1");
        assertTrue(payload.contains("<LineItem lineItemNumber=\"1\" lineItemId=\"0-4628-177\">\n" +
                "      <Quantity unitOfMeasure=\"EA\">0.5</Quantity>\n" +
                "      <UnitPrice>500.0</UnitPrice>\n" +
                "      <ExtendedPrice>500.0</ExtendedPrice>\n" +
                "      <Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" vertexTaxCode=\"Coupa Test\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>30.0</CalculatedTax>"));
        assertTrue(payload.contains("<LineItem lineItemNumber=\"2\" lineItemId=\"0-4629-179\">\n" +
                "      <Quantity unitOfMeasure=\"EA\">0.5</Quantity>\n" +
                "      <UnitPrice>200.0</UnitPrice>\n" +
                "      <ExtendedPrice>200.0</ExtendedPrice>\n" +
                "      <Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" vertexTaxCode=\"Coupa Test\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>12.0</CalculatedTax>"));
    }

    /**
     * Create Sales order with 1 Flex field and invoice
     *
     * CCOUPA-1533
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void flexCodeFieldInvoiceTest() {
        String id = mappings.createRequestMapping("FLEX_CODE_1","ACCOUNT_SEGMENT_18");
        String invoiceNumber = invoice.flexCodeInvoice();
        String payload = logs.getTaxFromLogs(invoiceNumber);
        assertTrue(payload.contains("<segment-18> FlexCodeTest</segment-18>"));
        mappings.deleteRequestMapping(id);
    }

    /**
     * Create Sales order with 1 Flex field and invoice
     *
     * CCOUPA-1534
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void allFlexCodeFieldInvoiceTest() {
        String idSix = mappings.createRequestMapping("FLEX_CODE_6","ACCOUNT_SEGMENT_6");
        String idSeven = mappings.createRequestMapping("FLEX_CODE_7","ACCOUNT_SEGMENT_7");
        String idEight = mappings.createRequestMapping("FLEX_CODE_8","ACCOUNT_SEGMENT_8");
        String idNine = mappings.createRequestMapping("FLEX_CODE_9","ACCOUNT_SEGMENT_9");
        String idTen = mappings.createRequestMapping("FLEX_CODE_10","ACCOUNT_SEGMENT_10");
        String idEleven = mappings.createRequestMapping("FLEX_CODE_11","ACCOUNT_SEGMENT_11");
        String idTwelve = mappings.createRequestMapping("FLEX_CODE_12","ACCOUNT_SEGMENT_12");
        String idThirteen = mappings.createRequestMapping("FLEX_CODE_13","ACCOUNT_SEGMENT_13");
        String idFourteen = mappings.createRequestMapping("FLEX_CODE_14","ACCOUNT_SEGMENT_14");
        String idFifteen = mappings.createRequestMapping("FLEX_CODE_15","ACCOUNT_SEGMENT_15");
        String idSixteen = mappings.createRequestMapping("FLEX_CODE_16","ACCOUNT_SEGMENT_16");
        String idSeventeen = mappings.createRequestMapping("FLEX_CODE_17","ACCOUNT_SEGMENT_17");
        String idEighteen = mappings.createRequestMapping("FLEX_CODE_18","ACCOUNT_SEGMENT_18");
        String idNineteen = mappings.createRequestMapping("FLEX_CODE_19","ACCOUNT_SEGMENT_19");
        String idTwenty = mappings.createRequestMapping("FLEX_CODE_20","ACCOUNT_SEGMENT_20");
        String invoiceNumber = invoice.allFlexCodeInvoice();
        String payload = logs.getTaxFromLogs(invoiceNumber);
        assertTrue(payload.contains("<segment-6>SixTest</segment-6>"));
        assertTrue(payload.contains("<segment-7>SevenTest</segment-7>"));
        assertTrue(payload.contains("<segment-8>EightTest</segment-8>"));
        assertTrue(payload.contains("<segment-9>NineTest</segment-9>"));
        assertTrue(payload.contains("<segment-10>TenTest</segment-10>"));
        assertTrue(payload.contains("<segment-11>ElevenTest</segment-11>"));
        assertTrue(payload.contains("<segment-12>TwelveTest</segment-12>"));
        assertTrue(payload.contains("<segment-13>ThirteenTest</segment-13>"));
        assertTrue(payload.contains("<segment-14>FourteenTest</segment-14>"));
        assertTrue(payload.contains("<segment-15>FifteenTest</segment-15>"));
        assertTrue(payload.contains("<segment-16>SixteenTest</segment-16>"));
        assertTrue(payload.contains("<segment-17>SeventeenTest</segment-17>"));
        assertTrue(payload.contains("<segment-18>EighteenTest</segment-18>"));
        assertTrue(payload.contains("<segment-19>NineteenTest</segment-19>"));
        assertTrue(payload.contains("<segment-20>TwentyTest</segment-20>"));
        mappings.deleteRequestMapping(idSix);
        mappings.deleteRequestMapping(idSeven);
        mappings.deleteRequestMapping(idEight);
        mappings.deleteRequestMapping(idNine);
        mappings.deleteRequestMapping(idTen);
        mappings.deleteRequestMapping(idEleven);
        mappings.deleteRequestMapping(idTwelve);
        mappings.deleteRequestMapping(idThirteen);
        mappings.deleteRequestMapping(idFourteen);
        mappings.deleteRequestMapping(idFifteen);
        mappings.deleteRequestMapping(idSixteen);
        mappings.deleteRequestMapping(idSeventeen);
        mappings.deleteRequestMapping(idEighteen);
        mappings.deleteRequestMapping(idNineteen);
        mappings.deleteRequestMapping(idTwenty);
    }

    /**
     * Api response test for header level invoices with chargeable summary tax
     *
     * CCOUPA-1850
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void taxWhenSummaryChargeableTest() {
        String invoiceNumber = invoice.postTaxSummaryChargeable();
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains(" <Purchase purchaseClass=\"InvoiceShippingCharge\"></Purchase>\n" +
                "      <Quantity>1.0</Quantity>\n" +
                "      <ExtendedPrice>5.0</ExtendedPrice>\n" +
                "      <Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" vertexTaxCode=\"Coupa Test\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>0.3</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>") || payload.contains(" <Purchase purchaseClass=\"InvoiceShippingCharge\"></Purchase>\n" +
                "      <Quantity>1.0</Quantity>\n" +
                "      <ExtendedPrice>5.0</ExtendedPrice>\n" +
                "      <Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>0.3</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>") );
        tenant.deleteTenant();
    }

    /**
    * Api response test for header level invoices with non-chargeable summary tax
     *
     *CCOUPA-1851
    * */
    @Test(groups = {"coupa","coupa_api"})
    public void taxWhenSummaryNotChargeableTest() {
        String invoiceNumber = invoice.postTaxSummaryNotChargeable();
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<Purchase purchaseClass=\"InvoiceShippingCharge\"></Purchase>\n" +
                "      <Quantity>1.0</Quantity>\n" +
                "      <ExtendedPrice>5.0</ExtendedPrice>\n" +
                "      <Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" vertexTaxCode=\"Coupa Test\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>0.3</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>") || payload.contains("<Purchase purchaseClass=\"InvoiceShippingCharge\"></Purchase>\n" +
                "      <Quantity>1.0</Quantity>\n" +
                "      <ExtendedPrice>5.0</ExtendedPrice>\n" +
                "      <Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>0.3</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Weighted distribution of charges for line level invoice
     *
     * No summary charges added for order. Tax put in line item level
     *
     * CCOUPA-1852
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void weightedNonSummaryForLineLevelInvoiceTest() {
        String invoiceNumber = invoice.weightedChargesNonSummaryInvoice();
        String payload = logs.getTaxFromInvoiceTaxResponse(invoiceNumber,"1");
        assertTrue(payload.contains("<line-num>1</line-num>\n" +
                "      <tax-lines>\n" +
                "        <tax-line>\n" +
                "          <amount-engine>60.82</amount-engine>\n" +
                "          <line-num>1</line-num>\n" +
                "          <rate-engine>6.0</rate-engine>"));
        assertTrue(payload.contains("<line-num>2</line-num>\n" +
                "      <tax-lines>\n" +
                "        <tax-line>\n" +
                "          <amount-engine>6.08</amount-engine>\n" +
                "          <line-num>1</line-num>\n" +
                "          <rate-engine>6.0</rate-engine>"));
    }

    /**
     * Weighted distribution of charges for line level invoice
     *
     * Summary charges added for order. Tax put in summary charges setion
     *
     * CCOUPA-1853
     */
    @Test(groups = {"coupa", "coupa_api"})
    public void weightedSummaryForLineLevelInvoiceTest() {
        String invoiceNumber = invoice.weightedChargesLineLevelInvoice();
        String payload = logs.getTaxFromInvoiceTaxResponse(invoiceNumber,"1");
        assertTrue(payload.contains("<type>InvoiceShippingCharge</type>\n" +
                "    </invoice-charge>\n" +
                "    <invoice-charge>\n" +
                "      <line-num>2</line-num>\n" +
                "      <tax-lines>\n" +
                "        <tax-line>\n" +
                "          <amount-engine>0.31</amount-engine>\n" +
                "          <line-num>1</line-num>\n" +
                "          <rate-engine>6.2</rate-engine>\n" +
                "        </tax-line>\n" +
                "      </tax-lines>"));
        assertTrue(payload.contains("<type>InvoiceHandlingCharge</type>\n" +
                "    </invoice-charge>\n" +
                "    <invoice-charge>\n" +
                "      <line-num>3</line-num>\n" +
                "      <tax-lines>\n" +
                "        <tax-line>\n" +
                "          <amount-engine>0.31</amount-engine>\n" +
                "          <line-num>1</line-num>\n" +
                "          <rate-engine>6.2</rate-engine>\n" +
                "        </tax-line>"));
    assertTrue(payload.contains("<type>InvoiceMiscCharge</type>\n" +
            "    </invoice-charge>\n" +
            "  </invoice-charges>\n" +
            "  <invoice-lines>\n" +
            "    <invoice-line>\n" +
            "      <line-num>1</line-num>\n" +
            "      <tax-lines>\n" +
            "        <tax-line>\n" +
            "          <amount-engine>59.99</amount-engine>\n" +
            "          <line-num>1</line-num>\n" +
            "          <rate-engine>6.0</rate-engine>\n" +
            "        </tax-line>"));
    }

    /**
     * Api response test for line level invoices with no vendor tax entered
     *
     *CCOUPA-1854
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void taxLineLevelNoVendorTaxTest() {
        String invoiceNumber = invoice.postLineLevelNoVendor();
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        System.out.println(payload);
        assertTrue(payload.contains("<Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" vertexTaxCode=\"Coupa Test\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>")|| payload.contains("<Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();
    }


    /**
     * Api response test for line level invoices with a vendor tax entered
     *
     *CCOUPA-1860
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void lineLevelWithVendorTaxTest() {
        String invoiceNumber = invoice.postLineLevelWithVendor();
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" vertexTaxCode=\"Coupa Test\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>")|| payload.contains("<Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Non-PO backed invoices should be mapped to Buyer 'remit-to-address' address test
     *
     * CCOUPA-1745
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void nonPOAddressMappingTest() {
        String invoiceNumber = invoice.postNonPORemitTo();
        String payload = logs.getTaxFromInvoiceVerificationRequestLog(invoiceNumber,"1");
        assertTrue(payload.contains("AdministrativeOrigin>\n" +
                "        <StreetAddress1>160 N Gulph Rd</StreetAddress1>\n" +
                "        <City>King of Prussia</City>\n" +
                "        <PostalCode>19406</PostalCode>\n" +
                "        <Country>US</Country>\n" +
                "      </AdministrativeOrigin>"));
    }

    /**
     * Header level tax with no vendor tax entered but
     * shipping, handling, and misc costs
     *
     * CCOUPA-1818
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void headerLevelNoUserTaxWithMiscCostsTest() {
        String invoiceNumber = invoice.postHeaderLevelNoUserWithMiscCosts();
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" vertexTaxCode=\"Coupa Test\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>")|| payload.contains("<Taxes taxResult=\"TAXABLE\" taxType=\"SALES\" situs=\"ADMINISTRATIVE_ORIGIN\" taxStructure=\"SINGLE_RATE\">\n" +
                "        <Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"31152\">PENNSYLVANIA</Jurisdiction>\n" +
                "        <CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Api response test for Illinois
     *
     * CCOUPA-1921
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void ilAPIResponesTest(){
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/StateInvoices/coupaILPayload.xml");
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<CalculatedTax>62.5</CalculatedTax>\n" +
                "        <EffectiveRate>0.0625</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Api response test for California
     *
     * CCOUPA-1922
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void caAPIResponesTest(){
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/StateInvoices/coupaCAPayload.xml");
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Api response test for Philadelphia, do to PA tax rules paying tax based on admin origin, not ship to address
     * So 6%, instead of Philly's usual 8%
     *
     * CCOUPA-1920
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void phlAPIResponseTest(){
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/StateInvoices/coupaPHLPayload.xml");
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<CalculatedTax>60.0</CalculatedTax>\n" +
                "        <EffectiveRate>0.06</EffectiveRate>"));
        tenant.deleteTenant();
    }

    /**
     * Checks to make sure Invoice Handling, Shipping, and Misc charges are successful passed in the invoice
     *
     * CCOUPA-1888
     * */
    @Test(groups = {"coupa", "coupa_api"})
    public void freightTest() {
        String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/StateInvoices/coupaPAPayload.xml");
        String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
        assertTrue(payload.contains("<Purchase purchaseClass=\"InvoiceHandlingCharge\"></Purchase>"));
        assertTrue(payload.contains("<Purchase purchaseClass=\"InvoiceShippingCharge\"></Purchase>"));
        assertTrue(payload.contains("<Purchase purchaseClass=\"InvoiceMiscCharge\"></Purchase>"));
        tenant.deleteTenant();
    }

    public void getTenantsTest(){
        tenant.doesTenantExist("test");
    }

	/**
	 * Coupa API Invoice Test; post taxes
	 *
	 * CCOUPA-1708
	 */
	@Test(groups = {"coupa", "coupa_api"})
	public void customFieldsInvoiceTest() {
		String invoiceNumber = invoice.postTaxes("resources/xmlfiles/coupa/HeaderLevel/coupaBaseXML.xml");
		String payload = logs.getTaxFromInvoiceVerificationLog(invoiceNumber,"test");
		assertTrue(payload.contains("<CalculatedTax>60.0</CalculatedTax>\n" +
									"        <EffectiveRate>0.06</EffectiveRate>"));
		tenant.deleteTenant();

	}
}
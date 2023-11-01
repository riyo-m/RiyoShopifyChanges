package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseVatTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class NetsuiteVatEUUKTests extends NetsuiteBaseVatTest {

    /**
     * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
     * Domestic: UK to UK, Registration: Both Customer and TaxPayer are registered in UK, Item_Type: Goods
     * @author ravunuri
     * (Scenario #1) CNSAPI-1097
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void UkIntraStateBothRegGoodsVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("100.00").build();
        customer = NetsuiteCustomer.CUSTOMER_UK;

        //List of XML response validations
		taxRate =0.2;
		country = "GB";
        tokenList.add("<Total>120.0</Total>");
        tokenList.add("<CalculatedTax>20.0</CalculatedTax>");
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78295\">UNITED KINGDOM</Jurisdiction>");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        tokenList.add("<SellerRegistrationId>222222222</SellerRegistrationId>");
        tokenList.add("<BuyerRegistrationId>111111119</BuyerRegistrationId>");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
     * Domestic: UK to UK, Registration: Both Customer and TaxPayer are registered in UK, Item_Type: Services
     * @author ravunuri
     * (Scenario #2) CNSAPI-1105
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void UkIntraStateBothRegServicesVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_ITEM)
                .quantity("1").amount("80.00").build();
        customer = NetsuiteCustomer.CUSTOMER_UK;

        //List of XML response validations
		taxRate = 0.2;
		price = 80.0;
		country = "GB";
        tokenList.add("<Total>96.0</Total>");
        tokenList.add("<CalculatedTax>16.0</CalculatedTax>");
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78295\">UNITED KINGDOM</Jurisdiction>");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        tokenList.add("<SellerRegistrationId>222222222</SellerRegistrationId>");
        tokenList.add("<BuyerRegistrationId>111111119</BuyerRegistrationId>");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
     * Domestic: UK to UK, Registration: Customer Not registered in UK but TaxPayer registered in UK, ItemType: Goods
     * @author ravunuri
     * (Scenario #3) CNSAPI-1106
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void UkIntraStateCustomerNotRegGoodsVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("100.00").build();
        customer = NetsuiteCustomer.CUSTOMER_UK;

        //List of XML response validations
		taxRate = 0.2;
		country = "GB";
        tokenList.add("<Total>120.0</Total>");
        tokenList.add("<CalculatedTax>20.0</CalculatedTax>");
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78295\">UNITED KINGDOM</Jurisdiction>");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        tokenList.add("<SellerRegistrationId>222222222</SellerRegistrationId>");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
     * Domestic: UK to UK, Registration: Customer Not registered in UK but TaxPayer registered in UK, ItemType: Services
     * @author ravunuri
     * (Scenario #4) CNSAPI-1107
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void UkIntraStateCustomerNotRegServicesVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_ITEM)
                .quantity("1").amount("80.00").build();
        customer = NetsuiteCustomer.CUSTOMER_UK;

        //List of XML response validations
		taxRate = 0.2;
		price = 80.0;
		country = "GB";
        tokenList.add("<Total>96.0</Total>");
        tokenList.add("<CalculatedTax>16.0</CalculatedTax>");
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78295\">UNITED KINGDOM</Jurisdiction>");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        tokenList.add("<SellerRegistrationId>222222222</SellerRegistrationId>");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
     * Domestic: Rest of Austria, Customer and Taxpayer address in Austria. Both registered in Austria, ItemType: Goods
     * @author ravunuri
     * (Scenario #9) CNSAPI-1110
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void RestOfAustriaBothRegVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("80.00").build();
        customer = NetsuiteCustomer.CUSTOMER_AUSTRIA_REG;

        //List of XML response validations
		taxRate = 0.2;
		price = 80.0;
		country = "AT";
        tokenList.add("<Total>96.0</Total>");
        tokenList.add("<CalculatedTax>16.0</CalculatedTax>");
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78278\">AUSTRIA</Jurisdiction>");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        tokenList.add("<BuyerRegistrationId>222111333</BuyerRegistrationId>");
        tokenList.add("<SellerRegistrationId>AT123456789</SellerRegistrationId>");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
     * Domestic: Jungholz, Austria. Customer and Taxpayer addresses in Jungholz.  Both are registered in Jungholz
     * NONTAXABLE for country but TAXABLE for City - Jungholz
     * @author ravunuri
     * (Scenario #11) CNSAPI-1111
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void IntraAustriaJungholzCityVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("80.00").build();
        customer = NetsuiteCustomer.CUSTOMER_AUSTRIA_JUNGHOLZ;

        //List of XML response validations
		price = 80.0;
		taxRate = 0.19;
		country = "AT";
        tokenList.add("<Total>95.2</Total>");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78278\">AUSTRIA</Jurisdiction>\n" +
                "<CalculatedTax>0.0</CalculatedTax>\n" +
                "<EffectiveRate>0.0</EffectiveRate>\n" +
                "<NonTaxable>80.0</NonTaxable>");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"CITY\" jurisdictionId=\"89439\">JUNGHOLZ</Jurisdiction>\n" +
                "<CalculatedTax>15.2</CalculatedTax>\n" +
                "<EffectiveRate>0.19</EffectiveRate>\n" +
                "<Taxable>80.0</Taxable>");
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        tokenList.add("<BuyerRegistrationId>222111222</BuyerRegistrationId>");
        tokenList.add("<SellerRegistrationId>AT123456789</SellerRegistrationId>");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
     * Domestic: Mittelberg, Austria. Customer and Taxpayer addresses in Mittelberg.  Both are registered in Mittelberg
     * NONTAXABLE for country but TAXABLE for City - Mittelberg
     * @author ravunuri
     * (Scenario #13) CNSAPI-1112
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void IntraAustriaMittelbergCityVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM)
                .quantity("1").amount("80.00").build();
        customer = NetsuiteCustomer.CUSTOMER_AUSTRIA_MITTELBERG;
		price = 80.0;
		taxRate = 0.19;
		country = "AT";
        tokenList.add("<Total>95.2</Total>");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78278\">AUSTRIA</Jurisdiction>\n" +
                "<CalculatedTax>0.0</CalculatedTax>\n" +
                "<EffectiveRate>0.0</EffectiveRate>\n" +
                "<NonTaxable>80.0</NonTaxable>\n" +
                "<Taxable>0.0</Taxable>");
        tokenList.add("<Jurisdiction jurisdictionLevel=\"CITY\" jurisdictionId=\"89441\">MITTELBERG</Jurisdiction>\n" +
                "<CalculatedTax>15.2</CalculatedTax>\n" +
                "<EffectiveRate>0.19</EffectiveRate>\n" +
                "<Taxable>80.0</Taxable>");
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        tokenList.add("<BuyerRegistrationId>222111444</BuyerRegistrationId>");
        tokenList.add("<SellerRegistrationId>AT123456789</SellerRegistrationId>");
        super.baseTest();
        super.postTest();
    }

	/**
	 * Create a Basic Sales Order for a VAT transaction and validate the xml response for tax details
	 * Domestic: UK to North Ireland - sale of goods. NonEU to EU using the qualifying category - Brexit - GB to NI as Export
	 * Zero rated tax for PHYSICAL_ORIGIN country, UNITED KINGDOM.
	 * To get the desired 0% VAT a value of "GB-NI-Export", enter "Export Type" text (FlexField 12) in SalesOrder/Invoice.
	 * @author ravunuri
	 * (Scenario #36) CNSAPI-1247
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void IntraUKtoNorthIrelandVatTest ( )  {
		//Define Customers and Items
		exportType = "GB-NI-Export";
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM)
							  .quantity("1").amount("100.00").location("UK_location").build();
		customer = NetsuiteCustomer.CUSTOMER_UK_NorthernIreland;

		//List of XML response validations
		taxRate =0.0;
		country = "GB";
		tokenList.add("<Total>100.0</Total>");
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>\n" + "<InvoiceTextCode>3</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Zero Rate\"");
		tokenList.add("<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78295\">UNITED KINGDOM</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGZREZ\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<SellerRegistrationId>222222222</SellerRegistrationId>");
		super.baseTest();
		super.postTest();
	}
}
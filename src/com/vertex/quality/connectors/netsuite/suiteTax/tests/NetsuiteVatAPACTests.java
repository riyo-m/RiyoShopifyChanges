package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteAddresses;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteAddress;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseVatTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class NetsuiteVatAPACTests extends NetsuiteBaseVatTest {

    /**
     * Create a Basic Sales Order for a APAC VAT transaction and validate the xml response for tax details
     * Domestic: Philippines, Registration: Both Customer and TaxPayer are registered in Philippines, Item_Type: Goods
     * @author ravunuri
     * CNSAPI-1135 (Scenario #1)
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void PhilippinesIntraBothRegGoodsVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).location("Philippines").build();
        customer = NetsuiteCustomer.CUSTOMER_PHILIPPINES_REG;

        //List of XML response validations
		taxRate =0.12;
		country = "PH";
        tokenList.add("<Total>112.0</Total>");
        tokenList.add("<CalculatedTax>12.0</CalculatedTax>");
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("PHILIPPINES</Jurisdiction>");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a APAC VAT transaction and validate the xml response for tax details
     * Domestic: Philippines, Registration: Both Customer and TaxPayer are registered in the Philippines, Item: Services
     * @author ravunuri
     * CNSAPI-1136 (Scenario #2)
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void PhilippinesIntraBothRegServicesVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_ITEM)
                .quantity("1").amount("80.00").location("Philippines").build();
        customer = NetsuiteCustomer.CUSTOMER_PHILIPPINES_REG;

        //List of XML response validations
		taxRate =0.12;
		price = 80.0;
		country = "PH";
		tokenList.add("<Total>89.6</Total>");
		tokenList.add("<CalculatedTax>9.6</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("PHILIPPINES</Jurisdiction>");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxCode=\"OGSTC\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a APAC VAT transaction and validate the xml response for tax details
     * Domestic: Philippines, Registration: Customer Not registered in Philippines but TaxPayer is registered, Item:Goods
     * @author ravunuri
     * CNSAPI-1137 (Scenario #3)
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void PhilippinesIntraCustNotRegGoodsVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).location("Philippines").build();
        customer = NetsuiteCustomer.CUSTOMER_PHILIPPINES_NOT_REG;

		//List of XML response validations
		taxRate =0.12;
		country = "PH";
		tokenList.add("<Total>112.0</Total>");
		tokenList.add("<CalculatedTax>12.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("PHILIPPINES</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		super.baseTest();
		super.postTest();
    }

    /**
     * Create a Basic Sales Order for a APAC VAT transaction and validate the xml response for tax details
     * Domestic: Philippines, Registration: Customer Not registered in Philippines but TaxPayer is registered, Services
     * @author ravunuri
     * CNSAPI-1138 (Scenario #4)
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void PhilippinesIntraCustNotRegServicesVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_ITEM)
                .quantity("1").amount("80.00").location("Philippines").build();
        customer = NetsuiteCustomer.CUSTOMER_PHILIPPINES_NOT_REG;

        //List of XML response validations
		taxRate =0.12;
		price = 80.0;
		country = "PH";
		tokenList.add("<Total>89.6</Total>");
		tokenList.add("<CalculatedTax>9.6</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("taxStructure=\"SINGLE_RATE\"");
        tokenList.add("rateClassification=\"Standard Rate\"");
        tokenList.add("PHILIPPINES</Jurisdiction>");
        tokenList.add("taxType=\"VAT\"");
        tokenList.add("taxResult=\"TAXABLE\"");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a APAC VAT transaction and validate the xml response for tax details
     * Domestic: Philippines, Regn.: Customer registered in Philippines but TaxPayer is NOT registered, ItemType: Goods
     * @author ravunuri
     * CNSAPI-1139 (Scenario #5)
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void PhilippinesIntraTaxpayerNotRegVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).location("Philippines").build();
        customer = NetsuiteCustomer.CUSTOMER_PHILIPPINES_REG;

		//List of XML response validations
		taxRate =0.12;
		country = "PH";
		tokenList.add("<Total>112.0</Total>");
		tokenList.add("<CalculatedTax>12.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("PHILIPPINES</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		super.baseTest();
		super.postTest();
    }

    /**
     * Create a Basic Sales Order for a APAC VAT transaction and validate the xml response for tax details
     * Domestic: Philippines, Registration: Both Customer and TaxPayer are NOT registered in Philippines, ItemType:Goods
     * @author ravunuri
     * CNSAPI-1140 (Scenario #7)
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void PhilippinesIntraBothNotRegVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).location("Philippines").build();
        customer = NetsuiteCustomer.CUSTOMER_PHILIPPINES_NOT_REG;
		transDate = "10/30/2016";
		//Define Customers and Items
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM)
							  .quantity("1").amount("100.00").location("Philippines")
							  .build();
		customer = NetsuiteCustomer.CUSTOMER_PHILIPPINES_NOT_REG;

		//List of XML response validations
		taxRate =0.12;
		country = "PH";
		tokenList.add("<Total>112.0</Total>");
		tokenList.add("<CalculatedTax>12.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("PHILIPPINES</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		super.baseTest();
		super.postTest();
    }

    /**
     * Create a Basic Sales Order for a APAC VAT transaction and validate the xml response for tax details
     * Domestic: Taiwan, Registration: Both Customer and TaxPayer are registered in Taiwan, Item_Type: Goods
     * @author ravunuri
     * CNSAPI-1144 (Scenario #9)
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void TaiwanIntraBothRegGoodsVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).location("Taiwan").build();
        customer = NetsuiteCustomer.CUSTOMER_TAIWAN_REG;

        //List of XML response validations
		taxRate =0.05;
		country = "TW";
		tokenList.add("<Total>105.0</Total>");
		tokenList.add("<CalculatedTax>5.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("TAIWAN</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales Order for a APAC VAT transaction and validate the xml response for tax details
     * Domestic: Taiwan - Gross Business Receipts tax (GBRT), Registration: Customer NOT registered in Taiwan but Taxpayer
     * is registered in Taiwan, Item_Type: Service - NIGHTCLUB for GBRT
     * @author ravunuri
     * CNSAPI-1145 (Scenario #10)
     */
    @Test(groups = {"netsuite_vat","suite_tax_regression"})
    protected void TaiwanIntraCustNotRegServiceVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_NIGHTCLUB_ITEM).location("Taiwan").build();
        customer = NetsuiteCustomer.CUSTOMER_TAIWAN_NOT_REG;

        //List of XML response validations
		taxRate =0.15;
		country = "TW";
		tokenList.add("<Total>115.0</Total>");
		tokenList.add("<CalculatedTax>15.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Luxury Rate\"");
		tokenList.add("TAIWAN</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OSLUX\"");
		tokenList.add("isService=\"true\"");
		tokenList.add("taxResult=\"TAXABLE\"");
        super.baseTest();
        super.postTest();
    }

	/**
	 * Create a Basic Sales Order for a APAC VAT transaction
	 * Validate VAT tax details in XML response for Vietnam
	 * @author mwilliams
	 * CNSAPI-1146 (Scenario #11)
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void VtIntraApacVatTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_VIETNAM_REG;
		price = 100.0;
		country = "VN";
		taxRate = 0.0;
		itemLocation = "Thailand";
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		//expectedTaxResult = "Failure";
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Basic Sales Order for a APAC VAT transaction
	 * Validate VAT tax details in XML response for Vietnam
	 * @author mwilliams
	 * CNSAPI-1147 (Scenario #12)
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void VtIntraApacVatNotRegTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_VIETNAM_NOT_REG;
		price = 100.0;
		country = "VN";
		taxRate = 0.0;
		itemLocation = "Thailand";
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		//expectedTaxResult = "Failure";
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Basic Sales Order for a APAC VAT transaction
	 * Validate VAT tax details in XML response for Vietnam
	 * @author mwilliams
	 * CNSAPI-1148 (Scenario #13)
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void VtIntraApacVatRegTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_VIETNAM_NOT_REG;
		price = 100.0;
		country = "VN";
		taxRate = 0.0;
		itemLocation = "Thailand";
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		//expectedTaxResult = "Failure";
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Basic Sales Order for a APAC VAT transaction
	 * Validate VAT tax details in XML response for Vietnam
	 * @author mwilliams
	 * CNSAPI-1149 (Scenario #14)
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void VtIntraApacVatNotNotRegTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_VIETNAM_NOT_REG;
		price = 100.0;
		country = "VN";
		taxRate = 0.0;
		itemLocation = "Thailand";
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		//expectedTaxResult = "Failure";
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Basic Sales Order for a APAC to US VAT transaction
	 * Validate VAT tax details in XML response for the US
	 * @author mwilliams
	 * CNSAPI-1150 (Scenario #15)
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void UsExportApacVatTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_VIETNAM_NOT_REG;
		price = 100.0;
		taxRate = 0.06;
		itemLocation = "South_Korea : South_Korea";
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Basic Sales Order for a APAC to US VAT transaction
	 * Validate VAT tax details in XML response for the US
	 * @author mwilliams
	 * CNSAPI-1152 (Scenario #17)
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void UsExportApacVatNoNexusTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_3M;
		price = 100.0;
		taxRate = 0.06;
		itemLocation = "South_Korea : South_Korea";
		shipToAddress = NetsuiteAddresses.PA_LOGAN_SQUARE.getAddress();
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Basic Sales Order for a inter-state APAC VAT transaction
	 * Validate VAT tax details in XML response for the Vietnam
	 * @author mwilliams
	 * CNSAPI-1154 (Scenario #19)
	 */
	@Ignore // This test isnt triggering the "reduced tax rate" of 5%. instead, it's 10%
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void VnApacVatNoNexusTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_VIETNAM_REG;
		price = 100.0;
		taxRate = 0.1;
		country = "VN";
		itemLocation = "Vietnam";
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Basic Sales Order for a inter-state APAC VAT transaction
	 * Validate VAT tax details in XML response for the Philippines
	 * @author mwilliams
	 * CNSAPI-1155 (Scenario #20)
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void PhApacVatTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_PHILIPPINES_REG;
		price = 100.0;
		taxRate = 0.1;
		country = "PH";
		itemLocation = "Philippines";
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Basic Sales Order for a inter-state APAC VAT transaction
	 * Validate VAT tax details in XML response for the Vietnam
	 * @author mwilliams
	 * CNSAPI-1156 (Scenario #21)
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void VnExemptApacVatTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_VIETNAM_REG;
		price = 100.0;
		taxRate = 0.1;
		country = "VN";
		itemLocation = "Philippines";
		shipToAddress = NetsuiteAddresses.VN_EXEMPT_ADDRESS.getAddress();
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxType=\"VAT\"");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Sales Order for domestic Australia APAC VAT transaction, Tax regn.: Taxpayer & Customer, Item: Goods
	 * Validate VAT tax details in XML response for Australia
	 * @author ravunuri
	 * CNSAPI-1342
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void AustraliaIntraCustRegVatGoodsTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_AUSTRALIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		//List of XML response validations
		taxRate =0.1; country = "AU";
		tokenList.add("<Total>110.0</Total>");
		tokenList.add("<CalculatedTax>10.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("AUSTRALIA</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">GST</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Sales Order for domestic Australia APAC VAT transaction, Tax regn.: Taxpayer & Customer, Item: Services
	 * Validate VAT tax details in XML response for Australia
	 * @author ravunuri
	 * CNSAPI-1372
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void AustraliaIntraCustRegVatServiceTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_AUSTRALIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_PROSERV).build();
		//List of XML response validations
		taxRate =0.1; country = "AU";
		tokenList.add("<Total>110.0</Total>");
		tokenList.add("<CalculatedTax>10.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("AUSTRALIA</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		//tokenList.add("isService=\"true\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">GST</Imposition>");
		//tokenList.add("<SellerRegistrationId>44444444222</SellerRegistrationId>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create a Sales Order for domestic Australia APAC VAT transaction, Tax regn.: Taxpayer & Customer, Item: Goods
	 * Validate VAT tax details in XML response for Australia, TAX: Zero Rate
	 * @author ravunuri
	 * CNSAPI-1343
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void AustraliaIntraVatZeroRateTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_AUSTRALIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.OTCDRUG_ITEM).build();
		//List of XML response validations
		taxRate =0.0; country = "AU";
		tokenList.add("<Total>100.0</Total>");
		tokenList.add("<CalculatedTax>0.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Zero Rate\"");
		tokenList.add("AUSTRALIA</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGZRC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">GST</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for domestic Australia APAC VAT transaction, Tax regn: Taxpayer & Customer, Item:Service, Exempt
	 * Validate VAT tax details in XML response for Australia, TAX: Exempt
	 * @author ravunuri
	 * CNSAPI-1344
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void AustraliaIntraVatExemptTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_AUSTRALIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_FINSVCS).build();
		//List of XML response validations
		taxRate =0.0; country = "AU";
		tokenList.add("<Total>100.0</Total>");
		tokenList.add("<CalculatedTax>0.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Exempt\"");
		tokenList.add("AUSTRALIA</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OSEMC\"");
		tokenList.add("taxResult=\"EXEMPT\"");
		tokenList.add("isService=\"true\"");
		tokenList.add("<Imposition impositionId=\"1\">GST</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for intra-Australia APAC VAT transaction, Regn: Taxpayer & Customer, Item:Services, NONTAXABLE
	 * Validate VAT tax details in XML response for Australia, TAX: NONTAXABLE
	 * @author ravunuri
	 * CNSAPI-1345
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void AustraliaIntraVatNonTaxTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_AUSTRALIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_REPAIRS).build();
		//List of XML response validations
		taxRate =0.0; country = "AU";
		tokenList.add("<Total>100.0</Total>");
		tokenList.add("<CalculatedTax>0.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Outside the Scope\"");
		tokenList.add("AUSTRALIA</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OSOSC\"");
		tokenList.add("isService=\"true\"");
		tokenList.add("taxResult=\"NONTAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">GST</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for intra-Malaysia APAC transaction, Regn: Taxpayer only, Item:Goods
	 * Validate Sales tax details in XML response for Malaysia
	 * @author ravunuri
	 * CNSAPI-1346
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void MalaysiaIntraSalesGoodsTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_MALAYSIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		//List of XML response validations
		taxRate =0.1; country = "MY";
		tokenList.add("<Total>110.0</Total>");
		tokenList.add("<CalculatedTax>10.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("MALAYSIA</Jurisdiction>");
		tokenList.add("taxType=\"SALES\"");
		tokenList.add("taxCode=\"DEFAULT\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">Sales Tax</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for intra-Malaysia APAC transaction, Regn: Taxpayer only, Item:Services
	 * Validate Sales tax details in XML response for Malaysia
	 * @author ravunuri
	 * CNSAPI-1347
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void MalaysiaIntraSalesServicesTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_MALAYSIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_PROSERV).build();
		//List of XML response validations
		taxRate =0.06; country = "MY";
		tokenList.add("<Total>106.0</Total>");
		tokenList.add("<CalculatedTax>6.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("MALAYSIA</Jurisdiction>");
		tokenList.add("taxType=\"SALES\"");
		tokenList.add("taxCode=\"DEFAULT\"");
		tokenList.add("isService=\"true\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">Sales Tax</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for intra-Malaysia APAC transaction, Regn: Taxpayer only, Item:Goods, Tax: Exempt
	 * Validate Sales tax details in XML response for Malaysia
	 * @author ravunuri
	 * CNSAPI-1348
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void MalaysiaIntraSalesExemptTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_MALAYSIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.OTCDRUG_ITEM).build();
		//List of XML response validations
		taxRate =0.00; country = "MY";
		tokenList.add("<Total>100.0</Total>");
		tokenList.add("<CalculatedTax>0.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Exempt\"");
		tokenList.add("MALAYSIA</Jurisdiction>");
		tokenList.add("taxType=\"SALES\"");
		tokenList.add("taxCode=\"DEFAULT\"");
		tokenList.add("taxResult=\"EXEMPT\"");
		tokenList.add("<Imposition impositionId=\"1\">Sales Tax</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for intra-Pakistan APAC transaction, Regn: Taxpayer only, Item:Goods
	 * Validate Sales tax details in XML response for Pakistan
	 * @author ravunuri
	 * CNSAPI-1349
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void PakistanIntraVatTaxGoodsTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_PAKISTAN;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		//List of XML response validations
		taxRate =0.17; country = "PK";
		tokenList.add("<Total>117.0</Total>");
		tokenList.add("<CalculatedTax>17.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("PAKISTAN</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">Sales Tax</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for intra-Pakistan APAC transaction, Regn: Taxpayer only, Item:Services, Tax: VAT
	 * Validate Sales tax details in XML response for Pakistan
	 * @author ravunuri
	 * CNSAPI-1350
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void PakistanIntraVatTaxServicesTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_PAKISTAN;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_PROSERV).build();
		//List of XML response validations
		taxRate =0.16; country = "PK";
		tokenList.add("<Total>116.0</Total>");
		tokenList.add("<CalculatedTax>16.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("PAKISTAN</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OSSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("isService=\"true\"");
		tokenList.add("<Imposition impositionId=\"1\">Provincial Sales Tax</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for intra-Pakistan APAC transaction, Regn: Taxpayer only, Item:Services, Tax: VAT
	 * Validate Sales tax details in XML response for Pakistan
	 * @author ravunuri
	 * CNSAPI-1351
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void PakistanProvincialSalesTaxTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_PAKISTAN;
		shipToAddress = NetsuiteAddresses.PK_THANDI_SARAK.getAddress();
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_FINSVCS).build();
		//List of XML response validations
		taxRate =0.13; country = "PK";
		tokenList.add("<Total>113.0</Total>");
		tokenList.add("<CalculatedTax>13.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("taxStructure=\"SINGLE_RATE\"");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("SINDH</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OSSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("isService=\"true\"");
		tokenList.add("<Imposition impositionId=\"1\">Provincial Sales Tax</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for Malaysia to Singapore Export transaction, Regn: Taxpayer only, Item:Services, Tax:Zero Rate
	 * Validate Sales tax details in XML response for Malaysia
	 * @author ravunuri
	 * CNSAPI-1352
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void MalaysiaSingaporeExportGoodsTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_MALAYSIA1;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).location("Singapore_location").build();
		//List of XML response validations
		taxRate =0.00; country = "SG";
		tokenList.add("<Total>100.0</Total>");
		tokenList.add("<CalculatedTax>0.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>\n" + "<InvoiceTextCode>3</InvoiceTextCode>");
		tokenList.add("rateClassification=\"Zero Rate\"");
		tokenList.add("SINGAPORE</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGZREZ\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">GST</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for Malaysia to Singapore Export transaction, Regn: Taxpayer only, Item:Goods, Tax: Zero Rate
	 * Validate Sales tax details in XML response for Malaysia
	 * @author ravunuri
	 * CNSAPI-1353
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void MalaysiaSingaporeExportServiceTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_MALAYSIA1;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_PROSERV).location("Singapore_location").build();
		//List of XML response validations
		taxRate =0.00; country = "SG";
		tokenList.add("<Total>100.0</Total>");
		tokenList.add("<CalculatedTax>0.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>\n" + "<InvoiceTextCode>3</InvoiceTextCode>");
		tokenList.add("rateClassification=\"Zero Rate\"");
		tokenList.add("SINGAPORE</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGZREZ\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">GST</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for Domestic: Russia, Regn: Both Taxpayer & Customer reg. in Russia, Item:Goods, Tax: 20%
	 * Validate Sales tax details in XML response for Russia
	 * @author ravunuri
	 * CNSAPI-1395
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void RussiaIntraCustRegVatGoodsTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_RUSSIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		//List of XML response validations
		taxRate =0.2; country = "RU";
		tokenList.add("<Total>120.0</Total>");
		tokenList.add("<CalculatedTax>20.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("RUSSIAN FEDERATION</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">VAT</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for Domestic: Russia, Regn: Both Taxpayer & Customer reg. in Russia, Item:Service, Tax: 20%
	 * Validate Sales tax details in XML response for Russia
	 * @author ravunuri
	 * CNSAPI-1396
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void RussiaIntraCustRegVatServiceTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_RUSSIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_PROSERV).build();
		//List of XML response validations
		taxRate =0.2; country = "RU";
		tokenList.add("<Total>120.0</Total>");
		tokenList.add("<CalculatedTax>20.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("RUSSIAN FEDERATION</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OSSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">VAT</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for Domestic: Switzerland, Regn: Both Taxpayer & Customer in Switzerland, Item:Goods, Tax:7.7%
	 * Validate Sales tax details in XML response for Switzerland
	 * @author ravunuri
	 * CNSAPI-1397
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void SwitzerlandIntraCustRegVatGoodsTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_SWITZERLAND;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		//List of XML response validations
		taxRate =0.077; country = "CH";
		tokenList.add("<Total>107.7</Total>");
		tokenList.add("<CalculatedTax>7.7</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("SWITZERLAND</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">VAT</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create SalesOrder for Domestic: Switzerland, Regn: Both Taxpayer & Customer in Switzerland, Item:Service,Tax:7.7%
	 * Validate Sales tax details in XML response for Switzerland
	 * @author ravunuri
	 * CNSAPI-1398
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void SwitzerlandIntraCustRegVatServiceTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_SWITZERLAND;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.SERVICE_PROSERV).build();
		//List of XML response validations
		taxRate =0.077; country = "CH";
		tokenList.add("<Total>107.7</Total>");
		tokenList.add("<CalculatedTax>7.7</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("rateClassification=\"Standard Rate\"");
		tokenList.add("SWITZERLAND</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OSSTC\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">VAT</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create SalesOrder for Domestic: Liechtenstein, Regn: Both Taxpayer & Customer in Liechtenstein, Goods,Tax:7.7%
	 * Validate Sales tax details in XML response for Liechtenstein
	 * @author ravunuri
	 * CNSAPI-1399
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void LiechtensteinIntraCustRegVatGoodsTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_LIECHTENSTEIN;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		//List of XML response validations
		taxRate =0.077; country = "LI";
		tokenList.add("<Total>107.7</Total>");
		tokenList.add("<CalculatedTax>7.7</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>18</InvoiceTextCode>\n" + "<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("SWITZERLAND</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("classCode=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">VAT</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for Liechtenstein to Switzerland Export transaction
	 * Taxpayer registered in Liechtenstein and Customer registered in Switzerland,  Item:Goods, Tax: 7.7%
	 * Validate Sales tax details in XML response for Liechtenstein to Switzerland export transaction
	 * @author ravunuri
	 * CNSAPI-1400
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void LiechtensteinSwitzerlandExportVatGoodsTest ( )  {
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_SWITZERLAND;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).build();
		//List of XML response validations
		taxRate =0.077; country = "CH";
		tokenList.add("<Total>107.7</Total>");
		tokenList.add("<CalculatedTax>7.7</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
		tokenList.add("SWITZERLAND</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGSTC\"");
		tokenList.add("classCode=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">VAT</Imposition>");
		super.baseTest();
		super.postTest();
	}

	/**
	 * Create Sales Order for Intra-Europe community. Sale of goods from Turkey to Albania.
	 * Taxpayer registered in Turkey and Customer registered in Albania. Goods, Tax: 0% Tax at PHYSICAL_ORIGIN (Turkey)
	 * Validate Sales tax details in XML response for Intra-Europe transaction
	 * @author ravunuri
	 * CNSAPI-1401
	 */
	@Test(groups = {"netsuite_vat","suite_tax_regression"})
	protected void IntraEuropeTurkeyAlbaniaVatGoodsTest ( )
	{
		//Define Customers and Items
		customer = NetsuiteCustomer.CUSTOMER_ALBANIA;
		itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).location("Turkey_location")
			.build();
		//List of XML response validations
		taxRate = 0.0;
		tokenList.add("<Total>100.0</Total>");
		tokenList.add("<CalculatedTax>0.0</CalculatedTax>");
		tokenList.add("<InvoiceTextCode>1</InvoiceTextCode>\n" + "<InvoiceTextCode>3</InvoiceTextCode>");
		tokenList.add("rateClassification=\"Zero Rate\"");
		tokenList.add("TURKEY</Jurisdiction>");
		tokenList.add("taxType=\"VAT\"");
		tokenList.add("taxCode=\"OGZREZ\"");
		tokenList.add("taxResult=\"TAXABLE\"");
		tokenList.add("<Imposition impositionId=\"1\">VAT</Imposition>");
		super.baseTest();
		super.postTest();
	}
}
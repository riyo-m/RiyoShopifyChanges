package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPIInvoicePage;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPISalesOrderPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseVatTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NewVatTests extends NetsuiteBaseVatTest {

    /**
     * Create a Basic Sales Order for a VAT transaction
     * validate the xml response for tax details
     * @author mwilliams
     * CNSAPI-675
     */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void UkIntraStateVatTest ( )  {
        //Define Customers and Items
        customer = NetsuiteCustomer.CUSTOMER_UK;
        price = 100.0;
        country = "GB";

        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("<SellerRegistrationId>GB123456789</SellerRegistrationId>");
        tokenList.add("<BuyerRegistrationId>111111119</BuyerRegistrationId>");
        super.baseTest();
        super.postTest();

    }

    /**
     * Create a basic sales order in Portugal
     * Validate VAT tax details in XML response
     */
	@Ignore
	@Test(groups = {"netsuite_vat"})
    protected void AtIntraStateVatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("80.00").build();
        customer = NetsuiteCustomer.CUSTOMER_AUSTRIA;
        price = 80.0;
        country = "PT";
        taxRate = 0.23;
        itemLocation = "Portugal";
        tokenList.add("<InvoiceTextCode>21</InvoiceTextCode>");
        tokenList.add("<SellerRegistrationId>103456789</SellerRegistrationId>");
        tokenList.add("<BuyerRegistrationId>123456789</BuyerRegistrationId>");
        super.baseTest();
        super.postTest();
    }

    /**
     * Create a Basic Sales order with currency conversion for VAT (EU GB), invoice and validate the xml
     * @author mwilliams
     */
    @Test(groups = {"netsuite_vat"})
    protected void VatTest ( )  {
        //Define Customers and Items
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00").build();
        customer = NetsuiteCustomer.CUSTOMER_UK;
        price = 100.0;
        country = "GB";
        taxRate = 0.20;
        super.baseTest();
        super.postTest();
    }


    /**
     * Create a Basic Sales order with currency conversion for VAT (EU FR-DE), invoice and validate the xml
     * @author mwilliams
     * CNSAPI-670 //Currently Out of scope for VAT
     */
    @Test(groups = {"netsuite_vat"})
    protected void currencyConversionTest( ) {   //Define Customers and Items
        customer = NetsuiteCustomer.CUSTOMER_FRANCE;
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00").build();
        price = 100.0;
        country = "USA";
        taxRate = 0.0;
        super.baseTest();
        super.postTest();

    }

    /**
     * Create a Basic Sales order for VAT (Austrian(AT) Sub-Division) invoice and validate the xmlCreate
     * Sale Order for Vat (Austrian Sub-Division) and Invoice* Create an Order same billing and shipping address
     * @author mwilliams
     * CNSAPI-669 //Currently Out of scope for VAT
     */
    @Test(groups = {"netsuite_vat"})
    protected void basicSalesOrderATTest( )
    {   //Define Customers and Items
        customer = NetsuiteCustomer.CUSTOMER_UK;
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00").build();
        price = 100.0;
        country = "UK";
        taxRate = 0.19;
        super.baseTest();
        super.postTest();

    }

    /**
     * Create a Basic Sales order for VAT (Intra EU UK), invoice and validate the xml
     * Sale Order for Vat Create an Order same billing and shipping address
     * @author mwilliams
     * CNSAPI-649 //Currently Out of scope for VAT
     */
    @Test(groups = {"netsuite_vat"})
    protected void vatWithShippingUKTest( )
    {   //Define Customers and Items
        customer = NetsuiteCustomer.CUSTOMER_UK;
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00").build();
        String shipping = "10.0";
        price = 100.0;
        country = "UK";
        taxRate = 0.20;
        super.baseTest();
        super.postTest();

    }

    /**
     * Create a Basic Sales order for VAT (Intra EU FR), invoice and validate the xml
     * Sale Order for Vat Create an Order same billing and shipping address
     * @author mwilliams
     * CNSAPI-648 //Currently Out of scope for VAT
     */
    @Test(groups = {"netsuite_vat"})
    protected void vatWithShippingFRTest( )
    {   //Define Customers and Items
        customer = NetsuiteCustomer.CUSTOMER_FRANCE;
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00").build();
        String shipping = "10.0";
        price = 100.0;
        country = "FR";
        taxRate = 0.095;
        super.baseTest();
        super.postTest();

    }

    /**
     * Create a Basic Sales order for VAT (Intra EU FR), invoice and validate the xml
     * Sale Order for Vat Create an Order same billing and shipping address
     * @author mwilliams
     * CNSAPI-647 //Currently Out of scope for VAT
     */
    @Test(groups = {"netsuite_vat"})
    protected void vatWithShippingDKTest( )
    {   //Define Customers and Items
        customer = NetsuiteCustomer.CUSTOMER_GERMANY;
        itemOne = NetsuiteItem.builder(NetsuiteItemName.ACC00002_ITEM).quantity("1").amount("100.00").build();
        String shipping = "10.0";
        price = 100.0;
        country = "DK";
        taxRate = 0.095;
        super.baseTest();
        super.postTest();

    }


}
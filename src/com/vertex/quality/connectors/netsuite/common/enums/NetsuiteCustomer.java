package com.vertex.quality.connectors.netsuite.common.enums;

import lombok.Getter;

/**
 * Contains Netsuite customers
 *
 * @author hho
 */
@Getter
public enum NetsuiteCustomer
{
	CUSTOMER_3M("3M"),
	ATMNERP_COMPANY2("ATMNERP COMPANY2"),
	ATMNOW_COMPANY2("ATMNOW COMPANY2"),
	TEST_AUTOMATION_CUSTOMER_1("Test Automation Customer 1"),
	TEST_CUSTOMER_CODE("Test Customer Code"),
	TEST_CUSTOMER_CLASS("Test Customer Class"),
	TEST_EXEMPTION_CUSTOMER("Exemption Customer"),
	CUSTOMER_CANADIAN("Canadian Customer"),
	CUSTOMER_SUPPLY_PA("Suppliers PA"),
	CUSTOMER_SUPPLIES_PA("Supplies - PA"),
	CUSTOMER_SUPPLY_CA("Supplies CA"),
	CUSTOMER_SUPPLY_NJ("Supplies - NJ"),
	CUSTOMER_LATAM_BZBZ("LatinAmerica_BZBZ"),
	CUSTOMER_LATAM_CRCO("LatinAmerica_CRCO"),
	CUSTOMER_APAC_CGST_SGST("AsiaPacific_CGST_SGST"),
	CUSTOMER_APAC_INTERSTATE("AsiaPacific_InterState"),
	CUSTOMER_APAC_INTERSTATE_REVERSE("AsiaPacific_InterState"),
	CUSTOMER_FRANCE("French Company 1"),
	CUSTOMER_FRANCE2("French Company 2"),
	CUSTOMER_GERMANY("German Company 1"),
	CUSTOMER_GREECE("Greek Company"),
	CUSTOMER_AUSTRIA("Austrian Company"),
	CUSTOMER_AUSTRIA_REG("Austria_Reg"),
	CUSTOMER_AUSTRIA_MITTELBERG("Austria_MittelbergCity_Cust"),
	CUSTOMER_AUSTRIA_JUNGHOLZ("Austria_JungholzCity_Cust"),
	CUSTOMER_BELGIUM("Belgian Customer"),
	CUSTOMER_HONG_KONG("Hong Kong Company"),
	CUSTOMER_UK("UK_Customer_Reg"),
	CUSTOMER_UK_NorthernIreland("Belfast, UK (Northern Ireland) Customer"),
	CUSTOMER_UK_NOT_REG("UK_Customer_Not_Reg"),
	CUSTOMER_EU("UK to DE Imports"),
	CUSTOMER_DE_IMPORTS("UK to DE Imports"),
	CUSTOMER_IR_IMPORTS("UK to Ireland Imports"),
	CUSTOMER_US_IMPORTS("UK to US Imports"),
	CUSTOMER_AUS_PORT("Austria to Portugal"),
	CUSTOMER_PHILIPPINES_REG("philippines_customer"),
	CUSTOMER_PHILIPPINES_NOT_REG("Philippines_Not_Reg"),
	CUSTOMER_TAIWAN_REG("Taiwan_Reg"),
	CUSTOMER_TAIWAN_NOT_REG("Taiwan_Not_Reg"),
	CUSTOMER_VIETNAM_REG("Vietnam"),
	CUSTOMER_VIETNAM_NOT_REG("Vietnam_Not_Reg"),
	CUSTOMER_THAILAND("Thailand"),
	CUSTOMER_AUSTRALIA("Australia_Reg_Customer"),
	CUSTOMER_PAKISTAN("Pakistan_Customer"),
	CUSTOMER_MALAYSIA("Malaysia_Customer"),
	CUSTOMER_MALAYSIA1("Malaysia_Cust"),
	CUSTOMER_RUSSIA("Russia_reg"),
	CUSTOMER_SWITZERLAND("Switzerland_reg"),
	CUSTOMER_LIECHTENSTEIN("Liechtenstein_reg"),
	CUSTOMER_ALBANIA("Albania_reg"),
	CUSTOMER_YEMEN("Yemen_reg"),
	CUSTOMER_CODE_EXEMPT("Test_Customer_Code_Exemption"),
	CUSTOMER_CLASS_EXEMPT("Test_Customer_Class_Exemption"),
	CUSTOMER_CURRENCY("Customer_Currency");

	private String customerName;

	NetsuiteCustomer( String customerName )
	{
		this.customerName = customerName;
	}
}

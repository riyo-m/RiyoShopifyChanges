package com.vertex.quality.connectors.saptaxservice.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.tests.VertexAPIBaseTest;
import com.vertex.quality.connectors.saptaxservice.utils.SAPTaxServiceAPITestUtilities;
import org.testng.annotations.Test;

/**
 * Base test for SAP Tax Service
 * Used mainly to authenticate requests.
 *
 * @author hho
 */
@Test(groups = { "sap_tax_service" })
public abstract class SAPTaxServiceBaseTest extends VertexAPIBaseTest
{
	protected SAPTaxServiceAPITestUtilities apiUtil;

	//don't modify this dynamically- its value is based on the value in the api test utility object
	protected String baseUrl;

	protected final DBConnectorNames connectorName = SAPTaxServiceAPITestUtilities.CONNECTOR_NAME;
	protected final DBEnvironmentNames environmentName = SAPTaxServiceAPITestUtilities.ENVIRONMENT_NAME;
	protected final DBEnvironmentDescriptors environmentDescriptor
		= SAPTaxServiceAPITestUtilities.ENVIRONMENT_DESCRIPTOR;

	// constant values
	protected final String id = SAPTaxServiceAPITestUtilities.ID;
	protected final String date = SAPTaxServiceAPITestUtilities.DATE;
	protected final String canadaCurrency = SAPTaxServiceAPITestUtilities.CANADA_CURRENCY;
	protected final String sale = SAPTaxServiceAPITestUtilities.SALE;
	protected final String purchase = SAPTaxServiceAPITestUtilities.PURCHASE;
	protected final String gross = SAPTaxServiceAPITestUtilities.GROSS;
	protected final String net = SAPTaxServiceAPITestUtilities.NET;
	protected final String yes = SAPTaxServiceAPITestUtilities.YES;
	protected final String no = SAPTaxServiceAPITestUtilities.NO;
	protected final String itemTypeMaterial = SAPTaxServiceAPITestUtilities.ITEM_TYPE_MATERIAL;
	protected final String itemTypeService = SAPTaxServiceAPITestUtilities.ITEM_TYPE_SERVICE;
	protected final String taxCategoryProductTaxes = SAPTaxServiceAPITestUtilities.TAX_CATEGORY_PRODUCT_TAXES;
	protected final String taxCategoryWithholding = SAPTaxServiceAPITestUtilities.TAX_CATEGORY_WITHHOLDING;
	protected final String stringValue = SAPTaxServiceAPITestUtilities.STRING_VALUE;
	protected final String gst = SAPTaxServiceAPITestUtilities.GST;
	protected final String pst = SAPTaxServiceAPITestUtilities.PST;
	protected final String qst = SAPTaxServiceAPITestUtilities.QST;
	protected final String hst = SAPTaxServiceAPITestUtilities.HST;
	protected final int successResponseCode = SAPTaxServiceAPITestUtilities.SUCCESS_RESPONSE_CODE;
	protected final int badRequestResponseCode = SAPTaxServiceAPITestUtilities.BAD_REQUEST_RESPONSE_CODE;

	/**
	 * performs the setup which is necessary for api tests of sap tax service
	 */
	@Override
	protected void setupTestCase( )
	{
		this.apiUtil = new SAPTaxServiceAPITestUtilities();
		this.baseUrl = apiUtil.getBaseUrl();
	}
}
package com.vertex.quality.connectors.ariba.portal.pojos;

import com.vertex.quality.connectors.ariba.portal.enums.AribaPlants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

/**
 * represents the line item components
 *
 * @author osabha
 */
@Getter
@Builder
@Setter
public class AribaLineItem

{
	@Builder.Default
	public int itemIndex = 1;
	@Builder.Default
	public String productName = "FORK1-New";
	@Builder.Default
	public String quantity = "1";
	@Builder.Default
	public String deliverToPerson = "Someone Important";
	@Builder.Default
	public String costCenter = "0000001000 (Corporate Services)";
	@Builder.Default
	public String shipFromAddress = "TestAutomationS_USPA_KingofPrussia";
	public AribaPlants plant_shipToAddress;
	@Builder.Default
	public String billToAddress = "1004 - PA - King of Prussia";
	@Builder.Default
	public String purchasingOrganization = "3000 (IDES USA)";
	@Builder.Default
	public String companyCode = "3000 (IDES US INC)";
	@Builder.Default
	public String shippingPurchaseGroup = "003 (IDES USA)";
	public String updatedPrice;
	public double expectedTaxAmount;
	@Singular
	public List<AribaTaxResult> taxResults;

	public String shippingCost;
}

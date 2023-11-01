package com.vertex.quality.connectors.ariba.connector.pojos;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnExternalTaxTypesPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * describes a row in the {@link AribaConnExternalTaxTypesPage}
 *
 * @author ssalisbury
 */
@Getter
@Builder
@AllArgsConstructor
public class AribaConnExternalTaxType
{
	@NonNull
	protected String externalTaxTypeName;
	protected boolean representsVAT;
	protected boolean representsSelfAssessed;
	protected String vendorPaidTaxAccountInstructions;
}

package com.vertex.quality.connectors.ariba.connector.pojos;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnVertexJurisdictionLevel;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnVertexTaxType;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnVertexVATTaxType;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnTaxRulesPage;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * describes a row in the {@link AribaConnTaxRulesPage}
 *
 * @author ssalisbury
 */
@Getter
@Builder
public class AribaConnTaxMappingRule
{
	@NonNull
	protected String aribaComponentTaxType;
	@NonNull
	protected AribaConnVertexTaxType vertexType;
	@Builder.Default
	protected AribaConnVertexJurisdictionLevel jurisdictionLevel = AribaConnVertexJurisdictionLevel.BLANK;
	@Builder.Default
	protected AribaConnVertexVATTaxType vatType = AribaConnVertexVATTaxType.BLANK;
	@Builder.Default
	protected String vertexImpositionType = "";
	@Builder.Default
	protected String vertexImpositionName = "";
	@Builder.Default
	protected String vertexJurisdictionName = "";
}

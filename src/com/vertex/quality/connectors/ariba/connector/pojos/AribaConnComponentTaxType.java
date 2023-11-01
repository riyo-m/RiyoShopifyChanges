package com.vertex.quality.connectors.ariba.connector.pojos;

import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnComponentTaxTypesPage;
import lombok.*;

import java.util.List;

/**
 * describes a row in the {@link AribaConnComponentTaxTypesPage}
 *
 * @author ssalisbury
 */
@Getter
@Builder
@AllArgsConstructor
public class AribaConnComponentTaxType
{
	@NonNull
	protected String componentTaxType;
	@Singular
	protected List<String> externalTaxTypes;
	protected String selfAssessedTaxAccountInstruction;
}

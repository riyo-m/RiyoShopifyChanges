package com.vertex.quality.connectors.ariba.connector.pojos;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnAribaFieldType;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnCustomFieldMappingPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * describes a row in the {@link AribaConnCustomFieldMappingPage}
 *
 * @author ssalisbury
 */
@Getter
@Builder
@AllArgsConstructor
public class AribaConnCustomFieldMapping
{
	protected AribaConnAribaFieldType aribaDataType;
	protected String aribaName;
	protected String vertexField;
}

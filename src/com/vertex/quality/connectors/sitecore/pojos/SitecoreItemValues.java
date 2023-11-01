package com.vertex.quality.connectors.sitecore.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SitecoreItemValues
{
	protected double price;
	protected double quantity;
	protected double subtotal;
}

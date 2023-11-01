package com.vertex.quality.connectors.netsuite.common.pojos;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteNavigationMenuTitles;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

/**
 * POJO for Netsuite's navigation menus
 *
 * @author hho
 */
@Builder
@Getter
@ToString
public class NetsuiteNavigationMenus
{
	@Singular("menu")
	private List<NetsuiteNavigationMenuTitles> menus;
}

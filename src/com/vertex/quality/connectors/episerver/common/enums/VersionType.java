package com.vertex.quality.connectors.episerver.common.enums;

public enum VersionType
{
	// @formatter:off
	Core_Version("Core Version"),
	Adapter_Version("Adapter Version");
	// @formatter:on

	public String text;

	VersionType( String text )
	{
		this.text = text;
	}
}

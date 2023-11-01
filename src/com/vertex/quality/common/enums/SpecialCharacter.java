package com.vertex.quality.common.enums;

public enum SpecialCharacter
{
	REGISTERED_TRADEMARK("\u00AE"),
	TRADEMARK("\u2122"),
	NON_BREAKING_SPACE("\u00A0");

	public String characterCode;

	SpecialCharacter( String characterCode )
	{
		this.characterCode = characterCode;
	}

	@Override
	public String toString( )
	{
		return characterCode;
	}
}

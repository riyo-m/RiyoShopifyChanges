package com.vertex.quality.connectors.mirakl.common.utils;

import com.vertex.quality.connectors.mirakl.api.enums.MiraklOperatorsData;

import java.util.ArrayList;

/**
 * contains global variables used in mirakl-regression suite
 *
 * @author rohit-mogane
 */
public class MiraklDeclareGlobals
{
	public ArrayList<String> miraklGlobalOperators;
	private static MiraklDeclareGlobals globals;
	public static String miraklApiUrl;

	private MiraklDeclareGlobals( )
	{
		miraklApiUrl = System.getProperty("ApiUrl");
		if ( miraklApiUrl == null )
		{
			miraklApiUrl = MiraklOperatorsData.API_URL.data;
		}
		miraklGlobalOperators = new ArrayList<>();
	}

	/**
	 * get single instance fro singleton class
	 *
	 * @return single instance
	 */
	public static MiraklDeclareGlobals getInstance( )
	{
		if ( globals == null )
		{
			globals = new MiraklDeclareGlobals();
		}
		return globals;
	}

	/**
	 * Get global operators arraylist
	 *
	 * @return all global operators used in mirakl regression suite
	 */
	public ArrayList<String> getArrayList( )
	{
		return miraklGlobalOperators;
	}

	/**
	 * api url to request mirakl apis
	 *
	 * @return mirakl api url
	 */
	public String getMiraklApiUrl( )
	{
		return miraklApiUrl;
	}
}

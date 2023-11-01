package com.vertex.quality.common.enums;

/**
 * encapsulates the different algorithms used for scrolling until the point of interest
 * on a page is in a certain position within the viewport, the portion of the page
 * that's currently visible to the user or the selenium driver
 * The prefix VERT_ on an entry clarifies that it involves only vertical scrolling
 * should be passed in as the first argument to JavascriptExecutor.executeScript()
 *
 * @author ssalisbury
 */
public enum PageScrollDestination
{
	TOP("arguments[0].scrollIntoView(true);"),
	VERT_CENTER("var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);" +
				"var elementTop = arguments[0].getBoundingClientRect().top;" +
				"window.scrollBy(0, elementTop-(viewPortHeight/2));"),
	BOTTOM("arguments[0].scrollIntoView(false);");

	public String navigationJavascript;

	PageScrollDestination( final String text )
	{
		this.navigationJavascript = text;
	}
}

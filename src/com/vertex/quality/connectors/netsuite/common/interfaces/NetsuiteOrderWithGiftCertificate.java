package com.vertex.quality.connectors.netsuite.common.interfaces;

/**
 * Represents an order that has the "GIFT CERTIFICATE" amount field
 *
 * @author hho
 */
public interface NetsuiteOrderWithGiftCertificate
{
	String summaryGiftCertificateHeader = "GIFT CERTIFICATE";

	/**
	 * Gets the order gift certificate amount
	 *
	 * @return the order gift certificate amount
	 */
	String getGiftCertificateAmount( );
}

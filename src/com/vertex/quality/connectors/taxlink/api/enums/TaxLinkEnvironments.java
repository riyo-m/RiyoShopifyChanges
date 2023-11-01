package com.vertex.quality.connectors.taxlink.api.enums;

import lombok.Getter;

/**
 * Contains all Taxlink environment names.
 *
 * @author msalomone
 */
@Getter
public enum TaxLinkEnvironments
{

	DEMO0063_UI(1, "https://demo0063.vertexinc.com/vertex-tl-ui/", "https://demo0063.vertexinc.com"),
	DEMO0063_AUTH(2, "https://demo0063.vertexinc.com/vertex-tl-auth/", "https://demo0063.vertexinc.com"),
	FRANKFURT(3, "https://functional01.oeu.ondemand.vertexinc.com/vertex-tl-web-ui/",
		"https://functional01.oeu.ondemand.vertexinc.com"),
	DEMO0030(4, "https://demo0030.vertexinc.com/vertex-tl-ui/", "https://demo0030.vertexinc.com"),// aka KOPAS0016
	DEMO0064(5, "https://demo0064.vertexinc.com/vertex-tl-ui/", "https://demo0064.vertexinc.com"),
	DEMO0072(6, "https://demo0072.vertexinc.com/vertex-tl-ui/", "https://demo0072.vertexinc.com"),
	ONPREM(7, "https://vtxorclaccloci.vertexinc.com/vertex-tl-web-ui/", "https://vtxorclaccloci.vertexinc.com");



    public int id;
    public String environmentURL;
    public String baseURL;

    TaxLinkEnvironments( int environmentId, String environmentURL, String baseURL )
    {
        this.id = environmentId;
        this.environmentURL = environmentURL;
        this.baseURL = baseURL;
    }
}

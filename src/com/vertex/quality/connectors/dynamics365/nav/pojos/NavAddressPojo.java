package com.vertex.quality.connectors.dynamics365.nav.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * contains all the address field objects with getter and setter methods for each .
 *
 * @author bhikshapathi
 */
@Builder
@Getter
@Setter
public class NavAddressPojo
{
    private String line1;
    private String city;
    private String state;
    private String country;
    private String zip_code;
}

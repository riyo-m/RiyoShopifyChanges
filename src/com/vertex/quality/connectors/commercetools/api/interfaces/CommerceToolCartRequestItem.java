package com.vertex.quality.connectors.commercetools.api.interfaces;

/**
 * an cart request which is part of an api  request
 *
 * WARN- when any field is added to this interface, make sure that every implementing class adds
 * copying of that field to their implementation of the copy method!!!
 *
 * @author osabha ssalisbury
 */
public interface CommerceToolCartRequestItem {
    //these setters have to use underscores because they correspond to keys in the JSON for a create cart request,
    // and Lombok's auto-generated setters just capitalize the first letter of the field
    // and add 'set' in front
    public void setCurrency( final String customerID);
    public void setTaxMode( final String taxMode);
    public void setCustomerId( final String customerId );

    /**
     * this creates a copy of commercetool cart request Item object.
     *
     * @return a copy of commercetool cart request object
     */
    public CommerceToolCartRequestItem copy( );

}

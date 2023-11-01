package com.vertex.quality.connectors.commercetools.api.pojos;

import java.util.List;

/**
 * an add line request Items which can be sent to the connector's API
 *
 * @author Vivek.Kumar
 */

public class CommerceToolAddLineItem {
    private long version;
    private List<CommerceToolAddLineActions> actions;

    public long getVersion() {
        return version;
    }

    public List<CommerceToolAddLineActions> getActions() {
        return actions;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setActions(List<CommerceToolAddLineActions> actions) {
        this.actions = actions;
    }
}

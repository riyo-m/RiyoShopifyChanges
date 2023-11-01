package com.vertex.quality.connectors.oraclecloud.common.utils;

public class OracleProperties {

    private static OracleProperties instance = null;

    public String omOrderNum;

    private OracleProperties(){}


    public static OracleProperties getOraclePropertiesInstance(){
        if (instance == null)
            instance = new OracleProperties();
        return instance;
    }

    public String getOmOrderNum(){
        return omOrderNum;
    }

    public void setOmOrderNum(String orderNum){
        omOrderNum = orderNum;
    }
}

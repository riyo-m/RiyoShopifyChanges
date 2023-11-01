<#if numberOfItems??>
    <#list 1..numberOfItems as i>
        <urn:item>
            <#if .vars["item" + i + "HasBillingAddress"]!false>
                <urn:BillingAddress>
                    <urn:PostalAddress>
                        <#if .vars["item" + i + "BillingCity"]??>
                            <urn:City>${.vars["item" + i + "BillingCity"]}</urn:City>
                        </#if>
                        <#if .vars["item" + i + "BillingCountry"]??>
                            <urn:Country>
                                <urn:UniqueName>${.vars["item" + i + "BillingCountry"]}</urn:UniqueName>
                            </urn:Country>
                        </#if>
                        <#if .vars["item" + i + "BillingLine1"]??>
                            <urn:Line1>${.vars["item" + i + "BillingLine1"]}</urn:Line1>
                        </#if>
                        <#if .vars["item" + i + "BillingLine2"]??>
                            <urn:Line2>${.vars["item" + i + "BillingLine2"]}</urn:Line2>
                        </#if>
                        <#if .vars["item" + i + "BillingLine3"]??>
                            <urn:Line3>${.vars["item" + i + "BillingLine3"]}</urn:Line3>
                        </#if>
                        <#if .vars["item" + i + "BillingZip"]??>
                            <urn:PostalCode>${.vars["item" + i + "BillingZip"]}</urn:PostalCode>
                        </#if>
                        <#if .vars["item" + i + "BillingState"]??>
                            <urn:State>${.vars["item" + i + "BillingState"]}</urn:State>
                        </#if>
                    </urn:PostalAddress>
                </urn:BillingAddress>
            </#if>
            <#if .vars["item" + i + "HasShipFromAddress"]!false>
                <urn:ShipFromAddress>
                    <urn:PostalAddress>
                        <#if .vars["item" + i + "ShipFromCity"]??>
                            <urn:City>${.vars["item" + i + "ShipFromCity"]}</urn:City>
                        </#if>
                        <#if .vars["item" + i + "ShipFromCountry"]??>
                            <urn:Country>
                                <urn:UniqueName>${.vars["item" + i + "ShipFromCountry"]}</urn:UniqueName>
                            </urn:Country>
                        </#if>
                        <#if .vars["item" + i + "ShipFromLine1"]??>
                            <urn:Line1>${.vars["item" + i + "ShipFromLine1"]}</urn:Line1>
                        </#if>
                        <#if .vars["item" + i + "ShipFromLine2"]??>
                            <urn:Line2>${.vars["item" + i + "ShipFromLine2"]}</urn:Line2>
                        </#if>
                        <#if .vars["item" + i + "ShipFromLine3"]??>
                            <urn:Line3>${.vars["item" + i + "ShipFromLine3"]}</urn:Line3>
                        </#if>
                        <#if .vars["item" + i + "ShipFromZip"]??>
                            <urn:PostalCode>${.vars["item" + i + "ShipFromZip"]}</urn:PostalCode>
                        </#if>
                        <#if .vars["item" + i + "ShipFromState"]??>
                            <urn:State>${.vars["item" + i + "ShipFromState"]}</urn:State>
                        </#if>
                    </urn:PostalAddress>
                </urn:ShipFromAddress>
            </#if>
            <#if .vars["item" + i + "BuyerTaxRegistrationNumber"]??>
                <urn:BuyerTaxRegistrationNumber>${.vars["item" + i + "BuyerTaxRegistrationNumber"]}</urn:BuyerTaxRegistrationNumber>
            </#if>
            <#if .vars["item" + i + "HasChargeDetail"]!false>
                <urn:ChargeDetail>
                    <#if .vars["item" + i + "HasChargeAmount"]!false>
                        <urn:ChargeAmount>
                            <#if .vars["item" + i + "ChargeAmount"]??>
                                <urn:Amount>${.vars["item" + i + "ChargeAmount"]}</urn:Amount>
                            </#if>
                            <#if .vars["item" + i + "ChargeAmountCurrency"]??>
                                <urn:Currency>
                                    <urn:UniqueName>${.vars["item" + i + "ChargeAmountCurrency"]}</urn:UniqueName>
                                </urn:Currency>
                            </#if>
                        </urn:ChargeAmount>
                    </#if>
                    <#if .vars["item" + i + "ChargeTypeName"]??>
                        <urn:ChargeType>
                            <urn:UniqueName>${.vars["item" + i + "ChargeTypeName"]}</urn:UniqueName>
                        </urn:ChargeType>
                    </#if>
                </urn:ChargeDetail>
            </#if>
            <#if .vars["item" + i + "CommodityCodeName"]??>
                <urn:CommodityCode>
                    <urn:UniqueName>${.vars["item" + i + "CommodityCodeName"]}</urn:UniqueName>
                </urn:CommodityCode>
            </#if>
            <#if .vars["item" + i + "HasDescription"]!false>
                <urn:Description>
                    <#if .vars["item" + i + "HasCommonCommodityCode"]!false>
                        <urn:CommonCommodityCode>
                            <urn:Domain>${.vars["item" + i + "CommonCommodityCodeDomain"]!}</urn:Domain>
                            <urn:UniqueName>${.vars["item" + i + "CommonCommodityCodeName"]!}</urn:UniqueName>
                        </urn:CommonCommodityCode>
                    </#if>
                    <#if .vars["item" + i + "UnitOfMeasureName"]??>
                        <urn:UnitOfMeasure>
                            <urn:UniqueName>${.vars["item" + i + "UnitOfMeasureName"]}</urn:UniqueName>
                        </urn:UnitOfMeasure>
                    </#if>
                </urn:Description>
            </#if>
            <#if .vars["item" + i + "IncoTermsCodeName"]??>
                <urn:IncoTermsCode>
                    <urn:UniqueName>${.vars["item" + i + "IncoTermsCodeName"]}</urn:UniqueName>
                </urn:IncoTermsCode>
            </#if>
            <#if .vars["item" + i + "IncoTermsDetail"]??>
                <urn:IncoTermsDetail>${.vars["item" + i + "IncoTermsDetail"]}</urn:IncoTermsDetail>
            </#if>
            <#if .vars["item" + i + "HasLineType"]!false>
                <urn:LineType>
                    <#if .vars["item" + i + "LineTypeCategory"]??>
                        <urn:Category>${.vars["item" + i + "LineTypeCategory"]}</urn:Category>
                    </#if>
                    <#if .vars["item" + i + "LineTypeName"]??>
                        <urn:UniqueName>${.vars["item" + i + "LineTypeName"]}</urn:UniqueName>
                    </#if>
                </urn:LineType>
            </#if>
            <#if .vars["item" + i + "HasNetAmount"]!false>
                <urn:NetAmount>
                    <urn:Amount>${.vars["item" + i + "NetAmount"]!}</urn:Amount>
                    <#if .vars["item" + i + "NetAmountCurrency"]??>
                        <urn:Currency>
                            <urn:UniqueName>${.vars["item" + i + "NetAmountCurrency"]}</urn:UniqueName>
                        </urn:Currency>
                    </#if>
                </urn:NetAmount>
            </#if>
            <urn:NumberInCollection>${.vars["item" + i + "NumberInCollection"]!}</urn:NumberInCollection>
            <#if .vars["item" + i + "OriginalSupplierAmount"]??>
                <urn:OriginalSupplierAmount>
                    <urn:Amount>${.vars["item" + i + "OriginalSupplierAmount"]}</urn:Amount>
                    <#if .vars["item" + i + "OriginalSupplierAmountCurrency"]??>
                        <urn:Currency>
                            <urn:UniqueName>${.vars["item" + i + "OriginalSupplierAmountCurrency"]}</urn:UniqueName>
                        </urn:Currency>
                    </#if>
                </urn:OriginalSupplierAmount>
            </#if>
            <urn:ParentLineNumber>${.vars["item" + i + "ParentLineNumber"]!}</urn:ParentLineNumber>
            <urn:Quantity>${.vars["item" + i + "Quantity"]!}</urn:Quantity>
            <#if .vars["item" + i + "SAPPlant"]??>
                <urn:SAPPlant>
                    <urn:UniqueName>${.vars["item" + i + "SAPPlant"]}</urn:UniqueName>
                </urn:SAPPlant>
            </#if>
            <#if .vars["item" + i + "SellerTaxRegistrationNumber"]??>
                <urn:SellerTaxRegistrationNumber>${.vars["item" + i + "SellerTaxRegistrationNumber"]}</urn:SellerTaxRegistrationNumber>
            </#if>
            <#if .vars["item" + i + "HasShipToAddress"]!false>
                <urn:ShipTo>
                    <urn:PostalAddress>
                        <#if .vars["item" + i + "ShipToCity"]??>
                            <urn:City>${.vars["item" + i + "ShipToCity"]}</urn:City>
                        </#if>
                        <#if .vars["item" + i + "ShipToCountry"]??>
                            <urn:Country>
                                <urn:UniqueName>${.vars["item" + i + "ShipToCountry"]}</urn:UniqueName>
                            </urn:Country>
                        </#if>
                        <#if .vars["item" + i + "ShipToLine1"]??>
                            <urn:Line1>${.vars["item" + i + "ShipToLine1"]}</urn:Line1>
                        </#if>
                        <#if .vars["item" + i + "ShipToLine2"]??>
                            <urn:Line2>${.vars["item" + i + "ShipToLine2"]}</urn:Line2>
                        </#if>
                        <#if .vars["item" + i + "ShipToLine3"]??>
                            <urn:Line3>${.vars["item" + i + "ShipToLine3"]}</urn:Line3>
                        </#if>
                        <#if .vars["item" + i + "ShipToZip"]??>
                            <urn:PostalCode>${.vars["item" + i + "ShipToZip"]}</urn:PostalCode>
                        </#if>
                        <#if .vars["item" + i + "ShipToState"]??>
                            <urn:State>${.vars["item" + i + "ShipToState"]}</urn:State>
                        </#if>
                    </urn:PostalAddress>
                </urn:ShipTo>
            </#if>
            <#if .vars["item" + i + "SupplierID"]??>
                <urn:SupplierID>${.vars["item" + i + "SupplierID"]}</urn:SupplierID>
            </#if>
            <#if .vars["item" + i + "HasSupplierAddress"]!false>
                <urn:SupplierLocation>
                    <urn:PostalAddress>
                        <#if .vars["item" + i + "SupplierCity"]??>
                            <urn:City>${.vars["item" + i + "SupplierCity"]}</urn:City>
                        </#if>
                        <#if .vars["item" + i + "SupplierCountry"]??>
                            <urn:Country>
                                <urn:UniqueName>${.vars["item" + i + "SupplierCountry"]}</urn:UniqueName>
                            </urn:Country>
                        </#if>
                        <#if .vars["item" + i + "SupplierLine1"]??>
                            <urn:Line1>${.vars["item" + i + "SupplierLine1"]}</urn:Line1>
                        </#if>
                        <#if .vars["item" + i + "SupplierLine2"]??>
                            <urn:Line2>${.vars["item" + i + "SupplierLine2"]}</urn:Line2>
                        </#if>
                        <#if .vars["item" + i + "SupplierLine3"]??>
                            <urn:Line3>${.vars["item" + i + "SupplierLine3"]}</urn:Line3>
                        </#if>
                        <#if .vars["item" + i + "SupplierZip"]??>
                            <urn:PostalCode>${.vars["item" + i + "SupplierZip"]}</urn:PostalCode>
                        </#if>
                        <#if .vars["item" + i + "SupplierState"]??>
                            <urn:State>${.vars["item" + i + "SupplierState"]}</urn:State>
                        </#if>
                    </urn:PostalAddress>
                </urn:SupplierLocation>
            </#if>
            <urn:TaxExclusion>${.vars["item" + i + "TaxExclusion"]!}</urn:TaxExclusion>
            <#if .vars["item" + i + "HasTaxItems"]!false>
                <urn:Taxes>
                    <#if .vars["item" + i + "NumberOfTaxItems"]??>
                        <#list 1.. .vars["item" + i + "NumberOfTaxItems"] as j>
                            <urn:item>
                                <#if .vars["item" + i + "TaxItem" + j + "FinalAmount"]??>
                                    <urn:FinalAmount>
                                        <urn:Amount>${.vars["item" + i + "TaxItem" + j + "FinalAmount"]}</urn:Amount>
                                        <urn:Currency>
                                            <urn:UniqueName>${.vars["item" + i + "TaxItem" + j + "FinalAmountCurrency"]}</urn:UniqueName>
                                        </urn:Currency>
                                    </urn:FinalAmount>
                                </#if>
                                <#if .vars["item" + i + "TaxItem" + j + "OriginalSupplierAmount"]??>
                                    <urn:OriginalSupplierAmount>
                                        <urn:Amount>${.vars["item" + i + "TaxItem" + j + "OriginalSupplierAmount"]}</urn:Amount>
                                        <urn:Currency>
                                            <urn:UniqueName>${.vars["item" + i + "TaxItem" + j + "OriginalSupplierAmountCurrency"]}</urn:UniqueName>
                                        </urn:Currency>
                                    </urn:OriginalSupplierAmount>
                                </#if>
                                <#if .vars["item" + i + "TaxItem" + j + "TaxType"]??>
                                    <urn:TaxType>
                                        <urn:UniqueName>${.vars["item" + i + "TaxItem" + j + "TaxType"]}</urn:UniqueName>
                                    </urn:TaxType>
                                </#if>
                                <#if .vars[ "item" + i + "TaxItem" + j + "IsBuyerPayableTax" ]??>
                                    <urn:IsBuyerPayableTax>${.vars["item" + i + "TaxItem" + j + "IsBuyerPayableTax"]}</urn:IsBuyerPayableTax>
                                </#if>
                            </urn:item>
                        </#list>
                    </#if>
                </urn:Taxes>
            </#if>
            <#if .vars["item" + i + "HasCustomFields"]!false>
                <urn:custom>
                    <#if .vars["item" + i + "CustomBoolean"]??>
                        <urn:CustomBoolean
                                name="${.vars["item" + i + "CustomBooleanName"]}">${.vars["item" + i + "CustomBoolean"]}</urn:CustomBoolean>
                    </#if>
                    <#if .vars["item" + i + "CustomDate"]??>
                        <urn:CustomDate
                                name="${.vars["item" + i + "CustomDateName"]}">${.vars["item" + i + "CustomDate"]}</urn:CustomDate>
                    </#if>
                    <#if .vars["item" + i + "CustomInteger"]??>
                        <urn:CustomInteger
                                name="${.vars["item" + i + "CustomIntegerName"]}">${.vars["item" + i + "CustomInteger"]}</urn:CustomInteger>
                    </#if>
                    <#if .vars["item" + i + "HasCustomMoney"]!false>
                        <urn:CustomMoney name="${.vars["item" + i + "CustomMoneyName"]}">
                            <#if .vars["item" + i + "CustomMoneyAmount"]??>
                                <urn:Amount>${.vars["item" + i + "CustomMoneyAmount"]}</urn:Amount>
                            </#if>
                            <#if .vars["item" + i + "CustomMoneyAmountInReportingCurrency"]??>
                                <urn:AmountInReportingCurrency>${.vars["item" + i + "CustomMoneyAmountInReportingCurrency"]}</urn:AmountInReportingCurrency>
                            </#if>
                            <#if .vars["item" + i + "CustomMoneyApproxAmount"]??>
                                <urn:ApproxAmountInBaseCurrency>${.vars["item" + i + "CustomMoneyApproxAmount"]}</urn:ApproxAmountInBaseCurrency>
                            </#if>
                            <#if .vars["item" + i + "CustomMoneyConversionDate"]??>
                                <urn:ConversionDate>${.vars["item" + i + "CustomMoneyConversionDate"]}</urn:ConversionDate>
                            </#if>
                            <#if .vars["item" + i + "CustomMoneyCurrency"]??>
                                <urn:Currency>
                                    <urn:UniqueName>${.vars["item" + i + "CustomMoneyCurrency"]}</urn:UniqueName>
                                </urn:Currency>
                            </#if>
                        </urn:CustomMoney>
                    </#if>
                    <#if .vars["item" + i + "CustomString"]??>
                        <urn:CustomString
                                name="${.vars["item" + i + "CustomStringName"]}">${.vars["item" + i + "CustomString"]}</urn:CustomString>
                    </#if>
                </urn:custom>
            </#if>
        </urn:item>
    </#list>
</#if>

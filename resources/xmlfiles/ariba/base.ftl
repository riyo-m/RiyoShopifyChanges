<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:urn="urn:Ariba:Buyer:TaxAPI">
    <#if hasSoapHeader!false>
        <soapenv:Header>
            <urn:Headers>
                <urn:variant>${headerVariant!}</urn:variant>
                <urn:partition>${headerPartition!}</urn:partition>
            </urn:Headers>
        </soapenv:Header>
    </#if>
    <soapenv:Body>
        <urn:TaxServiceExportRequest partition="${partition!}" variant="${variant!}">
            <urn:ProcureLineItemCollection_TaxServiceAPIRequest_Item>
                <urn:item>
                    <#if companyCode??>
                        <urn:CompanyCode>
                            <urn:UniqueName>${companyCode}</urn:UniqueName>
                        </urn:CompanyCode>
                    </#if>
                    <urn:DocumentDate>${documentDate!}</urn:DocumentDate>
                    <#if DocumentSubType ??>
                        <urn:DocumentSubType>${DocumentSubType}</urn:DocumentSubType>
                    </#if>
                    <urn:LineItems>

                        <#include "item.ftl">

                    </urn:LineItems>
                    <#if organizationName??>
                        <urn:OrganizationName>${organizationName}</urn:OrganizationName>
                    </#if>
                    <#if organizationUnit??>
                        <urn:OrganizationUnit>${organizationUnit}</urn:OrganizationUnit>
                    </#if>
                    <#if relatedDocument??>
                        <urn:RelatedDocument>${relatedDocument}</urn:RelatedDocument>
                    </#if>
                    <#if requestType??>
                        <urn:RequestType>${requestType}</urn:RequestType>
                    </#if>
                    <urn:Type>${type!}</urn:Type>
                    <#if uniqueName??>
                        <urn:UniqueName>${uniqueName}</urn:UniqueName>
                    </#if>
                    <#if isRetry??>
                        <urn:IsRetry>${isRetry}</urn:IsRetry>
                    </#if>
                    <#if hasCustomFields!false>
                        <urn:custom>
                            <#if customBoolean??>
                                <urn:CustomBoolean
                                        name="${customBooleanName!}">${customBoolean}</urn:CustomBoolean>
                            </#if>
                            <#if customDate??>
                                <urn:CustomDate
                                        name="${customDateName!}">${customDate}</urn:CustomDate>
                            </#if>
                            <#if customInteger??>
                                <urn:CustomInteger
                                        name="${customIntegerName!}">${customInteger}</urn:CustomInteger>
                            </#if>
                            <#if hasCustomMoney!false>
                                <urn:CustomMoney name="${customMoneyName!}">
                                    <#if customMoneyAmount??>
                                        <urn:Amount>${customMoneyAmount}</urn:Amount>
                                    </#if>
                                    <#if customMoneyAmountInReportingCurrency??>
                                        <urn:AmountInReportingCurrency>${customMoneyAmountInReportingCurrency}</urn:AmountInReportingCurrency>
                                    </#if>
                                    <#if customMoneyApproxAmount??>
                                        <urn:ApproxAmountInBaseCurrency>${customMoneyApproxAmount}</urn:ApproxAmountInBaseCurrency>
                                    </#if>
                                    <#if customMoneyConversionDate??>
                                        <urn:ConversionDate>${customMoneyConversionDate}</urn:ConversionDate>
                                    </#if>
                                    <#if customMoneyCurrency??>
                                        <urn:Currency>
                                            <urn:UniqueName>${customMoneyCurrency}</urn:UniqueName>
                                        </urn:Currency>
                                    </#if>
                                </urn:CustomMoney>
                            </#if>
                            <#if customString??>
                                <urn:CustomString
                                        name="${customStringName!}">${customString}</urn:CustomString>
                            </#if>
                        </urn:custom>
                    </#if>
                </urn:item>
            </urn:ProcureLineItemCollection_TaxServiceAPIRequest_Item>
        </urn:TaxServiceExportRequest>
    </soapenv:Body>
</soapenv:Envelope>
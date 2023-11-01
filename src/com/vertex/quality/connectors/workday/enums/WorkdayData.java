package com.vertex.quality.connectors.workday.enums;

/**
 * Data for Workday
 *
 * @author dpatel
 */
public enum WorkdayData {

    failedIntegrationForCustomerInvoice(" Customer Invoice Integration failed"),
    failedIntegrationForSupplierInvoice(" Supplier Invoice Integration Failed"),
    failedIntegrationForCustomerInvoiceAdj(" Customer Invoice adjustment Integration failed "),
    failedIntegrationForSupplierInvoiceAdj("Supplier Invoice Adjustment Integration Failed"),
    failedtaxCalcValidation("Actual and Expected Tax calculation doesn't match"),
    failedtaxCalcAdjValidation("Actual and Expected Tax calculation for Adjusted Invoice doesn't match"),
    vertexCloudTrustedID("9199090703839385"),
    vertexCloudTaxCalcURL("https://calccsconnect.vertexsmb.com/vertex-ws/services/CalculateTax80"),
    vertexOnDemandPremiseTrustedID("$TrustWorkday"),
    vertexOnDemandPremiseTaxCalcURL("https://oseries8-dev.vertexconnectors.com/vertex-ws/services/CalculateTax80"),
    xmlFileFullPath("C:\\upload\\Import_Customer_Invoice_100_5lines_Jenkins_input.xml"),
    batchQuoteIntegration("VTX-BatchQuoteCustomerInvoiceBP"),
    batchPostIntegration("VTX-BatchPostCustomerInvoiceBP"),
    vtxinvoiceLoadIntegration("VtxCustomerInvoiceLoad"),
    vtxCleanupSimulator("VTX-BatchQuoteCleanupSimulator"),
    vtxCustomerInvoiceIntegration("VTX-CustomerInvoiceBP"),
    vtxSupplierInvoiceIntegration("VTX-SupplierInvoiceBP"),
    vtxpreProcessIntegration("VTX-SupplierInvoicePreProcessBP"),
    vertexInvalidTaxCalcURLErrorMessage("ERROR-QUOTE-Invalid Request"),
    vertexInvalidSupplierInvoiceTaxCalcURLErrorMessage("ERROR-QUOTE-Invalid Request"),
    vertexInvalidTrustedIDErrorMessage("ERROR-QUOTE-The Trusted ID could not be resolved, please check your connector configuration. Note that Trusted IDs and Company Codes are case sensitive."),
    vertexInvalidSupplierInvoiceTrustedIDErrorMessage("ERROR-QUOTE-User login failed."),
    vertexInvalidTrustedID("vertexIncorrectID"),
    vertexInvalidTaxCalcURL("https://calccsconnect.vertexsmb.com/vertex-ws/services/Calcul"),
    vertexExtractedrequestXMLtxt("C:\\upload\\new.txt"),
    expectedREquestXML("C:\\upload\\Spectre_Supplier_Invoice_Expected_Vertex_Request.txt"),
    expectedUpdatedRequestXML("C:\\upload\\Spectre_Supplier_Invoice_Expected_Vertex_Request_updated.txt"),
    americanElectricPower("American Electric Power"),
    calculateSelfAssessedTax("Calculate Self-Assessed Tax"),
    enterTaxDue("Enter Tax Due to Supplier"),
    calcTaxDUe("Calculate Tax Due to Supplier"),
    bannersAndDisplay("Banners and Displays"),
    alcoholSwabsLong("Alcohol Swabs description is waaaaaay tooooooooo looooooooong"),
    alcoholSwabs("Alcohol Swabs"),
    publicity("publicity"),
    binderClips("Binder Clips"),
    alcoholWipePads("Alcohol Wipe Pads"),
    laptopPowerAdaptor("Laptop power adapter"),
    chairs("Chairs"),
    iPhone("iPhone"),
    hardDrive("External Hard Drive"),
    spectre("Spectre, Inc.");

    public String data;

    WorkdayData(String data )
    {
        this.data = data;
    }
}

package com.vertex.quality.connectors.taxlink.common;

/**
 * This class contains database related queries for TaxLink app
 *
 * @author mgaikwad
 */

public class TaxLinkDBQueries
{
	public static final String DB_QUERY_INVRULES =
		"EXEC VTX_BUMP_UP_SEQ_PRC 'VTX_SVC_SUBSCRIPTIONS_S', 'VTX_SVC_SUBSCRIPTIONS_T','SVC_SUBSCRIPTION_ID'\n" +
		"EXEC VTX_BUMP_UP_SEQ_PRC 'VTX_SVC_VERTEX_AUTH_S', 'VTX_SVC_VERTEX_AUTH_T','VTX_SVC_AUTH_ID'\n" +
		"EXEC VTX_BUMP_UP_SEQ_PRC 'VTX_BATCH_JOB_SCHEDULE_S', 'VTX_BATCH_JOB_SCHEDULE_T','BATCH_JOB_SCHEDULE_ID'\n" +
		"EXEC VTX_BUMP_UP_SEQ_PRC 'VTX_INV_ACCOUNTS_S','VTX_INV_ACCOUNTS_T','INV_ACCOUNTS_ID'\n" +
		"Declare @fusionInstanceId Numeric(19,0)\n" + "Declare @fusionInstanceName varchar(60)\n" +
		"Declare @fiCount Numeric (19,0) = 0\n" + "\n" + "Declare curFI CURSOR \n" + "for \n" +
		"SELECT fusion_instance_id, fusion_instance_name\n" + "from vtx_fusion_instances_t \n" +
		"where enabled_flag = 'Y'\n" + "and upper(fusion_instance_name) in (upper('ecog-dev3-us2'))\n" +
		"Declare @ucmDocUrl varchar(200) = 'https://ecog-dev3.fs.us2.oraclecloud.com/cs/idcplg?IdcService=GET_FILE'\n" +
		"\n" + "Declare @fusionUserName varchar(60)\n" + ", @fusionPassword varchar(60)\n" +
		", @ucmExportAccount varchar(60)\n" + ", @ucmExportSecurityGroup varchar(60)\n" +
		", @ucmImportAccount varchar(60)\n" + ", @ucmImportSecurityGroup varchar(60)\n" +
		"Declare @svcUrl varchar(240)\n" + "\n" + "Declare @vtxUserName varchar(30)\n" +
		", @vtxPassword varchar(60)\n" + ", @vtxTrustedId varchar(60)\n" +
		"Declare @bipInvScheduleParam varchar(60) = '0 0/15 * * * ?'\n" +
		"Declare @vtxInvConditionSetName VARCHAR(240) = UPPER('VTX_INV_ONLY')\n" +
		", @vtxInvConditionSetId NUMERIC(19,0)\n" + "\n" + "BEGIN TRY\n" + "  BEGIN TRANSACTION  \n" + "OPEN curFI \n" +
		"Fetch Next From curFI Into @fusionInstanceId, @fusionInstanceName     \n" +
		"While @@Fetch_Status = 0 Begin\n" + "\n" +
		"print 'Adding Inventory Service Subscriptions for Fusion Instance Name ' + @fusionInstanceName\n" +
		"set @fiCount = @fiCount + 1\n" + "\n" + "DELETE VTX_SVC_SUBSCRIPTIONS_T \n" +
		"  WHERE   VTX_SERVICE_ID in (5,6,7)\n" + "   AND FUSION_INSTANCE_ID = @fusionInstanceId\n" +
		"INSERT INTO VTX_SVC_SUBSCRIPTIONS_T\n" + "           (SVC_SUBSCRIPTION_ID\n" +
		"           ,FUSION_INSTANCE_ID\n" + "           ,VTX_SERVICE_ID\n" + "           ,START_DATE          \n" +
		"           ,ENABLED_FLAG\n" + "           )\n" + "     VALUES\n" +
		"           ( NEXT VALUE FOR VTX_SVC_SUBSCRIPTIONS_S\n" + "           ,@fusionInstanceId\n" +
		"           ,5\n" + "           ,CAST('01-01-2016' AS date)\n" + "           ,'Y'\n" + "           )\n" +
		"\t\t   \n" + "INSERT INTO VTX_SVC_SUBSCRIPTIONS_T\n" + "           (SVC_SUBSCRIPTION_ID\n" +
		"           ,FUSION_INSTANCE_ID\n" + "           ,VTX_SERVICE_ID\n" + "           ,START_DATE          \n" +
		"           ,ENABLED_FLAG\n" + "           )\n" + "     VALUES\n" +
		"           ( NEXT VALUE FOR VTX_SVC_SUBSCRIPTIONS_S\n" + "           ,@fusionInstanceId\n" +
		"           ,6\n" + "           ,CAST('01-01-2016' AS date)\n" + "           ,'Y'\n" + "           )\n" +
		"INSERT INTO VTX_SVC_SUBSCRIPTIONS_T\n" + "           (SVC_SUBSCRIPTION_ID\n" +
		"           ,FUSION_INSTANCE_ID\n" + "           ,VTX_SERVICE_ID\n" + "           ,START_DATE          \n" +
		"           ,ENABLED_FLAG\n" + "           )\n" + "     VALUES\n" +
		"           ( NEXT VALUE FOR VTX_SVC_SUBSCRIPTIONS_S\n" + "           ,@fusionInstanceId\n" +
		"           ,7\n" + "           ,CAST('01-01-2016' AS date)\n" + "           ,'Y'\n" + "           )\t\n" +
		"\n" + "\n" +
		"print 'Adding Fusion Attributes for Inventory Service Subscriptions for Fusion Instance Name ' + @fusionInstanceName\n" +
		"\n" + "DELETE VTX_SVC_FUSION_AUTH_ATTR_T \n" +
		"  WHERE   SVC_SUBSCRIPTION_ID IN ( SELECT SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (5,6,7) \n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId)  \n" +
		"   AND FUSION_INSTANCE_ID = @fusionInstanceId\n" + "\n" +
		"SELECT top(1) @fusionUserName =  [FUSION_USER_NAME]\n" + "           ,@fusionPassword = [FUSION_PASSWORD]\n" +
		"           ,@ucmExportAccount  = [UCM_EXPORT_ACCOUNT]\n" +
		"           ,@ucmExportSecurityGroup = [UCM_EXPORT_SECURITY_GROUP]\n" +
		"           ,@ucmImportAccount = [UCM_IMPORT_ACCOUNT]\n" +
		"           ,@ucmImportSecurityGroup = [UCM_IMPORT_SECURITY_GROUP]\n" + "from VTX_SVC_FUSION_AUTH_ATTR_T\n" +
		"WHERE FUSION_INSTANCE_ID = @fusionInstanceId\n" + "\n" + "INSERT INTO [VTX_SVC_FUSION_AUTH_ATTR_T]\n" +
		"           ([SVC_SUBSCRIPTION_ID]\n" + "           ,[FUSION_INSTANCE_ID]\n" +
		"           ,[FUSION_USER_NAME]\n" + "           ,[FUSION_PASSWORD]\n" + "           ,[UCM_EXPORT_ACCOUNT]\n" +
		"           ,[UCM_EXPORT_SECURITY_GROUP]\n" + "           ,[UCM_IMPORT_ACCOUNT]\n" +
		"           ,[UCM_IMPORT_SECURITY_GROUP])\n" + "     VALUES\n" +
		"           ( ( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (5) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           ,@fusionInstanceId\n" +
		"           ,@fusionUserName\n" + "           ,@fusionPassword\n" + "           ,@ucmExportAccount\n" +
		"           ,@ucmExportSecurityGroup\n" + "           ,@ucmImportAccount\n" +
		"           ,@ucmImportSecurityGroup)\n" + "\n" + "\n" + "INSERT INTO [VTX_SVC_FUSION_AUTH_ATTR_T]\n" +
		"           ([SVC_SUBSCRIPTION_ID]\n" + "           ,[FUSION_INSTANCE_ID]\n" +
		"           ,[FUSION_USER_NAME]\n" + "           ,[FUSION_PASSWORD]\n" + "           ,[UCM_EXPORT_ACCOUNT]\n" +
		"           ,[UCM_EXPORT_SECURITY_GROUP]\n" + "           ,[UCM_IMPORT_ACCOUNT]\n" +
		"           ,[UCM_IMPORT_SECURITY_GROUP])\n" + "     VALUES\n" +
		"           ( ( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (6) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           , @fusionInstanceId\n" +
		"           ,@fusionUserName\n" + "           ,@fusionPassword\n" + "           ,'fin/generalLedger/export'\n" +
		"           ,'FAFusionImportExport'\n" + "           ,'fin/generalLedger/import'\n" +
		"           ,'FAFusionImportExport')\n" + "\n" + "\t\t   \n" + "INSERT INTO [VTX_SVC_FUSION_AUTH_ATTR_T]\n" +
		"           ([SVC_SUBSCRIPTION_ID]\n" + "           ,[FUSION_INSTANCE_ID]\n" +
		"           ,[FUSION_USER_NAME]\n" + "           ,[FUSION_PASSWORD]\n" + "           ,[UCM_EXPORT_ACCOUNT]\n" +
		"           ,[UCM_EXPORT_SECURITY_GROUP]\n" + "           ,[UCM_IMPORT_ACCOUNT]\n" +
		"           ,[UCM_IMPORT_SECURITY_GROUP])\n" + "     VALUES\n" +
		"           ( ( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (7) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           ,@fusionInstanceId\n" +
		"           ,@fusionUserName\n" + "           ,@fusionPassword\n" +
		"           ,'prj/projectManagement/export'\n" + "           ,'FAFusionImportExport'\n" +
		"           ,'prj/projectManagement/import'\n" + "           ,'FAFusionImportExport')\n" + "\t\t   \n" +
		"print 'Adding Vertex Attributes for Inventory Service Subscriptions for Fusion Instance Name ' + @fusionInstanceName\n" +
		"\n" + "SELECT top(1) @svcUrl = VTX_SVC_ENDPOINT\n" + "FROM VTX_SVC_VERTEX_ATTR_T\n" +
		"WHERE FUSION_INSTANCE_ID = @fusionInstanceId\n" + "\n" + "DELETE VTX_SVC_VERTEX_ATTR_T \n" +
		"  WHERE   SVC_SUBSCRIPTION_ID IN ( SELECT SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (5,6,7) \n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId)  \n" +
		"   AND FUSION_INSTANCE_ID = @fusionInstanceId\n" + "   \n" + "INSERT INTO [VTX_SVC_VERTEX_ATTR_T]\n" +
		"           ([SVC_SUBSCRIPTION_ID]\n" + "           ,[FUSION_INSTANCE_ID]\n" +
		"           ,[VTX_SVC_ENDPOINT]\n" + "           ,[REQ_RETENTION_PERIOD_DAYS]\n" +
		"           ,[RESP_RETENTION_PERIOD_DAYS]\n" + "           )\n" + "     VALUES\n" +
		"           ( ( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (5) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           , @fusionInstanceId\n" +
		"           , @svcUrl\n" + "           ,30\n" + "           ,30\n" + "           )\n" +
		"INSERT INTO [VTX_SVC_VERTEX_ATTR_T]\n" + "           ([SVC_SUBSCRIPTION_ID]\n" +
		"           ,[FUSION_INSTANCE_ID]\n" + "           ,[VTX_SVC_ENDPOINT]\n" +
		"           ,[REQ_RETENTION_PERIOD_DAYS]\n" + "           ,[RESP_RETENTION_PERIOD_DAYS]\n" + "           )\n" +
		"     VALUES\n" + "           ( ( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (6) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           , @fusionInstanceId\n" +
		"           , @svcUrl\n" + "           ,30\n" + "           ,30\n" + "           )\n" + "\t\t   \n" +
		"INSERT INTO [VTX_SVC_VERTEX_ATTR_T]\n" + "           ([SVC_SUBSCRIPTION_ID]\n" +
		"           ,[FUSION_INSTANCE_ID]\n" + "           ,[VTX_SVC_ENDPOINT]\n" +
		"           ,[REQ_RETENTION_PERIOD_DAYS]\n" + "           ,[RESP_RETENTION_PERIOD_DAYS]\n" + "           )\n" +
		"     VALUES\n" + "           ( ( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (7) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           , @fusionInstanceId\n" +
		"           , @svcUrl\n" + "           ,30\n" + "           ,30\n" + "           )\n" + "\n" +
		"print 'Adding Vertex Auth Attributes for Inventory Service Subscriptions for Fusion Instance Name ' + @fusionInstanceName\n" +
		"\t\t   \n" + "DELETE VTX_SVC_VERTEX_AUTH_T \n" +
		"  WHERE   SVC_SUBSCRIPTION_ID IN ( SELECT SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (5,6,7) \n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId)  \n" +
		"   AND FUSION_INSTANCE_ID = @fusionInstanceId\n" + "   \n" +
		"SELECT TOP(1) @vtxUserName = VTX_USER_NAME, @vtxPassword = VTX_PASSWORD, @vtxTrustedId = VTX_TRUSTED_ID\n" +
		"FROM   VTX_SVC_VERTEX_AUTH_T\n" + "WHERE FUSION_INSTANCE_ID = @fusionInstanceId\n" +
		"   INSERT INTO [VTX_SVC_VERTEX_AUTH_T]\n" + "           ([VTX_SVC_AUTH_ID]\n" +
		"           ,[SVC_SUBSCRIPTION_ID]\n" + "           ,[BUSINESS_UNIT_ID]\n" +
		"           ,[DESTINATION_COUNTRY_CODE]\n" + "           ,[PHY_ORIGIN_COUNTRY_CODE]\n" +
		"           ,[FUSION_INSTANCE_ID]\n" + "           ,[VTX_USER_NAME]\n" + "           ,[VTX_PASSWORD]\n" +
		"           ,[VTX_TRUSTED_ID])\n" + "     VALUES\n" + "           (NEXT VALUE FOR VTX_SVC_VERTEX_AUTH_S \n" +
		"           ,( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (5) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           ,NULL\n" +
		"           ,NULL\n" + "           ,NULL\n" + "           ,@fusionInstanceId\n" + "           ,@vtxUserName\n" +
		"           ,@vtxPassword\n" + "           ,@vtxTrustedId)\n" + "\n" + "INSERT INTO [VTX_SVC_VERTEX_AUTH_T]\n" +
		"           ([VTX_SVC_AUTH_ID]\n" + "           ,[SVC_SUBSCRIPTION_ID]\n" + "           ,[BUSINESS_UNIT_ID]\n" +
		"           ,[DESTINATION_COUNTRY_CODE]\n" + "           ,[PHY_ORIGIN_COUNTRY_CODE]\n" +
		"           ,[FUSION_INSTANCE_ID]\n" + "           ,[VTX_USER_NAME]\n" + "           ,[VTX_PASSWORD]\n" +
		"           ,[VTX_TRUSTED_ID])\n" + "     VALUES\n" + "           (NEXT VALUE FOR VTX_SVC_VERTEX_AUTH_S \n" +
		"           ,( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (6) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           ,NULL\n" +
		"           ,NULL\n" + "           ,NULL\n" + "           ,@fusionInstanceId\n" + "           ,@vtxUserName\n" +
		"           ,@vtxPassword\n" + "           ,@vtxTrustedId)\n" + "\n" + "\t\t   \n" +
		"INSERT INTO [VTX_SVC_VERTEX_AUTH_T]\n" + "           ([VTX_SVC_AUTH_ID]\n" +
		"           ,[SVC_SUBSCRIPTION_ID]\n" + "           ,[BUSINESS_UNIT_ID]\n" +
		"           ,[DESTINATION_COUNTRY_CODE]\n" + "           ,[PHY_ORIGIN_COUNTRY_CODE]\n" +
		"           ,[FUSION_INSTANCE_ID]\n" + "           ,[VTX_USER_NAME]\n" + "           ,[VTX_PASSWORD]\n" +
		"           ,[VTX_TRUSTED_ID])\n" + "     VALUES\n" + "           (NEXT VALUE FOR VTX_SVC_VERTEX_AUTH_S \n" +
		"           ,( SELECT  SVC_SUBSCRIPTION_ID FROM VTX_SVC_SUBSCRIPTIONS_T SS \n" +
		"\t\t\t\t\t\t\t\t   WHERE SS.VTX_SERVICE_ID IN (7) \n" + "\t\t\t\t\t\t\t\t   AND SS.ENABLED_FLAG = 'Y'\n" +
		"\t\t\t\t\t\t\t\t   AND  SS.FUSION_INSTANCE_ID = @fusionInstanceId) \n" + "           ,NULL\n" +
		"           ,NULL\n" + "           ,NULL\n" + "           ,@fusionInstanceId\n" + "           ,@vtxUserName\n" +
		"           ,@vtxPassword\n" + "           ,@vtxTrustedId)\n" + "\t\t   \n" +
		"print 'Adding batch schedule for Fusion Instance Name ' + @fusionInstanceName\n" + "\n" +
		"DELETE VTX_BATCH_JOB_SCHEDULE_T \n" + "  WHERE   BATCH_JOB_NAME = 'pollDocumentIdsJob'\n" +
		"  AND ( BATCH_JOB_PARAM LIKE '%applicationShortName=XXINV,docType=Batch-InventoryTrxExtract,subscriptionService=INV_TAX_CALC')\n" +
		"   AND FUSION_INSTANCE_ID = @fusionInstanceId\n" + "\n" + "INSERT [VTX_BATCH_JOB_SCHEDULE_T] \n" +
		"([BATCH_JOB_SCHEDULE_ID], \n" + " [FUSION_INSTANCE_ID], \n" + " [SVC_SUBSCRIPTION_ID], \n" +
		" [BATCH_JOB_NAME], \n" + " [BATCH_JOB_CRON], \n" + " [BATCH_JOB_PARAM], \n" + " [ENABLED_FLAG], \n" +
		" [CREATED_BY], \n" + " [CREATION_DATE], \n" + " [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE],\n" +
		" [JOB_DESCRIPTION],\n" + " [USER_BATCH_JOB_NAME],\n" + " [BATCH_JOB_TYPE]) \n" + "VALUES \n" +
		"(next value for VTX_BATCH_JOB_SCHEDULE_S, \n" + " @fusionInstanceId, \n" + " null, \n" +
		" N'pollDocumentIdsJob', \n" + " @bipInvScheduleParam  , \n" +
		" N'fusionInstanceName='+@fusionInstanceName+',applicationShortName=XXINV,docType=Batch-InventoryTrxExtract,subscriptionService=INV_TAX_CALC', \n" +
		" N'Y', \n" + " N'VERTEX', \n" + " SYSDATETIME(), \n" + " N'VERTEX', \n" + " SYSDATETIME(),\n" +
		" N'Inventory Polling Job runs every 15 mins',\n" + " N'pollDocumentIdsJob',\n" + " N'POLL')\n" + "\n" +
		"print 'Adding notification event for Fusion Instance Name ' + @fusionInstanceName\n" + "\n" +
		"DELETE VTX_NOTIF_SVC_EVENT_T \n" + "WHERE  NOTIF_SVC_CODE   IN ( 'BIP_UCM')\n" +
		"AND EVENT_CODE IN ('INV_TAXCALC_STATUS_REPORT')\n" + "AND FUSION_INSTANCE_ID = @fusionInstanceId\n" + "\n" +
		"INSERT VTX_NOTIF_SVC_EVENT_T (NOTIF_SVC_CODE, EVENT_CODE, FUSION_INSTANCE_ID,USER_JOB_NAME, UCM_DOC_URL) \n" +
		"VALUES (N'BIP_UCM', N'INV_TAXCALC_STATUS_REPORT', @fusionInstanceId,  N'INV_TAXCALC_STATUS_REPORT', @ucmDocUrl) \n" +
		"\n" + "\n" + "print 'Adding notification parameters for Fusion Instance Name ' + @fusionInstanceName\n" +
		"\n" + "DELETE VTX_NOTIF_SVC_PARAM_T \n" + "WHERE  NOTIF_SVC_CODE   IN ( 'BIP_EMAIL','BIP_UCM')\n" +
		"AND FUSION_INSTANCE_ID = @fusionInstanceId\n" + "\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_EMAIL', @fusionInstanceId, N'p_bcc_email', N'SETUP', N'OracleERPCloud@vertexinc.com', N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_EMAIL', @fusionInstanceId, N'p_body', N'SETUP', N'Unable to connect to UCM server', N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_EMAIL', @fusionInstanceId, N'p_cc_email', N'SETUP', N'OracleERPCloud@vertexinc.com', N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" + "\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_EMAIL', @fusionInstanceId, N'p_from_email', N'SETUP', N'noreply@oraclecloud.com', N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" + "\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_EMAIL', @fusionInstanceId, N'p_key', N'SETUP', N'ERP', N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_EMAIL', @fusionInstanceId, N'p_subject', N'SETUP', N'Vertex Taxlink Error', N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_EMAIL', @fusionInstanceId, N'p_to_email', N'SETUP', N'OracleERPCloud@vertexinc.com', N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" + "\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_UCM', @fusionInstanceId, N'p_author', N'SETUP', N'Vertex', N'VERTEX',\n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_UCM', @fusionInstanceId, N'p_content_id', N'RUNTIME', NULL, N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_UCM', @fusionInstanceId, N'p_doc_title', N'RUNTIME', NULL, N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" + "\n" + "\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_UCM', @fusionInstanceId, N'p_file_comment', N'RUNTIME', NULL, N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_UCM', @fusionInstanceId, N'p_file_name', N'RUNTIME', NULL, N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" + "\n" +
		"INSERT VTX_NOTIF_SVC_PARAM_T (NOTIF_SVC_CODE, FUSION_INSTANCE_ID, PARAM_NAME, PARAM_TYPE, PARAM_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE) \n" +
		"VALUES (N'BIP_UCM', @fusionInstanceId, N'p_key', N'SETUP', N'ERP', N'VERTEX', \n" +
		"SYSDATETIME(), N'VERTEX', SYSDATETIME())\n" + "\n" +
		"print 'Adding Inv Accounts for Fusion Instance Name ' + @fusionInstanceName\n" +
		"DELETE from VTX_INV_ACCOUNTS_T\n" + "WHERE  FUSION_INSTANCE_ID = @fusionInstanceId\n" +
		"INSERT INTO VTX_INV_ACCOUNTS_T (INV_ACCOUNTS_ID,FUSION_INSTANCE_ID,BUSINESS_UNIT_ID,MAIN_DIVISION,COUNTRY_CODE,CODE_COMBINATION_ID,SEGMENT1,SEGMENT2,SEGMENT3,SEGMENT4,SEGMENT5,SEGMENT6,SEGMENT7,SEGMENT8,SEGMENT9,SEGMENT10,CONCAT_SEGS,START_DATE,END_DATE,ENABLED_FLAG) VALUES ( NEXT VALUE FOR VTX_INV_ACCOUNTS_S  , @fusionInstanceId ,300000005503393,NULL,NULL,300000004223436,'3211','00','13070','000','0000','0000',NULL,NULL, NULL, NULL, '3211-00-13070-000-0000-0000','01-01-2018',NULL,'Y');\n" +
		"INSERT INTO VTX_INV_ACCOUNTS_T (INV_ACCOUNTS_ID,FUSION_INSTANCE_ID,BUSINESS_UNIT_ID,MAIN_DIVISION,COUNTRY_CODE,CODE_COMBINATION_ID,SEGMENT1,SEGMENT2,SEGMENT3,SEGMENT4,SEGMENT5,SEGMENT6,SEGMENT7,SEGMENT8,SEGMENT9,SEGMENT10,CONCAT_SEGS,START_DATE,END_DATE,ENABLED_FLAG) VALUES ( NEXT VALUE FOR VTX_INV_ACCOUNTS_S  , @fusionInstanceId ,NULL,NULL,NULL,14029,'3211','00','13070','000','0000','0000',NULL,NULL, NULL, NULL, '3211-00-13070-000-0000-0000','01-01-2018',NULL,'Y');\n" +
		"\n" + "\n" + "print 'Adding profile option for Fusion Instance Name ' + @fusionInstanceName\n" +
		"DELETE VTX_PROFILE_OPTION_VALUES_T\n" + "WHERE PROFILE_OPTION_ID IN \n" +
		"(SELECT PO.PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T  PO\n" +
		"         WHERE PO.FUSION_INSTANCE_ID = 0 \n" +
		"         AND PO.PROFILE_OPTION_NAME in ('VTX_INV_JOURNAL_SOURCE', 'VTX_INV_PA_TRX_SOURCE', 'VTX_INV_JOURNAL_TYPE',\n" +
		"\t\t\t\t\t\t\t\t\t\t'VTX_GL_ACCOUNT_DELIMITER',\n" +
		"\t\t\t\t\t\t\t\t\t\t'VTX_INV_ADDL_EXTRACT_FLAG', 'VTX_INV_ADDL_EXT_TRX_COUNT', \n" +
		"\t\t\t\t\t\t\t\t\t\t'VTX_INV_ADDL_EXT_IGNORE_ERR', 'VTX_INV_ADDL_EXT_ALL_TRX') )\n" +
		"AND FUSION_INSTANCE_ID = @fusionInstanceId\n" + "\n" + " INSERT  INTO [VTX_PROFILE_OPTION_VALUES_T] \n" +
		"([PROFILE_OPTION_ID], \n" + " [FUSION_INSTANCE_ID], \n" + " [LEVEL_ID], \n" + " [LEVEL_VALUE], \n" +
		" [PROFILE_OPTION_VALUE], \n" + " [START_DATE], \n" + " [END_DATE], \n" + " [ENABLED_FLAG], \n" +
		" [CREATED_BY], \n" + " [CREATION_DATE], \n" + " [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE]) \n" +
		"VALUES \n" +
		"((SELECT PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T WHERE FUSION_INSTANCE_ID = 0 AND PROFILE_OPTION_NAME = 'VTX_INV_JOURNAL_SOURCE') , \n" +
		" @fusionInstanceId , \n" + " CAST(10000 AS Numeric(18, 0)), \n" + " @fusionInstanceId , \n" +
		" N'Vertex', \n" + " CAST(N'2000-01-01' AS Date), \n" + " NULL, \n" + " N'Y', \n" + " N'VERTEX', \n" +
		" SYSDATETIME(), \n" + " N'VERTEX', \n" + " SYSDATETIME())\n" + " \n" +
		"INSERT  INTO [VTX_PROFILE_OPTION_VALUES_T] \n" + "([PROFILE_OPTION_ID], \n" + " [FUSION_INSTANCE_ID], \n" +
		" [LEVEL_ID], \n" + " [LEVEL_VALUE], \n" + " [PROFILE_OPTION_VALUE], \n" + " [START_DATE], \n" +
		" [END_DATE], \n" + " [ENABLED_FLAG], \n" + " [CREATED_BY], \n" + " [CREATION_DATE], \n" +
		" [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE]) \n" + "VALUES \n" +
		"((SELECT PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T WHERE FUSION_INSTANCE_ID = 0 AND PROFILE_OPTION_NAME = 'VTX_INV_JOURNAL_TYPE') , \n" +
		" @fusionInstanceId , \n" + " CAST(10000 AS Numeric(18, 0)), \n" + " @fusionInstanceId , \n" +
		" N'Vertex', \n" + " CAST(N'2000-01-01' AS Date), \n" + " NULL, \n" + " N'Y', \n" + " N'VERTEX', \n" +
		" SYSDATETIME(), \n" + " N'VERTEX', \n" + " SYSDATETIME())\n" + " \n" + "\n" +
		"INSERT  INTO [VTX_PROFILE_OPTION_VALUES_T] \n" + "([PROFILE_OPTION_ID], \n" + " [FUSION_INSTANCE_ID], \n" +
		" [LEVEL_ID], \n" + " [LEVEL_VALUE], \n" + " [PROFILE_OPTION_VALUE], \n" + " [START_DATE], \n" +
		" [END_DATE], \n" + " [ENABLED_FLAG], \n" + " [CREATED_BY], \n" + " [CREATION_DATE], \n" +
		" [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE]) \n" + "VALUES \n" +
		"((SELECT PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T WHERE FUSION_INSTANCE_ID = 0 AND PROFILE_OPTION_NAME = 'VTX_INV_PA_TRX_SOURCE') , \n" +
		" @fusionInstanceId , \n" + " CAST(10000 AS Numeric(18, 0)), \n" + " @fusionInstanceId , \n" +
		" N'VTX Inventory', \n" + " CAST(N'2000-01-01' AS Date), \n" + " NULL, \n" + " N'Y', \n" + " N'VERTEX', \n" +
		" SYSDATETIME(), \n" + " N'VERTEX', \n" + " SYSDATETIME())\n" + "\n" + "\n" + " \n" +
		" INSERT  INTO [VTX_PROFILE_OPTION_VALUES_T] \n" + "([PROFILE_OPTION_ID], \n" + " [FUSION_INSTANCE_ID], \n" +
		" [LEVEL_ID], \n" + " [LEVEL_VALUE], \n" + " [PROFILE_OPTION_VALUE], \n" + " [START_DATE], \n" +
		" [END_DATE], \n" + " [ENABLED_FLAG], \n" + " [CREATED_BY], \n" + " [CREATION_DATE], \n" +
		" [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE]) \n" + "VALUES \n" +
		"((SELECT PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T WHERE FUSION_INSTANCE_ID = 0 AND PROFILE_OPTION_NAME = 'VTX_GL_ACCOUNT_DELIMITER') , \n" +
		" @fusionInstanceId , \n" + " CAST(10000 AS Numeric(18, 0)), \n" + " @fusionInstanceId , \n" + " N'-', \n" +
		" CAST(N'2000-01-01' AS Date), \n" + " NULL, \n" + " N'Y', \n" + " N'VERTEX', \n" + " SYSDATETIME(), \n" +
		" N'VERTEX', \n" + " SYSDATETIME())\n" + " \n" + " \n" + " INSERT [VTX_PROFILE_OPTION_VALUES_T] \n" +
		"([PROFILE_OPTION_ID], \n" + " [FUSION_INSTANCE_ID], \n" + " [LEVEL_ID], \n" + " [LEVEL_VALUE], \n" +
		" [PROFILE_OPTION_VALUE], \n" + " [START_DATE], \n" + " [END_DATE], \n" + " [ENABLED_FLAG], \n" +
		" [CREATED_BY], \n" + " [CREATION_DATE], \n" + " [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE]) \n" +
		"VALUES \n" +
		"((SELECT PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T WHERE FUSION_INSTANCE_ID = 0 AND PROFILE_OPTION_NAME = 'VTX_INV_ADDL_EXTRACT_FLAG') , \n" +
		" @fusionInstanceId, \n" + " CAST(10000 AS Numeric(18, 0)), \n" + " @fusionInstanceId, \n" + " N'N', \n" +
		" CAST(N'2000-01-01' AS Date), \n" + " NULL, \n" + " N'Y', \n" + " N'VERTEX', \n" + " SYSDATETIME(), \n" +
		" N'VERTEX', \n" + " SYSDATETIME())\n" + " \n" + " INSERT [VTX_PROFILE_OPTION_VALUES_T] \n" +
		"([PROFILE_OPTION_ID], \n" + " [FUSION_INSTANCE_ID], \n" + " [LEVEL_ID], \n" + " [LEVEL_VALUE], \n" +
		" [PROFILE_OPTION_VALUE], \n" + " [START_DATE], \n" + " [END_DATE], \n" + " [ENABLED_FLAG], \n" +
		" [CREATED_BY], \n" + " [CREATION_DATE], \n" + " [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE]) \n" +
		"VALUES \n" +
		"((SELECT PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T WHERE FUSION_INSTANCE_ID = 0 AND PROFILE_OPTION_NAME = 'VTX_INV_ADDL_EXT_ALL_TRX') , \n" +
		" @fusionInstanceId, \n" + " CAST(10000 AS Numeric(18, 0)), \n" + " @fusionInstanceId, \n" + " N'N', \n" +
		" CAST(N'2000-01-01' AS Date), \n" + " NULL, \n" + " N'Y', \n" + " N'VERTEX', \n" + " SYSDATETIME(), \n" +
		" N'VERTEX', \n" + " SYSDATETIME())\n" + "\n" + "INSERT [VTX_PROFILE_OPTION_VALUES_T] \n" +
		"([PROFILE_OPTION_ID], \n" + " [FUSION_INSTANCE_ID], \n" + " [LEVEL_ID], \n" + " [LEVEL_VALUE], \n" +
		" [PROFILE_OPTION_VALUE], \n" + " [START_DATE], \n" + " [END_DATE], \n" + " [ENABLED_FLAG], \n" +
		" [CREATED_BY], \n" + " [CREATION_DATE], \n" + " [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE]) \n" +
		"VALUES \n" +
		"((SELECT PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T WHERE FUSION_INSTANCE_ID = 0 AND PROFILE_OPTION_NAME = 'VTX_INV_ADDL_EXT_TRX_COUNT') , \n" +
		" @fusionInstanceId, \n" + " CAST(10000 AS Numeric(18, 0)), \n" + " @fusionInstanceId, \n" + " N'0', \n" +
		" CAST(N'2000-01-01' AS Date), \n" + " NULL, \n" + " N'Y', \n" + " N'VERTEX', \n" + " SYSDATETIME(), \n" +
		" N'VERTEX', \n" + " SYSDATETIME())\n" + "\n" + "INSERT [VTX_PROFILE_OPTION_VALUES_T] \n" +
		"([PROFILE_OPTION_ID], \n" + " [FUSION_INSTANCE_ID], \n" + " [LEVEL_ID], \n" + " [LEVEL_VALUE], \n" +
		" [PROFILE_OPTION_VALUE], \n" + " [START_DATE], \n" + " [END_DATE], \n" + " [ENABLED_FLAG], \n" +
		" [CREATED_BY], \n" + " [CREATION_DATE], \n" + " [LAST_UPDATED_BY], \n" + " [LAST_UPDATE_DATE]) \n" +
		"VALUES \n" +
		"((SELECT PROFILE_OPTION_ID FROM VTX_PROFILE_OPTIONS_T WHERE FUSION_INSTANCE_ID = 0 AND PROFILE_OPTION_NAME = 'VTX_INV_ADDL_EXT_IGNORE_ERR') , \n" +
		" @fusionInstanceId, \n" + " CAST(10000 AS Numeric(18, 0)), \n" + " @fusionInstanceId, \n" + " N'Y', \n" +
		" CAST(N'2000-01-01' AS Date), \n" + " NULL, \n" + " N'Y', \n" + " N'VERTEX', \n" + " SYSDATETIME(), \n" +
		" N'VERTEX', \n" + " SYSDATETIME())\n" + "\n" +
		"Fetch Next From curFI Into @fusionInstanceId, @fusionInstanceName\n" + "     \n" + "End -- End of Fetch\n" +
		"\n" + "Close curFI\n" + "Deallocate curFI\n" + "\n" + "COMMIT TRANSACTION\n" +
		"print 'Added Inventory Service Subscriptions for Fusion Instances COUNT  ' + cast (@fiCount as varchar(30))\n" +
		"  END TRY\n" + "   BEGIN CATCH\n" + "      IF @@trancount > 0 ROLLBACK TRANSACTION\n" + "\t  Close curFI\n" +
		"      Deallocate curFI\n" + "      DECLARE @msg nvarchar(2048) = error_message()  \n" +
		"      RAISERROR (@msg, 16, 1)     \n" + "  END CATCH";
	public static final String DB_QUERY_INSTANCEID
		= "Select FUSION_INSTANCE_ID from VTX_FUSION_INSTANCES_T where FUSION_INSTANCE_NAME='ecog-dev3-us2'";
}


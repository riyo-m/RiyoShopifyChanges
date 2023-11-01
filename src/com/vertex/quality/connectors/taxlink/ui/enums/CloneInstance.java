package com.vertex.quality.connectors.taxlink.ui.enums;

/**
 * This class contains enums for Clone Instance page
 */
public enum CloneInstance
{

	INSTANCE_DETAILS("ecog-dev3-us2", "test2", "Note:",
		"The Target Instance will be locked and unavailable until clone is completed.",
		"Configuration setups from Target Instance will be cleared out and transaction data will be deleted.",
		"Do you want to Continue?");

	public String source_instanceName;
	public String target_instanceName;
	public String cloneConfirm_msg1;
	public String cloneConfirm_msg2;
	public String cloneConfirm_msg3;
	public String cloneConfirm_msg4;

	CloneInstance( String source_instanceName, String target_instanceName, String cloneConfirm_msg1,
		String cloneConfirm_msg2, String cloneConfirm_msg3, String cloneConfirm_msg4 )
	{
		this.source_instanceName = source_instanceName;
		this.target_instanceName = target_instanceName;
		this.cloneConfirm_msg1 = cloneConfirm_msg1;
		this.cloneConfirm_msg2 = cloneConfirm_msg2;
		this.cloneConfirm_msg3 = cloneConfirm_msg3;
		this.cloneConfirm_msg4 = cloneConfirm_msg4;
	}
}

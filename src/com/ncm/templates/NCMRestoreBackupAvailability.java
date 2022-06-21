//$Id$
package com.ncm.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class NCMRestoreBackupAvailability {
	
	public static final String BACKUP_RUNNING_CONFIG = "Backup Running Configuration";
	public static final String BACKUP_STARTUP_CONFIG = "Backup Startup Configuration";
	public static final String RESTORE_RUNNING_CONFIG = "Upload Running Configuration";
	public static final String RESTORE_STARTUP_CONFIG = "Upload Startup Configuration";
	
	public static final Map<String, String> BACKUP_OPERATIONS_MAP = new HashMap<String, String>() {{
		put(BACKUP_RUNNING_CONFIG,"running_config");
		put(BACKUP_STARTUP_CONFIG,"startup_config");
	}};
	
	public static final Map<String, String> RESTORE_OPERATIONS_MAP = new HashMap<String, String>() {{
		put(RESTORE_RUNNING_CONFIG,"running_config");
		put(RESTORE_STARTUP_CONFIG,"startup_config");
	}};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File ncmTemplateFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Templates/ncm-device-templates.json");
			FileInputStream fis = new FileInputStream(ncmTemplateFile);
			byte[] readByte = new byte[(int) ncmTemplateFile.length()];
			fis.read(readByte);
			String ncmTemplateStr = new String(readByte);
			//System.out.println(ncmTemplateStr);
			JSONArray ncmTemplateList = new JSONArray(ncmTemplateStr);
			fis.close();
			
			for(int i = 0; i < ncmTemplateList.length(); i++) {
				JSONObject ncmTemplate = ncmTemplateList.getJSONObject(i);
				JSONObject commandObject = ncmTemplate.getJSONObject("command");
				Iterator<String> protocolItr = commandObject.keys();
				JSONObject backupProtocolAvailOperations = new JSONObject();
				JSONObject restoreProtocolAvailOperations = new JSONObject();
				//iterating over each available protocols
				while(protocolItr.hasNext()) {
					String protocol = protocolItr.next();
					JSONObject operationsObject = commandObject.getJSONObject(protocol);
					Set<String> operationSet = operationsObject.keySet();
					JSONArray availableRestoreOperations = new JSONArray();
					JSONArray availableBackupOperations = new JSONArray();
					//iterating over each operations (backup, restore, sync, get hardware details
					for(String operation : operationSet) {
						if(RESTORE_OPERATIONS_MAP.containsKey(operation)) {
							availableRestoreOperations.put(RESTORE_OPERATIONS_MAP.get(operation));
						}
						if(BACKUP_OPERATIONS_MAP.containsKey(operation)) {
							availableBackupOperations.put(BACKUP_OPERATIONS_MAP.get(operation));
						}
					}
					
					if(availableBackupOperations.length() > 0) {
						backupProtocolAvailOperations.put(protocol, availableBackupOperations);
					}
					if(availableRestoreOperations.length() > 0) {
						restoreProtocolAvailOperations.put(protocol, availableRestoreOperations);
					}
				}
				if(backupProtocolAvailOperations.length() > 0) {
					ncmTemplate.put("BACKUP", backupProtocolAvailOperations);
				}
				if(restoreProtocolAvailOperations.length() > 0) {
					ncmTemplate.put("RESTORE", restoreProtocolAvailOperations);
				}
			}
			
			File outputFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Templates/new-ncm-device-templates.json");
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(ncmTemplateList.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

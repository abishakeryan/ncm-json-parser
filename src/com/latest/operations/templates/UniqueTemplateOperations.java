//$Id$
package com.latest.operations.templates;

import java.io.File;
import java.io.FileInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class UniqueTemplateOperations {
	
	static String[] templateIds = {"170","172","194","173","196","197","176","177","156","135","157","136","158","137","138","139","16","18","19","140","141","163","142","164","187","144","167","168","147","169","41","85","86","87"};
	static String UPLOAD_CONFIGURATION = "Upload Configuration";
	static String BACKUP_CONFIGURATION = "Backup Configuration";

	public static void main(String[] args) {
		try {
			for(int i = 0; i < templateIds.length; i++) {
				String id = templateIds[i];
				File inputFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/Template_Details/" + id + ".txt");
				FileInputStream fis = new FileInputStream(inputFile);
				byte[] read = new byte[(int) inputFile.length()];
				fis.read(read);
				String fileStr = new String(read);
				JSONObject template = new JSONObject(fileStr);
				fis.close();
				JSONArray protocolCombination = template.getJSONArray("protocolCombination");
				
				for(int j = 0; j < protocolCombination.length(); j++) {
					JSONObject protocolObj = protocolCombination.getJSONObject(j);
					String protocol = protocolObj.getString("protocol");
					JSONArray execGroups = protocolObj.getJSONArray("execGroups");
					for(int k = 0; k < execGroups.length(); k++) {
						JSONObject operationObj = execGroups.getJSONObject(k);
						String operation = operationObj.getString("groupName");
						if(operation.equals(UPLOAD_CONFIGURATION)) {
							String configType = operationObj.getString("operationName");
							System.out.println(id + " | Upload | " + configType);
						}
						else if(operation.equals(BACKUP_CONFIGURATION)) {
							String configType = operationObj.getString("operationName");
							System.out.println(id + " | Backup | " + configType);
						}
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

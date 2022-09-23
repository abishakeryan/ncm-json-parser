//$Id$
package com.latest.operations.templates;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class UniqueOperations {
	
	public static void main(String[] args) {
		try {
			Set<String> uniqueOperationSet = new HashSet<>();
			Set<String> uniqueTemplateSet = new HashSet<>();
			File templateFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/Restore_Availability/Template_List/template_with_protocolCombination.json");
			FileInputStream fis = new FileInputStream(templateFile);
			byte[] readByte = new byte[(int) templateFile.length()];
			fis.read(readByte);
			String templateStr = new String(readByte);
			JSONArray templateList = new JSONArray(templateStr);
			fis.close();
			
			for(int i = 0; i < templateList.length(); i++) {
				JSONObject template = templateList.getJSONObject(i);
				JSONObject cmdObj = template.getJSONObject("command");
				Iterator<String> protocolItr = cmdObj.keys();
				while(protocolItr.hasNext()) {
					String protocol = protocolItr.next();
					JSONObject operationsObj = cmdObj.getJSONObject(protocol);
					//uniqueOperationSet.addAll(operationsObj.keySet());
					//To find templates with operations Upload Configuration and Backup Configuration
					Set<String> operationSet = operationsObj.keySet();
					for(String operation : operationSet) {
						if(operation.equals("Backup Configuration") || operation.equals("Upload Configuration")) {
							uniqueTemplateSet.add(template.getString("id"));
						}
					}
				}
			}
			
			System.out.println(uniqueTemplateSet);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

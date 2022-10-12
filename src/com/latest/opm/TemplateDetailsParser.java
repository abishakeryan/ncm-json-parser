//$Id$
package com.latest.opm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author abishake-9966
 * 
 */
public class TemplateDetailsParser {
	
	static Map<String, String> restoreMap = new HashMap<String, String>() {{
		put("Upload Running Configuration", "running_config");
		put("Upload Startup Configuration", "startup_config");
	}};
	
	static List<Integer> missingTemplateIds = new ArrayList<>();

	public static void main(String[] args) {
		try {
			File templateInputFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/2_Template_Details/Template_List/template_list.json");
			FileInputStream fis = new FileInputStream(templateInputFile);
			byte[] inputByteArray = new byte[(int) templateInputFile.length()];
			fis.read(inputByteArray);
			String inputFileContent = new String(inputByteArray);
			JSONArray templateList = new JSONArray(inputFileContent);
			for(int i = 0; i < templateList.length(); i++) {
				JSONObject template = templateList.getJSONObject(i);
				String id = template.getString("id");
				if(!String.valueOf(i+1).equals(id)) {
					missingTemplateIds.add(i+1);
				}
				JSONObject templateCmdObj = new JSONObject();
				template.put("command", templateCmdObj);
				JSONObject restoreObj = new JSONObject();
				
				File templateDetailFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/2_Template_Details/" + id + ".txt");
				FileInputStream templateDetailFis = new FileInputStream(templateDetailFile);
				byte[] templateDetailByteArray = new byte[(int) templateDetailFile.length()];
				templateDetailFis.read(templateDetailByteArray);
				String templateDetailContent = new String(templateDetailByteArray);
				templateDetailFis.close();
				JSONObject templateDetailObj = new JSONObject(templateDetailContent);
				//Parse Commands
				JSONArray protocolCombinations = templateDetailObj.getJSONArray("protocolCombination");
				
				for(int j = 0; j < protocolCombinations.length(); j++) {
					JSONObject protocolsObj = protocolCombinations.getJSONObject(j);
					String protocols = protocolsObj.getString("protocol");
					JSONArray execGroups = protocolsObj.getJSONArray("execGroups");
					
					String[] protocolsArray = protocols.split("/");
					String endProtocol = protocolsArray[protocolsArray.length - 1];
					String postFix = "";
					if(endProtocol.contains("-")) {
						postFix = endProtocol.substring(endProtocol.indexOf(" "));
					}
					for(int protocolInd = 0; protocolInd < protocolsArray.length; protocolInd++) {
						//protocol name to be inserted in the template
						String protocol = protocolsArray[protocolInd];
						if(protocol.contains("-")) {
							protocol = protocol.substring(0, protocol.indexOf(" "));
						}
						protocol += postFix;
						JSONObject templateOperationObj = new JSONObject();
						templateCmdObj.put(protocol, templateOperationObj);
						JSONArray restoreAvailability = new JSONArray();
						
						
						//iterate all operation for the protocol
						for(int operationInd = 0; operationInd < execGroups.length(); operationInd++) {
							JSONObject operationObj = execGroups.getJSONObject(operationInd);
							String operation = operationObj.getString("groupName");
							if(operation.matches("Backup Configuration|Upload Configuration")) {
								String configType = operationObj.getString("operationName");
								if(operation.contains("Backup")) {
									operation = "Backup " + configType + " Configuration";
								}
								else {
									operation = "Upload " + configType + " Configuration";
								}
							}
							
							//Restore operations
							if(operation.contains("Upload")) {
								restoreAvailability.put(restoreMap.get(operation));
							}
							JSONArray operationCmd = operationObj.getJSONArray("execGroupInfoList");
							templateOperationObj.put(operation, operationCmd);
						}
						if(restoreAvailability.length() > 0) {
							restoreObj.put(protocol, restoreAvailability);
						}
					}
				}
				
				//Fetch SysOid
				JSONArray sysOidList = templateDetailObj.getJSONArray("sysOid");
				JSONArray sysOidParsed = new JSONArray();
				for(int oidIdx = 0; oidIdx < sysOidList.length(); oidIdx++) {
					JSONObject sysOid = sysOidList.getJSONObject(oidIdx);
					JSONObject newSysOid = new JSONObject();
					newSysOid.put("oid", sysOid.getString("sysoid"));
					newSysOid.put("model", sysOid.getString("model"));
					newSysOid.put("series", sysOid.getString("series"));
					newSysOid.put("default-custom", sysOid.getString("default-custom"));
					sysOidParsed.put(newSysOid);
				}
				template.put("sysoid", sysOidParsed);
				if(restoreObj.length() > 0) {
					template.put("RESTORE", restoreObj);
				}
			}
			//System.out.println(templateList.getJSONObject(20));
			File outputTemplate = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/2_Template_Details/Template_List/template_list_with_command.json");
			FileOutputStream fos = new FileOutputStream(outputTemplate);
			fos.write(templateList.toString().getBytes());
			fos.close();
			
			System.out.println(missingTemplateIds);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

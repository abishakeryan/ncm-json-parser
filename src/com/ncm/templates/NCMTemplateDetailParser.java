//$Id$
package com.ncm.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class NCMTemplateDetailParser {
//Get API response using FetchTemplateDetails.sh
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File ncmTemplatesFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/ncm-templates.json");
			FileInputStream fis = new FileInputStream(ncmTemplatesFile);
			byte[] inputByte = new byte[(int) ncmTemplatesFile.length()];
			fis.read(inputByte);
			String templateListString = new String(inputByte);
			JSONArray templateList = new JSONArray(templateListString);
			fis.close();
			
			//READING TEMPLATE DETAILS
			JSONObject commandObj = new JSONObject();
			for(int i = 1; i <= 200 ; i++) {
				File templateDetailFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/TemplatesDetails/" + i + ".txt");
				fis = new FileInputStream(templateDetailFile);
				byte[] tempDetailByte = new byte[(int) templateDetailFile.length()];
				fis.read(tempDetailByte);
				System.out.println("Reading file:" + i + ".txt");
				String tempDetailStr = new String(tempDetailByte);
				JSONObject templateDetail = new JSONObject(tempDetailStr);
				String id = templateDetail.get("DEVICETEMPLATEID").toString();
				JSONArray commandList = templateDetail.getJSONArray("protocolCombination");
				JSONObject protocolCommandObj = new JSONObject();
				for(int j = 0; j < commandList.length(); j++) {
					JSONObject protocolDetail = commandList.getJSONObject(j);
					String protocol = protocolDetail.getString("protocol");
					JSONArray execGroups = protocolDetail.getJSONArray("execGroups");
					JSONObject groupCommandObj = new JSONObject();
					for(int k = 0; k < execGroups.length(); k++) {
						JSONObject execGroup = execGroups.getJSONObject(k);
						String groupName = execGroup.getString("groupName");
						JSONArray groupCommandList = execGroup.getJSONArray("execGroupInfoList");
						groupCommandObj.put(groupName, groupCommandList);
					}
					protocolCommandObj.put(protocol, groupCommandObj);
				}
				commandObj.put(id, protocolCommandObj);
				fis.close();
				//System.out.println(commandObj.toString());
			}
			
			//merging templates
			JSONArray outputArray = new JSONArray();
			for(int i = 0; i < templateList.length(); i++) {
				JSONObject templateObject = new JSONObject();
				JSONObject ncmTemplateObj = templateList.getJSONObject(i);
				String id = ncmTemplateObj.getString("id");
				templateObject.put("id", id);
				templateObject.put("OS", ncmTemplateObj.getString("OS"));
				templateObject.put("vendor", ncmTemplateObj.getString("vendor"));
				templateObject.put("name", ncmTemplateObj.getString("name"));
				templateObject.put("description", ncmTemplateObj.getString("description"));
				
				System.out.println(ncmTemplateObj.toString());
				System.out.println("commands");
				JSONObject commandObject = commandObj.getJSONObject(id);
				System.out.println(commandObject.toString());
				templateObject.put("command", commandObject);
				//System.out.println("result");
				//System.out.println(templateObject.toString());
				outputArray.put(templateObject);
				System.out.println("---------------------------------------------------------------------------------------------------------------------");
			}
			
			//printing the constructed json values to output file
			File outFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/command-ncm-templates.json");
			FileOutputStream fos = new FileOutputStream(outFile);
			fos.write(outputArray.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
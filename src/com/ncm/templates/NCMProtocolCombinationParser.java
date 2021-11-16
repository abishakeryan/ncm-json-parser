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

public class NCMProtocolCombinationParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			JSONObject idProtocolListObj = new JSONObject();
			for(int i = 1; i <= 200; i++) {
				String protocolCombinationList = "";
				File inputFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/ProtocolCombinations/" + i + ".txt");
				FileInputStream fis = new FileInputStream(inputFile);
				byte[] readByte = new byte[(int) inputFile.length()];
				fis.read(readByte);
				String protocolJsonStr = new String(readByte);
				JSONObject protocolObj = new org.json.JSONObject(protocolJsonStr);
				JSONArray dataArray = protocolObj.getJSONArray("data");
				fis.close();
				for(int j = 0; j < dataArray.length(); j++) {
					JSONObject protocolDetail = dataArray.getJSONObject(j);
					String protocol = protocolDetail.getString("displayname");
					if(protocolCombinationList.length() > 0) {
						protocolCombinationList += "," + protocol;
					}
					else {
						protocolCombinationList = protocol;
					}
					//protocolCombinationList.put(protocol);
				}
				idProtocolListObj.put(String.valueOf(i), protocolCombinationList);
			}
			//System.out.println(idProtocolListObj.toString());
			
			//Reading ncm template json file
			File ncmTemplateFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/command-ncm-templates.json");
			JSONArray outputArray = new JSONArray();
			FileInputStream fis = new FileInputStream(ncmTemplateFile);
			byte[] readByte = new byte[(int) ncmTemplateFile.length()];
			fis.read(readByte);
			String templateStr = new String(readByte);
			JSONArray ncmTemplateArray = new JSONArray(templateStr);
			fis.close();
			//ncmTemplateArray.length()
			for(int i = 0; i < ncmTemplateArray.length(); i++) {
				JSONObject newTemplateObj = new JSONObject();
				JSONObject templateObject = ncmTemplateArray.getJSONObject(i);
				String id = templateObject.getString("id");
				newTemplateObj.put("id", id);
				newTemplateObj.put("vendor", templateObject.getString("vendor"));
				newTemplateObj.put("OS", templateObject.getString("OS"));
				newTemplateObj.put("name", templateObject.getString("name"));
				newTemplateObj.put("description", templateObject.getString("description"));
				newTemplateObj.put("command", templateObject.getJSONObject("command"));
				String protocolCombinationList = idProtocolListObj.getString(id);
				//String protocolCombinationStr = protocolCombinationList.join(",");
				newTemplateObj.put("ProtocolCombination", protocolCombinationList);
				outputArray.put(newTemplateObj);
			}
			//System.out.println(outputArray.toString());
			
			File outputFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/protocol-ncm-templates.json");
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(outputArray.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

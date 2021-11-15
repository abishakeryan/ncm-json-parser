//$Id$
package com.ncm.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class TemplateListParser {
//Fetch API response from OPM NCM Device Template List
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File templateListFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/TemplateList.txt");
			FileInputStream fis = new FileInputStream(templateListFile);
			byte[] inputByte = new byte[(int) templateListFile.length()];
			fis.read(inputByte);
			String fileContent = new String(inputByte);
			JSONArray templateList = new JSONArray(fileContent);
			JSONArray outPutList = new JSONArray();
			for(int i = 0; i < templateList.length(); i++) {
				JSONObject templateObj = new JSONObject();
				JSONObject templateDetail = templateList.getJSONObject(i);
				String id = templateDetail.get("id").toString();
				JSONArray cell = templateDetail.getJSONArray("cell");
				String templateName = cell.getString(1);
				String vendor = cell.getString(2);
				String OS = cell.getString(3);
				String description = cell.getString(5);
				templateObj.put("name", templateName);
				templateObj.put("vendor", vendor);
				templateObj.put("OS", OS);
				templateObj.put("description", description);
				templateObj.put("id", id);
				outPutList.put(templateObj);
			}
			fis.close();
			//writing to output file
			File outputFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/ncm-templates.json");
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(outPutList.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
//$Id$
package com.latest.opm.exclude.criteria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.json.JSONObject;
import org.json.JSONArray;

public class TemplateCriteriaMapper {

	public static void main(String[] args) {
		try {
			String dir = "/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/5_Exclude_Criteria/";
			File inputFile = new File(dir + "templateCriteriaMap.json");
			FileInputStream fis = new FileInputStream(inputFile);
			byte[] read = new byte[(int) inputFile.length()];
			fis.read(read);
			String fileStr = new String(read);
			fis.close();
			JSONObject templateCriteriaObj = new JSONObject(fileStr);
			JSONArray data = templateCriteriaObj.getJSONArray("data");
			JSONArray retArray = new JSONArray();
			
			for(int i = 0; i < data.length(); i++) {
				JSONObject mappingObj = data.getJSONObject(i);
				JSONObject newObj = new JSONObject();
				String templateId = mappingObj.getString("devicetemplateid");
				String criteriaId = mappingObj.getString("criteria_id");
				String templateName = mappingObj.getString("templatename");
				String personality = mappingObj.getString("personalityname");
				newObj.put("personality", personality);
				newObj.put("criteria_id", criteriaId);
				newObj.put("template_id", templateId);
				newObj.put("template_name", templateName);
				retArray.put(newObj);
			}
			
			System.out.println("Old length : " + data.length() + " | New Length : " + retArray.length());
			
			File outFile = new File(dir + "FinalList/ncm-template-criteria-mapping.json");
			FileOutputStream fos = new FileOutputStream(outFile);
			fos.write(retArray.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

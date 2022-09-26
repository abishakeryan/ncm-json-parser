//$Id$
package com.latest.opm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

public class TemplateListParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<JSONObject> retArray = new ArrayList<>();
		try {
			File inputFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/1_Initial_Template/Initial_Template.json");
			FileInputStream fis = new FileInputStream(inputFile);
			byte[] inputByteArray = new byte[(int) inputFile.length()];
			fis.read(inputByteArray);
			String inputFileContent = new String(inputByteArray);
			JSONArray templateList = new JSONArray(inputFileContent);
			System.out.println("total input first : " + templateList.length());
			fis.close();
			for(int i = 0; i < templateList.length(); i++) {
				JSONObject template = templateList.getJSONObject(i);
				String templateName = template.getString("DISPLAYNAME");
				String id = String.valueOf(template.getInt("id"));
				String vendor = template.getString("VENDORNAME");
				Boolean depricated = template.getBoolean("IS_DEPRICATED");
				String description = template.getString("DESCRIPTION");
				String os = template.getString("OSTYPE");
				
				JSONObject newTemplate = new JSONObject();
				newTemplate.put("id", id);
				newTemplate.put("name", templateName);
				newTemplate.put("vendor", vendor);
				newTemplate.put("description", description);
				newTemplate.put("OS", os);
				newTemplate.put("depricated", depricated);
				
				retArray.add(newTemplate);
			}
			Collections.sort(retArray, new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject a, JSONObject b) {
					Integer aId = a.getInt("id");
					Integer bId = b.getInt("id");
					return aId.compareTo(bId);
				}
			});
			System.out.println("total input final : " + retArray.size());
			//System.out.println(retArray);
			File outputFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/1_Initial_Template/template_list.json");
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(retArray.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

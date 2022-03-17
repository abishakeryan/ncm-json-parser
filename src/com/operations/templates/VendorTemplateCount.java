//$Id$
package com.operations.templates;

import java.io.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class VendorTemplateCount {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File templateJson = new File("/home/local/ZOHOCORP/abishake-9966/Documents/ncm-device-templates.json");
			FileInputStream fis = new FileInputStream(templateJson);
			byte[] readByte = new byte[(int) templateJson.length()];
			fis.read(readByte);
			String jsonStr = new String(readByte);
			JSONArray jsonArr = new JSONArray(jsonStr);
			System.out.println("Total no of templates : " + jsonArr.length());
			fis.close();
			Map<String, List<String>> vendorTemplateMap = new HashMap<>();
			for(int i = 0; i < jsonArr.length(); i++) {
				JSONObject template = jsonArr.getJSONObject(i);
				String vendor = template.getString("vendor");
				if(!vendorTemplateMap.containsKey(vendor)) {
					vendorTemplateMap.put(vendor, new ArrayList<String>());
				}
				String templateName = template.getString("name");
				List<String> templateList = vendorTemplateMap.get(vendor);
				templateList.add(templateName);
			}
			System.out.println("Total no of vendors : " + vendorTemplateMap.size());
			System.out.println(vendorTemplateMap);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

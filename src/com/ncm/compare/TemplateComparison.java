//$Id$
package com.ncm.compare;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class TemplateComparison {
	
	static Map<String, String> oldTemplateNameIdMap = new HashMap<>();
	static Map<String, String> newTemplateNameIdMap = new HashMap<>();
	static List<String> newlyAdded = new ArrayList<>();
	static Map<String, String> modifiedTemplateName = new HashMap<>();
	
	static Map<String, String> getNameIdMap(JSONArray list) {
		Map<String, String> retMap = new HashMap<>();
		for(int i = 0; i < list.length(); i++) {
			JSONObject template = list.getJSONObject(i);
			String name = template.getString("name");
			String id = template.getString("id");
			retMap.put(name, id);
		}
		return retMap;
	}
	
	static void compareTemplates(Map<String, String> oldTemplateMap, Map<String, String> newTemplateMap) {
		for(Map.Entry<String, String> entry : newTemplateMap.entrySet()) {
			String newTemplateName = entry.getKey();
			if(!oldTemplateMap.containsKey(newTemplateName)) {
				newlyAdded.add(newTemplateName);
			}
		}
		Set<String> oldTemplateSet = new HashSet<>(oldTemplateMap.keySet());
		Set<String> newTemplateSet = new HashSet<>(newTemplateMap.keySet());
		oldTemplateSet.removeAll(newTemplateMap.keySet());
		newTemplateSet.removeAll(oldTemplateMap.keySet());
		
		System.out.println("Missing : " + oldTemplateSet.size() + " | " + oldTemplateSet);
		System.out.println("Added : " + newTemplateSet.size() + " | " + newTemplateSet);
		System.out.println("Added check : " + newlyAdded.size());
		
	}

	public static void main(String[] args) {
		try {
			String locationDir = "/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/4_Final_Template_List/";
			File oldTemplateFile = new File(locationDir + "old-ncm-device-templates.json");
			File newTemplateFile = new File(locationDir + "ncm-device-templates.json");
			FileInputStream fis = new FileInputStream(oldTemplateFile);
			byte[] read = new byte[(int) oldTemplateFile.length()];
			fis.read(read);
			String oldStr = new String(read);
			fis.close();
			fis = new FileInputStream(newTemplateFile);
			read = new byte[(int) newTemplateFile.length()];
			fis.read(read);
			String newStr = new String(read);
			JSONArray oldTemplateList = new JSONArray(oldStr);
			JSONArray newTemplateList = new JSONArray(newStr);
			System.out.println("Old length : " + oldTemplateList.length() + " | New length : " + newTemplateList.length());
			
			oldTemplateNameIdMap = getNameIdMap(oldTemplateList);
			newTemplateNameIdMap = getNameIdMap(newTemplateList);
			
			compareTemplates(oldTemplateNameIdMap, newTemplateNameIdMap);
			System.out.println();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
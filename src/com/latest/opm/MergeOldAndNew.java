//$Id$
package com.latest.opm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class MergeOldAndNew {
	
	static Map<String, String> oldTemplateNameIdMap = new HashMap<>();
	static Map<String, String> newTemplateNameIdMap = new HashMap<>();
	static Map<String, String> conflictNameIdMap = new HashMap<>();
	static List<String> mergedTemplateList = new ArrayList<>();
	
	static Map<String, String> getTemplateNameIdMap(JSONArray templateList) {
		Map<String, String> retMap = new HashMap<>();
		for(int i = 0; i < templateList.length(); i++) {
			JSONObject template = templateList.getJSONObject(i);
			String id = template.getString("id");
			String name = template.getString("name");
			retMap.put(name, id);
		}
		return retMap;
	}
	
	static void mergeTemplates(JSONArray oldList, JSONArray newList) {
		for(int i = 0; i < oldList.length(); i++) {
			JSONObject template = oldList.getJSONObject(i);
			String name = template.getString("name");
			if(!newTemplateNameIdMap.containsKey(name)) {
				String id = template.getString("id");
				if(newTemplateNameIdMap.containsValue(id)) {
					conflictNameIdMap.put(name, id);
				}
				else {
					mergedTemplateList.add(name);
					newList.put(template);
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			String dir = "/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/4_Final_Template_List/";
			File oldTemplateFile = new File(dir + "old-ncm-device-templates.json");
			File newTemplateFile = new File(dir + "ncm-device-templates.json");
			FileInputStream fis = new FileInputStream(oldTemplateFile);
			byte[] read = new byte[(int) oldTemplateFile.length()];
			fis.read(read);
			String oldTemplateStr = new String(read);
			fis.close();
			JSONArray oldTemplateList = new JSONArray(oldTemplateStr);
			
			fis = new FileInputStream(newTemplateFile);
			read = new byte[(int) newTemplateFile.length()];
			fis.read(read);
			String newTemplateStr = new String(read);
			fis.close();
			JSONArray newTemplateList = new JSONArray(newTemplateStr);
			
			System.out.println("Old length : " + oldTemplateList.length() + " | New length : " + newTemplateList.length());
			
			oldTemplateNameIdMap = getTemplateNameIdMap(oldTemplateList);
			newTemplateNameIdMap = getTemplateNameIdMap(newTemplateList);
			
			//System.out.println("Old : " + oldTemplateNameIdMap + " | New : " + newTemplateNameIdMap);
			mergeTemplates(oldTemplateList, newTemplateList);
			System.out.println("New length after merged : " + newTemplateList.length());
			System.out.println("conflict list : " + conflictNameIdMap);
			
			File mergedFile = new File(dir + "Merged/merged.json");
			FileOutputStream fos = new FileOutputStream(mergedFile);
			fos.write(newTemplateList.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

//$Id$
package com.latest.opm.exclude.criteria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.json.JSONObject;
import org.json.JSONArray;

public class NCMExcludeCriteriaParser {

	public static void main(String[] args) {
		try {
			String dir = "/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/5_Exclude_Criteria/";
			File inputFile = new File(dir + "excludeCriteria.json");
			FileInputStream fis = new FileInputStream(inputFile);
			byte[] read = new byte[(int) inputFile.length()];
			fis.read(read);
			fis.close();
			String fileStr = new String(read);
			JSONObject response = new JSONObject(fileStr);
			JSONArray data = response.getJSONArray("data");
			
			JSONArray retArray = new JSONArray();
			
			for(int i = 0; i < data.length(); i++) {
				JSONObject row = data.getJSONObject(i);
				String criteria = row.getString("criteria");
				String criteriaId = row.getString("criteria_id");
				String name = row.getString("criteria_name");
				String description = row.getString("description");
				
				JSONObject criteriaObj = new JSONObject();
				criteriaObj.put("name", name);
				criteriaObj.put("criteria", criteria);
				criteriaObj.put("id", criteriaId);
				criteriaObj.put("description", description);
				
				retArray.put(criteriaObj);
			}
			
			System.out.println("Length : " + retArray.length());
			
			File outFile = new File(dir + "FinalList/ncm-exclude-criteria.json");
			FileOutputStream fos = new FileOutputStream(outFile);
			fos.write(retArray.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

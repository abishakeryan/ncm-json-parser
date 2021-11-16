//$Id$
package com.ncm.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.json.JSONObject;
import org.json.JSONArray;

public class SysOIDParser {
//get the sysoid list using sysOid.sh
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			JSONObject idOidListObj = new JSONObject();
			for(int i = 1; i <= 200; i++) {
				//opening sysoid file
				String string;
				File inputFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/SysOID/" + i + ".txt");
				FileInputStream fis = new FileInputStream(inputFile);
				byte[] readByte = new byte[(int) inputFile.length()];
				fis.read(readByte);
				String oidStr = new String(readByte);
				JSONObject oidObj = new JSONObject(oidStr);
				JSONArray rows = oidObj.getJSONArray("rows");
				fis.close();
				String id = String.valueOf(i);
				JSONArray sysOIDArray = new JSONArray();
				for(int j = 0; j < rows.length(); j++) {
					JSONObject newSysOIDObj = new JSONObject();
					JSONObject obj = rows.getJSONObject(j);
					JSONArray cell = obj.getJSONArray("cell");
					String model = cell.getString(2);
					String series = cell.getString(3);
					String oid = cell.getString(4);
					newSysOIDObj.put("series", series);
					newSysOIDObj.put("model", model);
					newSysOIDObj.put("oid", oid);
					sysOIDArray.put(newSysOIDObj);
				}
				//System.out.println("ID:" + i);
				//System.out.println("SysID:\n" + sysOIDArray.toString());
				idOidListObj.put(id, sysOIDArray);
			}
			
			//Fetching ncmtemplate list
			String string;
			File ncmTemplateFile = new File("/home/abishake-9966/Documents/NCM_Templates_v2/protocol-ncm-templates.json");
			FileInputStream fis = new FileInputStream(ncmTemplateFile);
			byte[] readByte = new byte[(int) ncmTemplateFile.length()];
			fis.read(readByte);
			String ncmTemplateStr = new String(readByte);
			JSONArray ncmTemplates = new JSONArray(ncmTemplateStr);
			JSONArray finalTemplateList = new JSONArray();
			fis.close();
			//ncmTemplates.length()
			for(int i = 0; i < ncmTemplates.length(); i++) {
				JSONObject templateDetail = ncmTemplates.getJSONObject(i);
				JSONObject templateObj = new JSONObject();
				String id = templateDetail.getString("id");
				templateObj.put("id", id);
				templateObj.put("name", templateDetail.getString("name"));
				templateObj.put("vendor", templateDetail.getString("vendor"));
				templateObj.put("OS", templateDetail.getString("OS"));
				templateObj.put("description", templateDetail.getString("description"));
				templateObj.put("ProtocolCombination", templateDetail.getString("ProtocolCombination"));
				templateObj.put("command", templateDetail.getJSONObject("command"));
				templateObj.put("sysoid", idOidListObj.getJSONArray(id));
				finalTemplateList.put(templateObj);
			}
			//System.out.println(finalTemplateList.toString());
			
			File finalNCMTemplates = new File("/home/abishake-9966/Documents/NCM_Templates_v2/final-ncm-templates.json");
			FileOutputStream fos = new FileOutputStream(finalNCMTemplates);
			fos.write(finalTemplateList.toString().getBytes());
			fos.close();
			System.out.println("Terminated");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

//$Id$
package com.latest.opm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProtocolCombinationParser {

	public static void main(String[] args) {
		try {
			File templateFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/ProtocolCombinations/Template_List/template_list_with_command.json");
			FileInputStream fis = new FileInputStream(templateFile);
			byte[] readByte = new byte[(int) templateFile.length()];
			fis.read(readByte);
			String templateFileContent = new String(readByte);
			JSONArray templateList = new JSONArray(templateFileContent);
			fis.close();
			
			for(int i = 0; i < templateList.length(); i++) {
				JSONObject template = templateList.getJSONObject(i);
				String id = template.getString("id");
				List<String> protocolList = new ArrayList<>();
				
				File protocolCombFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/ProtocolCombinations/" + id + ".txt");
				FileInputStream protocolFis = new FileInputStream(protocolCombFile);
				byte[] protocolByte = new byte[(int) protocolCombFile.length()];
				protocolFis.read(protocolByte);
				String protocolCombContent = new String(protocolByte);
				protocolFis.close();
				JSONObject protocolCombObj = new JSONObject(protocolCombContent);
				
				JSONArray rows = protocolCombObj.getJSONArray("data");
				for(int j = 0; j < rows.length(); j++) {
					JSONObject row = rows.getJSONObject(j);
					String protocol = row.getString("displayname");
					protocolList.add(protocol);
				}
				
				template.put("ProtocolCombination", protocolList.stream().collect(Collectors.joining(",")));
			}
			
			File outFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Template_Latest_OPM/ProtocolCombinations/Template_List/template_with_protocolCombination.json");
			FileOutputStream fos = new FileOutputStream(outFile);
			fos.write(templateList.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

//$Id$
package com.latest.opm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class CombineCommands {

	public static void main(String[] args) {
		try {
			String directory = "/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Templates_Latest_Merge/";
			File templateFileIn = new File(directory + "ncm-device-templates-old.json");
			FileInputStream fis = new FileInputStream(templateFileIn);
			byte[] read = new byte[(int) templateFileIn.length()];
			fis.read(read);
			String templateFileInStr = new String(read);
			JSONArray templateList = new JSONArray(templateFileInStr);
			fis.close();
			
			ArrayList<JSONObject> templateListOut = new ArrayList<>();
			System.out.println(templateList.length());
			
			for(int i = 0; i < templateList.length(); i++) {
				JSONObject template = templateList.getJSONObject(i);
				JSONObject commandObj = template.getJSONObject("command");
				JSONArray protocols = commandObj.names();
				List<String> protocolList = protocols.toList().stream().map(obj -> ((String) obj)).collect(Collectors.toList());
				//System.out.println(protocolList);
				if(protocolList.contains("SSH") && protocolList.contains("TELNET")) {
					JSONObject command = commandObj.getJSONObject("SSH");
					commandObj.put("SSH/TELNET", command);
					commandObj.remove("SSH");
					commandObj.remove("TELNET");
				}
				
				if(protocolList.contains("SSH - TFTP") && protocolList.contains("TELNET - TFTP")) {
					JSONObject command = commandObj.getJSONObject("SSH - TFTP");
					commandObj.put("SSH - TFTP/TELNET - TFTP", command);
					commandObj.remove("SSH - TFTP");
					commandObj.remove("TELNET - TFTP");
				}
				templateListOut.add(template);
			}
			
			Collections.sort(templateListOut, new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject a, JSONObject b) {
					Integer aId = null, bId = null;
					try {
						aId = a.getInt("id");
						bId = b.getInt("id");
					} catch (JSONException json) {
						json.printStackTrace();
					}
					return aId.compareTo(bId);
				}
			});
			
			System.out.println(templateListOut.size());
			
			File templateOutFile = new File(directory + "ncm-device-templates.json");
			FileOutputStream fos = new FileOutputStream(templateOutFile);
			fos.write(templateListOut.toString().getBytes());
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

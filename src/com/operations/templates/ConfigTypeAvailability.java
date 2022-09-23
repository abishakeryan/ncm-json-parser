//$Id$
package com.operations.templates;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConfigTypeAvailability {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashSet<String> startupSSHSupported = new HashSet<>();
		HashSet<String> startupSCPSupported = new HashSet<>();
		HashSet<String> startupTFTPSupported = new HashSet<>();
		HashSet<String> startupSNMPSupported = new HashSet<>();
		HashSet<String> runningSSHSupported = new HashSet<>();
		HashSet<String> runningSCPSupported = new HashSet<>();
		HashSet<String> runningTFTPSupported = new HashSet<>();
		HashSet<String> runningSNMPSupported = new HashSet<>();
		
		Set<String> SSHSupportedTemplates = new HashSet<>();
		Set<String> SCPSupportedTemplates = new HashSet<>();
		Set<String> TFTPSupportedTemplates = new HashSet<>();
		Set<String> SnmpSupportedTemplates = new HashSet<>();
		try {
			File templateJson = new File("/home/local/ZOHOCORP/abishake-9966/Documents/ncm-device-templates.json");
			FileInputStream fis = new FileInputStream(templateJson);
			byte[] readByte = new byte[(int) templateJson.length()];
			fis.read(readByte);
			String jsonStr = new String(readByte);
			JSONArray jsonArr = new JSONArray(jsonStr);
			for(int i = 0; i < jsonArr.length(); i++) {
				JSONObject template = jsonArr.getJSONObject(i);
				String templateName = template.getString("name");
				JSONObject commandObj = template.getJSONObject("command");
				Iterator<String> protocolItr = commandObj.keys();
				while(protocolItr.hasNext()) {
					String protocol = protocolItr.next();
					JSONObject operationsObj = commandObj.getJSONObject(protocol);
					Iterator<String> operationItr = operationsObj.keys();
					while(operationItr.hasNext()) {
						String operation = operationItr.next();
						if(protocol.equals("SSH")) {
							SSHSupportedTemplates.add(templateName);
							if(operation.equals("Backup Running Configuration")) {
								runningSSHSupported.add(templateName);
							}
							else if(operation.equals("Backup Startup Configuration")) {
								startupSSHSupported.add(templateName);
							}
						}
						else if(protocol.equals("SSH - SCP")) {
							SCPSupportedTemplates.add(templateName);
							if(operation.equals("Backup Running Configuration")) {
								runningSCPSupported.add(templateName);
							}
							else if(operation.equals("Backup Startup Configuration")) {
								startupSCPSupported.add(templateName);
							}
						}
						else if(protocol.equals("SSH - TFTP")) {
							TFTPSupportedTemplates.add(templateName);
							if(operation.equals("Backup Running Configuration")) {
								runningTFTPSupported.add(templateName);
							}
							else if(operation.equals("Backup Startup Configuration")) {
								startupTFTPSupported.add(templateName);
							}
						}
						else if(protocol.equals("SNMP - TFTP")) {
							SnmpSupportedTemplates.add(templateName);
							if(operation.equals("Backup Running Configuration")) {
								runningSNMPSupported.add(templateName);
							}
							else if(operation.equals("Backup Startup Configuration")) {
								startupSNMPSupported.add(templateName);
							}
						}
					}
				}
			}
			
			System.out.println("SSH Startup : " + startupSSHSupported.size() + " | Running : " + runningSSHSupported.size());
			System.out.println("SCP Startup : " + startupSCPSupported.size() + " | Running : " + runningSCPSupported.size());
			System.out.println("TFTP Startup : " + startupTFTPSupported.size() + " | Running : " + runningTFTPSupported.size());
			System.out.println("SNMP Startup : " + startupSNMPSupported.size() + " | Running : " + runningSNMPSupported.size());
			
			System.out.println("SSH Supported : " + SSHSupportedTemplates.size());
			System.out.println("SCP Supported : " + SCPSupportedTemplates.size());
			System.out.println("TFTP Supported : " + TFTPSupportedTemplates.size());
			System.out.println("SNMP Supported : " + SnmpSupportedTemplates.size());
			
			for(String template : SCPSupportedTemplates) {
				if(!startupSCPSupported.contains(template)) {
					System.out.println(template);
				}
			}
			runningSSHSupported.removeAll(startupSSHSupported);
			System.out.println("remaining : " + runningSSHSupported);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}

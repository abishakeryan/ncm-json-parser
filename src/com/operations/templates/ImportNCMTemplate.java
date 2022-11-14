//$Id$
package com.operations.templates;

import com.manageengine.eum.agent.util.ServerAgentConstants;
import com.manageengine.eum.url.httpclient.HttpConnection;
import com.manageengine.eum.url.httpclient.MultipartNVPair;
import com.manageengine.eum.url.httpclient.NVPair;
import com.manageengine.eum.url.httpclient.Response;
import com.manageengine.eum.util.EUMLog;
import com.site24x7.probe.network.datacollection.NetworkConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ImportNCMTemplate {
	
	public static void trustHttps() {
		try {
			SSLContext sc;
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	 
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	 
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		}
		catch (NoSuchAlgorithmException | KeyManagementException e) {
			EUMLog.log(NetworkConstants.NETWORK_LOG_CONST, "Exception while trusting HTTPs");
			e.printStackTrace();
		}
	}
	
	static NVPair[] headersPair = null;
	
	public static void postByMDM() {
		try {
			headersPair = new NVPair[4];
			headersPair[0] = new NVPair("Pragma","no-cache"); //NO I18N
	        headersPair[1] = new NVPair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"); //No I18N
	        headersPair[2] = new NVPair("Connection","Keep-Alive"); //No I18N
	        headersPair[3] = new NVPair("Accept-Language","en-us");//NO I18N
			String url = "https://8825-u20.csez.zohocorpin.com:8060/client/api/json/ncmsettings/importDeviceTemplate";
			HttpConnection httpConn = new HttpConnection(url);
			httpConn.setContext("default");
			String urlFile = new URL(url).getFile();
			System.out.println("urlFile : " + urlFile);
			
			System.out.println("urlFile : " + urlFile);
			Response response = httpConn.PostMultipart(urlFile, getPostParams(), getPostFile("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Export_Template/NCM_Template_Import.zip"), headersPair);
			if(response != null) {
				System.out.println("response code : " + response.getStatusCode());
				System.out.println("response String : " + new String(response.getData()));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void multiPartPost() {
		String fullUrl = "https://8825-u20.csez.zohocorpin.com:8060/api/json/ncmsettings/importDeviceTemplate";
		try {
			String boundary = "**" + System.currentTimeMillis() + "**";
			URL url = new URL(fullUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setConnectTimeout(4 * 60 * 1000);
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			OutputStream outStream = con.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true);
			//writer.append("Connection: keep-alive").append("\r\n");
			//writer.flush();
			//writer.append("Accept: */*").append("\r\n");
			//writer.flush();
			
			writer.append("--").append(boundary).append("\r\n");
			writer.append("Content-Disposition: form-data; name=\"apiKey\"").append("\r\n");
			writer.append("Content-Type: text/plain; charset=").append("UTF-8").append("\r\n\r\n");
			writer.append("2f856947a8f577331ff1df42148b88a4").append("\r\n");
			writer.flush();
			
			File uploadFile = new File("/home/local/ZOHOCORP/abishake-9966/Documents/NCM_Export_Template/Dell_PowerConnect_Switch_OS10.xml");
			String fileName = uploadFile.getName();
			
			writer.append("--" + boundary).append("\r\n");
			writer.append("Content-Disposition: form-data; name=\"importDTFile\"; filename=\"" + fileName + "\"\r\n");
			writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName) + "\r\n");
			writer.append("Content-Transfer-Encoding: binary\r\n\r\n");
			writer.flush();
			
			FileInputStream fis = new FileInputStream(uploadFile);
			byte[] buffer= new byte[4096];
			int bytesRead = -1;
			while((bytesRead = fis.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			outStream.flush();
			fis.close();
			writer.append("\r\n");
			writer.flush();
			writer.append("\r\n");
			writer.append("--" + boundary + "--").append("\r\n");
			writer.close();
			
			int status = con.getResponseCode();
			String result = "failed";
			if(status == HttpsURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String sb = new String();
				result = "";
				while((sb = br.readLine()) != null) {
					result += sb;
				}
			}
			System.out.println(status);
			
			System.out.println(result);
			con.disconnect();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		trustHttps();
		multiPartPost();
	}
	
	public static NVPair[] getPostParams() {
		ArrayList<NVPair> paramsList = new ArrayList<>();
		paramsList.add(new NVPair("name", "importDTFile"));
		paramsList.add(new NVPair("importDTFile", "Dell_PowerConnect_Switch_OS10.xml"));
		System.out.println("Post Params : " + paramsList.toArray(new NVPair[paramsList.size()]));
		return paramsList.toArray(new NVPair[paramsList.size()]);
	}
	
	public static MultipartNVPair[] getPostFile(String zipFilePath) {
		MultipartNVPair[] multiPartEntity = new MultipartNVPair[1];
		multiPartEntity[0] = new MultipartNVPair("zip_file", new File(zipFilePath));
		return multiPartEntity;
	}

}

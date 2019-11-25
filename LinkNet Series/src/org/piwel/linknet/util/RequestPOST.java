package org.piwel.linknet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class RequestPOST {
	
	/**
	 * @author http://blog.idleman.fr/snippet-7-java-faire-une-requete-http-en-post-et-en-get/
	 */
	public static String post(String adress,Map<String, String> params) throws IOException{
		String result = "";
		OutputStreamWriter writer = null;
		BufferedReader reader = null;
		//encodage des param�tres de la requ�te
		String data="";
		int i=0;
		for(Map.Entry<String,String> entry : params.entrySet()){
			if (i!=0) data += "&";
			data +=URLEncoder.encode(entry.getKey(), "UTF-8")+"="+URLEncoder.encode(entry.getValue(), "UTF-8");
			i++;
		}
		//cr�ation de la connection
		URL url = new URL(adress);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);


		//envoi de la requ�te
		writer = new OutputStreamWriter(conn.getOutputStream());
		writer.write(data);
		writer.flush();




		//lecture de la r�ponse
		reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String ligne;
		while ((ligne = reader.readLine()) != null) {
			result+=ligne;
		}
		writer.close();
		reader.close();
		
		return result;
	}
}


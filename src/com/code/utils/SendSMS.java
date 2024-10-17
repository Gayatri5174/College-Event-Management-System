package com.code.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Random;

public class SendSMS {

	public static String callURL(String msgC,String mobile,String msgType) throws UnsupportedEncodingException 
	{
            
	   //msgC = "Hello Your Activation or Verification Code is "+uid+" for Project "+msgC;
            
       String msg= URLEncoder.encode(msgC, "ISO-8859-1");
            
      String OTPRURL ="http://195.201.12.185/http-api.php?username=sgminfotech&password=sgm@9020&senderid=SGMSIN&route=5&number="+mobile+"&message="+msg+"&templateid=1207161777845562600";
      String LoginRURL ="http://195.201.12.185/http-api.php?username=sgminfotech&password=sgm@9020&senderid=SGMSIN&route=5&number="+mobile+"&message="+msg+"&templateid=1207161743839739518";

      String RURL=LoginRURL;
       
      if(msgType.equals("OTP"))
      {
    	  RURL=OTPRURL;
      }
       
       
       System.out.println("Requeted URL After:" + RURL);
                
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(RURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
		in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:" , e);
		} 
 
		return sb.toString();
	}

	public static String otGenerate() 
	{
		char[] chars = "1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

}


 


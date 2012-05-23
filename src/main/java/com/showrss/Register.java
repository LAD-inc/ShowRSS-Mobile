package com.showrss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;

import com.showrss.utilities.HttpClientHelper;

public class Register {
	
	public String captchaImageUrl;
	public String captchaKey;
	public String captchaChallengeKey;
	
	private final String REGISTER_URL = "http://showrss.karmorra.info/?cs=register";
	private final String CAPTCHA_INFO_URL = "http://api.recaptcha.net/challenge?k=";
	private final String CAPTCHA_IMAGE_URL = "http://www.google.com/recaptcha/api/image?c=";
	
	
	public boolean getRegisterDetails() throws Exception
	{
		
		String htmlCode = "";
		try 
		{

			htmlCode = HtmlCode.getHtmlCode(REGISTER_URL, false);

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		
		extractCaptchaChallengeKey(htmlCode);
		
		htmlCode = "";
		try 
		{
			htmlCode = HtmlCode.getHtmlCode(CAPTCHA_INFO_URL + captchaChallengeKey, false);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		extractRegisterDetails(htmlCode);
		
		return true;
	}

	private boolean extractCaptchaChallengeKey(String htmlCode)
	{
		Pattern p = Pattern.compile("src=\\\"http://api.recaptcha.net/challenge\\?k=([^\\\"]*)\\\"");
		Matcher m = p.matcher(htmlCode);
		 
		if (m.find())
		{
			
			captchaChallengeKey = m.group(1); 
			return true;
		}
		else
			return false;
		
	}
	
	private boolean extractRegisterDetails(String htmlCode)
	{
		
		//This is not the right way to parse a json object!
		Pattern p = Pattern.compile("challenge : '([^']*)'");
		Matcher m = p.matcher(htmlCode);
		 
		if (m.find())
		{
			
			this.captchaKey = m.group(1); 
			this.captchaImageUrl = CAPTCHA_IMAGE_URL + this.captchaKey;
			
			return true;
			
		}
		else
			return false;
		
	}
	
	public String sendRegisterRequest(String username, String password, String rPassword, String captchaText) throws ClientProtocolException, IOException
	{
		HttpClient httpclient = HttpClientHelper.getHttpClient();
		
		HttpPost httppost = new HttpPost(REGISTER_URL);

		// Add your data
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		//TODO: We should validate these are the same without going to the website to save data
		nameValuePairs.add(new BasicNameValuePair("rpassword", rPassword));
		
		
		nameValuePairs.add(new BasicNameValuePair("recaptcha_response_field", captchaText));
		nameValuePairs.add(new BasicNameValuePair("recaptcha_challenge_field", this.captchaKey));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		// Execute HTTP Post Request and get response
		return httpclient.execute(httppost, responseHandler);
	}
	
	public ArrayList<String> extractErrors(String htmlCode)
	{
		ArrayList<String> errors = new ArrayList<String>();
		
		Pattern p = Pattern.compile("<li>([^<]*)<\\/li>");
		Matcher m = p.matcher(htmlCode);
		 
		while (m.find())
		{
			errors.add(m.group(1));
		}
		
		if (errors.size() == 0)
			errors.add("Unkown Error");
		
		return errors;
		
	}
	
	

}

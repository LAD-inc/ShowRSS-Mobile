package com.showrss;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {
	
	public String captchaImageUrl;
	public String captchaKey;
	public String captchaChallengeKey;
	
	public boolean getRegisterDetails() throws Exception
	{
		String url = "http://showrss.karmorra.info/?cs=register";
		String captchaUrl = "http://api.recaptcha.net/challenge?k=";
		
		String htmlCode = "";
		try 
		{
			htmlCode = HtmlCode.GetHtmlCode(url, false);
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
			htmlCode = HtmlCode.GetHtmlCode(captchaUrl + captchaChallengeKey, false);
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
			this.captchaImageUrl = "http://www.google.com/recaptcha/api/image?c=" + this.captchaKey;
			
			return true;
			
		}
		else
			return false;
		
	}
	
	

}

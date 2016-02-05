package edu.usc.persona.extraction;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class UserExtractor {

	public static void main(String[] args) {
		
		List<String> username = new ArrayList<String>();
		WebDriver driver = new FirefoxDriver();
	       driver.get("http://www.hipointfirearmsforums.com/members/");
	       
	       // XPath to extract href attributes
	       List<WebElement> list=driver.findElements(By.xpath("//*[contains(@href,'members')]"));

	       for(WebElement e : list){
	           String link = e.getAttribute("href");
	           if(null==link)
	               link=e.getAttribute("src");
	           System.out.println(e.getTagName() + "=" + link);
	           
	        	   //System.out.println("1");
	        	   int index=link.lastIndexOf('/');
	        	   String lastString=(link.substring(index +1));
	        	//   System.out.println(lastString);
	        	   username.add(lastString);
	           
	       }

	       driver.close();
	       
	     //  System.out.println("++++++++++++++++++++++++++++++++++++++");
	       
	       
	       LinkedHashSet<String> users = new LinkedHashSet<String>();
	       users.addAll(username);
	       username.clear();
	       username.addAll(users);
	       
	       System.out.println("--Username--");
	       for (int i = 0; i < username.size(); i++) {
	    	     System.out.println((username.get(i)).toString());
	    	}
	       
	       
	}

}

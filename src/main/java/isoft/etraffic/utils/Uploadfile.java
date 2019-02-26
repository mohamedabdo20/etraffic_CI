package isoft.etraffic.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Uploadfile {
	
	
	 public static void main(String[] args) throws Exception {
         
		// This will open Chrome browser
		WebDriver driver=new ChromeDriver();
		        
		// This will maximize browser to full screen
		driver.manage().window().maximize();
		        
		// This will open respective URL
		driver.get("file:///C:/Users/mohamed.abdo/Desktop/upload.html");
		        
		// This will click on Upload button
		driver.findElement(By.xpath("//*[@type='file']")).click();
		     
		// This will invoke AutoIT script here give the path of the script 
		//and this will throw IO exception so u can use throw or try catch
		// In my case I am using throws
		 System.out.println(System.getProperty("user.dir")+"\\attachments\\uploadImage.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\attachments\\uploadImage.exe");
		 
		// Once you will run this program AutoIt script will be invoked and respective f//ile will be attached
		  
		  }
		 
		}

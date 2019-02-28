package isoft.etraffic.ctatestcases;

import org.testng.annotations.Test;

import isoft.etraffic.testbase.TestBase;
import junit.framework.Assert;

public class Smoke extends TestBase {
	//WebDriver driver;
	
	public void FirstTest()
	{}
	
	
	public void PassedTC()
	{
		Assert.assertEquals("1","1");}
	
	
	public void FailedTC()
	{
		Assert.assertEquals("1","2");}
	
}

package isoft.etraffic.ctatestcases;

import org.testng.annotations.Test;

import isoft.etraffic.testbase.TestBase;
import junit.framework.Assert;

public class Smoke extends TestBase {
	//WebDriver driver;
	@Test
	public void FirstTest()
	{}
	
	@Test
	public void PassedTC()
	{
		Assert.assertEquals("1","1");}
	
	@Test
	public void FailedTC()
	{
		Assert.assertEquals("1","2");}
	
}

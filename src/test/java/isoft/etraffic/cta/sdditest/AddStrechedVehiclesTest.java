package isoft.etraffic.cta.sdditest;

import org.testng.annotations.Test;

import isoft.etraffic.cta.sddipages.*;
import isoft.etraffic.testbase.TestBase;
import isoft.etraffic.vhl.sddipages.CommonPageOnline;


public class AddStrechedVehiclesTest extends TestBase{

	String TRF = "50090637";
	@Test
	public void addsterchedvehicles() throws InterruptedException  {
		
		CommonPageOnline onlinlogin = new CommonPageOnline(driver);
		onlinlogin.loginByTrafficFile(TRF);
		
		CtaCommonPages mycontract = new CtaCommonPages(driver);
		mycontract.openmycontract();
		
		AddStretchedVehiclesPage newvehicle = new AddStretchedVehiclesPage(driver);
		newvehicle.addstretchedvehiclespage();
				
	}
		
}


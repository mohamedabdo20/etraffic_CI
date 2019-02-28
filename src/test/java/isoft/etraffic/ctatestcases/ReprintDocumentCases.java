package isoft.etraffic.ctatestcases;

import java.sql.SQLException;

import org.testng.annotations.Test;

import isoft.etraffic.ctapages.DeliveryMethod;
import isoft.etraffic.ctapages.GeneralPages;
import isoft.etraffic.ctapages.PaymentCreaditCard;
import isoft.etraffic.ctapages.ReprintDocumentPage;
import isoft.etraffic.db.GetTFAndTL;
import isoft.etraffic.testbase.TestBase;

public class ReprintDocumentCases extends TestBase 
{
	public String TF;
	public String TL;
	GetTFAndTL getTFTL = new GetTFAndTL();
	GeneralPages GP = new GeneralPages(driver);
	
	@Test
	private void reprintDocument() throws ClassNotFoundException, InterruptedException, SQLException 
	{
		ReprintDocumentPage printObject = new ReprintDocumentPage(driver);
		getTFTL.getGeneralTFandTL();
		TF = getTFTL.TRF;
		TL = getTFTL.TL;
		
		
		printObject.reprintdocumentInitiate(TF, TL);
		DeliveryMethod DeliveryObject = new DeliveryMethod(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		/*Thread.sleep(30000);
		GP.closehappinies();*/
		
	}

}

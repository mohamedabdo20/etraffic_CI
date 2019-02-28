package isoft.etraffic.ctatestcases;

import java.sql.SQLException;

import org.testng.annotations.Test;

import isoft.etraffic.ctapages.DeliveryMethod;
import isoft.etraffic.ctapages.IssueToWhomItMayConcernCertificatPage;
import isoft.etraffic.ctapages.PaymentCreaditCard;
import isoft.etraffic.testbase.TestBase;

public class IssueToWhomConcernCertificate extends TestBase {

	@Test
	public void nocForIssueToWhomConcernCertificate()
			throws InterruptedException, ClassNotFoundException, SQLException {
	
		
		IssueToWhomItMayConcernCertificatPage NOCobject = new IssueToWhomItMayConcernCertificatPage(driver);
		NOCobject.IssueToWhomItMayConcern();

		DeliveryMethod DeliveryObject = new DeliveryMethod(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");

		Thread.sleep(10000);
		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		Thread.sleep(30000);

	}
}

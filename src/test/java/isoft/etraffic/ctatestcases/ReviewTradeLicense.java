package isoft.etraffic.ctatestcases;

import java.sql.SQLException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import isoft.etraffic.ctapages.DeliveryMethod;
import isoft.etraffic.ctapages.PaymentCreaditCard;
import isoft.etraffic.ctapages.ReviewTLPage;
import isoft.etraffic.db.GetNo;
import isoft.etraffic.testbase.TestBase;

public class ReviewTradeLicense extends TestBase {

	@Parameters({ "transaction_No", "order_no" })
	@Test
	public void ReviewTL(String transaction_No, String order_no) throws InterruptedException, ClassNotFoundException, SQLException {
		
		ReviewTLPage ReviewObject = new ReviewTLPage(driver);
		//NewTLNOCPage NOCpage = new NewTLNOCPage(driver);
		  
		ReviewObject.ReviewTL(transaction_No, order_no); 
		
		DeliveryMethod DeliveryObject = new DeliveryMethod(driver);
		DeliveryObject.delivermethod("0501234657", "04065858585", "test@test.com", "test@test.com");
		 
		// Pay by credit card
		
		PaymentCreaditCard payment = new PaymentCreaditCard(driver);
		payment.paymentcreaditcard(driver);
		Thread.sleep(30000);
		
		// get certification No
		
		GetNo getorderno = new GetNo();
		String certificatenumber = getorderno.getcertificateno(order_no);
		System.out.println("Certificate No "+certificatenumber);
		System.out.println("Order No "+order_no);
	}

}

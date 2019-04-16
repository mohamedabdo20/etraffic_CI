package isoft.etraffic.cta.sdditest;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;

public class CheckCTAEventsTest 
{

	@Test
	private void checkevents() throws ClassNotFoundException, SQLException {
		
		DBQueries dbqueries = new DBQueries();
		dbqueries.checkEventsLog("CML", "SYSDATE");
		int eventsCount = dbqueries.checkEventsLog("CML", "SYSDATE").size();
		System.out.println("Events found in log table : "+eventsCount);
		Assert.assertEquals(eventsCount,20);
		
		
		
	}
}

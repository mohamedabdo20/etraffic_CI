package isoft.etraffic.vhl.sdditest;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;

public class CheckVHLEventsTest 
{

	@Test
	private void checkevents() throws ClassNotFoundException, SQLException {
		
		DBQueries dbqueries = new DBQueries();
		dbqueries.checkEventsLog("VLD", "SYSDATE");
		int eventsCount = dbqueries.checkEventsLog("VLD", "SYSDATE").size();
		System.out.println("Events found in log table : "+eventsCount);
		Assert.assertEquals(eventsCount,15);
		
		
		
	}
}

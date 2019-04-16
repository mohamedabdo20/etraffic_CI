package isoft.etraffic.drl.sdditest;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import isoft.etraffic.db.DBQueries;

public class CheckDRLEventsTest 
{

	@Test
	private void checkevents() throws ClassNotFoundException, SQLException {
		
		DBQueries dbqueries = new DBQueries();
		dbqueries.checkEventsLog("DLD", "SYSDATE");
		int eventsCount = dbqueries.checkEventsLog("DLD", "SYSDATE").size();
		System.out.println("Events found in log table : "+eventsCount);
		Assert.assertEquals(eventsCount,0);
		
		
		
	}
}

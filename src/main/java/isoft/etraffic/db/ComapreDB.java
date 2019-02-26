package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;


public class ComapreDB {

		String NOCS_TO_BE_APPLIED;
		String NOCS_TO_BE_IGNORED;

		@Test(priority = 2, enabled = true)
		public void getcertificateno() throws ClassNotFoundException, SQLException, InterruptedException {

			// step1 load the driver class

			Class.forName("oracle.jdbc.driver.OracleDriver");
			// step2 create the connection object
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.11.206.10:1527:trftest2", "a_nour_stg",
					"123456");

			// step3 create the statement object
			System.out.println("connected to DB to get compared companies");

			Statement stmt3 = con.createStatement();
			// step4 execute query
			ResultSet rs = stmt3.executeQuery("select  count(distinct CAP_ID) AS COUNT from tf_cml_compare_organizations where \r\n" + 
					"comparison_type in (2,3) and status = 2 AND ACTION_TYPE=3\r\n" + 
					"union\r\n" + 
					"select  count(distinct CAP_ID) as COUNT from tf_cml_compare_organizations where \r\n" + 
					"comparison_type in (2,3) and status = 2 AND ACTION_TYPE=2");

			System.out.println("connected to DB to get updates companies");
			Thread.sleep(10000);
		
			/*while (rs.next()) {
				
		
			NOCS_TO_BE_APPLIED = rs.getString(1);
			System.out.println("Count of NOCS TO BE APPLIED = " + NOCS_TO_BE_APPLIED);
			}*/
			
		
				
			//System.out.println("Count of NOCS TO BE IGNORED = " + NOCS_TO_BE_IGNORED);
			

			stmt3.close();
			// closing DB Connection
			con.close();
			// Thread.sleep(10000);

		}



}

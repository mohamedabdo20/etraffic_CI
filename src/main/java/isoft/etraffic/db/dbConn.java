package isoft.etraffic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConn {
	static Connection con = null;

	public static Connection dbconntion() {
 
		// step1 load the driver class
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			System.out.println("JDBC class not found");
		}
		// step2 create the connection object

		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.11.206.11:1527:trftest1", "A_ELKADY_STG", "Arcgis.isoft");
			System.out.println("connected to DB Success");
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("Error to connect to DB");
		}


		return con;

	}
}
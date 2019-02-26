package isoft.etraffic.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

	public static Properties userData = loadProperties(System.getProperty("user.dir") + "\\src\\data\\activites.properties");

	public static Properties loadProperties (String path) {
		
		Properties pro = new Properties();
		
		try {
			FileInputStream stream = new FileInputStream(path);
			pro.load(stream);
		} catch (FileNotFoundException e) {
		System.out.println("File not found " + e.getMessage());
			
		} catch (IOException e) {
			
			System.out.println("IO error " + e.getMessage());
		}
		return pro;
		
		
	}
}

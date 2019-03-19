package isoft.etraffic.testrunner;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;

class ThreadRun extends Thread {

	private String[] tableRow;
	private XmlFile xmlFile;
	private String filePath;

	public ThreadRun(String[] tableRow) {
		this.tableRow = tableRow;
	}

	public void run() {
		TestNG runner = new TestNG();
		List<String> suitefiles = new ArrayList<String>();
		String channel = tableRow[3];
		if (tableRow[0].equals("By Selected Transaction")) {
			if (tableRow[3].contains("-")) {
				tableRow[3] = tableRow[3].substring(0, tableRow[3].indexOf('-'));
			}
			filePath = System.getProperty("user.dir") + "\\XMLFiles" + "\\Run" + tableRow[1] + tableRow[3] + "\\"
					+ tableRow[2] + ".xml";
			suitefiles.add(System.getProperty("user.dir") + "\\XMLFiles" + "\\Run" + tableRow[1] + tableRow[3] + "\\"
					+ tableRow[2] + ".xml");
		}

		else{
			suitefiles.add(System.getProperty("user.dir") + "\\XMLFiles" + "\\"+tableRow[0]+ "\\" +tableRow[1]+ "\\" + tableRow[2] + ".xml");
			filePath = System.getProperty("user.dir") + "\\XMLFiles" + "\\"+tableRow[0]+ "\\" +tableRow[1]+ "\\" + tableRow[2] + ".xml";
			System.out.println("filePath filePath filePath:  " + filePath);
		}
		xmlFile = new XmlFile();
		//System.out.println("Generated Url: " + getEnvURL(tableRow[3], tableRow[4]));
		xmlFile.updateXmlParameters(filePath, getEnvURL(channel, tableRow[4]), tableRow[1] + tableRow[3]);
		runner.setTestSuites(suitefiles);
		runner.run();
	}

	private String getEnvURL(String channel, String environment) {
		if (channel.equals("FTF")) {
			if (environment.equals("Stagging"))
				return "https://tst12c:7791/traffic/faces/jsf/auth/login.jsf";
			if (environment.equals("ER"))
				return "https://er12cr2:7784/traffic/faces/jsf/auth/login.jsf";
		} else {
			if (environment.equals("Stagging")) {
				if (channel.contains("Online") || channel.contains("TrustedAgent") || channel.contains("CallCenter"))
					return "https://tst12c:7793/trfesrv/test_login.jsp";
				else
					return "https://tst12c:7793/trfesrv/public_resources/public-access.do";
			}
			if (environment.equals("ER")) {
				if (channel.contains("Online") || channel.contains("TrustedAgent") || channel.contains("CallCenter"))
					return "https://er12cr2:7784/trfesrv/test_login.jsp";
				else
					return "https://er12cr2:7784/trfesrv/public_resources/public-access.do";
			}
		}
		return "";
	}
}
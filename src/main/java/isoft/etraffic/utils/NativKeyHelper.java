package isoft.etraffic.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class NativKeyHelper {
	 public static void hitEnter() throws AWTException
	    {
	        Robot robot = new Robot();

	        robot.keyPress(KeyEvent.VK_ENTER);
	    }
	  
	 public static void hitTab() throws AWTException
	    {
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_TAB);
	    }
	 public static void hitLeftArrow() throws AWTException
	    {
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_LEFT);
	    }
	 
	 public static void hitRightArrow() throws AWTException
	    {
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_RIGHT);
	    }
	 public static void hitESC() throws AWTException
	    {
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_ESCAPE);
	    }
}

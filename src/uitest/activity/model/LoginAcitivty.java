package uitest.activity.model;

import org.openqa.selenium.WebElement;

import pers.vinken.appiumUtil.elementManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class LoginAcitivty {

	public static void setLockPattern(AppiumDriver driver)
			throws Exception {


		WebElement welem = driver.findElementById("lock_pattern");

		 int startX = welem.getLocation().getX();
		 int startY = welem.getLocation().getY();
		 int height = welem.getSize().getHeight();
		 int width = welem.getSize().getWidth();
		 int yStep = height / 4;
		 int xStep = width / 4;
		 
		 int beginX = startX + xStep;
		 int beginY = startY + yStep;
		 
		 
		TouchAction touchAction1 = new TouchAction(driver);
		touchAction1.press(beginX,beginY).moveTo(xStep,0).moveTo(xStep,0).moveTo(0,yStep).moveTo(0,yStep).release().perform();
	}
}

package pers.vinken.appiumUtil;

import io.appium.java_client.AppiumDriver;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

public class AssertUtiil {


	/**
	 * 判断是否通过验证，如果不通过结束并截图,并添加具体问题描述
	 * 
	 * @param bl
	 * @param caseName
	 */
	public static void Myassert(AppiumDriver<WebElement> driver, String message, boolean bl) {
		if (bl == false) {
			ImageUtil.snapshot(driver, getTestCaseName(), message);
			
		}
		driverUtil.sleepTime(3000);
		Assert.assertTrue(message, bl);

}

	public static String getTestCaseName(){
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}
	
}
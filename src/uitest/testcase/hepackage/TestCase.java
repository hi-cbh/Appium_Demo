package uitest.testcase.hepackage;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import pers.vinken.appiumUtil.AssertUtiil;
import pers.vinken.appiumUtil.driverUtil;
import pers.vinken.appiumUtil.elementManager;
import uitest.activity.model.LoginAcitivty;

public class TestCase {
	private AppiumDriver driver;
	
	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "MXF0215911001825");
		capabilities.setCapability("platformVersion", "5.1.1");

		capabilities.setCapability("appPackage","com.cmcc.wallet");
		capabilities.setCapability("appActivity", ".LoadingActivity");

		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		driverUtil.sleepTime(8000);
		
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}
	
//	@Test
//	public void fistLogin(){
//		
//		if(elementManager.isExistenceById(driver, "contentTV")){
//			elementManager.clickByName(driver, "确定");
//		}
//		
//		elementManager.clickByName(driver, "继续");
//		
//		driverUtil.sleepTime(5000);
//		
//		driverUtil.swipeToLeft(driver);
//		driverUtil.sleepTime(500);
//		driverUtil.swipeToLeft(driver);
//		driverUtil.sleepTime(500);
//		driverUtil.swipeToLeft(driver);
//		driverUtil.sleepTime(500);
//		driverUtil.swipeToLeft(driver);
//		driverUtil.sleepTime(500);
//		
//		elementManager.clickById(driver, "startBtn");
//		
//		driverUtil.sleepTime(5000);
//		
//		elementManager.clickByName(driver, "同意");
//		elementManager.clickById(driver, "ckb_nfc");
//		elementManager.clickByName(driver, "忽略");
//		
//		elementManager.clickPoint(driver);
//		elementManager.clickPoint(driver);
//		elementManager.clickPoint(driver);
//		elementManager.clickPoint(driver);
//		
//		driverUtil.sleepTime(5000);
//		
//	}
	
	
	
	//登陆
	//@Test
	public void testCase001() throws Exception {
		
		//手势解锁
		if (elementManager.isExistenceById(driver, "lock_pattern")) {
			LoginAcitivty.setLockPattern(driver);
		}
		
		driverUtil.sleepTime(2000);
		
		//点击弹窗
		if(elementManager.isExistenceById(driver, "image_real_name")){
			driver.findElementById("com.cmcc.wallet:id/image_real_name").click();
		}

		elementManager.clickById(driver, "layout_head");

		//点击弹窗
		if(elementManager.isExistenceById(driver, "image_real_name")){
			driver.findElementById("com.cmcc.wallet:id/image_real_name").click();
		}
		
		
		driverUtil.swipeToUp(driver);
		
		elementManager.clickByName(driver, "更多");
		
		elementManager.clickById(driver, "exit_mocam_button");
		
		elementManager.clickByName(driver, "确定");
	}
	
	
	//付款码
	//@Test
	public void testCase002() throws Exception{
		//手势解锁
		if (elementManager.isExistenceById(driver, "lock_pattern")) {
			LoginAcitivty.setLockPattern(driver);
		}
		
		driverUtil.sleepTime(2000);
		
		//点击弹窗
		if(elementManager.isExistenceById(driver, "image_real_name")){
			driver.findElementById("com.cmcc.wallet:id/image_real_name").click();
		}

		elementManager.clickByName(driver, "付款码");
		
		driverUtil.sleepTime(3000);
		
		elementManager.clickByName(driver, "修改");
		
		elementManager.clickByName(driver, "300");
		
		elementManager.intoContentEditTextById(driver, "topSpeedPasswordEdit_cyber_gtf", "159357");
		
		driverUtil.sleepTime(2000);
		
		elementManager.clickById(driver, "topSpeedModifyMoneyButton_cyber_gtf");
		
		driverUtil.sleepTime(5000);
		
		elementManager.clickByName(driver, "确定");
		
		driverUtil.sleepTime(5000);
		
		String text = elementManager.getTextViewNameById(driver , "topSpeedMonthMonneyText_cyber_gtf");
		
		AssertUtiil.Myassert(driver, "验证失败", text.contains("300"));
		
		driverUtil.pressBack(driver);
		
	}
	
	@Test
	public void testcase003() throws Exception{
		//手势解锁
		if (elementManager.isExistenceById(driver, "lock_pattern")) {
			LoginAcitivty.setLockPattern(driver);
		}
		
		driverUtil.sleepTime(2000);
		
		//点击弹窗
		if(elementManager.isExistenceById(driver, "image_real_name")){
			driver.findElementById("com.cmcc.wallet:id/image_real_name").click();
		}
		
		driverUtil.swipeToUp(driver);
		
		elementManager.clickByName(driver, "爱奇艺");
		
		driverUtil.sleepTime(5000);
		
		String url = driver.getCurrentUrl();
		
		System.out.println("url" + url);
		
		driverUtil.sleepTime(2000);
	}
	
	
	
}

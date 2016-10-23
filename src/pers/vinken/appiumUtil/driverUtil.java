package pers.vinken.appiumUtil;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDeviceActionShortcuts;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import org.junit.Assert;


/**
 * 用于直接操作设备，如按坐标点击，滑动等。
 * 
 */

public class driverUtil {

  /**
   * 特殊上滑，按照坐标屏幕尺寸的比例滑动
   * 
   * @param driver
   *          AndroidDriver
   * @param beginXpercent
   *          传入滑动起点在从左到右宽度的百分比(1-99之间)
   * @param endXpercent
   *          传入滑动终点在从左到右宽度的百分比(1-99之间)
   * @param beginYpercent
   *          传入滑动起点在从上到下高度的百分比(1-99之间)
   * @param endYpercent
   *          传入滑动终点在从上到下高度的百分比(1-99之间)
   *          
   */
  public static void swipeWithPercent(AndroidDriver driver, int beginXpercent, 
      int beginYpercent, int endXpercent, int endYpercent) {
    Assert.assertFalse("参数传入错误", 
        (beginXpercent <= 0 || beginXpercent >= 100) && (beginYpercent <= 0 || beginYpercent >= 100)
        && (endXpercent <= 0 || endXpercent >= 100) && (endYpercent <= 0 || endYpercent >= 100));
    int x = driver.manage().window().getSize().width;
    int y = driver.manage().window().getSize().height;
    try {
      driver.swipe(x / 100 * beginXpercent, y / 100 * beginYpercent, 
          x / 100 * endXpercent, y / 100 * endYpercent, 300);
      Thread.sleep(1000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 点击坐标
   * 
   * @param driver
   *          AndroidDriver
   * @param x
   *          the X coordinate of the upper-left corner of the screen
   * @param y
   *          the Y coordinate of the upper-left corner of the screen
   */
  public static void clickByCoordinate(AndroidDriver driver, int x, int y) {
    TouchAction ta = new TouchAction(driver);
    try {
      ta.tap(x, y).release().perform();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 点击相对坐标 传入长宽的百分比
   * 
   * @param driver
   *          AndroidDriver
   * @param i
   *          宽度从左到右的百分比
   * @param j
   *          长度从上到下的百分比
   */
  public static void clickScreenWithPercentage(AndroidDriver driver, int i, int j) {
    int width = driver.manage().window().getSize().width;
    int height = driver.manage().window().getSize().height;
    try {
      driver.tap(1, width * i / 100, height * j / 100, 200);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  
  /**
	 * 休眠 1000 = 1秒
	 * 
	 * @param num
	 */
	public static void sleepTime(int num) {
		try {
			System.out.println("Wait time: " + num / 1000
					+ "s, doing something...");
			Thread.sleep(num);
		} catch (Exception e) {
			System.out.println("[ error ] Time out");

		}
	}
	/**
	 * 手势向左滑动
	 */
	public static void swipeToLeft(AppiumDriver driver) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, 500);
		System.out.println("[ doing ] swipeToLeft ");
	}

	/**
	 * 手势向右滑动
	 */
	public static void swipeToRight(AndroidDriver driver) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, 500);
		System.out.println("[ doing ] swipeToRight ");
	}

	/**
	 * 手势向右滑动,添加水平高度变量（即点击屏幕高、中、低，向右滑） location选项为high\mid\bottom
	 */
	public static void swipeToLeft(AndroidDriver driver,String location) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		if (location.equals("bottom")) {
			driver.swipe(width * 3 / 4, height * 3 / 4, width / 4,
					height * 3 / 4, 500);
			System.out.println("[ doing ] swipeToLeft ");
		} else if (location.equals("mid")) {
			driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, 500);
			System.out.println("[ doing ] swipeToLeft ");
		} else if (location.equals("high")) {
			driver.swipe(width * 3 / 4, height / 4, width / 4, height / 4, 500);
			System.out.println("[ doing ] swipeToLeft ");
		} else {
			System.out.println("[ error ] location null ");
		}

	}

	/**
	 * 手势向右滑动,添加水平高度变量（即点击屏幕高、中、低，向右滑） location选项为high\mid\bottom
	 */
	public static void swipeToRight(AndroidDriver driver,String location) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		if (location.equals("bottom")) {
			driver.swipe(width / 4, height * 3 / 4, width * 3 / 4,
					height * 3 / 4, 500);
			System.out.println("[ doing ] swipeToRight ");
		} else if (location.equals("mid")) {
			driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, 500);
			System.out.println("[ doing ] swipeToRight ");
		} else if (location.equals("high")) {
			driver.swipe(width / 4, height / 4, width * 3 / 4, height / 4, 500);
			System.out.println("[ doing ] swipeToRight ");
		} else {
			System.out.println("[ error ] location null ");
		}

	}

	/**
	 * 手势向下滑动
	 */
	public static void swipeToDown(AndroidDriver driver) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 1000);
		// wait for page loading
	}

	/**
	 * 
	 * 手势向上滑动
	 */
	public static void swipeToUp(AppiumDriver driver) {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, 1000);
		// wait for page loading
	}

	
	public static void pressBack(AppiumDriver driver){
		
		((AndroidDeviceActionShortcuts) driver).pressKeyCode(AndroidKeyCode.BACK);
	}
	
	
}

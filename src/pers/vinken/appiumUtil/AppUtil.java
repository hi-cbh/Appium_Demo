package pers.vinken.appiumUtil;


import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AppUtil {

	public static AndroidDriver<WebElement> driver;
	public static String imagePath = "";
	public static String myport = "";
	private static String appActivity = ".Main";
	private static String packageName = "com.chinamobile.contacts.im";
	
	// ////////////////////////////////////辅助方法//////////////////////////////////////////


	
	

	
	/**
	 * 截取子字符串
	 * @param str
	 * @param string
	 * @return
	 */
	public static String getSubString(String str, String string){
		return str.substring(str.indexOf(string));
	}
	
	/**
	 * 截取子字符串
	 * @param str
	 * @param string
	 * @return
	 */
	public static String getSubString(String str, String string, int index){
		return str.substring(str.indexOf(string) + index);
	}
	
	

	// ///////////////////////////公共模块///////////////////

	
	/**
	 * 判断是否通过验证，如果不通过结束并截图
	 * 
	 * @param bl
	 * @param caseName
	 */
	public static void Myassert(boolean bl, String caseName) {
		if (bl == false) {
			AppUtil.snapshot(driver, caseName);
		}
		sleepTime(3000);
		Assert.assertTrue(bl);
	}

	/**
	 * 判断是否通过验证，如果不通过结束并截图,并添加具体问题描述
	 * 
	 * @param bl
	 * @param caseName
	 */
	public static void Myassert(String message, boolean bl, String caseName) {
		if (bl == false) {
			AppUtil.snapshot(driver, caseName, message);
		}
		sleepTime(3000);
		Assert.assertTrue(message, bl);
	}


	/**
	 * 判断是否通过验证，如果不通过结束并截图,并添加具体问题描述,自动添加用例名称
	 * 
	 * @param bl
	 * @param caseName
	 */
	public static void Myassert(String message, boolean bl) {
		if (bl == false) {
			AppUtil.snapshot(driver, getMethodName(), message);
		}
		sleepTime(3000);
		Assert.assertTrue(message, bl);
	}


	

	
	

	
	
	// /////////////////////////////通用基础方法////////////////////////////////////////////////////



	/***
	 * 检查网络
	 * 
	 * @return 是否正常
	 */
	public static boolean checkNet() {
		String text = driver.getConnection().toString();
		System.out.println("text: " + text);
		// 获取联网状态注意：没卡时，data也为true，只有在飞行模式下，才显示false
		if (text.contains("Wifi: true") && text.contains("Data: true")) {
			System.out.println("checkNet: true");
			return true;
		} else {
			System.out.println("checkNet: false");
			return false;
		}

	}

	/**
	 * 点击控件ID，粘贴已有的内容
	 */
	public static void pasteString(String id) {
		clickById(id);
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_V,
				AndroidKeyCode.META_CTRL_MASK);
	}

	/**
	 * 输入元素列表，查找的字段
	 * 
	 * @param list
	 * @param search
	 * @return
	 */
	public static boolean searListContainName(List<WebElement> list, String search) {
		// 如果等于空或者null返回false
		if ((list.isEmpty()) || list == null) {
			return false;
		}

		// 找到返回true
		for (WebElement we : list) {
			if (we.getText().contains(search)) {
				return true;
			}
		}

		// 找不到返回false
		return false;
	}

	/**
	 * 根据控件ID获取 元素列表
	 * 
	 * @param id
	 * @return
	 */
	public static List<WebElement> getLisWebElementById(String id) {
		List<WebElement> list = driver.findElementsById(id);
		return list;
	}

	/**
	 * 返回列表元素，根据列表的顶部，或底部 location: start\end
	 * 
	 * @param id
	 * @param location
	 * @return
	 */
	public static WebElement getWebElementInList(String id, String location) {

		// 获取元素列表
		List<WebElement> list = driver.findElementsById(id);
		// 获取列表长度
		int size = list.size();

		WebElement we = null;

		if (location.equals("start")) {
			System.out.println("getWebElementInList size: " + size);
			we = list.get(0);
		} else if (location.equals("end")) {
			System.out.println("getWebElementInList size: " + size);
			we = list.get(size - 1);
		} else {
			System.out.println("getWebElementInList size: " + size);
			we = null;
		}

		return we;
	}

	/**
	 * 对列表元素遍历，搜索“含有” content字段的元素。（使用contains方法）
	 * 
	 * @param list
	 * @param content
	 * @return
	 */
	public static WebElement getWebElementInList(List<WebElement> list, String content) {
		System.out.println("[start] getWebElementInList");
		// 如果为空，返回
		if (list == null) {
			System.out.println("[ end ] getWebElementInList is null");
			return null;
		}
		for (WebElement we : list) {
			System.out.println("text " + we.getText());
			if (we.getText().contains(content)) {
				System.out.println("[ end ] getWebElementInList not null");
				return we;
			}
		}
		System.out.println("[ end ] getWebElementInList is null");
		return null;
	}

	/**
	 * 根据控件ID获取元素列表，sytle分别是(Image、TextView、CheckBox)类型。
	 * 
	 * @param id
	 * @param sytle
	 * @return
	 */
	public static List<WebElement> getWebElementList(String sytle, String id) {
		System.out.println("[ start ] getWebElementList");
		List<WebElement> weList = new ArrayList<WebElement>();
		// 获取Image类型，且匹配的元素
		if (sytle.equals("ImageView")) {
			for (WebElement webElement : getAllImageView()) {
				String str = webElement.getAttribute("resourceId");
				String subStr = str.substring(str.indexOf('/') + 1);
				if (subStr.equals(id)) {
					weList.add(webElement);
				}
			}
			System.out.println("[ return ] ImageView size: " + weList.size());
		}// 获取TextView类型，且匹配的元素
		else if (sytle.equals("TextView")) {
			for (WebElement webElement : getAllTextView()) {
				String str = webElement.getAttribute("resourceId");
				String subStr = str.substring(str.indexOf('/') + 1);
				if (subStr.equals(id)) {
					weList.add(webElement);
				}
			}
			System.out.println("[ return ] TextView size: " + weList.size());
		}
		// 获取CheckBox类型，且匹配的元素
		else if (sytle.equals("CheckBox")) {
			for (WebElement webElement : getAllCheckBox()) {
				String str = webElement.getAttribute("resourceId");
				String subStr = str.substring(str.indexOf('/') + 1);
				if (subStr.equals(id)) {
					weList.add(webElement);
				}
			}
			System.out.println("[ return ] CheckBox size: " + weList.size());
		}
		// 若不匹配类型，返回空列表
		else {
			weList = null;
		}
		System.out.println("[ end ] getWebElementList");
		return weList;
	}

	/**
	 * 根据文字，获取其中的数字
	 */
	public static int getNumerByText(String txt) {
		// 提取数字
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(txt);
		// 发生异常时，返回-1
		int num = 0;
		try {
			// 将获取数据转为int类型
			num = Integer.parseInt(m.replaceAll("").trim());
		} catch (Exception ex) {

			num = -1;
			// System.out.println("");
		}
		System.out.println("getNumerByText: " + num);

		return num;

	}

	/**
	 * 根据控件的ID，获取控件文字中的数字
	 * 
	 * @param id
	 * @return
	 */
	public static int getNumById(String id) {
		// 获取元素ID
		WebElement we = driver.findElementById(id);

		// 获取控件text
		String str = we.getAttribute("text");

		// 提取数字
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		// 将获取数据转为int类型
		int num = Integer.parseInt(m.replaceAll("").trim());
		System.out.println("getNumById: " + num);
		return num;
	}

	/**
	 * 手势向左滑动
	 */
	public static void swipeToLeft() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, 500);
		System.out.println("[ doing ] swipeToLeft ");
	}

	/**
	 * 手势向右滑动
	 */
	public static void swipeToRight() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, 500);
		System.out.println("[ doing ] swipeToRight ");
	}

	/**
	 * 手势向右滑动,添加水平高度变量（即点击屏幕高、中、低，向右滑） location选项为high\mid\bottom
	 */
	public static void swipeToLeft(String location) {
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
	public static void swipeToRight(String location) {
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
	public static void swipeToDown() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 1000);
		// wait for page loading
	}

	/**
	 * 
	 * 手势向上滑动
	 */
	public static void swipeToUp() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		driver.swipe(width / 2, height * 3 / 4, width / 2, height / 4, 1000);
		// wait for page loading
	}

	/**
	 * 判断某个元素是否存在,true存在；false不存在。
	 */
	public static boolean isExistenceById(String id) {
		System.out.println("isExistenceById: " + id);
		try {
			driver.findElementById(id);
			System.out.println("isExistenceById: true");
			return true;
		} catch (Exception ex) {
			// 找不到元素
		}
		System.out.println("isExistenceById: false");
		return false;
	}

	/**
	 * 在指定时间内，判断元素是否存在，true存在；false不存在。
	 */
	public static boolean isExistenceById(String id, int timeout) {
		try {
			driver.findElementById(id);
			new WebDriverWait(driver, timeout).until(ExpectedConditions
					.presenceOfAllElementsLocatedBy(By.id(id)));
			System.out.println("isExistenceById: true");
			return true;
		} catch (Exception ex) {
			// 找不到元素
		}
		System.out.println("isExistenceById: false");
		return false;
	}

	/**
	 * 判断某个元素是否存在,true存在；false不存在。
	 */
	public static boolean isExistenceByName(String name) {
		System.out.println("isExistenceByName: " + name);
		try {
			driver.findElementByName(name);
			System.out.println("isExistenceByName: true");
			return true;
		} catch (Exception ex) {
			// 找不到元素
		}
		System.out.println("isExistenceByName: false");
		return false;
	}

	/**
	 * 实现按钮长按，传参为name。（该类方法不建议使用，容易报错）
	 * 
	 * @param name
	 */
	public static void clickLongByName(String name) {
		try {
			TouchAction ta = new TouchAction(driver);
			WebElement el = driver.findElementByName("name");
			// WebElement el = driver.findElementById(packageName + ":id/" +
			// name);
			ta.longPress(el).waitAction(5000).release().perform();
		} catch (Exception e) {
			System.out.println("clickLongTime error.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 实现按钮长按，传参为WebElement（该类方法不建议使用，容易报错）
	 * 
	 * @param el
	 */
	public static void clickLongByWebElement(WebElement el) {
		try {
			TouchAction ta = new TouchAction(driver);
			ta.longPress(el).waitAction(2000).release().perform();
		} catch (Exception e) {
			System.out.println("clickLongTime error.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 调用JS脚本实现长按，参数是WebElement元素
	 * 
	 * @param element
	 */
	public static void clickLongByElementUseJs(WebElement element) {
		System.out.println("[start] clickLongByElementUseJs");
		JavascriptExecutor js = driver;
		HashMap<String, String> tapObject = new HashMap<String, String>();
		tapObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: longClick", tapObject);
		System.out.println("[ end ] clickLongByElementUseJs");

	}

	/**
	 * 调用JS脚本实现长按，参数是txt
	 * 
	 * @param TextViewName
	 */
	public static void clickLongByNameUseJs(String TextViewName) {
		System.out.println("[start] clickLongByNameUseJs " + TextViewName);
		// 这里容易出问题，查找控件经常出错
		WebElement element = getFirstTextView(TextViewName, 1);
		if(element == null){
			System.out.println("对象为空");
			return;
		}
		JavascriptExecutor js = driver;
		HashMap<String, String> tapObject = new HashMap<String, String>();
		tapObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: longClick", tapObject);
		System.out.println("[ end ] clickLongByNameUseJs");

	}

	/**
	 * 调用JS脚本实现长按，参数是txt
	 * 
	 * @param TextView
	 *            id
	 */
	public static void clickLongByIdUseJs(String id) {
		System.out.println("[start] clickLongByIdUseJs");
		WebElement element = driver.findElementById(id);
		JavascriptExecutor js = driver;
		HashMap<String, String> tapObject = new HashMap<String, String>();
		tapObject.put("element", ((RemoteWebElement) element).getId());
		js.executeScript("mobile: longClick", tapObject);
		System.out.println("[ end ] clickLongByIdUseJs");

	}

	/**
	 * 通过控件的id，点击控件
	 * 
	 * @param id
	 */
	public static void clickById(String id) {
		try {
			System.out.println("[start] click: " + id);
			driver.findElement(By.id(id)).click();
			System.out.println("[ end ] " + id + ".click");
		} catch (Exception ex) {
			System.out.println("Can not find " + id);
			// ex.printStackTrace();
		}
	}

	/**
	 * 通过控件的txt，点击控件
	 * 
	 * @param name
	 */
	public static void clickByName(String name) {
		try {
			sleepTime(1000);
			System.out.println("[start] click: " + name);
			driver.findElement(By.name(name)).click();
			System.out.println("[ end ] " + name + ".click");
		} catch (Exception ex) {
			System.out.println("Can not find " + name);
			// ex.printStackTrace();
		}
	}

	/**
	 * 清空输入框内容
	 * 
	 * @param text
	 */
	public static void clearText(String text) {
		System.out.println("[start] Clear Text " + text);
		if (text == null) {
			return;
		}

		// 光标移动到末尾
		driver.pressKeyCode(123);

		// 逐一删除内容
		for (int i = 0; i < text.length(); i++) {
			driver.pressKeyCode(AndroidKeyCode.DEL);
		}
		System.out.println("[ end ] Clear Text finish ");
	}

	/**
	 * 获取当前界面上，所有的textView，运行比较慢； 注意：若列表过长不建议使用，容易出现找不到元素，返回null
	 * 
	 * @return
	 */
	public static List<WebElement> getAllTextView() {
		System.out.println("find All TextView");
		List<WebElement> lis = driver
				.findElementsByClassName("android.widget.TextView");
		return lis;
	}

	/**
	 * 获取当前界面上，所有的CheckBox，运行比较慢； 注意：若列表过长不建议使用，容易出现找不到元素，返回null
	 * 
	 * @return
	 */
	public static List<WebElement> getAllCheckBox() {
		System.out.println("find All CheckBox");
		List<WebElement> lis = driver
				.findElementsByClassName("android.widget.CheckBox");
		return lis;
	}

	/**
	 * 获取当前界面上，所有的ImageView，运行比较慢； 注意：若列表过长不建议使用，容易出现找不到元素，返回null
	 * 
	 * @return
	 */
	public static List<WebElement> getAllImageView() {
		System.out.println("find All ImageView");
		List<WebElement> lis = driver
				.findElementsByClassName("android.widget.ImageView");
		return lis;
	}

	/**
	 * 对上面的获取所有TextView进行优化，获取返回元素的，第二元素
	 * <p>
	 * 调整index可以获取第一元素：默认是1、其他情况为0
	 * </p>
	 * 返回时元素对象
	 * <p>
	 * name：查找的字符串
	 * </p>
	 * 
	 * @return
	 */
	public static WebElement getFirstTextView(String name, int index) {
		System.out.println("[start] getFirstTextView");
		for (WebElement el : getElementsByClassAndIndex(
				"android.widget.TextView", index)) {
			System.out.println("getFirstTextView " + el.getText().toString());
			if (el.getText().toString().equals(name)) {
				System.out.println("[ end ] getFirstTextView");
				return el;
			}
		}
		System.out.println("[ end ] getFirstTextView");
		return null;
	}

	/**
	 * 通过使用UiSelector，获取当前列表某个元素，比较高效。返回List<WebElement>
	 * 
	 * @param classname
	 * @param index
	 * @return
	 */
	public static List<WebElement> getElementsByClassAndIndex(String classname,
			int index) {
		List<WebElement> lis = null;
		System.out.println("[start] findElementsByAndroidUIAutomator");
		lis = driver
				.findElementsByAndroidUIAutomator("new UiSelector().className("
						+ classname + ").index(" + index + ")");
		System.out.println("[ end ] findElementsByAndroidUIAutomator");
		return lis;
	}




	/**
	 * 向输入框收入内容
	 * 
	 * @param id
	 * @param content
	 */
	public static void intoContentEditTextById(String id, String content) {

		try {

			System.out.println("[start] into name: " + id);

			WebElement e = driver.findElementById(id);
			e.click();

			String text = e.getAttribute("text");
			clearText(text);
			e.sendKeys(content);
			// e.submit(); //用户提交内容，一般不用
			System.out.println("[ end ] into content: " + content);
		} catch (Exception ex) {
			System.out.println("[ error ]can not find " + id);
			ex.printStackTrace();
		}
	}

	/**
	 * 向输入框收入内容，若该控件没有ID，只有name，可以使用该方面
	 * 
	 * @param name
	 * @param content
	 */
	public static void intoContentEditTextByName(String name, String content) {

		try {

			System.out.println("[start] into name: " + name
					+ ", this name is Exist: " + isExistenceByName(name));

			WebElement e = driver.findElementByName(name);
			e.click();

			String text = e.getAttribute("text");
			clearText(text);
			e.sendKeys(content);
			// e.submit(); //用户提交内容，一般不用
			System.out.println("[ end ] into content: " + content);
		} catch (Exception ex) {
			System.out.println("[ error ]can not find " + name);
			// ex.printStackTrace();
		}
	}

	/**
	 * 通过控件ID，获取输入框内容
	 * 
	 * @param name
	 * @return
	 */
	public static String getEditTextContent(String name) {

		try {
			System.out.println("[start] get EditText name: " + name);
			WebElement PhoneContent = driver.findElementById(name);
			PhoneContent.click();
			String text = PhoneContent.getAttribute("text");
			System.out.println("[ end ] get EditText text: " + text);
			return text;
		} catch (Exception ex) {
			System.out.println("can not find " + name);
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 这方法适用于查找联系人，不通用。 判断页面是否存在TextView name的元素
	 * mode等于1的时候，使用简单模式；等于0的时候，使用获取所有TextView元素 使用简单模式容易找不到内容，注意使用。
	 * 
	 * @param search
	 * @param mode
	 * @return
	 */
	public static boolean searchContact(String search, int mode) {
		System.out.println("[start] searchContact");
		
		if(isExistenceById("no_contact_text"))
		{
			System.out.println("[ end ] searchContact: no data");
			return false;
		}
		
		// mode 等于1的时候，使用简单模式；等于0的时候，使用获取所有TextView元素
		if (mode == 1) {
			// 搜索所有的textView，找到匹配到联系人
			WebElement el = getFirstTextView(search, 1);
			if (el.getText().equals(search)) {
				System.out.println("getFirstTextView search text: "
						+ el.getText());
				// 找到符合的显示true
				System.out.println("[ end ] searchContact");
				return true;
			}
			// 没有找到返回false
			System.out.println("[ end ] searchContact");
			return false;

		} else if (mode == 0) {
			// 搜索所有的textView，找到匹配到联系人
			for (WebElement el : getAllTextView()) {
				if (el.getText().equals(search)) {
					System.out.println("getAllTextView " + el.getText());
					System.out.println("[ end ] searchContact");
					// 找到符合的显示true
					return true;
				}
			}
			// 没有找到返回false
			System.out.println("[ end ] searchContact");
			return false;

		}
		System.out.println("[ end ] searchContact");
		return false;
	}

	/**
	 * 通过textView name 查找元素，返回找到WebElement对象
	 * 
	 * @param search
	 * @return
	 */
	public static WebElement searchWebElement(String search) {

		// 搜索所有的textView，找到匹配到联系人
		for (WebElement el : getAllTextView()) {
			if (el.getText().equals(search)) {
				System.out.println("find text: " + el.getText());
				// 找到符合的显示true
				return el;
			}
		}
		// 没有找到返回null
		return null;
	}

	/**
	 * 根据控件ID获取该控件的text
	 * 
	 * @param id
	 * @return
	 */
	public static String getTextViewNameById(String id) {
		try {
			System.out.println("[start] get TextView id: " + id);
			String text = driver.findElementById(id).getAttribute("text");
			System.out.println("[ end ] get TextView id: " + text);
			return text;
		} catch (Exception ex) {
			System.out.println("can not find " + id);
			ex.printStackTrace();
		}
		return null;
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
	 * 获取调用该方法的名称
	 * 
	 * @return
	 */
	public static String getMethodName() {

		return Thread.currentThread().getStackTrace()[4].getMethodName();
	}

	public static String getTestCaseName(){
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}
	
	/**
	 * //此方法为屏幕截图
	 * 
	 * @param drivername
	 * @param filename
	 */
	public static void snapshot(AndroidDriver<WebElement> driver,
			String testCasename) {
		// 获取当前工作路径
		String currentPath = System.getProperty("user.dir");

		// 利用 TakesScreenshot接口提供的 getScreenshotAs()方法捕捉屏幕,会将获取到的截图存放到一个临时文件中
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		// 保存路径
		String filePath = currentPath + imagePath;

		// 文件名
		String filename = testCasename + "_" + getCurrentDateTime() + ".jpg";

		try {
			System.out.println("save snapshot path is:" + currentPath
					+ filePath + filename);
			// 将临时文件拷贝到当前工作路径
			FileUtils.copyFile(scrFile, new File(filePath + filename));
		} catch (IOException e) {
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		} finally {
			System.out.println("screen shot finished, it's in " + filePath
					+ " folder");
		}
	}
	
	public static void snapshot(AndroidDriver<WebElement> driver,
			String testCasename, String text){
		
		System.out.println("snapshot");
		// 获取当前工作路径
		String currentPath = System.getProperty("user.dir");

		//String tmpfile = currentPath + "tmp.png";
		//System.out.println("user.dir" + tmpfile);
		
//		 利用 TakesScreenshot接口提供的 getScreenshotAs()方法捕捉屏幕,会将获取到的截图存放到一个临时文件中
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		
		System.out.println("TakesScreenshot");

		//第一种张图片，源图像地址
		String filePath = currentPath + imagePath;
		String srcPath = filePath + "tmp.jpg";
		try {
			FileUtils.copyFile(scrFile, new File(srcPath));
			
			//第二种张图片，目标图像地址
			String filename = testCasename + "_" + getCurrentDateTime() + ".jpg";
			String newpath = filePath +  filename;
			new File(newpath);
			
			pressText2(text, srcPath, newpath, "黑体", 36, Color.RED, 35, 0, 0, 0.5f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	 /**
    * 给图片添加文字水印
    * @param pressText 水印文字
    * @param srcImageFile 源图像地址
    * @param destImageFile 目标图像地址
    * @param fontName 字体名称
    * @param fontStyle 字体样式
    * @param color 字体颜色
    * @param fontSize 字体大小
    * @param x 修正值
    * @param y 修正值
    * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
    */
   public final static void pressText2(String pressText, String srcImageFile,String destImageFile,
           String fontName, int fontStyle, Color color, int fontSize, int x,
           int y, float alpha) {
       try {
           File img = new File(srcImageFile);
           Image src = ImageIO.read(img);
           int width = src.getWidth(null);
           int height = src.getHeight(null);
           //System.out.println("width" + width);
           //System.out.println("height" + height);
           BufferedImage image = new BufferedImage(width, height,
                   BufferedImage.TYPE_INT_RGB);
           Graphics2D g = image.createGraphics();
           g.drawImage(src, 0, 0, width, height, null);
           g.setColor(color);
           g.setFont(new Font(fontName, fontStyle, fontSize));
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                   alpha));
           // 在指定坐标绘制水印文字
//           g.drawString(pressText, (width - (getLength(pressText) * fontSize))
//                   / 2 + x, (height - fontSize) / 2 + y);
           g.drawString(pressText, x, (height - fontSize)* 4 / 5 + y);
           g.dispose();
           ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
	
   /**
    * 给图片添加图片水印
    * @param pressImg 水印图片
    * @param srcImageFile 源图像地址
    * @param destImageFile 目标图像地址
    * @param x 修正值。 默认在中间
    * @param y 修正值。 默认在中间
    * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
    */
   public final static void pressImage(String pressImg, String srcImageFile,String destImageFile,
           int x, int y, float alpha) {
       try {
           File img = new File(srcImageFile);
           Image src = ImageIO.read(img);
           int wideth = src.getWidth(null);
           int height = src.getHeight(null);
           BufferedImage image = new BufferedImage(wideth, height,
                   BufferedImage.TYPE_INT_RGB);
           Graphics2D g = image.createGraphics();
           g.drawImage(src, 0, 0, wideth, height, null);
           // 水印文件
           Image src_biao = ImageIO.read(new File(pressImg));
           int wideth_biao = src_biao.getWidth(null);
           int height_biao = src_biao.getHeight(null);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                   alpha));
           g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                   (height - height_biao) / 2, wideth_biao, height_biao, null);
           // 水印文件结束
           g.dispose();
           ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }


   /**
    * 计算text的长度（一个中文算两个字符）
    * @param text
    * @return
    */
   public final static int getLength(String text) {
       int length = 0;
       for (int i = 0; i < text.length(); i++) {
           if (new String(text.charAt(i) + "").getBytes().length > 1) {
               length += 2;
           } else {
               length += 1;
           }
       }
       return length / 2;
   }

	
	public static String getCurrentDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");// 设置日期格式
		return df.format(new Date());
	}

	public static String getCurrentDate(){
		String date = getCurrentDateTime();
		return date.substring(0, 8);
	}
	
	
	// /获取截图，并对比
	public static boolean sameAs(BufferedImage myImage,
			BufferedImage otherImage, double percent) {
		// BufferedImage otherImage = other.getBufferedImage();
		// BufferedImage myImage = getBufferedImage();
		if (otherImage.getWidth() != myImage.getWidth()) {
			return false;
		}
		if (otherImage.getHeight() != myImage.getHeight()) {
			return false;
		}
		// int[] otherPixel = new int[1];
		// int[] myPixel = new int[1];
		int width = myImage.getWidth();
		int height = myImage.getHeight();
		int numDiffPixels = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (myImage.getRGB(x, y) != otherImage.getRGB(x, y)) {
					numDiffPixels++;
				}
			}
		}
		double numberPixels = height * width;
		double diffPercent = numDiffPixels / numberPixels;
		return percent <= 1.0D - diffPercent;
	}

	public  static BufferedImage getSubImage(BufferedImage image, int x, int y,
			int w, int h) {
		return image.getSubimage(x, y, w, h);
	}

	/**
	 * 获取文件
	 * 
	 * @param f
	 * @return
	 */
	public static BufferedImage getImageFromFile(File f) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// if failed, then copy it to local path for later check:TBD
			// FileUtils.copyFile(f, new File(p1));
			e.printStackTrace();
			System.exit(1);
		}
		return img;
	}

}

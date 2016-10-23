package pers.vinken.appiumUtil;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


public class ImageUtil {

	
   public static String imagePath = "";
  /**
   * 从文件路径中读出图像
   * 
   * @param filePath
   *          文件路径
   * @return return a <code>BufferedImage</code>.
   */
  public static BufferedImage getImageFromPath(String filePath) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(filePath));
    } catch (IOException e) {
      // if failed, then copy it to local path for later check:TBD
      // FileUtils.copyFile(f, new File(p1));
      e.printStackTrace();
      System.exit(1);
    }
    return img;
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
	
	public static void snapshot(AppiumDriver<WebElement> driver,
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
//         g.drawString(pressText, (width - (getLength(pressText) * fontSize))
//                 / 2 + x, (height - fontSize) / 2 + y);
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

//import java.util.concurrent.TimeUnit;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//public class WebTest {
//    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver","/Users/apple/Downloads/chromedriver");//chromedriver服务地址
//        WebDriver driver = new ChromeDriver(); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
//        driver.get("https://psych.liebes.top/st");//打开指定的网站
//        //driver.findElement(By.id("kw")).sendKeys(new  String[] {"hello"});//找到kw元素的id，然后输入hello
//        //driver.findElement(By.id("su")).click(); //点击按扭
//        try {
//            /**
//             * WebDriver自带了一个智能等待的方法。
//            dr.manage().timeouts().implicitlyWait(arg0, arg1）；
//            Arg0：等待的时间长度，int 类型 ；
//            Arg1：等待时间的单位 TimeUnit.SECONDS 一般用秒作为单位。
//             */
//            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);        
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        /**
//         * dr.quit()和dr.close()都可以退出浏览器,简单的说一下两者的区别：第一个close，
//         * 如果打开了多个页面是关不干净的，它只关闭当前的一个页面。第二个quit，
//         * 是退出了所有Webdriver所有的窗口，退的非常干净，所以推荐使用quit最为一个case退出的方法。
//         */
//        driver.quit();//退出浏览器
//    }
//}

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;

import jxl.Cell;      
import jxl.Sheet;   
import jxl.Workbook;    

public class WebTest {
	private String baseUrl;
	private WebDriver driver;
	
	@Before
    public void setUp() throws Exception {
	    System.setProperty("webdriver.chrome.driver","/Users/apple/Downloads/chromedriver");//chromedriver服务地址
        driver = new ChromeDriver(); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
        baseUrl = "https://psych.liebes.top/st";//打开指定的网站
        try {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);        
        } catch (Exception e) {
            e.printStackTrace();
        } 
     }
	@Test
    public void Test1() throws Exception {
         //直接从本地文件创建Workbook  
         InputStream instream = new FileInputStream("/Users/apple/Documents/study/大三下/软件测试/实验/input.xls");   
         Workbook readwb = Workbook.getWorkbook(instream);
         //Sheet的下标是从0开始  
         //获取第一张Sheet表  
         Sheet readsheet = readwb.getSheet(0);  
         //获取Sheet表中所包含的总列数  
         int rsColumns = readsheet.getColumns();  
         //获取Sheet表中所包含的总行数  
         int rsRows = readsheet.getRows();  
         //获取指定单元格的对象引用  
         for (int i = 0; i < rsRows; i++)  
         {  
             driver.get(baseUrl);
             Cell cell = readsheet.getCell(0, i); 
             String username = cell.getContents();
             String password = username.substring(4, 10);
             // 通过 id 找到 input 的 DOM
             WebElement element = driver.findElement(By.id("username"));
             WebElement element1 = driver.findElement(By.id("password"));

             //System.out.println(element.getSize());  
             // 输入关键字
             element.sendKeys(username);
             element1.sendKeys(password);

             // 提交 input 所在的form
             element.submit();
            
             //获取得到的邮箱
             WebElement element2 = driver.findElement(By.xpath("html/body/div/div/a/p"));
            
             String mailByWeb = element2.getText();
             String mailByInfo = readsheet.getCell(1,i).getContents();
            
             assertEquals(mailByInfo, mailByWeb);
            
             System.out.println(element2.getText());  
            
             System.out.println(username);  
         }          
         //关闭读入流
         readwb.close();         
   }
}
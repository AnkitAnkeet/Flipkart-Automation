package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.logging.Level;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.flipkart.com");
        Assert.assertTrue(driver.getCurrentUrl().contains("flipkart"), "Did not launch to flipkart url");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try{
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='_30XB9F']")));
            element.click();
        }catch(Exception e){
            System.out.println("Element not found or not clickable: " + e.getMessage());
        }
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start Test: Flipkart Automation");
        System.out.println("Start test case: TestCase01");
        //search for "Washing Machine"
        Wrappers.searchProduct(driver, "Washing Machine");
        //sort by "Popularity"
        Wrappers.sortBy(driver, "Popularity");
        //print the count of products with less than or equal to 4 rating
        System.out.println(Wrappers.countByRating(driver, 4.0));
        System.out.println("End test case: TestCase01");
       
    }

    @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Start test case: TestCase02");
        //search for "iPhone"
        Wrappers.searchProduct(driver, "iPhone");
        //print the titles
        Wrappers.printOnly(driver, By.xpath("./div/div[@class='KzDlHZ']"), Wrappers.filterByDiscount(driver, 17.0));
        //print the percentages
        Wrappers.printOnly(driver, By.xpath("./div/following-sibling::div/div/div/div[3]/span"), Wrappers.filterByDiscount(driver, 17.0));
        System.out.println("End test case: TestCase02");
    }

    @Test
    public void testCase03() throws InterruptedException {
        System.out.println("Start test case: TestCase03");
        //search for "Coffee Mug"
        Wrappers.searchProduct(driver, "Coffee Mug");
        //use filter 4★ & above
        Wrappers.selectOption(driver, "4", "above");
        //list all titles and img urls of 5 maximum reviewed products  
        Wrappers.numberOfReviewList(driver, 5);
        System.out.println("End test case: TestCase03");
        System.out.println("End Test: Flipkart Automation");
    }
}

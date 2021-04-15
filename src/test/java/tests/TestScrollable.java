package tests;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.scrollablepages.ScrollablePage;

public class TestScrollable {

  WebDriver driver;

  ScrollablePage objScrollablePage;

  @BeforeMethod
  @Parameters("browser")
  public void beforeMethod(String browser) throws Exception {
    if (browser.equalsIgnoreCase("firefox")) {
      driver = new FirefoxDriver();
    } else if (browser.equalsIgnoreCase("chrome")) {
      driver = new ChromeDriver();
    } else if (browser.equalsIgnoreCase("ie")) {
      driver = new InternetExplorerDriver();
    } else {
      throw new Exception("Browser is not correct");
    }

    // Maximize the browser size
    driver.manage().window().maximize();

    // Navigate to the http://www.globalsqa.com/angularJs-protractor/Scrollable/
    driver.get("http://www.globalsqa.com/angularJs-protractor/Scrollable/");

    // wait 10 seconds for driver
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @AfterMethod
  public void afterMethod() {
    // Close browser
    driver.quit();
  }

  @BeforeClass
  @Parameters("browser")
  public void beforeClass(String browser) throws Exception {
    if (browser.equalsIgnoreCase("firefox")) {
      System.setProperty("webdriver.gecko.driver",
          "./src/test/resources/drivers/geckodriver_v0.11.1.exe");
    } else if (browser.equalsIgnoreCase("chrome")) {
      System.setProperty("webdriver.chrome.driver",
          "./src/test/resources/drivers/chromedriver_2.27.exe");
    } else if (browser.equalsIgnoreCase("ie")) {
      System.setProperty("webdriver.ie.driver",
          "./src/test/resources/drivers/IEDriverServer_3.0.0.exe");
    } else {
      throw new Exception("Browser is not correct");
    }
  }

  @Test
  public void TestScrollableDown() {

    // Create Scrollable Page object
    objScrollablePage = new ScrollablePage(driver);

    WebDriverWait wait = new WebDriverWait(driver, 7);
    wait.until(ExpectedConditions.visibilityOf(objScrollablePage.getFirstName()));

    // Scroll down 200 pixels
    objScrollablePage.scrollDown(200);

    Assert.assertTrue(objScrollablePage.getScrollYPosition() == 200);

  }

  @Test
  public void TestScrollableDownAndUp() {

    // Create Scrollable Page object
    objScrollablePage = new ScrollablePage(driver);

    WebDriverWait wait = new WebDriverWait(driver, 7);
    wait.until(ExpectedConditions.visibilityOf(objScrollablePage.getFirstName()));

    // Scroll down 250 pixels
    objScrollablePage.scrollDown(250);

    // Scroll up 90 pixels
    objScrollablePage.scrollUp(90);

    // Verify scroll position
    Assert.assertTrue(objScrollablePage.getScrollYPosition() == 160);

  }


}

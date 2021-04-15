package tests;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import pages.webtablepages.WebTablePage;

public class TestWebTable {

  WebDriver driver;

  WebTablePage objWebTablePage;

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

    // Navigate to the http://www.globalsqa.com/angularJs-protractor/WebTable/
    driver.get("http://www.globalsqa.com/angularJs-protractor/WebTable/");

    // Wait 10 second for driver
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

  @Test(invocationCount = 6)
  public void testSearchByFirstName() {

    // Create Web Table Page object
    objWebTablePage = new WebTablePage(driver);

    WebDriverWait wait = new WebDriverWait(driver, 7);
    wait.until(ExpectedConditions.visibilityOf(objWebTablePage.getSearchFirstName()));

    // Set search by first name
    objWebTablePage.setSearchFirstName("Pol");

    List<WebElement> elements = wait.until(ExpectedConditions
        .visibilityOfAllElementsLocatedBy(By.xpath(".//tr/td[1 and text() = 'Pol']")));

    // Verify if size of list is more than 0
    Assert.assertTrue(elements.size() != 0);
  }

  @Test(invocationCount = 6)
  public void testSearchByLastName() {

    // Create Web Table Page object
    objWebTablePage = new WebTablePage(driver);

    WebDriverWait wait = new WebDriverWait(driver, 7);
    wait.until(ExpectedConditions.visibilityOf(objWebTablePage.getSearchGlobal()));

    // Set search by last name
    objWebTablePage.setSearchGlobal("Germain");

    List<WebElement> elements = wait.until(ExpectedConditions
        .visibilityOfAllElementsLocatedBy(By.xpath(".//tr/td[2 and text() = 'Germain']")));

    // Verify if size of list is more than 0
    Assert.assertTrue(elements.size() != 0);
  }

}

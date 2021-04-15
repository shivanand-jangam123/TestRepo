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
import pages.searchfilterpages.SearchFilterPage;

public class TestSearchFilter {

  WebDriver driver;

  SearchFilterPage objSearchFilterPage;

  @BeforeMethod
  @Parameters("browser")
  public void beforeMethod(String browser) throws Exception {
    System.setProperty("webdriver.chrome.driver",
        "C:\\Users\\shiva\\eclipse-workspace\\PracticeRepo\\src\\test\\resources\\drivers\\chromedriver.exe");
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

    // Navigate to the http://www.globalsqa.com/angularJs-protractor/SearchFilter/
    driver.get("http://www.globalsqa.com/angularJs-protractor/SearchFilter/");

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
    System.setProperty("webdriver.chrome.driver",
        "C:\\Users\\shiva\\eclipse-workspace\\PracticeRepo\\src\\test\\resources\\drivers\\chromedriver.exe");
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
  public void TestSearchByPayee() {

    // Create Search Filter Page object
    objSearchFilterPage = new SearchFilterPage(driver);

    WebDriverWait wait = new WebDriverWait(driver, 7);
    wait.until(ExpectedConditions.visibilityOf(objSearchFilterPage.getSearchPayeeName()));

    // Set search by payee name
    objSearchFilterPage.setSearchPayeeName("InternetBill");

    List<WebElement> elements = wait.until(ExpectedConditions
        .visibilityOfAllElementsLocatedBy(By.xpath(".//tr/td[4 and text() = 'InternetBill']")));

    // Verify if size of list equal 2
    Assert.assertTrue(elements.size() == 2);
  }

  @Test(invocationCount = 6)
  public void TestSearchByAccountAndExpenditurePayee() {

    // Create Search Filter Page object
    objSearchFilterPage = new SearchFilterPage(driver);

    WebDriverWait wait = new WebDriverWait(driver, 7);
    wait.until(ExpectedConditions.visibilityOf(objSearchFilterPage.getSearchAccount()));

    // Set search by account
    objSearchFilterPage.setSearchAccount("Cash");

    // Set search by expenditure payee
    objSearchFilterPage.setSearchExpenditurePayee("PowerBill");

    List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
        By.xpath(".//tr[(td[4 and text() = 'PowerBill']) and (td[2 and text() = 'Cash'])]")));

    // Verify if size of list equal 1
    Assert.assertTrue(elements.size() == 1);
  }

}

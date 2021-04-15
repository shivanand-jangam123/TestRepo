package tests;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import base.BrowserFactory;
import base.DriverManager;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import utils.PropertyStorage;

@CucumberOptions(features = ".\\src\\test\\resources\\features\\",
    extraGlue = {"ecolab\\connect\\stepdefinition",},
    plugin = {"pretty", "html:target/cucumber-reports/cucumber-pretty",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "rerun:target/rerun.txt"},
    tags = "@QuicklistTest")
public class TestMultiForm<CucumberFeatureWrapper> extends BaseTest {
  private TestNGCucumberRunner testNGCucumberRunner;
  private BrowserFactory browserFactory;

  @BeforeClass
  public void beforeClass() {
    testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
  }

  @BeforeMethod
  public void openBrowser() {
    browserFactory = new BrowserFactory();
    browserFactory.openBrowser(PropertyStorage.getBrowser());
//    browserFactory.navigateToUrl();
  }

  @AfterMethod
  public void closeBrowser() {
    browserFactory.closeTest();
  }

  @Test(dataProvider = "scenarios", description = "Scenario Name: ")
  public void testMultiForm() {

    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 5);
    // wait.until(ExpectedConditions.elementToBeClickable(objFormProfilePage.getNextSection()));

    /*
     * // Set user name objFormProfilePage.setUserName("Janek");
     * 
     * // Set user email objFormProfilePage.setUserEmail("Bocian");
     * 
     * // Go to next section "Interests" objFormProfilePage.clickNextSection();
     * 
     * wait.until(ExpectedConditions.elementToBeClickable(objFormInterestsPage.getNextSection()));
     * 
     * // Click playstation button objFormInterestsPage.clickPlaystation();
     * 
     * // Go to next section "Payment" objFormInterestsPage.clickNextSection();
     * 
     * wait.until(ExpectedConditions.elementToBeClickable(objFormPaymentPage.getSubmit()));
     * 
     * // Click submit button objFormPaymentPage.clickSubmit();
     * 
     * // Verify alert text
     * Assert.assertTrue(objFormPaymentPage.getAlertText().contains("awesome!"));
     */

  }

}

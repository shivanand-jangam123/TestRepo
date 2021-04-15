package base;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.safari.SafariDriver;
import io.cucumber.java.en.Given;
import utils.PropertyStorage;

public class BrowserFactory {
  private WebDriver driver;
  protected DriverManager driverManager = new DriverManager();
  public static final String DOWNLOADS_FOLDER =
      System.getProperty("user.dir") + File.separator + PropertyStorage.getDownloadPath();
  String propertyFileName = "config.properties";

  public void openBrowser(String browser) {
    try {
      if (browser.equalsIgnoreCase("chrome")) {
        System.setProperty("webdriver.chrome.driver",
            "C:\\Users\\shiva\\eclipse-workspace\\PracticeRepo\\src\\test\\resources\\drivers\\chromedriver.exe");
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", DOWNLOADS_FOLDER);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--enable-javascript");
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
      } else if (browser.equalsIgnoreCase("firefox")) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", DOWNLOADS_FOLDER);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
            "text/csv,application/vnd.ms-excel,application/pdf");
        profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");
        profile.setPreference("pdfjs.disabled", true);
        profile.setAlwaysLoadNoFocusLib(true);
        driver = new FirefoxDriver();
      } else if (browser.equalsIgnoreCase("safari")) {
        driver = new SafariDriver();
      } else if (browser.equalsIgnoreCase("Edge")) {
      }
    } catch (Exception exception) {
    }
    driverManager.setDriver(driver);
    DriverManager.getDriver().manage().window().maximize();
    DriverManager.getDriver().manage().timeouts()
        .implicitlyWait(Integer.parseInt(PropertyStorage.getImplicitWait()), TimeUnit.SECONDS);
  }

  public void closeTest() {
    DriverManager.getDriver().quit();
  }
}

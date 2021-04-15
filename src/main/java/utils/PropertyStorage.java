package utils;

import org.testng.Reporter;

public class PropertyStorage {

  private PropertyStorage() {}

  private static final String TEST_PROPERTY_FILE = "config.properties";
  private static final String BROWSER_KEY = "webdriver.driver";
  private static final String BASE_URL_KEY = "webdriver.base.url";
  private static final String PATH_TO_CHROME_DRIVER_EXECUTABLE_KEY = "webdriver.chrome.driver";
  private static final String EXPLICIT_WAIT = "webdriver.timeouts.explicitWait";
  private static final String IMPLICIT_WAIT = "webdriver.timeouts.implicitlywait";
  private static final String ENVIRONMENT_KEY = "environment";
  private static final String EXTENT_HTML_REPORT = "extent.html.report";
  private static final String EXTENT_HTML_FIRSTRUN_REPORT = "extent.html.firstrun.report";
  private static final String EXTENT_HTML_RERUN_REPORT = "extent.html.rerun.report";
  private static final String DOWNLOAD_PATH = "download.dir";

  private static String readEnvironmentProperty(String key) {
    return PropertyReader.getInstance().getProperty(key,
        readCommonProperty(ENVIRONMENT_KEY).trim() + ".properties");
  }

  public static String getEnvironmentKey() {
    if (System.getProperty(ENVIRONMENT_KEY) != null) {
      return System.setProperty(ENVIRONMENT_KEY, System.getProperty(ENVIRONMENT_KEY).trim());
    }
    return readCommonProperty(ENVIRONMENT_KEY).trim();
  }


  private static String readCommonProperty(String key) {
    return PropertyReader.getInstance().getProperty(key, TEST_PROPERTY_FILE);
  }

  public static String getBrowser() {
    if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
        .getParameter("browser") == null) {
      return readCommonProperty(BROWSER_KEY);
    }
    return Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest()
        .getParameter("browser");
  }

  public static String getPathToChromeDriverExecutable() {
    return readCommonProperty(PATH_TO_CHROME_DRIVER_EXECUTABLE_KEY);
  }

  public static String getExplicitWait() {
    return readCommonProperty(EXPLICIT_WAIT);
  }

  public static String getImplicitWait() {
    return readCommonProperty(IMPLICIT_WAIT);
  }

  public static String getBaseUrl() {
    if (System.getProperty(BASE_URL_KEY) != null) {
      return System.setProperty(BASE_URL_KEY, System.getProperty(BASE_URL_KEY).trim());
    }
    return readEnvironmentProperty(BASE_URL_KEY);
  }

  public static String getDownloadPath() {
    return readCommonProperty(DOWNLOAD_PATH);
  }

  public static String getGeneratedReportDir() {
    return readCommonProperty(EXTENT_HTML_REPORT);
  }

  public static String getFirstRunReportDir() {
    return readCommonProperty(EXTENT_HTML_FIRSTRUN_REPORT);
  }

  public static String getReRunReportDir() {
    return readCommonProperty(EXTENT_HTML_RERUN_REPORT);
  }
}

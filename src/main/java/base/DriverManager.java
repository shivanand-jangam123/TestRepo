package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import io.cucumber.core.exception.CucumberException;
import io.cucumber.java.Scenario;

public class DriverManager {

  private static WrapperClass wrapperClass;

  private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
  private static ThreadLocal<Scenario> scenario = new ThreadLocal<>();

  public static WebDriver getDriver() {
    WebDriver session = driver.get();
    if (session != null) {
      return session;
    }
    throw new WebDriverException("Webdriver Instance is not found");
  }

  public void setDriver(WebDriver dr) {
    driver.set(dr);
  }

  public void removeDriver() {
    driver.remove();
  }

  public static WrapperClass getWrapperClass() {
    return wrapperClass;
  }

  public static void setWrapperClass(WrapperClass objWrapperClass) {
    wrapperClass = objWrapperClass;
  }

  public static Scenario getScenario() {
    Scenario currentScenario = scenario.get();
    if (currentScenario != null) {
      return currentScenario;
    }
    throw new CucumberException("Cucumber Scenario Instance is not found");
  }

  public static void setScenario(Scenario scenarios) {
    scenario.set(scenarios);
  }

  public void removeScenario() {
    scenario.remove();
  }
}

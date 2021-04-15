package base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import io.cucumber.messages.internal.com.google.common.net.MediaType;
import utils.PropertyStorage;
import utils.WindowsStorage;

public class WrapperClass {

  DriverManager driverManager;

  private static Logger log = LoggerFactory.getLogger(WrapperClass.class);
  static int explicitWaitTimeout = Integer.parseInt(PropertyStorage.getExplicitWait());
  private static final String LOADING_DETAILS = "overlay";
  private static final String JS_SCROLL_TO_ELEMENT = "arguments[0].scrollIntoView(true);";
  private static final String JS_SCROLL_TO_TOP = "window.scrollTo(0, 0);";
  private static final String JS_SCROLL_TO_VIEW_ELEMENT = "arguments[0].scrollIntoView();";
  private static final String USER_DIR = System.getProperty("user.dir");

  public WrapperClass(DriverManager driver) {
    driverManager = driver;
  }

  public static boolean getLinkResponse(String url) {
    try {
      HttpClient client = HttpClientBuilder.create().build();
      HttpGet request = new HttpGet(url);
      HttpResponse response = client.execute(request);
      return (response.getStatusLine().getStatusCode() == 200);
    } catch (Exception e) {
      log.info(e.getMessage());
      return false;
    }
  }

  public void scrollIntoView(WebElement element) {
    ((JavascriptExecutor) DriverManager.getDriver()).executeScript(JS_SCROLL_TO_ELEMENT, element);
    log.info("----------Scrolled element into view-----------");
  }

  public void scrollIntoView(By element) {
    ((JavascriptExecutor) DriverManager.getDriver()).executeScript(JS_SCROLL_TO_ELEMENT,
        DriverManager.getDriver().findElement(element));
    log.info("----------Scrolled element into view-----------");
  }

  public void scrollIntoCenterView(WebElement element) {
    Point p = element.getLocation();
    ((JavascriptExecutor) DriverManager.getDriver()).executeScript(
        String.format("window.scroll(%s,%s - (window.innerHeight / 2));", p.getX(), p.getY()));
    log.info("----------Scrolled element into center view-----------");
  }

  public void scrollIntoCenterView(By element) {
    Point p = DriverManager.getDriver().findElement(element).getLocation();
    ((JavascriptExecutor) DriverManager.getDriver()).executeScript(
        String.format("window.scroll(%s,%s - (window.innerHeight / 2));", p.getX(), p.getY()));
    log.info("----------Scrolled element into center view-----------");
  }

  public void waitForTime(int timeDuration) {
    try {
      Thread.sleep(timeDuration);
    } catch (InterruptedException e) {
      log.info(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void waitForTimeOutInSec(int waitTime) {
    int wait = waitTime * 1000;
    try {
      Thread.sleep(wait);
    } catch (InterruptedException e) {
      log.info(e.toString());
      Thread.currentThread().interrupt();
    }
  }

  public static void explicitWaitTime(int waitTime) {
    try {
      Thread.sleep(waitTime);
    } catch (InterruptedException e) {
      log.info(e.toString());
      Thread.currentThread().interrupt();
    }
  }

  public void waitForPageLoaderToDisappear() {
    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), explicitWaitTimeout);
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(LOADING_DETAILS)));
    DriverManager.getWrapperClass().waitForTimeOutInSec(5);
  }

  public WebElement waitForElementToBeRefreshedAndClickable(By by) {
    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), explicitWaitTimeout);
    return wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));
  }

  public WebElement waitForElementToBeVisible(WebElement webElement) {
    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), explicitWaitTimeout);
    return wait.until(ExpectedConditions.visibilityOf(webElement));
  }

  public void scrollToTop() {
    ((JavascriptExecutor) DriverManager.getDriver()).executeScript(JS_SCROLL_TO_TOP);
    log.info("----------Scrolled page to top-----------");
  }

  public void forcedScrollBottomOfPage() {
    try {
      ((JavascriptExecutor) DriverManager.getDriver()).executeScript(
          "$('html, body').animate({scrollTop: $(window).height(),scrollLeft:$(window).width()});;");
      log.info("Scrolled to Bottom of the page in the current browser view ");
    } catch (Exception exception) {
      log.info(exception.getMessage());
    }
  }

  private ExpectedCondition<String> newWindowHandleIsPresent(
      final Set<String> currentWindowHandles) {
    return new ExpectedCondition<String>() {
      @Override
      public String apply(WebDriver input) {
        Iterator<String> iterator = DriverManager.getDriver().getWindowHandles().iterator();
        while (iterator.hasNext()) {
          String next = iterator.next();
          if (!currentWindowHandles.contains(next)) {
            return next;
          }
        }
        return null;
      }
    };
  }

  public String clickOnElementAndWaitForPopUp(WebElement elementToClick, long timeOutInSeconds) {
    String newWindowHandle;
    final Set<String> openedWindows = DriverManager.getDriver().getWindowHandles();
    if (openedWindows.size() == 1) {
      WindowsStorage.addWindow(DriverManager.getDriver().getWindowHandle());
    }
    click(elementToClick);
    log.info("----------Clicked on Event URL----------");
    newWindowHandle = new WebDriverWait(DriverManager.getDriver(), timeOutInSeconds)
        .until(newWindowHandleIsPresent(openedWindows));
    WindowsStorage.addWindow(newWindowHandle);
    return newWindowHandle;
  }

  public void closeActivePageAndSwitchToPreviousPage() {
    DriverManager.getDriver().close();
    WindowsStorage.removeActiveWindow();
    DriverManager.getDriver().switchTo().window(WindowsStorage.getActiveWindow());
  }

  public void switchToWindow(final String window) {
    DriverManager.getDriver().switchTo().window(window);
  }

  public WrapperClass waitTillElementClickable(By locator) {
    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), explicitWaitTimeout);
    wait.until(ExpectedConditions.elementToBeClickable(locator));
    return this;
  }

  public void waitTillElementClickable(WebElement element) {
    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), explicitWaitTimeout);
    wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  public void enterText(WebElement element, String str) {
    this.waitTillElementClickable(element);
    this.click(element);
    element.clear();
    element.sendKeys(str);
  }

  public void clickPracticedName(By locator) {
    waitForTimeOutInSec(3);
    try {
      DriverManager.getDriver().findElement(locator).click();
    } catch (NoSuchElementException e) {
      try {
        clickWithJS(locator);
      } catch (Exception e1) {
        WebElement element = DriverManager.getDriver().findElement(locator);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(element).click().build().perform();
      }
    }
  }

  public void click(WebElement element) {
    try {
      element.click();
    } catch (Exception e) {
      try {
        this.clickWithJS(element);
      } catch (Exception e1) {
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(element).click().build().perform();
      }
    }
  }

  public String getAttribute(By locator, String attribute) {
    return DriverManager.getDriver().findElement(locator).getAttribute(attribute);
  }

  public String getAttribute(WebElement locator, String attribute) {
    return locator.getAttribute(attribute);
  }

  public String getValueAttribute(By locator) {
    return DriverManager.getDriver().findElement(locator).getAttribute("value");
  }

  public String getValueAttribute(WebElement locator) {
    return locator.getAttribute("value");
  }

  public String getText(By locator) {
    this.waitTillElementDisplyed(locator);
    return DriverManager.getDriver().findElement(locator).getText();
  }

  public void waitTillElementDisplyed(By locator) {
    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), explicitWaitTimeout);
    wait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public void switchToFrameByIndex(int index) {
    DriverManager.getDriver().switchTo().frame(index);
  }

  public void comeOutFromFrame() {
    DriverManager.getDriver().switchTo().defaultContent();
  }

  public void selectFromDropDownusingVisibleText(By locator, String str) {
    this.waitTillElementClickable(locator);
    WebElement weblocator = DriverManager.getDriver().findElement(locator);
    Select dropdown = new Select(weblocator);
    dropdown.selectByVisibleText(str);
  }

  public boolean isElementDisplayed(List<WebElement> elementList) {
    boolean flag = false;
    for (WebElement element : elementList) {
      try {
        flag = element.isDisplayed();
        Assert.assertTrue(element.isDisplayed(),
            element + " : Element is not displayed in the list");
      } catch (Exception e) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(JS_SCROLL_TO_VIEW_ELEMENT, element);
        flag = element.isDisplayed();
        Assert.assertTrue(element.isDisplayed(),
            element + " : Element is not  displayed in the list");
      }
    }
    return flag;
  }

  public boolean isElementDisplayed(By locator) {
    WebElement weblocator = DriverManager.getDriver().findElement(locator);
    boolean flag = false;
    try {
      flag = weblocator.isDisplayed();
    } catch (NoSuchElementException e) {
      return flag;
    } catch (Exception e) {
      JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
      js.executeScript(JS_SCROLL_TO_VIEW_ELEMENT, weblocator);
      flag = weblocator.isDisplayed();
    }
    return flag;
  }

  public boolean isElementDisplyed(WebElement weblocator) {
    boolean flag = false;
    try {
      flag = weblocator.isDisplayed();
    } catch (NoSuchElementException e) {
      return flag;
    } catch (Exception e) {
      JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
      js.executeScript(JS_SCROLL_TO_VIEW_ELEMENT, weblocator);
      flag = weblocator.isDisplayed();
    }
    return flag;
  }

  public void scrollandClick(WebElement element) {
    this.waitForTime(1000);
    JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
    js.executeScript(JS_SCROLL_TO_VIEW_ELEMENT, element);
    this.waitForTime(500);
    this.waitTillElementClickable(element);
    element.click();
  }

  public void navigateBackToPage() {
    DriverManager.getDriver().navigate().back();
  }

  public boolean isElementPresent(By webLocator) {
    boolean isPresent = false;
    if (!DriverManager.getDriver().findElements(webLocator).isEmpty()) {
      isPresent = true;
    }
    return isPresent;
  }

  public boolean isElementPresentCheckUsingJS(WebElement element) {
    boolean isPresent = false;
    JavascriptExecutor jse = (JavascriptExecutor) DriverManager.getDriver();
    try {
      Object obj = jse.executeScript(
          "return typeof(arguments[0]) != 'undefined' && arguments[0] != null;", element);
      if (obj.toString().contains("true")) {
        isPresent = true;
      } else {
        isPresent = false;
      }
    } catch (NoSuchElementException e) {
      log.info(e.getMessage());
    }
    return isPresent;
  }

  public void setText(By locator, String str) {
    this.waitTillElementClickable(locator);
    this.clickPracticedName(locator);
    DriverManager.getDriver().findElement(locator).clear();
    DriverManager.getDriver().findElement(locator).sendKeys(str);
  }

  public void selectFromDropDownusingVisibleText(WebElement weblocator, String str) {
    this.waitTillElementClickable(weblocator);
    Select dropdown = new Select(weblocator);
    dropdown.selectByVisibleText(str);
  }

  public void switchToFrame(WebElement weblocator) {
    DriverManager.getDriver().switchTo().frame(weblocator);
  }

  public boolean isElementDisplayed(WebElement weblocator) {
    boolean flag = false;
    try {
      flag = weblocator.isDisplayed();
    } catch (NoSuchElementException e) {
      JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
      js.executeScript(JS_SCROLL_TO_VIEW_ELEMENT, weblocator);
      flag = weblocator.isDisplayed();
    }
    return flag;
  }

  public boolean isPresent(WebElement weblocator) {
    try {
      weblocator.isDisplayed();
    } catch (NoSuchElementException e) {
      return false;
    }
    return true;
  }

  public boolean isPresent(By locator) {
    try {
      WebElement weblocator = DriverManager.getDriver().findElement(locator);
      weblocator.isDisplayed();
    } catch (NoSuchElementException e) {
      return false;
    }
    return true;
  }

  public void actionClick(By locator) {
    this.waitForTimeOutInSec(3);
    WebElement element = DriverManager.getDriver().findElement(locator);
    Actions actionClick = new Actions(DriverManager.getDriver());
    actionClick.moveToElement(element).click(element).perform();
  }

  public void hoverOverAnElement(WebElement element) {
    Actions builder = new Actions(DriverManager.getDriver());
    builder.moveToElement(element).build().perform();
  }

  public void hoverOverAnElement(By locator) {
    WebElement element = DriverManager.getDriver().findElement(locator);
    Actions builder = new Actions(DriverManager.getDriver());
    builder.moveToElement(element).build().perform();
  }

  public void clickWithJS(By locator) {
    WebElement element = DriverManager.getDriver().findElement(locator);
    JavascriptExecutor jsClick = (JavascriptExecutor) DriverManager.getDriver();
    jsClick.executeScript("arguments[0].click();", element);
  }

  public void clickWithJS(WebElement locator) {
    JavascriptExecutor jsClick = (JavascriptExecutor) DriverManager.getDriver();
    jsClick.executeScript("arguments[0].click();", locator);
  }

  public void scrollToBottom() {
    this.waitForTimeOutInSec(5);
    JavascriptExecutor js = ((JavascriptExecutor) DriverManager.getDriver());
    js.executeScript("scroll(0,400)");
    this.waitForTimeOutInSec(5);
  }

  public void uploadFile(String filePath) {
    try {
      StringSelection path = new StringSelection(filePath);
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);
      Robot rb = new Robot();
      DriverManager.getWrapperClass().waitForTimeOutInSec(1);
      rb.keyPress(KeyEvent.VK_CONTROL);
      rb.keyPress(KeyEvent.VK_V);
      rb.keyRelease(KeyEvent.VK_CONTROL);
      rb.keyRelease(KeyEvent.VK_V);
      DriverManager.getWrapperClass().waitForTimeOutInSec(1);
      rb.keyPress(KeyEvent.VK_ENTER);
      rb.keyRelease(KeyEvent.VK_ENTER);
      DriverManager.getWrapperClass().waitForTimeOutInSec(1);
    } catch (AWTException e) {
      log.info(e.getMessage());
    }
  }

  public void switchToWindow() {
    String parentHandle = DriverManager.getDriver().getWindowHandle();
    new WebDriverWait(DriverManager.getDriver(), 40)
        .until(ExpectedConditions.numberOfWindowsToBe(2));
    Set<String> handles = DriverManager.getDriver().getWindowHandles();
    for (String handle1 : handles)
      if (!parentHandle.equals(handle1)) {
        DriverManager.getDriver().switchTo().window(handle1);
      }
  }

  public void switchToParentWindow() {
    String parent = DriverManager.getDriver().getWindowHandle();
    Set<String> s = DriverManager.getDriver().getWindowHandles();
    Iterator<String> iterator = s.iterator();
    while (iterator.hasNext()) {
      String childWindow = iterator.next();
      if (!parent.equals(childWindow)) {
        DriverManager.getDriver().switchTo().window(parent);
      }
    }
  }

  public String getSelectedValueFromDropDown(WebElement weblocator) {
    Select select = new Select(weblocator);
    WebElement option = select.getFirstSelectedOption();
    String selectedValue = option.getText();
    if (selectedValue != null) {
      return selectedValue;
    } else {
      return null;
    }
  }

  public String getSelectedValueFromDropDown(By weblocator) {
    Select select = new Select(DriverManager.getDriver().findElement(weblocator));
    WebElement option = select.getFirstSelectedOption();
    String selectedValue = option.getText();
    if (selectedValue != null) {
      return selectedValue;
    } else {
      return null;
    }
  }

  public void reloadThePage() {
    DriverManager.getDriver().navigate().refresh();
  }

  public void setZoomLevelTo(int zoomLevel) {
    JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
    js.executeScript("document.body.style.zoom='" + zoomLevel + "%'");
    DriverManager.getWrapperClass().waitForTimeOutInSec(2);
  }

  public void setHtmlCssZoomLevelTo(int zoomLevel) {
    JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
    js.executeScript("document.body.style.transform='scale(" + (float) zoomLevel / 100 + ")';");
    DriverManager.getWrapperClass().waitForTimeOutInSec(2);
  }

  public static void topEmbedScreenshot() {
    explicitWaitTime(2000);
    DriverManager.getWrapperClass().scrollToTop();
    byte[] srcBytes =
        ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    DriverManager.getScenario().attach(srcBytes, MediaType.PNG.toString(),
        DriverManager.getScenario().getName());
  }

  public static void bottomEmbedScreenshot() {
    explicitWaitTime(2000);
    DriverManager.getWrapperClass().scrollToBottom();
    byte[] srcBytes =
        ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    DriverManager.getScenario().attach(srcBytes, MediaType.PNG.toString(),
        DriverManager.getScenario().getName());
  }

  public static byte[] embedScreenshot() {
    explicitWaitTime(2000);
    byte[] srcBytes =
        ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    DriverManager.getScenario().attach(srcBytes, MediaType.PNG.toString(),
        DriverManager.getScenario().getName());
    return srcBytes;
  }

  public static void copyFirstRunReport() {
    File source = new File(USER_DIR + PropertyStorage.getGeneratedReportDir());
    Path dest = Paths.get(USER_DIR + PropertyStorage.getFirstRunReportDir());
    try {
      copyFile(source, dest);
    } catch (IOException e) {
      log.info(e.getMessage());
    }
  }

  public static void copyRerunReport() {
    File source = new File(USER_DIR + PropertyStorage.getGeneratedReportDir());
    Path dest = Paths.get(USER_DIR + PropertyStorage.getReRunReportDir());
    try {
      copyFile(source, dest);
    } catch (IOException e) {
      log.info(e.getMessage());
    }
  }

  public String openNewWindow(long timeOutInSeconds) {
    final Set<String> openedWindows = DriverManager.getDriver().getWindowHandles();
    ((JavascriptExecutor) DriverManager.getDriver()).executeScript("window.open()");
    return new WebDriverWait(DriverManager.getDriver(), timeOutInSeconds)
        .until(newWindowHandleIsPresent(openedWindows));
  }

  public void openNewPage(String url) {
    switchToWindow(openNewWindow(explicitWaitTimeout));
    DriverManager.getDriver().get(url);
  }

  private static void copyFile(File source, Path dest) throws IOException {
    if (Files.exists(dest)) {
      FileUtils.cleanDirectory(new File(dest.toString()));
    } else {
      new File(dest.toString()).mkdir();
    }
    FileUtils.copyFileToDirectory(source, dest.toFile());
  }
}

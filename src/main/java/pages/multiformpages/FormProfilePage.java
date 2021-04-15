package pages.multiformpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import base.DriverManager;

public class FormProfilePage {

  WebDriver driver;

  public FormProfilePage() {
    PageFactory.initElements(DriverManager.getDriver(), this);
    this.driver = DriverManager.getDriver();
  }

  // Find the name input field
  @FindBy(name = "name")
  WebElement userName;

  // Find the email input field
  @FindBy(name = "email")
  WebElement userEmail;

  // Find the "Next Section" button
  @FindBy(xpath = ".//form//a[contains(@href, '#/form/interests')]")
  WebElement nextSection;

  // Set user name
  public void setUserName(String strUserName) {
    userName.sendKeys(strUserName);
  }

  // Set user email
  public void setUserEmail(String strUserEmail) {
    userEmail.sendKeys(strUserEmail);
  }

  // Click "Next Section" button
  public void clickNextSection() {
    nextSection.click();
  }

  // Get WebElement nextSection
  public WebElement getNextSection() {
    return nextSection;
  }

}

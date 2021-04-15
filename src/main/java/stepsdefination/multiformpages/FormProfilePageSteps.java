package stepsdefination.multiformpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FormProfilePageSteps {
	
	WebDriver driver;
	
	// Find the name input field
	@FindBy(name="name")
	WebElement userName;
	
	// Find the email input field
	@FindBy(name="email")
	WebElement userEmail;
	
	// Find the "Next Section" button
	@FindBy(xpath=".//form//a[contains(@href, '#/form/interests')]")
	WebElement nextSection;
	
	public FormProfilePageSteps(WebDriver driver){
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	// Set user name
	public void setUserName(String strUserName){
		userName.sendKeys(strUserName);
	}
	
	// Set user email
	public void setUserEmail(String strUserEmail){
		userEmail.sendKeys(strUserEmail);
	}
	
	// Click "Next Section" button
	public void clickNextSection(){
		nextSection.click();
	}
	
	// Get WebElement nextSection
	public WebElement getNextSection(){
		return nextSection;
	}

}

package stepsdefination.multiformpages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FormPaymentPageSteps {
	
	WebDriver driver;
	
	// Find the "Submit" button
	@FindBy(xpath=".//button[contains(text(), 'Submit')]")
	WebElement submit;
	
	public FormPaymentPageSteps(WebDriver driver){
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	// Click "Submit" button
	public void clickSubmit(){
		submit.click();
	}
	
	// Get alert text
	public String getAlertText(){
		Alert alert;
		String text;

		alert = driver.switchTo().alert();
		text = alert.getText();
		return text;
	}
	
	// Get WebElement submit
	public WebElement getSubmit(){
		return submit;
	}

}

package pages.scrollablepages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScrollablePage {
	
	WebDriver driver;
	
	// Find first name label
	@FindBy(xpath=".//thead/tr[1]/th[contains(text(), 'first name')]")
	WebElement firstName;
	
	// Find div which is table container
	@FindBy(className="table-container")
	WebElement divTableContainer;

	public ScrollablePage(WebDriver driver){
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	// Get first name
	public WebElement getFirstName(){
		return firstName;
	}
	
	// Get first name "By locator"
	public By getFirstNameByLocator(){
		return By.xpath(".//thead/tr[1]/th[contains(text(), 'first name')]");
	}
	
	// Scroll down page
	public void scrollDown(int pixels){				
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollTop += "+pixels+";", divTableContainer);		
	}
	
	// Scroll up page
	public void scrollUp(int pixels){
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollTop -= "+pixels+";", divTableContainer);		
	}
	
	// Get scroll Y position
	public Long getScrollYPosition(){
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Long value = (Long) jse.executeScript("return arguments[0].scrollTop;", divTableContainer);
		return value;
	}
	
}

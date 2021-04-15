package pages.webtablepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WebTablePage {

	WebDriver driver;
	
	// Find field to search for the first name
	@FindBy(xpath=".//th/input[contains(@st-search, 'firstName')]")
	WebElement searchFirstName;
	
	// Find field to search for global
	@FindBy(xpath=".//th/input[contains(@placeholder, 'global search')]")
	WebElement searchGlobal;
	
	public WebTablePage(WebDriver driver){
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	// Set search for first name
	public void setSearchFirstName(String strFirstName){
		searchFirstName.sendKeys(strFirstName);
	}
	
	// Set search for global
	public void setSearchGlobal(String strGlobal){
		searchGlobal.sendKeys(strGlobal);
	}
	
	// Get WebElement searchFirstName
	public WebElement getSearchFirstName(){
		return searchFirstName;
	}
	
	// Get WebEelemt searchGlobal
	public WebElement getSearchGlobal(){
		return searchGlobal;
	}
}

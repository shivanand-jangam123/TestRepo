package pages.searchfilterpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchFilterPage {
	
	WebDriver driver;
	
	// Find field to search by payee name
	@FindBy(id="input1")
	WebElement searchPayeeName;
	
	// Find field to search by account
	@FindBy(id="input2")
	WebElement searchAccount;
	
	// Find field to search by type
	@FindBy(id="input3")
	WebElement searchType;
	
	// Find field to search by expenditure payee
	@FindBy(id="input4")
	WebElement searchExpenditurePayee;
	
	public SearchFilterPage(WebDriver driver){
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	// Set search by payee name
	public void setSearchPayeeName(String strPayeeName){
		searchPayeeName.sendKeys(strPayeeName);		
	}
	
	// Set search by account
	public void setSearchAccount(String strAccount){
		searchAccount.sendKeys(strAccount);
	}
	
	// Set search by type
	public void setSearchType(String strType){
		searchType.sendKeys(strType);
	}
	
	// Set search by expenditure payee
	public void setSearchExpenditurePayee(String strExpPayee){
		searchExpenditurePayee.sendKeys(strExpPayee);
	}
	
	// Get WebElement searchPayeeName
	public WebElement getSearchPayeeName(){
		return searchPayeeName;
	}
	
	// Get WebElement searchAccount
	public WebElement getSearchAccount(){
		return searchAccount;
	}
	
	// Get WebElement searchExpenditurePayee
	public WebElement getSearchExpenditurePayee(){
		return searchExpenditurePayee;
	}
}

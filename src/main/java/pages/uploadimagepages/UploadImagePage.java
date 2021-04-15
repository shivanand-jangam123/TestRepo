package pages.uploadimagepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UploadImagePage {

	WebDriver driver;
	
	// Find input to select file
	@FindBy(css="input")
	WebElement inputImageFile;
	
	// Find img 
	@FindBy(css="img")
	WebElement image;
	
	public UploadImagePage(WebDriver driver){
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	// Get input image file
	public WebElement getInputImageFile(){
		return inputImageFile;
	}
	
	// Set image file
	public void setImageFile(String strImagePath){
		inputImageFile.sendKeys("C:\\Users\\Piotrek\\Desktop\\time1500.png");
	}
	
	// Get image length src
	public int getImageLengthSrc(){
		return image.getAttribute("ng-src").length();
	}
}

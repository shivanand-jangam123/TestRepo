package stepsdefination.multiformpages;

import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BaseTest;
import base.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.multiformpages.FormInterestsPage;
import pages.multiformpages.FormPaymentPage;
import pages.multiformpages.FormProfilePage;
import utils.PropertyStorage;

public class FormInterestsPageSteps extends BaseTest {

  protected FormInterestsPage objFormInterestsPage = new FormInterestsPage();

  protected FormPaymentPage objFormPaymentPage = new FormPaymentPage();

  protected FormProfilePage objFormProfilePage = new FormProfilePage();

  WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 5);

  @When("^Enter Username$")
  public void enter_username() {
    objFormProfilePage.setUserName("Janek");
  }

  @And("^Enter User email$")
  public void enter_user_email() {
    objFormProfilePage.setUserEmail("Bocian");
  }

  @And("^Go to next section Interests$")
  public void go_to_next_section_interests() {
    objFormProfilePage.clickNextSection();
  }

  @And("^Click playstation button$")
  public void click_playstation_button() {
    WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 5);
    wait.until(ExpectedConditions.elementToBeClickable(objFormInterestsPage.getNextSection()));
    objFormInterestsPage.clickPlaystation();
  }

  @And("^Go to next section Payment$")
  public void go_to_next_section_payment() {
    objFormInterestsPage.clickNextSection();
  }

  @And("^Click submit button$")
  public void click_submit_button() {
    wait.until(ExpectedConditions.elementToBeClickable(objFormPaymentPage.getSubmit()));
    objFormPaymentPage.clickSubmit();
  }

  @And("^Verify alert text$")
  public void verify_alert_text() {
    Assert.assertTrue(objFormPaymentPage.getAlertText().contains("awesome!"));
  }

  @Given("Nagivate to URL")
  public void nagivate_to_url() {
    DriverManager.getDriver().get(PropertyStorage.getBaseUrl());
  }

}

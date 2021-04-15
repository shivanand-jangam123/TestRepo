Feature: SampleAutomation Feature Tests

  @RegressionTest @MultiFormTest
  Scenario: Verify MultiForm	 Page1
    Given Nagivate to URL
    When Enter Username
    And Enter User email
    And Go to next section Interests
    And Click playstation button
    And Go to next section Payment
    And Click submit button
    Then Verify alert text

  @RegressionTest @MultiFormTest
  Scenario: Verify MultiForm Page2
    Given Nagivate to URL
    When Enter Username
    And Enter User email
    And Go to next section Interests
    And Click playstation button
    And Go to next section Payment
    And Click submit button
    Then Verify alert text

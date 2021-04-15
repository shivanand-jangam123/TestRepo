Feature: Quicklist Module TestCases

  @RegressionTest
  Scenario: Verify MultiForm	 Page
    Given Nagivate to URL
    When Enter Username
    And Enter User email
    And Go to next section Interests
    And Click playstation button
    And Go to next section Payment
    And Click submit button
    Then Verify alert text
    
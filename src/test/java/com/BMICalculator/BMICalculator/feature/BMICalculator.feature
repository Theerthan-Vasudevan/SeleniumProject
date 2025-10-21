Feature: BMI Calculator Test

  Scenario: Calculate BMI using calculator.net
    Given the user launches the Chrome browser
    When the user opens the calculator.net homepage
    And the user searches for "bmi" calculator
    And the user enters age as "21"
    And the user selects gender as "male"
    And the user enters height as "178"
    And the user enters weight as "75"
    And the user clicks the calculate button
    Then the user closes the browser

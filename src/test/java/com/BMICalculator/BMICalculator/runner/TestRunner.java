
package com.BMICalculator.BMICalculator.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/com/BMICalculator/BMICalculator/feature/BMICalculator.feature",
    glue = "com.BMICalculator.BMICalculator.StepDefinition",
    plugin = {"pretty"},
    monochrome = true
)
public class TestRunner {
}

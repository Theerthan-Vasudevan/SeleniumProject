# Selenium Test Automation Project

A comprehensive Java-based test automation framework using Selenium WebDriver, TestNG, and Cucumber for end-to-end testing of web applications.

## 🚀 Project Overview

This project contains automated test suites for:
- **OrangeHRM Application** - HR management system testing
- **BMI Calculator** - Calculator.net BMI functionality testing
- **API Testing** - ReqRes API validation using RestAssured

## 🛠️ Tech Stack

- **Java 17** - Programming language
- **Selenium WebDriver 4.34.0** - Web browser automation
- **TestNG 7.9.0** - Testing framework
- **Cucumber 7.11.2** - BDD framework
- **RestAssured 5.3.2** - API testing
- **Maven** - Build and dependency management
- **Apache POI 5.4.1** - Excel file handling

## 📁 Project Structure

```
src/
├── test/
│   └── java/
│       ├── com/
│       │   ├── orangehrm/          # Page Object Model classes
│       │   │   ├── LoginPage.java
│       │   │   ├── LeavePage.java
│       │   │   ├── RecruitmentPage.java
│       │   │   └── OrangeHRMTests.java
│       │   ├── utils/              # Utility classes
│       │   │   └── BaseTest.java
│       │   └── BMICalculator/      # Cucumber BDD tests
│       │       └── BMICalculator/
│       │           ├── feature/    # Feature files
│       │           ├── StepDefinition/ # Step definitions
│       │           └── runner/     # Test runners
│       └── resources/
pom.xml                             # Maven configuration
testng.xml                          # TestNG configuration
```

## 🎯 Test Features

### 1. OrangeHRM Test Suite (Page Object Model)
- **User Authentication** - Login/logout functionality
- **Leave Management** - Apply for leave, view leave records
- **Recruitment Module** - Add candidates, view candidate lists
- **Employee Management** - Create and manage employee records

### 2. BMI Calculator Test (Cucumber BDD)
- **Feature**: Calculate BMI using Calculator.net
- **Scenario**: Complete BMI calculation workflow
- **Browser**: Chrome automation with WebDriver

### 3. API Testing Suite
- **ReqRes API** - CRUD operations testing
- **Response validation** - Status codes and data verification
- **JSON handling** - Request/response processing

## 🔧 Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Chrome browser
- Eclipse IDE (recommended)

### Installation
1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd dayForce
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Import project in Eclipse**
   - File → Import → Existing Maven Projects
   - Select the project directory
   - Import

## 🚀 Running Tests

### Method 1: Eclipse IDE
**OrangeHRM Tests:**
- Right-click `OrangeHRMTests.java` → Run As → TestNG Test

**BMI Calculator Tests:**
- Right-click `TestRunner.java` → Run As → JUnit Test

**Via TestNG XML:**
- Right-click `testng.xml` → Run As → TestNG Suite

### Method 2: Command Line (with Maven)
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=OrangeHRMTests

# Run Cucumber tests
mvn test -Dtest=TestRunner
```

### Method 3: Individual Test Methods
- Right-click any `@Test` method → Run As → TestNG Test

## 📋 Test Configuration

### Browser Configuration
- **Default**: Chrome browser with optimized settings
- **Headless mode**: Available in BaseTest configuration
- **Window management**: Maximized by default
- **Timeouts**: 10s implicit, 60s page load

### Test Data
- **OrangeHRM**: Uses timestamp-based unique data generation
- **BMI Calculator**: Predefined test values (Age: 21, Height: 178cm, Weight: 75kg)
- **API Tests**: Dynamic test data creation

## 🎨 Design Patterns

### Page Object Model (POM)
```java
// Example: LoginPage class
public class LoginPage {
    private WebDriver browserDriver;
    private WebDriverWait driverWait;
    
    // Locators
    private By userInputField = By.name("username");
    
    // Methods
    public void performFullLogin(String userID, String userPassword) {
        // Implementation
    }
}
```

### Test Base Class
```java
// BaseTest provides common setup/teardown
public class BaseTest {
    protected WebDriver webBrowser;
    
    @BeforeMethod
    public void initializeTestEnvironment() {
        // Browser setup
    }
}
```

## 🔍 Key Features

### Test Automation Capabilities
- ✅ **Cross-browser support** - Chrome (extensible to Firefox, Edge)
- ✅ **Parallel execution** - TestNG parallel testing support
- ✅ **Data-driven testing** - Excel integration with Apache POI
- ✅ **BDD approach** - Cucumber for behavior-driven development
- ✅ **API validation** - RestAssured for service testing
- ✅ **Robust waits** - Explicit waits for stable tests
- ✅ **Error handling** - Comprehensive exception management

### Reporting
- **TestNG Reports** - Built-in HTML reports in `test-output/`
- **Cucumber Reports** - Pretty console output
- **Custom logging** - System.out messages for test progress

## 🌐 Test Applications

### 1. OrangeHRM Demo
- **URL**: https://opensource-demo.orangehrmlive.com
- **Credentials**: Admin / admin123
- **Modules**: Dashboard, Leave, Recruitment, PIM

### 2. Calculator.net
- **URL**: https://www.calculator.net
- **Feature**: BMI Calculator functionality
- **Test Flow**: Search → Navigate → Input → Calculate

### 3. ReqRes API
- **URL**: https://reqres.in/api
- **Endpoints**: Users, Register, Login
- **Methods**: GET, POST, PUT, DELETE


## 📈 Test Execution Flow

### OrangeHRM Test Workflow
1. **Authentication** → Login validation
2. **Leave Management** → Apply & view leave
3. **Recruitment** → Add & manage candidates
4. **Cleanup** → Logout & browser closure

### BMI Calculator Workflow
1. **Navigation** → Open calculator.net
2. **Search** → Find BMI calculator
3. **Input** → Enter personal details
4. **Calculation** → Execute BMI computation
5. **Verification** → Validate results
# allure-testrail
Simple integration of allure with test rail to extend test repots

Usage:
```java
@Listeners(TestRailListener.class)
public class TestNgListenerTests {

    @BeforeSuite
    public void beforeSuite() {
         AllureTestRail.registerHandler((testCase, testResult) -> testResult.setDescription(testCase.getCustomCaseDescription()));
         AllureTestRail.registerHandler((testCase, testResult) -> testResult.setFullName(testCase.getTitle()));
    }
```

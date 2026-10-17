# Java Selenium Demo Framework

A compact, production-style UI automation framework for the public SauceDemo site.

## Included

- Java 25 and Maven
- Selenium WebDriver with Selenium Manager, so no manual driver executable is required
- TestNG suites, groups, parallel execution, and assertions
- Page Object Model
- Thread-safe `ThreadLocal<WebDriver>` management
- Chrome, Firefox, and Edge support
- Explicit waits, external configuration, Log4j2 logging
- Extent HTML reporting and screenshots on failure
- Maven local/CI profiles
- Jenkins pipeline and GitHub Actions workflow

## Project structure

```text
src/main/java/com/demo/framework
├── config       Configuration reader
├── driver       Driver factory and thread-local lifecycle
├── pages        Page objects and reusable page actions
├── reporting    Extent report and TestNG listener
└── utils        Wait and screenshot utilities

src/test/java/com/demo/tests
├── BaseTest.java
├── LoginTest.java
└── CheckoutTest.java
```

## Requirements

- JDK 25+
- Maven 3.9+
- Chrome, Firefox, or Edge installed
- Internet access on the first run for Maven dependencies and Selenium Manager

## Run

```bash
mvn clean test
```

Headless CI run:

```bash
mvn clean test -Pci
```

Override settings from the command line:

```bash
mvn clean test -Dbrowser=firefox -Dheadless=true
```

Run one group:

```bash
mvn clean test -Dgroups=smoke
```

## Outputs

- Extent report: `target/reports/extent-report.html`
- Screenshots: `target/screenshots/`
- Logs: `target/logs/automation.log`
- TestNG/Surefire results: `target/surefire-reports/`

## Design notes

Tests contain assertions and business intent only. Locators and UI behavior remain inside page objects. Driver instances and Extent tests are thread-local, which makes the suite safe for TestNG parallel execution. Runtime properties override values from `config.properties`, making the same code suitable for local machines and CI agents.

## Safe next enhancements

For a larger enterprise project, add API clients, database utilities, secret management, Dockerized Selenium Grid, test-data builders, accessibility checks, and rerun policies only for known transient failures.

## Important project-structure note

`TestListener` is stored under `src/test/java` because it depends on TestNG, which is declared with Maven `test` scope. This keeps TestNG out of the main application classpath and prevents `package org.testng does not exist` errors during the Maven `compile` phase.

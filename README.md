# FieldForce QA & Test Automation Suite

End-to-end Manual QA, API Testing, and Cucumber BDD UI Automation suite for the **Test Field Force** web application (`https://test.fieldforceconnect.com`).

---

## Project Overview

This repository consolidates three core quality assurance deliverables:
1. **Manual QA Documentation:** Test Scenarios, Test Cases, Defect Report, and Requirements Traceability Matrix (RTM).
2. **REST API Testing (Postman):** Automated assertions and environment configuration for Authentication and CRM Leads APIs.
3. **UI Test Automation (Cucumber BDD + Java + Selenium):** Data-driven automated regression suite covering Login, Punch-In, and Add Customer workflows.

---

## Tech Stack
* **Manual QA:** Microsoft Excel (Test Scenarios, Test Cases, Defect Log, RTM)
* **API Testing:** Postman (Collections, Environment Variables, Automated Assertions)
* **UI Automation:** Java, Selenium WebDriver, Cucumber BDD, TestNG/JUnit, Maven, Page Object Model (POM)

---

## Repository Structure

```text
FieldForce-End-to-End-QA/
├── docs/
│   └── api-notes.md
├── manual-testing/
│   └── FieldForce_Manual_Testing.xlsx
├── postman/
│   ├── FieldForce_Env.postman_environment.json
│   └── NiMap.postman_collection.json
├── src/
│   ├── main/
│   │   ├── java/fieldforce/
│   │   │   ├── pages/
│   │   │   │   ├── CustomerPage.java
│   │   │   │   ├── DashboardPage.java
│   │   │   │   ├── LoginPage.java
│   │   │   │   └── SignUpPage.java
│   │   │   ├── utils/
│   │   │   │   └── Elements.java
│   │   │   └── BaseClass.java
│   │   └── resources/
│   │       └── config.properties
│   └── test/
│       ├── java/
│       │   ├── fieldforce/test/
│       │   │   └── FieldForceBaseTest.java
│       │   ├── hooks/
│       │   │   └── Hooks.java
│       │   ├── runner/
│       │   │   └── FieldForceTestRunner.java
│       │   └── stepdefinitions/
│       │       └── FieldForceSteps.java
│       └── resources/
│           └── features/
│               └── fieldforce_complete.feature
├── .gitignore
├── pom.xml
└── README.md

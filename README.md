# FieldForce QA & Test Automation Suite

End-to-end Manual QA, API Testing, and Cucumber BDD UI Automation suite for the **Test Field Force** web application (`https://test.fieldforceconnect.com`).

---

## Project Overview

This repository consolidates three core quality assurance deliverables:
1. **Manual QA Documentation:** Test Scenarios, Test Cases, Defect Report, and Requirements Traceability Matrix (RTM).
2. **REST API Testing (Postman):** Automated assertions and environment configuration for Authentication and CRM Leads APIs.
3. **UI Test Automation (Cucumber BDD + Java + Selenium):** Data-driven automated regression suite covering Login, Punch-In, and Add Customer workflows.

---

## Repository Structure

fieldforce-qa-automation/
├── .gitignore
├── README.md
├── pom.xml
├── docs/
│   └── api-notes.md
├── postman/
│   ├── NiMap.postman_collection.json
│   └── FieldForce_Env.postman_environment.json
└── src/
    ├── main/
    │   └── java/
    │       └── fieldforce/
    │           ├── BaseClass.java
    │           ├── pages/
    │           │   ├── customerpage.java
    │           │   ├── dashboardpage.java
    │           │   ├── loginpage.java
    │           │   └── signuppage.java
    │           └── utils/
    │               └── Elements.java
    └── test/
        ├── java/
        │   ├── fieldforce/test/
        │   │   └── field_force_BaseTest.java
        │   ├── hooks/
        │   │   └── Hooks.java
        │   ├── runner/
        │   │   └── fieldforceTestRunner.java
        │   └── stepdefination/
        │       └── FieldForceSteps.java
        └── resources/
            └── Features/
                └── fieldforce_complete.feature

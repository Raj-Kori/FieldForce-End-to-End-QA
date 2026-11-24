# FieldForce QA & Test Automation Suite

End-to-end Manual QA, API Testing, and Cucumber BDD UI Automation suite for the **Test Field Force** web application.

---

## Project Overview

This repository contains three QA workstreams:

1. **Manual QA Documentation:** Test scenarios, test cases, defect report, and Requirements Traceability Matrix (RTM).
2. **REST API Testing (Postman):** The exported collection currently contains valid-login, invalid-login, and Get Leads requests.
3. **UI Test Automation (Cucumber BDD + Java + Selenium):** Browser-based workflows including Login, Punch-In, and Add Customer.

### API coverage clarification

**Add Customer is currently covered in the UI automation workflow, not by a dedicated request in the exported Postman collection.** The Postman collection does not contain an Add Customer API endpoint or request payload. Do not describe Add Customer API testing as demonstrated by this collection until the correct endpoint, request, response, and execution evidence are added.

The manual-testing workbook includes API test cases TC-70 to TC-73 marked as PASS. Those results should be cross-checked against the original Postman execution history or other retained evidence. The current exported collection alone does not substantiate those four Add Customer API results.

---

## Tech Stack

- **Manual QA:** Microsoft Excel (Test Scenarios, Test Cases, Defect Log, RTM)
- **API Testing:** Postman (Collections, Environment Variables, JavaScript Assertions)
- **UI Automation:** Java, Selenium WebDriver, Cucumber BDD, TestNG/JUnit, Maven, Page Object Model (POM)

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
│   │   │   ├── utils/
│   │   │   └── BaseClass.java
│   │   └── resources/
│   └── test/
│       ├── java/
│       └── resources/
├── .gitignore
├── pom.xml
└── README.md
```

## Security

- Do not commit real usernames, passwords, tokens, or other credentials.
- Set secret variables locally in Postman; keep their exported values blank or use placeholders.
- If a real credential was previously committed to a public repository, rotate it. Removing it from the latest file does not remove it from Git history.

## Running the Postman Collection

1. Import `postman/NiMap.postman_collection.json`.
2. Import and select `postman/FieldForce_Env.postman_environment.json`.
3. Set the required variables locally (see `docs/api-notes.md`).
4. Run the requests individually or with Collection Runner.

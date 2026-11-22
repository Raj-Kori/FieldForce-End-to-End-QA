Feature: FieldForce Core Automation Task

  @login
  Scenario Outline: Automate Login Journey with parametrization and validation
    Given the user is on the FieldForce login page
    When the user enters username "<username>" and password "<password>"
    And clicks the Sign In button
    Then the login result should be "<outcome>"

    Examples:
      | username   | password  | outcome |
      | 8433593380 | Hacker007 | success |
      | 8433593380 | Wrong@123 | error   |

  @punchin
  Scenario: Verify Toast or Popup message after PunchIn
    Given the user is on the FieldForce login page
    And the user logs in with valid credentials
    When the user performs PunchIn on the dashboard
    Then a toast or popup message should be displayed after PunchIn

  @addCustomer
  Scenario Outline: Add Customer using parametrization and validate it
    Given the user is on the FieldForce login page
    And the user logs in with valid credentials
    And navigates to the Add Customer section
    When the user adds a customer with name "<customerName>", mobile "<mobile>", email "<email>", and address "<address>"
    Then the add customer outcome should be "<outcome>" for "<customerName>"

    Examples:
      | customerName         | mobile     | email                    | address            | outcome |
      | QuickXpert Infotech  | 8433593380 | contact@quickxpert.test  | Thane West, Mumbai | created |
      | Apex Field Solutions | 9876543211 | sales@apexfield.test     | Ghatkopar, Mumbai  | created |
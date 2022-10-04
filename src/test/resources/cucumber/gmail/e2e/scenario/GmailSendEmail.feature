Feature: To test Gmail send email functionality.

  Scenario: Login scenario test for Gmail

    Given navigate to Gmail page
    When user logged in using username as "yourEmailId" and password as "yourEmailPassword"
    Then home page should be displayed
    When user compose new email and send it to same "surabhigawali1993" with subject as "Incubyte" and body as "Automation QA test for Incubyte"
    Then the email should be displayed in the user's inbox with subject as "Incubyte" and body as "Automation QA test for Incubyte"
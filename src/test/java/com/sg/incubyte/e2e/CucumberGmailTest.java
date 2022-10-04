package com.sg.incubyte.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber/gmail/e2e/scenario/GmailSendEmail.feature")
public class CucumberGmailTest {
}

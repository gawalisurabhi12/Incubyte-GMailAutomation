package com.sg.incubyte.e2e;

import com.sg.incubyte.pageobjects.PageObjects;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailSendEmail extends TestCase {

    private WebDriver driver;

    @Given("navigate to Gmail page")
    public void navigate_to_gmail_page() {
        System.setProperty("webdriver.chrome.driver", "F:\\Tools\\selenium\\chrome_106\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://mail.google.com/");
    }

    @When("user logged in using username as {string} and password as {string}")
    public void user_logged_in_using_username_as_and_password_as(String username, String password) throws Exception {

        if("yourEmailId".equalsIgnoreCase(username)||"yourEmailPassword".equalsIgnoreCase(password)){
            throw new RuntimeException("Kindly provide proper input in the feature file to execute this scenario");
        }

        waitForElementToLoad(driver, By.id(PageObjects.UserName_TextBox));
        driver.findElement(By.id(PageObjects.UserName_TextBox)).sendKeys(username);

        waitForElementToLoad(driver, By.id(PageObjects.UserName_Next));
        driver.findElement(By.id(PageObjects.UserName_Next)).click();
        waitForPageLoad(driver);

        waitForElementToLoad(driver, By.name(PageObjects.Password_TextBox));
        driver.findElement(By.name(PageObjects.Password_TextBox)).sendKeys(password);

        waitForElementToLoad(driver, By.id(PageObjects.Password_Next));
        driver.findElement(By.id(PageObjects.Password_Next)).click();
        waitForPageLoad(driver);
    }

    @Then("home page should be displayed")
    public void home_page_should_be_displayed() {
        waitForElementToLoad(driver, By.name(PageObjects.Smart_Social_Label));
        assertTrue("Verifying home page", driver.findElement(By.name(PageObjects.Smart_Social_Label)).isDisplayed());
    }

    @When("user compose new email and send it to same {string} with subject as {string} and body as {string}")
    public void user_compose_new_email_and_send_it_to_same_with_subject_as_and_body_as(String username, String mailSubject, String mailBody) {

        waitForElementToLoad(driver, By.xpath(PageObjects.Compose_Button));
        driver.findElement(By.xpath(PageObjects.Compose_Button)).click();
        waitForPageLoad(driver);

        waitForElementToLoad(driver, By.name(PageObjects.Mail_TO));
        driver.findElement(By.name(PageObjects.Mail_TO)).sendKeys(String.format("%s@gmail.com", username));

        waitForElementToLoad(driver, By.name(PageObjects.Mail_TO));
        driver.findElement(By.name(PageObjects.Mail_TO)).sendKeys(Keys.ENTER);

        waitForElementToLoad(driver, By.name(PageObjects.Mail_Subject));
        driver.findElement(By.name(PageObjects.Mail_Subject)).sendKeys(mailSubject);

        waitForElementToLoad(driver, By.xpath(PageObjects.Mail_Body));
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath(PageObjects.Mail_Body)));
        actions.click();
        actions.sendKeys(mailBody);
        actions.build().perform();

        waitForElementToLoad(driver, By.xpath(PageObjects.Mail_Send));
        driver.findElement(By.xpath(PageObjects.Mail_Send)).click();
        waitForPageLoad(driver);

        waitForElementToLoad(driver, By.xpath(PageObjects.Gmail_Inbox));
        driver.findElement(By.xpath(PageObjects.Gmail_Inbox)).click();
        waitForPageLoad(driver);

        waitForElementToLoad(driver, By.xpath(PageObjects.Mail_Refresh));
        driver.findElement(By.xpath(PageObjects.Mail_Refresh)).click();
        waitForPageLoad(driver);

        waitForElementToLoad(driver, By.xpath(PageObjects.Starred_Email));
        driver.findElement(By.xpath(PageObjects.Starred_Email)).click();
        waitForPageLoad(driver);

        waitForElementToLoad(driver, By.name(PageObjects.Click_Email));
        driver.findElement(By.name(PageObjects.Click_Email)).click();
        waitForPageLoad(driver);

    }
    @Then("the email should be displayed in the user's inbox with subject as {string} and body as {string}")
    public void the_email_should_be_displayed_in_the_user_s_inbox_with_subject_as_and_body_as(String mailSubject, String mailBody) {
        By subjectByXpath = By.xpath(PageObjects.Check_Mail_Subject.replaceAll("replacethissubject", mailSubject)) ;
        waitForElementToLoad(driver, subjectByXpath);
        assertTrue("Verifying Email Subject", driver.findElement(subjectByXpath).isDisplayed());

        waitForElementToLoad(driver, By.xpath(PageObjects.Mail_Body));
        assertTrue("Verifying Email Content", driver.findElement(By.xpath(PageObjects.Mail_Body)).getText().contains(mailBody));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    public void waitForElementToLoad(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitForPageLoad(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
                        .equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(expectation);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}

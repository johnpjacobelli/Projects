package com.ers.eval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ers.page.ERSPage;

public class E2ETest {
	
	private ERSPage page;
	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String filePath = "src/test/resources/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", filePath);
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}
	
	@Before
	public void setUp() throws Exception {
		this.page = new ERSPage(driver);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testSuccuessfulLogin() {
		page.setUsername("user1");
		page.setPassword("password");
		page.submitLogin();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.urlMatches("/employeehome.html"));
		assertEquals("http://localhost:9001/html/employeehome.html", driver.getCurrentUrl());
	}
	
	@Test
	public void testFailedLogin() {
		page.setUsername("user1");
		page.setPassword("NOPE");
		page.submitLogin();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.urlMatches("/badlogin.html"));
		assertEquals("http://localhost:9001/html/badlogin.html", driver.getCurrentUrl());
	}
	
	@Test
	public void testSubmitForm() {
		// index.html
		page.setUsername("user1");
		page.setPassword("password");
		page.submitLogin();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.urlMatches("/employeehome.html"));
		assertEquals("http://localhost:9001/html/employeehome.html", driver.getCurrentUrl());
		
		// employeehome.html
		page.setUpEmployeeElements();
		page.openForm();
		page.setAmount("1000");
		page.setDesc("Traveling");
		page.selectType();
		page.submitForm();
		wait.until(ExpectedConditions.urlMatches("/successful-submission.html"));
		assertEquals("http://localhost:9001/html/successful-submission.html", driver.getCurrentUrl());
		wait.until(ExpectedConditions.urlMatches("/employeehome.html"));
		assertEquals("http://localhost:9001/html/employeehome.html", driver.getCurrentUrl());
		page.retrieveLogout();
		page.logout();
		wait.until(ExpectedConditions.urlMatches("/index.html"));
		assertEquals("http://localhost:9001/html/index.html", driver.getCurrentUrl());
	}
	
	@Test
	public void testSuccuessfulLoginManager() {
		page.setUsername("user3");
		page.setPassword("p");
		page.submitLogin();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.urlMatches("/financemanagerhome.html"));
		assertEquals("http://localhost:9001/html/financemanagerhome.html", driver.getCurrentUrl());
	}
	
	@Test
	public void testSuccuessfulApproval() throws Exception {
		// index.html
		page.setUsername("user3");
		page.setPassword("p");
		page.submitLogin();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.urlMatches("/financemanagerhome.html"));
		assertEquals("http://localhost:9001/html/financemanagerhome.html", driver.getCurrentUrl());
		
		// financemanagerhome.html
		page.setUpManagerElements();
		page.openPending();
		TimeUnit.SECONDS.sleep(1);
		page.approvePending();
		page.confirmApproved();
		page.openApproved();
		assertTrue(page.checkApproved());
		page.retrieveLogout();
		page.logout();
		wait.until(ExpectedConditions.urlMatches("/index.html"));
		assertEquals("http://localhost:9001/html/index.html", driver.getCurrentUrl());
	}

}

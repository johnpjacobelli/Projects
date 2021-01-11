package com.ers.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ERSPage {
	
	private WebDriver driver;
	private WebElement usernameField;
	private WebElement passwordField;
	private WebElement loginButton;
	
	private WebElement reimFormOpen;
	private WebElement reimFormType;
	private WebElement reimFormAmount;
	private WebElement reimFormDesc;
	private WebElement reimFormSubmit;
	private WebElement logoutButton;
	
	private WebElement pendingButton;
	private WebElement approveBtn;
	private WebElement approvedButton;
	private WebElement approvedReim;
	
	
	
	
	public ERSPage(WebDriver driver) {
		this.driver = driver;
		this.navigateTo();
		
		this.usernameField = driver.findElement(By.id("exampleInputUsername1"));
		this.passwordField = driver.findElement(By.id("exampleInputPassword1"));
		this.loginButton = driver.findElement(By.name("loginsubmit"));
	}
	
	public void setUsername(String name) {
		this.usernameField.clear();
		this.usernameField.sendKeys(name);
	}
	
	public void setPassword(String power) {
		this.passwordField.clear();
		this.passwordField.sendKeys(power);
	}
	
	public void submitLogin() {
		this.loginButton.click();
	}
	
	public void setUpEmployeeElements() {
		this.reimFormOpen = driver.findElement(By.id("reimForm"));
		this.reimFormType = driver.findElement(By.id("lodging"));
		this.reimFormAmount = driver.findElement(By.id("reimAmount"));
		this.reimFormDesc = driver.findElement(By.id("reimDesc1"));
		this.reimFormSubmit = driver.findElement(By.name("reimsubmit"));
	}
	
	public void openForm() {
		this.reimFormOpen.click();
	}
	
	public void selectType() {
		this.reimFormType.click();
	}
	
	public void setAmount(String power) {
		this.reimFormAmount.clear();
		this.reimFormAmount.sendKeys(power);
	}
	
	public void setDesc(String power) {
		this.reimFormDesc.clear();
		this.reimFormDesc.sendKeys(power);
	}
	
	public void submitForm() {
		this.reimFormSubmit.click();
	}
	
	public void retrieveLogout() {
		this.logoutButton = driver.findElement(By.id("returnToIndex"));
	}
	
	public void logout() {
		this.logoutButton.click();
	}
	
	public void setUpManagerElements() {
		this.pendingButton = driver.findElement(By.id("allPending"));
		this.approvedButton = driver.findElement(By.id("allApproved"));
	}
	
	public void openPending() {
		this.pendingButton.click();
	}
	
	public void approvePending() {
		this.approveBtn = driver.findElement(By.id("approveBtn14"));
		this.approveBtn.click();
	}
	
	public void confirmApproved() {
		this.approveBtn.click();
	}
	
	public void openApproved() {
		this.approvedButton.click();
	}
	
	public boolean checkApproved() {
		this.approvedReim = driver.findElement(By.id("reimburse14"));
		return true;
	}
	
	public void navigateTo() {
		this.driver.get("http://localhost:9001/html/index.html");
	}

}

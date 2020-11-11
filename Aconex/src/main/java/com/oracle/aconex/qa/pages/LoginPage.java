package com.oracle.aconex.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.oracle.aconex.qa.base.TestBase;

public class LoginPage extends TestBase{

	private static final Logger logger = Logger.getLogger(LoginPage.class);
	
		//Page Factory - Object Repo
		@FindBy(id="userName")
		WebElement userName;
		
		@FindBy(id="password")
		WebElement password;
		
		@FindBy(id="login")
		WebElement loginBtn;
		
		//Initializing the Page Objects:
		public LoginPage(){
			PageFactory.initElements(driver, this);
		}
		
		//Actions
		public String validateLoginPageTitle(){
			return driver.getTitle();
		}
		
		public MailPage login(String un, String pwd){
			logger.info("login");
			userName.sendKeys(un);
			password.sendKeys(pwd);
			loginBtn.click();
			return new MailPage();
		}
		
}

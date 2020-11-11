package com.oracle.aconex.qa.testcases;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.oracle.aconex.qa.base.TestBase;
import com.oracle.aconex.qa.pages.MailPage;
import com.oracle.aconex.qa.testdata.TestVar;
import com.oracle.aconex.qa.pages.LoginPage;

public class SendMailTest extends TestBase {

	private static final Logger logger = Logger.getLogger(SendMailTest.class);
	
	LoginPage loginPage;
	MailPage blankMailPage;
	
	public SendMailTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp() throws InterruptedException{
		logger.info("setup");
		initialization();
		loginPage = new LoginPage();	
		blankMailPage = loginPage.login(prop.getProperty("username1"), prop.getProperty("password1"));
	}
	
	@Test
	public void sendMailTest() throws Exception{
		logger.info("sendMailTest");
		blankMailPage.blankMail();
		blankMailPage.directory();
		blankMailPage.fillDirectoryFields(TestVar.firstName,TestVar.lastName);
		String mailNumber=blankMailPage.sendMail(TestVar.subject, TestVar.opt1,TestVar.opt2);
		Assert.assertTrue(blankMailPage.verifyMail(mailNumber));
	}
	
	@AfterMethod
	public void tearDown(){
		logger.info("tearDown");
		driver.quit();
	}
	
}

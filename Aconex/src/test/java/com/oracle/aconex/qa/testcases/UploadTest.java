package com.oracle.aconex.qa.testcases;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.oracle.aconex.qa.base.TestBase;
import com.oracle.aconex.qa.pages.LoginPage;
import com.oracle.aconex.qa.pages.MailPage;
import com.oracle.aconex.qa.pages.UploadPage;
import com.oracle.aconex.qa.testdata.TestVar;

public class UploadTest extends TestBase{

	private static final Logger logger = Logger.getLogger(UploadTest.class);
	LoginPage loginPage;
	UploadPage uploadPage;
	MailPage mailPage;
	
	public UploadTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() throws InterruptedException{
		initialization();
		loginPage = new LoginPage();	
		mailPage = loginPage.login(prop.getProperty("username2"), prop.getProperty("password2"));
	}
	
	@Test
	public void uploadTest() throws Exception{
		logger.info("upload test");
		uploadPage = new UploadPage();
		uploadPage.fillUploadPage(TestVar.version,TestVar.title,TestVar.date, TestVar.dtype,TestVar.dstatus, TestVar.disc);
		uploadPage.robotUpload(TestVar.uploadFile);
		uploadPage.clickUpload();
		String docId= uploadPage.verifyUpload();
		boolean b=uploadPage.searchDocument(docId);
		//verify uploaded document in document register
		Assert.assertTrue(b);
		uploadPage.saveSearch(docId);
	}
	
	@AfterMethod
	public void tearDown(){
		//driver.quit();
	}
}

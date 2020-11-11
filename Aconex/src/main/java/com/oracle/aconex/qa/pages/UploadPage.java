package com.oracle.aconex.qa.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import com.oracle.aconex.qa.base.TestBase;

public class UploadPage extends TestBase {

	private static final Logger logger = Logger.getLogger(UploadPage.class);

	public void fillUploadPage(String ver, String title, String date, String dtype, String dstatus ,String disc) throws Exception {
		logger.info("fillUploadPage");
		driver.findElement(By.xpath("//body/div[@id='navContainer']/div[@id='nav']/div[2]/div[2]/button[3]/div[1]/div[1]/div[1]")).click(); // Documents
		driver.findElement(By.xpath("//div[@id='nav-bar-DOC-DOC-NEW']")).click(); // Upload a New Document

		driver.switchTo().frame("frameMain");

		driver.findElement(By.xpath("//input[@id='docno_auto_0_chk']")).click(); // Auto number
		driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[1]/input[1]")).sendKeys(ver);
		driver.findElement(By.xpath("//tbody/tr[3]/td[2]/div[1]/input[1]")).sendKeys(title);
		driver.findElement(By.xpath("//input[@id='revisiondate_0_da']")).sendKeys(date);
		
		Select sel1 = new Select(driver.findElement(By.xpath("//select[@id='doctype_0']")));
		sel1.selectByVisibleText(dtype);

		Select sel2 = new Select(driver.findElement(By.xpath("//select[@id='docstatus_0']")));
		sel2.selectByVisibleText(dstatus);

		Select sel3 = new Select(driver.findElement(By.xpath("//select[@id='discipline_0']")));
		sel3.selectByVisibleText(disc);

	}

	//Using AWT-Robot class for upload file
	public void robotUpload(String fPath) throws Exception {
		logger.info("robotUpload");
		driver.findElement(By.xpath("//span[@id='clickToUpload']")).click();
		Thread.sleep(3000);
		logger.info(fPath);
		String fileAbsolutePath = fPath;
		StringSelection clipBoardPath = new StringSelection(fileAbsolutePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipBoardPath, null);

		Robot robot = new Robot();
		// Keyboard Action : CTRL+V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		// Keyboard Action : Enter robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);

	}

	public void clickUpload() {
		logger.info("clickUpload");
		driver.findElement(By.xpath("//body/form[@id='form1']/div[1]/div[1]/div[1]/div[2]/button[1]/div[1]/div[1]")).click();
	}

	public String verifyUpload() {
		logger.info("verify upload");
		driver.findElement(By.xpath("//body/div[@id='regSuccessPanel']/div[1]/span[1]")).getText().equals("Document Uploaded Successfully");
		logger.info(driver.findElement(By.xpath("//body/div[@id='regSuccessPanel']/div[1]/span[1]")).getText());
		
		//doc id 
		String docId=driver.findElement(By.xpath("//body/div[@id='regSuccessPanel']/div[2]/div[1]/ul[1]/li[1]/div[1]/div[1]/h3[1]/b[1]")).getText();
		logger.info(docId);
		return docId;
	}

	public boolean searchDocument(String docId) {
		logger.info("Document");
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//body/div[@id='navContainer']/div[@id='nav']/div[2]/div[2]/button[3]/div[1]/div[1]/div[1]")).click();	//Document
		driver.findElement(By.xpath("//div[@id='nav-bar-DOC-DOC-SEARCH']")).click();	//Document Register
		
		driver.switchTo().frame("frameMain");
		driver.findElement(By.xpath("//input[@id='search-keywords-id']")).clear();
		driver.findElement(By.xpath("//input[@id='search-keywords-id']")).sendKeys(docId);
		driver.findElement(By.xpath("//button[@class=\"auiButton primary ng-binding\"]")).click();	//search
		
		String xdocNo="//body/form[@id='form1']/div[@id='page']/div[@id='main']/div[2]/div[1]/div[1]/div[3]/acx-search-results-grid[1]/div[1]/div[1]/div[3]/div[1]/acx-search-ag-grid[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[3]/div[1]";
		String docNo=driver.findElement(By.xpath(xdocNo)).getText();
		logger.info(docNo);
		boolean b= docId.equals(docNo);
		return b;
		
	}
	
	public void saveSearch(String docId) {
		//save search
		driver.findElement(By.xpath("//body/form[@id='form1']/div[@id='page']/div[@id='main']/div[2]/div[1]/div[1]/div[3]/div[1]/acx-search-criteria[1]/ng-form[1]/div[1]/div[1]/div[1]/div[3]/div[1]/button[1]")).click();
		driver.findElement(By.xpath("//body/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[2]/input[1]")).sendKeys(docId);
		driver.findElement(By.xpath("//input[@id='ok']")).click();
		
	}
}

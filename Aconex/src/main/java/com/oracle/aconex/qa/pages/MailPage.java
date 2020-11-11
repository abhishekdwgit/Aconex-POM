package com.oracle.aconex.qa.pages;

import com.oracle.aconex.qa.base.TestBase;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailPage extends TestBase{

	private static final Logger logger = Logger.getLogger(MailPage.class);
	
	public void blankMail() {
		logger.info("blankmail");
		driver.findElement(By.id("nav-bar-CORRESPONDENCE")).click(); //Mail
		driver.findElement(By.id("nav-bar-CORRESPONDENCE-CORRESPONDENCE-CREATEMAIL")).click(); //Blank Mail
	}

	public void directory() {
		driver.switchTo().frame("frameMain"); 
		driver.findElement(By.xpath("//tbody/tr[1]/td[3]/button[1]/div[1]/div[1]")).click(); //Directory
	}

	public void fillDirectoryFields(String fname, String lname) {
		driver.findElement(By.id("ACONEX_list")).click();
		driver.findElement(By.id("FIRST_NAME")).sendKeys(fname);
		driver.findElement(By.id("LAST_NAME")).sendKeys(lname);
		driver.findElement(By.xpath("//div[contains(text(),'Search')]")).click();
		driver.findElement(By.xpath("//tbody/tr[1]/td[1]/input[1]")).click();
		driver.findElement(By.xpath("//body/div[@id='page']/div[@id='main']/form[@id='command']/div[@id='searchResultsWrapper']/div[@id='searchResultsToolbar']/div[1]/button[1]/div[1]/div[1]"
				)).click();;
		driver.findElement(By.xpath("//div[contains(text(),'OK')]")).click();
	}

	public String sendMail(String sub, String opt1, String opt2) throws Exception {
		logger.info("sendmail");
		Select se = new Select(driver.findElement(By.xpath("//select[@id='Correspondence_correspondenceTypeID']")));
		se.selectByVisibleText(opt1);									
		logger.info("selected- Consultants Advice Notice");

		driver.findElement(By.id("Correspondence_subject")).sendKeys(sub);				
		logger.info("Test Subject");

		driver.findElement(By.id("multiselect1")).click();
		Select se1 = new Select(driver.findElement(By.xpath("//body/div[@id='attributePanel']/div[2]/div[2]/div[1]/select[1]")));
		se1.selectByVisibleText(opt2);												
		logger.info("selected Available Attribute 2- option Piling");

		driver.findElement(By.xpath("//body/div[@id='attributePanel']/div[2]/div[2]/div[3]/button[1]/div[1]/div[1]")).click();	// move >>

		driver.findElement(By.xpath("//body/div[@id='attributePanel']/div[3]/span[1]/button[1]/div[1]/div[1]")).click();

		driver.findElement(By.xpath("//body/div[@id='page']/form[@id='form1']/div[@id='header']/div[1]/div[2]/button[1]/div[1]/div[1]")).click();	// send

		logger.info("view mail");
		String mailNumber=driver.findElement(By.xpath("//mail-header/div[1]/div[2]/div[2]/div[2]")).getText();
		logger.info("mailNumber is:"+mailNumber);
		return mailNumber;
	}

	public boolean verifyMail(String mailNum) throws InterruptedException {

		logger.info("verifyMail");
		driver.switchTo().defaultContent();
		driver.findElement(By.id("nav-bar-CORRESPONDENCE")).click();
		driver.findElement(By.xpath("//div[@id='nav-bar-CORRESPONDENCE-CORRESPONDENCE-SEARCHSENT']")).click();

		logger.info("switch to main frame");
		driver.switchTo().frame("frameMain");												
		driver.findElement(By.xpath("//input[@id='rawQueryText']")).sendKeys(mailNum);
		driver.findElement(By.xpath("//body/div[@id='page']/div[1]/div[1]/div[3]/div[1]/div[1]/form[1]/div[1]/button[1]")).click();

		logger.info("verify sent mail number");
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]/td[3]/span[1]")));
		
		//verify same mail number under sent mail
		String sentMailNum=driver.findElement(By.xpath("//tbody/tr[1]/td[3]/span[1]")).getText();
		logger.info(sentMailNum);
		return sentMailNum.equals(mailNum);
	}

}

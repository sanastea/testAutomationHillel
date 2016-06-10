package testAutomation;

/**
 * Created by asadreev on 6/8/16.
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import java.util.Arrays;
import org.openqa.selenium.interactions.Actions;



public class GmailTests {
    private WebDriver driver;
    private String baseUrl;

    @BeforeClass(alwaysRun = true)
    public void setUp()  {
        driver = new FirefoxDriver();
        baseUrl = "https://www.google.com/intl/en/mail/help/about.html";
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    }

    @Test
    public void TestGmailHomework() throws Exception{
        //Navigate to the page and login
        driver.get(baseUrl);
        Actions action = new Actions(driver);
        driver.findElement(By.id("gmail-sign-in")).click();
        //enter email address
        driver.findElement(By.id("Email")).sendKeys("");
        driver.findElement(By.id("next")).click();
        //enter password
        driver.findElement(By.id("Passwd")).sendKeys("");
        driver.findElement(By.id("signIn")).click();

        //Click on settings icon and get menu items from the list
        driver.findElement(By.cssSelector(".aos.T-I-J3.J-J5-Ji")).click();
        List<String> menuitems = Arrays.asList("Comfortable (on larger displays)", "Cozy (on larger displays)", "Compact (current view)","Configure inbox","Settings", "Themes","Help");
        List<WebElement> listOfElements = driver.findElements(By.xpath(".//div[@class='J-M asi aYO jQjAxd']/div[@class='SK AX']/div[@role!='separator' and @style!='display: none; -moz-user-select: none;']"));
        int iterator = 0;

        //Check number of the menu items and the text of each item
        Assert.assertEquals(listOfElements.size(),7);

        for(WebElement element : listOfElements) {
            Assert.assertEquals(element.getText(),menuitems.get(iterator));
            action.moveToElement(element).perform();
            Assert.assertTrue(element.getAttribute("class").contains("J-N-JT"));
            iterator++;
        }

        // Click on GoogleApps icon , check the list of displayed apps
        driver.findElement(By.cssSelector(".gb_b.gb_Rb")).click();
        List<WebElement> googleAppsList = driver.findElements(By.xpath("//ul[@class='gb_ja gb_ca']/li"));
        Assert.assertEquals(googleAppsList.size(),11);
        WebElement myAccountIcon = googleAppsList.get(0);
        action.moveToElement(myAccountIcon).perform();
        //TODO:Need to add check for hover on item

        //Click on "More" and check visibility of other items
        driver.findElement(By.cssSelector(".gb_ka.gb_Ie")).click();
        Thread.sleep(200);
        List<WebElement> googleAppsListFull = driver.findElements(By.xpath("//ul[@class='gb_ja gb_da' and @aria-hidden='false']/li"));
        Assert.assertEquals(googleAppsListFull.size(),4);
        driver.findElement(By.cssSelector(".gb_b.gb_Rb")).click();

        //Check number of emails and pagination
        List<WebElement> listOfEmails = driver.findElements(By.xpath("//div[@class='Cp']/div/table/tbody/tr"));
        Assert.assertEquals(listOfEmails.size(),50);
        WebElement paginationElement = driver.findElement(By.xpath("//div[@role='button' and @aria-label = 'Older']"));
        new Actions(driver).moveToElement(paginationElement).click().perform();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.cssSelector(".Dj")).getText().contains("51–100 of"));

    }


    @AfterClass(alwaysRun = true)
    public void tearDown()  {
        driver.quit();
    }

}

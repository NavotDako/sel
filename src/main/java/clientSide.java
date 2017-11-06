import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by navot.dako on 10/23/2017.
 */
public class clientSide {
    public static void main(String[] args) throws MalformedURLException {
//        String url = "http://localhost:4000";
        String url = "http://127.0.0.1:4001";//444/wd/hub";

        DesiredCapabilities dc = new DesiredCapabilities().chrome();
//        dc.setCapability("user", "admin");
//        dc.setCapability("password", "Experitest2012");
//        dc.setCapability("testName", "testName");
        System.out.println("Starting Request..");
        RemoteWebDriver driver = new RemoteWebDriver(new URL(url), dc);
        System.out.println(driver.getCapabilities());
        driver.get("http://www.google.com");
        RemoteWebElement element = (RemoteWebElement)driver.findElement(By.xpath("//*[@id='gbwa']/div[1]/a111"));
        System.out.println(((RemoteWebElement)element).getId());
        element.click();
        driver.quit();
    }
}

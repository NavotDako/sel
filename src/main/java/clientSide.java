import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        String url = "http://localhost:4001";
        DesiredCapabilities dc = new DesiredCapabilities();//.chrome();
        dc.setCapability("user", "admin");
        dc.setCapability("password", "Experitest2012");
        dc.setCapability("testName", "testName");
        System.out.println("Starting Request..");
        RemoteWebDriver driver = new RemoteWebDriver(new URL(url), dc);
        System.out.println(driver.getCapabilities());
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.xpath("//div"));
        System.out.println(((RemoteWebElement)element).getId());
        ((RemoteWebElement)element).click();
    }
}

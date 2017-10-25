import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class sparkServer {
    private static final String CHROME_DRIVER = "lib\\chromedriver.exe";
    static Map<String,WebDriver> driversList = new HashMap<>();
    static Map<String,String> elementsList = new HashMap<>();


    public static void main(String[] args) {

        port(4001);
        final String[] reqOld = {"Not Yet!"};
        System.setProperty("webdriver.chrome.driver",CHROME_DRIVER);
        get("/session", (req, res) -> "hello");

        post("/session", (req, res) -> {
            System.out.println("sever->newSession");
            res.type("application/json");
//            System.out.println(req.body());

            Map<String, Object> map = new HashMap<>();
            map.put("sessionId", "81732746-c58e-4383-9d2f-687fa895ddab");
            map.put("value", new HashMap<>());
            map.put("status", "0");

            DesiredCapabilities dc = new DesiredCapabilities();
            ChromeDriver driver = new ChromeDriver();
            driversList.put("81732746-c58e-4383-9d2f-687fa895ddab",driver);

            return map;

        });

        delete("/session", (req, res) -> {
            System.out.println("server->quit");
            try {
                System.out.println("sessionId=" + req.attribute("sessionId"));
                res.status(200);
                return res;
            } finally {
                halt();
            }
        });

        post("/session/:sessionId/url", (req, res) -> {
            System.out.println("driver.get");
//            System.out.println("Body -\n"+req.body());

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> myMap = gson.fromJson(req.body(), type);

            driversList.get(req.params(":sessionId")).get(myMap.get("url"));

            Map<String, Object> map = new HashMap<>();
            map.put("sessionId", req.params(":sessionId"));
            map.put("value", new HashMap<>());
            map.put("status", "0");
            return map;
        });

        post("/session/:sessionId/element", (req, res) -> {

            System.out.println("driver.findElement");
//            System.out.println("Body -\n"+req.body());

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> myMap = gson.fromJson(req.body(), type);

            WebElement element = driversList.get(req.params(":sessionId")).findElement(By.xpath(myMap.get("value")));
            String id = ((RemoteWebElement) element).getId();
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("ELEMENT",id);
            elementsList.put(id,myMap.get("value"));
//            System.out.println("id - "+id);

            Map<String, Object> map = new HashMap<>();
            map.put("sessionId", req.params(":sessionId"));
            map.put("value", stringStringHashMap);
            map.put("status", "0");
            return map;
        });

        post("/session/:sessionId/element/:elementId/click", (req, res) -> {
            Map<String, Object> returnMap = new HashMap<>();
            System.out.println("driver.click");
            System.out.println("Body -\n"+req.body());
            System.out.println(req.params(":sessionId") +" - "+ req.params(":elementId"));
            driversList.get(req.params(":sessionId")).findElement(By.xpath(elementsList.get(req.params(":elementId")))).click();
//            Gson gson = new Gson();
//            Type type = new TypeToken<Map<String, String>>(){}.getType();
//            Map<String, String> myMap = gson.fromJson(req.body(), type);
//
//            WebElement value = driversList.get(req.params(":sessionId")).findElement(By.xpath(myMap.get("value")));
//            String id = ((RemoteWebElement) value).getId();
//            HashMap<String, String> stringStringHashMap = new HashMap<>();
//            stringStringHashMap.put("ELEMENT",id);
//            System.out.println("id - "+id);
//
//            returnMap.put("sessionId", req.params(":sessionId"));
//            returnMap.put("value", stringStringHashMap);
//            returnMap.put("status", "0");
            return returnMap;
        });

    }
}
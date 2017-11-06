import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.*;
import spark.Request;

import javax.servlet.RequestDispatcher;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class sparkServer {
    private static final String CHROME_DRIVER = "lib\\chromedriver.exe";
    static Map<String, WebDriver> driversList = new HashMap<>();
    static Map<String, String> elementsList = new HashMap<>();


    public static void main(String[] args) {

        port(4001);
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);

        get("/session", (req, res) -> "We Are Alive!");

        post("/session", (req, res) -> {
            StringBuffer response = forwardRequest(req);
            return response;

        });

        post("/session/:sessionId/url", (req, res) -> {
            StringBuffer response = forwardRequest(req);
            return response;

        });

        post("/session/:sessionId/element", (req, res) -> {
            StringBuffer response = forwardRequest(req);
            return response;
        });

        post("/session/:sessionId/element/:elementId/click", (req, res) -> {
            StringBuffer response = forwardRequest(req);
            return response;
        });

    }

    private static StringBuffer forwardRequest(Request req) throws IOException {
        System.out.println("\nSending " + req.requestMethod() + " request to URI : " + req.uri());

        String url = "http://localhost:4444/wd/hub" + req.uri();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(req.requestMethod());

        for (String header : req.headers()) {
            System.out.println("header - " + header + " - " + req.headers(header));
            con.setRequestProperty(header, req.headers(header));
        }
        System.out.println("body\n - " + req.body().toString());

        byte[] outputInBytes = req.body().getBytes("UTF-8");
        con.setDoOutput(true);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(outputInBytes);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
        StringBuffer response = null;
        InputStream stream = null;

        if (responseCode == 200) {
            stream = con.getInputStream();
        } else {
            stream = con.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());

        //print result
        return response;
    }


}
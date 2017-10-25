import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by navot.dako on 10/23/2017.
 */
public class startSocket {
    private static final String CHROME_DRIVER = "lib\\chromedriver.exe";
    private static boolean startedCap = false;
    static Map<String, String> map = new HashMap<String, String>();
    static WebDriver driver;
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver",CHROME_DRIVER);

        System.out.println("Starting Service..");

        DesiredCapabilities dc = new DesiredCapabilities();

        ServerSocket serverSocket = new ServerSocket(4000);
        Socket socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            String comingInMessage = "";
            try {
                comingInMessage = in.readLine();
                System.out.println(comingInMessage);

//                if (startedCap && comingInMessage.contains("},")) {
//                    System.out.println("Ending Capabilities!!");
//                    startedCap = false;
//                    if(checkCredentials(map)){
//                        addToDB(map);
//                        System.out.println("Starting Browser..");
//                        driver = new ChromeDriver();
//                    }else{
//                        System.out.println("Credentials Are Not Correct!");
//                    }
//                }

                if(startedCap){
                    String[] stringArr = comingInMessage.split("\"");
                    System.out.println(stringArr[1]+" -> "+stringArr[3]);
                    map.put(stringArr[1],stringArr[3]);
                    dc.setCapability(stringArr[1], stringArr[3]);
                }

                if (comingInMessage.equals("  \"desiredCapabilities\": {")) {
                    System.out.println("Starting Capabilities!!");
                    startedCap = true;
                }

            } catch (IOException e) {
                System.out.println("System: " + "Connection to client has lost!");
                socket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                startedCap = false;
                comingInMessage = "";
                map = new HashMap<String, String>();
            }catch (Exception e){
                System.out.println("Fuck.. Exception..");
                startedCap = false;
                comingInMessage = "";
                map = new HashMap<String, String>();
            }
        }
    }

    private static void addToDB(Map<String, String> map) {
        BufferedWriter writer = null;
        try {
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File db = new File("db.txt");

            writer = new BufferedWriter(new FileWriter(db));
            writer.append(System.lineSeparator()+timeLog+"->"+map.get("testName"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    private static boolean checkCredentials(Map<String, String> map) throws IOException {
        Map<String,String> usersMap = readUsersToMap("users.txt");
       if(usersMap.get(map.get("user")).equals(map.get("password"))){
            return true;
        }
        return false;
    }

    private static Map<String, String> readUsersToMap(String s) throws IOException {
        Map<String,String> usersMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("users.txt"));
        try {
            String line = br.readLine();
            while (line != null) {
                String[] split = line.split("=");
                usersMap.put(split[0],split[1]);
                line = br.readLine();
            }

        } finally {
            br.close();
        }
        return usersMap;
    }
}

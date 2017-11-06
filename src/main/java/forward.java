import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by navot.dako on 11/5/2017.
 */
public class forward {
    private static final String HOST_ADDRESS = "localhost";
    private static final int PORT = 4444;

    public static void main(String[] args) throws IOException {
        Socket socketHub = new Socket(HOST_ADDRESS, PORT);
        BufferedReader bufferedReaderHub = new BufferedReader(new InputStreamReader(socketHub.getInputStream()));
        PrintWriter printWriter = new PrintWriter(socketHub.getOutputStream());


        ServerSocket serverSocket = new ServerSocket(4001);
        Socket socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while(true){
            String comingInMessage = "";
            try {
                comingInMessage = in.readLine();
                System.out.println(comingInMessage);
            }catch (Exception e){
                e.printStackTrace();
                printWriter.flush();
                printWriter.close();
            }

            printWriter.write(comingInMessage);

        }

    }
    private void sendPost() throws Exception {

        String url = "http://localhost:4444/wd/hub/";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        con.setRequestProperty("Content-Length", "193");
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("User-Agent", "Apache-HttpClient/4.5.3 (Java/1.8.0_91)");
        con.setRequestProperty("Accept-Encoding", "gzip,deflate");

//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";



        // Send post request
//        con.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
//        wr.flush();
//        wr.close();

//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + urlParameters);
//        System.out.println("Response Code : " + responseCode);
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//
//        //print result
//        System.out.println(response.toString());

    }
    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
//        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}
//POST /session HTTP/1.1
//        Content-Type: application/json; charset=utf-8
//        Content-Length: 193
//        Host: 127.0.0.1:4001
//        Connection: Keep-Alive
//        User-Agent: Apache-HttpClient/4.5.3 (Java/1.8.0_91)
//        Accept-Encoding: gzip,deflate
//
//        {
//        "desiredCapabilities": {},
//        "requiredCapabilities": {},
//        "capabilities": {
//        "desiredCapabilities": {},
//        "requiredCapabilities": {},
//        "alwaysMatch": {},
//        "firstMatch": []
//        }

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos2;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author RoyZheng
 */
public class Get {

    public static String sendRequest(URL u) throws IOException {
        HttpURLConnection connection;
        connection = (HttpURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Host", u.getPath());


        InputStream in = connection.getInputStream();
        InputStreamReader isw = new InputStreamReader(in);

        StringBuilder sb = new StringBuilder();
        int data = isw.read();
        while (data != -1) {
            char current = (char) data;
            data = isw.read();
            sb.append(current);
        }
        return sb.toString();
    }
}

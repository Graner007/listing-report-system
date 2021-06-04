package com.codecool.report.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiReader {

    public static String getDataFromApi(String path) {
        String inline = "";

        try {
            URL url = new URL(path);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            if (conn.getResponseCode() != 200)
                throw new RuntimeException("HttpResponseCode: " +responsecode);
            else
            {
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext())
                    inline += sc.nextLine();

                sc.close();
            }

            conn.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return inline;
    }
}

package com.namchain.lib.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {

    public static String addblock(String UUID, String VReg, String ENo, String CNo, String Vmodel, String VType, String VSS) {
        HttpURLConnection connection = null;
        StringBuilder response;
        String targetURL = "http://localhost:9005";
        String urlParameters = "{\"UUID\":"+UUID+",\"VReg\":\""+VReg+"\",\"ENo\":\""+ENo+"\",\"CNo\" : \""+CNo+"\",\"Vmodel\" : \""+Vmodel+"\", \"VType\" : \""+VType+"\", \"VSS\" : \""+VSS+"\"}";

        System.out.println("Sending HTTP request to "+targetURL+urlParameters);

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            System.out.print(response.toString());
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

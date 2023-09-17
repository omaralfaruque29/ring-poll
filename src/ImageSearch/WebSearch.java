/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author raj
 */
public class WebSearch {

    //private String fullText = "https://www.google.com.bd/search?q=uae&hl=bn&gbv=2&tbm=isch&prmd=ivns&start=20&sa=N";
    private String firstText = "https://www.google.com.bd/search?q=";
    private String secondText = "&hl=bn&gbv=2&tbm=isch&prmd=ivns&start=";
    private String lastText = "&sa=N";
    private String givenText = "";
    private String fullText = "";
    private String line = "";
    private String response = "";
    private final String USER_AGENT = "";
    private String num = "";
    private Map<String, String> imgInfoMap = new HashMap<>();

    public WebSearch(String Text, int number) {
        givenText = Text;
        num = Integer.toString(number);
        fullText = firstText + givenText + secondText + num + lastText;
//        //tbm=isch&q=uae
//        String firstKey = urlPair("tbm", "isch");
//        String secondKey = urlPair("q", givenText);
//        lastText = firstKey + "&" + secondKey;
//        fullText = firstText + lastText;
        initialize();
    }

    private void initialize() {

        try {
            URL url = new URL(fullText);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                response += line;
            }

            File file = new File("F:\\filename.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(response);
            bw.close();
        } catch (Exception e) {

        }
    }

//    private String urlPair(String name, String val) {
//        try {
//            return name + "=" + URLEncoder.encode(val.toString(), "UTF-8");
//        } catch (Exception ex) {
//            return "";
//        }
//    }
    public Map<String, String> getURL() {
        List<String> list = new ArrayList<String>();
        String word0 = null;
        int count = 0;
        //<img[^>]*src=[\"']([^\"^']*)
        Pattern p = Pattern.compile("<img[^>]*src=[\"]([^\"]*)",
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(response);

        while (m.find()) {
            count++;
            if (count > 1) {
                System.out.println("" + count);
                word0 = m.group(1);
                System.out.println("" + word0.length());
                String imgName = word0.toString();
                imgName = imgName.substring(48, imgName.length());
                System.out.println(word0.toString());
                System.out.println(" " + imgName);
                imgInfoMap.put(word0, imgName);
            //list.add(word0);

            }

        }
        int a = list.size();
        System.out.println("size of list:" + a);
        return imgInfoMap;
    }

}

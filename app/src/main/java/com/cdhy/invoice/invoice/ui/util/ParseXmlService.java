package com.cdhy.invoice.invoice.ui.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ParseXmlService {
    public HashMap<String, String> parseXml(InputStream inputStream
    ) {
        HashMap<String, String> hashMap = null;
        boolean flag = true;
        try {
            XmlPullParser pullParser = Xml.newPullParser();
            pullParser.setInput(inputStream, "UTF-8");
            int event = pullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        hashMap = new HashMap<String, String>();
                        break;
                    case XmlPullParser.START_TAG:
                        flag = true;
                        String name = pullParser.getName();
                        if ("VERSIONCODE".equalsIgnoreCase(name) && flag == true) {
                            hashMap.put("versionCode", pullParser.nextText().trim());
                        } else if ("FILENAME".equalsIgnoreCase(name) && flag == true) {
                            hashMap.put("fileName", pullParser.nextText().trim());
                        } else if ("LOADURL".equalsIgnoreCase(name) && flag == true) {
                            hashMap.put("loadUrl", pullParser.nextText().trim());
                        } else if ("MUST".equalsIgnoreCase(name) && flag == true) {
                            hashMap.put("must", pullParser.nextText().trim());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        flag = false;
                        break;
                }
                event = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // hashMap = new HashMap<String, String>();
        // hashMap.put("versionCode", "2");
        // hashMap.put("fileName", "updateversion");
        // hashMap.put("loadUrl",
        //  "http://p.gdown.baidu.com/68158bbd943899fc0b4886b7e137de072eaf7f695829ea1230d94e3519a3bccaff23c9b33734210a17e2d97b62253a07394c4e8af4b345e7e5937033e55967cc5374a899feaf10ee88505abfcc55d9f9fafda782487bff17fe85c7f34adfde7d7dc78596c4532d25684c61558c671d45ee6c34c85222a9a7e50f2afcba27c4909714ab124374b523b58139a65589a59b7a580ad759d83d03227939d0f523e722a2cfd786a8dabc7840d19fa6d3adfb64b0cbdafcf118ac0683a1994f4c0b71904df10020da17d94278fbb95ef9060799bf7db17de8aae15aec9eeb836398f59452651f73c5d0e63412417bc0681203d418735b3ea1093486b61ab5e0dd0e10d9fe4fbdbc24f0cbc174098b541546b449bd5c0ae85786268f9e50b8b401b7fed391b4bd164c1790a340de2c837e0c78d0");
        return hashMap;
    }
}

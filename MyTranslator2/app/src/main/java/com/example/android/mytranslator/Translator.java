package com.example.android.mytranslator;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by Hamza Zyoud on 8/19/2018.
 */

/**
 * Hamza Zyoud -- this class is used to send request to mymemory API and handle the returned JSON
 */
public class Translator {
    private String  urlSpec;
    private static final String TAG="Returned Result";
    public void setUrlSpec(String urlSpec) {
        this.urlSpec = urlSpec;
    }

    public String getUrlSpec() {
        return urlSpec;
    }

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public String fetchURL(String text, Locale srcLanguage, Locale dstLanguage) {
        String st=null;
        try {
            String query = URLEncoder.encode(text, "UTF-8");
            String langpair = URLEncoder.encode(srcLanguage.getLanguage() + "|" + dstLanguage.getLanguage(), "UTF-8");
            String url = "http://api.mymemory.translated.net/get?q="+ query + "&langpair=" + langpair;
            String jsonString = getUrlString(url);
            String[] array=jsonString.split("\"");
            st=array[5];
            Log.i(TAG, "Received JSON: " + st);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return st;
    }
}


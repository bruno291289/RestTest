package com.br.resttest.com.br.resttest.tasks;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.br.resttest.com.br.resttest.AsyncActivityService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RestOperation extends AsyncTask<String, Void, String> {

    String content = "";
    String error = "";
    String serverUrl;
    String route;
    AsyncActivityService service;

    public RestOperation(String serverUrl, String route, AsyncActivityService service){
        this.serverUrl = serverUrl;
        this.route = route;
        this.service = service;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        InputStream is = null;

        try {
            URL url = new URL(serverUrl+route);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();
            int response = con.getResponseCode();
            is = con.getInputStream();
            content = readIt(is);
            return content;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            error = e.getMessage();
        } catch (ProtocolException e) {
            e.printStackTrace();
            error = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            error = e.getMessage();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        service.endOperation(content, error);
    }

    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }
}

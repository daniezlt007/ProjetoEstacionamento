package projetoestacionamento.com.br.projetoestacionamento.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import projetoestacionamento.com.br.projetoestacionamento.MainActivity;

public class ConexaoHttp {
    public static String postDados(String urlWebService, HashMap<String, String> mapa) {
        String responseJson = "";
        try {
            String requestJson = toJson(mapa);

            URL url = new URL(urlWebService);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Cache-Control", "no-cache");

            connection.setRequestProperty("Content-Length", Integer.toString(requestJson.length()));

            DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
            stream.write(requestJson.getBytes("UTF-8"));
            stream.flush();
            stream.close();
            connection.connect();

            responseJson = inputStreamToString(connection.getInputStream());
            connection.disconnect();
            return responseJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return responseJson.toString();
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static String toJson(Object objeto) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter jsonValue = new StringWriter();
        mapper.writeValue(new PrintWriter(jsonValue), objeto);
        return jsonValue.toString();
    }
}

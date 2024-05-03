/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package Acrimony.oauth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class CookieLogin {
    private static final String useragent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";
    private static final Gson gson = new Gson();

    public static LoginData loginWithCookie(File cookieFile) {
        try {
            String[] cookiesText = FileUtils.readFileToString(cookieFile, StandardCharsets.UTF_8).split("\n");
            StringBuilder sb = new StringBuilder();
            for (String cookie : cookiesText) {
                String name = cookie.split("\t")[5].trim();
                String value = cookie.split("\t")[6].trim();
                sb.append(name).append("=").append(value).append("; ");
            }
            String cookie = sb.toString();
            String location = CookieLogin.getNextLocation(new URI("https://sisu.xboxlive.com/connect/XboxLive/?state=login&cobrandId=8058f65d-ce06-4c30-9559-473c9275a65d&tid=896928775&ru=https://www.minecraft.net/en-us/login&aid=1142970254").toURL(), "PHPSESSID=0");
            String location2 = CookieLogin.getNextLocation(new URI(location.replace(" ", "%20")).toURL(), cookie);
            String location3 = CookieLogin.getNextLocation(new URI(location2).toURL(), cookie);
            String accessToken = location3.split("accessToken=")[1];
            String decoded = new String(Base64.getDecoder().decode(accessToken), StandardCharsets.UTF_8).split("\"rp://api.minecraftservices.com/\",")[1];
            String token = decoded.split("\"Token\":\"")[1].split("\"")[0];
            String uhs = decoded.split(Pattern.quote("{\"DisplayClaims\":{\"xui\":[{\"uhs\":\""))[1].split("\"")[0];
            String xbl = "XBL3.0 x=" + uhs + ";" + token;
            String output = CookieLogin.postExternal("https://api.minecraftservices.com/authentication/login_with_xbox", "{\"identityToken\":\"" + xbl + "\",\"ensureLegacyEnabled\":true}", true);
            String mcToken = gson.fromJson(output, JsonObject.class).get("access_token").getAsString();
            HttpsURLConnection connection = (HttpsURLConnection)new URI("https://api.minecraftservices.com/minecraft/profile").toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + mcToken);
            JsonObject profileResponse = gson.fromJson(IOUtils.toString(connection.getInputStream()), JsonObject.class);
            return new LoginData(mcToken, null, profileResponse.get("id").getAsString(), profileResponse.get("name").getAsString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to login with cookie " + cookieFile.getName().replaceAll(".txt", "."));
            e.printStackTrace();
            return null;
        }
    }

    public static String getNextLocation(URL url, String cookie) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        connection.setRequestProperty("Accept-Encoding", "*");
        connection.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7");
        connection.setRequestProperty("User-Agent", useragent);
        connection.setRequestProperty("Cookie", cookie);
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        return connection.getHeaderField("Location");
    }

    public static String postExternal(String url, String post, boolean json) {
        try {
            String lineBuffer;
            InputStream stream;
            HttpsURLConnection connection = (HttpsURLConnection)new URI(url).toURL().openConnection();
            connection.addRequestProperty("User-Agent", useragent);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            byte[] out = post.getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            connection.setFixedLengthStreamingMode(length);
            connection.addRequestProperty("Content-Type", json ? "application/json" : "application/x-www-form-urlencoded; charset=UTF-8");
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();
            try (OutputStream os = connection.getOutputStream();){
                os.write(out);
            }
            int responseCode = connection.getResponseCode();
            InputStream inputStream = stream = responseCode / 100 == 2 || responseCode / 100 == 3 ? connection.getInputStream() : connection.getErrorStream();
            if (stream == null) {
                System.err.println(responseCode + ": " + url);
                System.out.println(IOUtils.toString(connection.getInputStream()));
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            while ((lineBuffer = reader.readLine()) != null) {
                response.append(lineBuffer);
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class LoginData {
        public String mcToken;
        public String newRefreshToken;
        public String uuid;
        public String username;

        public LoginData(String mcToken, String newRefreshToken, String uuid, String username) {
            this.mcToken = mcToken;
            this.newRefreshToken = newRefreshToken;
            this.uuid = uuid;
            this.username = username;
        }
    }
}


package top.nomelin.send;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DataSender {
    private static final String API_URL = "http://localhost:12345/uploadData";
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void sendData(String userId, String deviceId, Map<Long, Map<String, Object>> data) {
        try {
            HttpPost httpPost = new HttpPost(API_URL);

            Map<String, Object> payload = new HashMap<>();
            payload.put("userId", userId);
            payload.put("id", deviceId);
            payload.put("data", data);

            StringEntity entity = new StringEntity(new Gson().toJson(payload));
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(httpPost);
            log.info("Status code: " + response.getStatusLine().getStatusCode());
            log.info("Response: " + new Gson().toJson(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

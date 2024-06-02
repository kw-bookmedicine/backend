package kr.KWGraduate.BookPharmacy.global.infra.fastapi;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class FastApiService {

    public enum RecommendType{
        Board("board/"),Client("client/"),Book("book/");
        private final String urlPath;
        RecommendType(String urlPath){
            this.urlPath = urlPath;
        }
        public String getUrlPath(Long id){
            return "http://localhost:8000/"+ this.urlPath +Long.toString(id);
        }
    }

    public String RecommendUpdate(RecommendType type, Long id) throws IOException {
        URL url = new URL(type.getUrlPath(id));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();

        return stringBuffer.toString();
    }


}

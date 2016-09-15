import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

public class HttpRequest {

    public static String sendPost(String targetURL, List<BasicNameValuePair> urlParameters)  {

        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(targetURL);

            // add header
            post.setHeader("User-Agent", USER_AGENT);

            post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));

            HttpResponse response = client.execute(post);
            System.out.println("\nSending 'POST' request to URL : " + targetURL);
            System.out.println("Post parameters : " + post.getEntity());
            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }
}

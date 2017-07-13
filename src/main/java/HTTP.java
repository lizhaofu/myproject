import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizhaofu on 2017/5/25.
 */
public class HTTP {

    public static String do_get(String url) throws ClientProtocolException, IOException {
        String body = "{}";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return body;
    }

    public static String getNO(String body){
        String[] st = body
                .split("\\n");
        String[] num= null;
        String no = null;
        for (int i = 0; i < st.length; i++) {
            if (st[i].contains("No")&&!st[i].contains("KeyNo")){
                num = st[i].trim().replaceAll("\\(i+\\)", "")
                        .replaceAll("[\\p{Punct}]", " ")
                        .replaceAll("\\t", " ").split(" ");
                for (int j = 0; j < num.length; j++) {
                    Pattern p = Pattern.compile(".*\\d+.*");

                    Matcher m = p.matcher(num[j]);
                    if (m.matches()){
                        no = num[j];
                    }
                }
            }
        }
        return no;
    }
}


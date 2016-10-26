package personal.mario.service;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*根据URL获取HTML文本*/
public class HtmlFetcherService {
	
	public static String fetch(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String rs = "";
		
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        
        HttpEntity entity = response.getEntity();
        
        if (response.getStatusLine().getStatusCode() == 200 && entity != null) {
        	rs = EntityUtils.toString(entity, "utf-8");
        }
        
    	response.close();
        httpclient.close();
        return rs;
	}
}
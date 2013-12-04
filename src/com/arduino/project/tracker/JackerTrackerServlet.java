package com.arduino.project.tracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


@SuppressWarnings("serial")
public class JackerTrackerServlet extends HttpServlet {
	
	private final String API_KEY = "AIzaSyB7u5flxCLsAQzypIgbMNbiJzioA1je2A0";
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		// the url to post info to
		HttpPost httpPost = new HttpPost("https://android.googleapis.com/gcm/send");
		
		// the info to post
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("key", API_KEY));
        nvps.add(new BasicNameValuePair("registration_ids", "[ABC]"));
		
        // encode the info
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        
        // do the request
        CloseableHttpResponse response = client.execute(httpPost);

        // do something with the response
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        
        client.close();
                
	}
	
	//curl --header "Authorization: key=AIzaSyB7u5flxCLsAQzypIgbMNbiJzioA1je2A0" --header Content-Type:"application/json" https://android.googleapis.com/gcm/send  -d "{\"registration_ids\":[\"ABC\"]}"
	
}
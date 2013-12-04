package com.arduino.project.tracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
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
public class MessageServlet extends BaseServlet {
	
	// server key AIzaSyDAR1ZuCCUXPLIaO-nXcTojSxbrFFefCcE
	// AIzaSyDrABM4JmbT2TzBDLMjL_A06B261g8YYgo
	private final String API_KEY 		= "AIzaSyDAR1ZuCCUXPLIaO-nXcTojSxbrFFefCcE";
	private final String PROJECT_ID 	= "jacker-tracker";
	private final String PROJECT_NUMBER = "933606158574";
	
	private static final String PARAMETER_REG_ID 	= "regId";
	private static final String MESSAGE 			= "msg";
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		// the url and headers to post
		HttpPost httpPost = new HttpPost("https://android.googleapis.com/gcm/send");
		
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		httpPost.setHeader("Authorization", "key="+API_KEY);
		
		// get the registration id of the phone to message
		String regId = getParameter(req, PARAMETER_REG_ID);
		
		// get the message type
		String msgType = getParameter(req, MESSAGE);
		
		// the info to post
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        //nvps.add(new BasicNameValuePair("key", API_KEY));
        nvps.add(new BasicNameValuePair("registration_ids", "[" + regId + "]"));
        nvps.add(new BasicNameValuePair("data.msg", msgType));
		
        // encode the info
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        
        // do the request
        CloseableHttpResponse response = client.execute(httpPost);

        // do something with the response
        try {
            
            HttpEntity entity = response.getEntity();
            
            // do something useful with the response body
            
            // TODO log this
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(entity));
            
            
            
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        
        client.close();
                
	}
	
	//curl --header "Authorization: key=AIzaSyB7u5flxCLsAQzypIgbMNbiJzioA1je2A0" --header Content-Type:"application/json" https://android.googleapis.com/gcm/send  -d "{\"registration_ids\":[\"ABC\"]}"
	
}
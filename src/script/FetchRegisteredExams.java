package script;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class FetchRegisteredExams {

	private CloseableHttpClient httpclient;
	private CloseableHttpResponse response;
	private HttpGet httpget;
	private HttpPost httppost;
	
	private String cookie;
	private String lt;
	
	private String username;
	private String password;
	
	public FetchRegisteredExams(String username, String password) {
		httpclient = HttpClients.createDefault();
		this.username = username;
		this.password = password;
	}
	
	public void fetchCookie() throws ParseException, IOException{
		httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(Constants.ilias_url);
		response = httpclient.execute(httpget);
		String html = org.apache.http.util.EntityUtils.toString(response.getEntity());
		Header[] headerArray = response.getAllHeaders();
		String cookie = "";
		for (int i = 0; i < headerArray.length; i++) {
			if (headerArray[i].getName().equals("Set-Cookie")) {
				cookie = headerArray[i].getValue();
				break;
			}
		}
		this.cookie = cookie.split("\\;")[0];	
		System.out.println(this.cookie);
		this.lt = (Parser.parseLT(html));
	}
	
	public void performLogin() throws ClientProtocolException, IOException{
		this.httppost = new HttpPost(Constants.ilias_url);
		httppost.setHeader("Cookie", this.cookie);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", this.username));
		params.add(new BasicNameValuePair("password", this.password));
		params.add(new BasicNameValuePair("lt", lt));
		params.add(new BasicNameValuePair("execution", "e1s1"));
		params.add(new BasicNameValuePair("_eventId", "submit"));
		params.add(new BasicNameValuePair("submit", "Anmelden"));
		httppost.setEntity(new UrlEncodedFormEntity(params));
		response = httpclient.execute(httppost);
	}
	

	public String fetchPruefungenNotenspiegel() throws ClientProtocolException, IOException{
		this.httpget = new HttpGet(Constants.portal2_url_2);
		httpget.setHeader("Cookie", this.cookie);
		response = httpclient.execute(httpget);
		String html = org.apache.http.util.EntityUtils.toString(response.getEntity());
		return html;
	}
	
	public String fetchNotenPage(String html) throws ClientProtocolException, IOException{
		System.out.println(html);
		String asiUrl = Parser.parseASIURL(html);
		httpget = new HttpGet(asiUrl);
		httpget.setHeader("Cookie", this.cookie);
		response = httpclient.execute(httpget);
		String htmlPage = org.apache.http.util.EntityUtils.toString(response.getEntity());
		return htmlPage;
	}
	
	public void performLogout() throws ClientProtocolException, IOException{
		this.httpget = new HttpGet(Constants.logout_url);
		httpget.setHeader("Cookie", this.cookie);
		this.response = httpclient.execute(httpget);
		
		this.cookie = null;
		this.lt = null;
	}
	
	public static void fetchRegisteredGrades(String username, String password) throws ParseException, IOException{
		FetchRegisteredExams fre = new FetchRegisteredExams(username, username);
		fre.fetchCookie();
		fre.performLogin();		
		String htmlPage = fre.fetchNotenPage(fre.fetchPruefungenNotenspiegel());
		//System.out.println(htmlPage);
		fre.performLogout();
		System.out.println("Script executed successfully");
		
	}
	
	
	public static void main(String[] args) throws IOException {
		try {
			//FetchRegisteredExams fre = new FetchRegisteredExams(Constants.PORTAL_USERNAME, Constants.PORTAL_PASSWORD);			
			fetchRegisteredGrades(Constants.PORTAL_USERNAME, Constants.PORTAL_PASSWORD);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

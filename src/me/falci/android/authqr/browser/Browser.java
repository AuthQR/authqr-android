package me.falci.android.authqr.browser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class Browser {

	private HttpClient httpClient;

	public Browser() {
		httpClient = new DefaultHttpClient();
	}

	private String process(HttpUriRequest method) throws BrowserException,
			IOException {
		HttpResponse response;
		try {
			response = httpClient.execute(method);

		} catch (IOException e) {
			throw new BrowserException("Problemas na conex�o com o servidor", e);
		}

		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();

		if (statusCode == 200) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);

		} else {
			throw new BrowserException("Falha no download");
		}

	}

	public String get(String url) throws BrowserException, IOException {
		Log.d("Browser:GET", url);
		HttpGet httpGet = new HttpGet(url);

		return process(httpGet);
	}

	public String post(String url, Map<String, String> postData) throws BrowserException {
		Log.d("Browser:POST", url);
		try {
			HttpPost httpPost = new HttpPost(url);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>(
					postData.size());
			for (String key : postData.keySet()) {
				nvps.add(new BasicNameValuePair(key, postData.get(key)));
				Log.d("PostData", String.format("%s: %s", key, postData.get(key)));
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			entity.writeTo(os);

			return os.toString(HTTP.UTF_8);

		} catch (IOException e) {
			throw new BrowserException("Problemas na conex�o com o servidor", e);
		}
	}

}

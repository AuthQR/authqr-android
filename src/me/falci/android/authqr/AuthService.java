package me.falci.android.authqr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class AuthService {

	private static final String URI = "%s/qr?code=%s&device=%s";
	private static final String SERVER = "http://api.authqr.com";

	public String processar(String qrCode, String android_id) {
		Log.d("AuthService", qrCode);

		String[] split = qrCode.split("\\?");

		String url = String.format(URI, SERVER, split[1], android_id);

		Log.d("AuthService", url);
		
		try {
			return get(url);
		} catch (MalformedURLException e) {
			return "QRCode incorreto.";

		} catch (IOException e) {
			return "Erro: "+e.getLocalizedMessage();

		}
	}

	public String get(String requestURL) throws MalformedURLException,
			IOException {
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
		return readStream(con.getInputStream());
	}

	private String readStream(InputStream in) throws IOException {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();

		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
}

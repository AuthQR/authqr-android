package me.falci.android.authqr.task;

import me.falci.android.authqr.browser.Browser;
import me.falci.android.authqr.browser.BrowserException;
import me.falci.android.authqr.vo.AuthVO;
import android.os.AsyncTask;

public class AuthTask extends AsyncTask<AuthVO, Void, Boolean>{
	private static final String SERVER = "http://authqr.herokuapp.com";
	private static final String ACCESS_URL = SERVER + "/access";

	public Boolean processar(AuthVO authVO) throws BrowserException {
		Browser browser = new Browser();
		String result = browser.post(ACCESS_URL, authVO.toMap());
		
		return result.contains("success: true");
	}

	@Override
	protected Boolean doInBackground(AuthVO... params) {
		for (AuthVO authVO : params) {
			try{
				return processar(authVO);
			} catch(BrowserException e){
				return false;
			}
		}
		
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
	}

}

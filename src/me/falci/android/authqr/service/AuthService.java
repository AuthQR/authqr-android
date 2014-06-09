package me.falci.android.authqr.service;

import java.util.regex.Pattern;

import me.falci.android.authqr.R;
import me.falci.android.authqr.task.AuthTask;
import me.falci.android.authqr.vo.AuthVO;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;

public class AuthService extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Pessimista :(
		setResult(RESULT_CANCELED);
		
		Uri uri = getIntent().getData();
		if(uri == null){
			finish();
			return;
		}
		
		try{
			String user = getUser();
			Boolean success = new AuthTask().execute(new AuthVO(uri, user)).get();

			if(success){
				setResult(RESULT_OK);
			}
			
		} catch(Exception e){

		}
		
		finish();
	}

	private String getUser() {
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(this).getAccounts();
		
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        return account.name;
		    }
		}
		
		throw new IllegalStateException("Impossível localizar o id do usuário");
	}

}

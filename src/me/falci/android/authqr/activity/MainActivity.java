package me.falci.android.authqr.activity;

import me.falci.android.authqr.R;
import me.falci.android.authqr.service.AuthService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	private static final int ZXING_INTENT_NUMBER = 1;
	private static final String ZXING_INTENT = "com.google.zxing.client.android.SCAN";
	private static final String ZXING_MARKET = "market://search?q=pname:com.google.zxing.client.android";
	private static final String ZXING_DIRECT = "https://zxing.googlecode.com/files/BarcodeScanner3.1.apk";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == ZXING_INTENT_NUMBER) {
			
			if (resultCode == RESULT_OK) {
				String qrCode = intent.getStringExtra("SCAN_RESULT");
				
				Intent accessActivity = new Intent("android.intent.action.VIEW", Uri.parse(qrCode), this, AuthService.class);
				startActivity(accessActivity);
				finish();
			}
		}
	}

	public void qrCode(View v) {
		Intent intent = new Intent(ZXING_INTENT);
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		intent.putExtra("PROMPT_MESSAGE", "Aguardando QR Code");
		
		try{
			startActivityForResult(intent, ZXING_INTENT_NUMBER);
			
		} catch (ActivityNotFoundException e) {
			solicitarInstalacao();
		}
		
	}
	
	private void solicitarInstalacao() {
		new AlertDialog.Builder(this)
		.setTitle("Instalar barcode scanner?")
		.setMessage("Para escanear QR code você precisa instalar o ZXing barcode scanner.")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("Instalar",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ZXING_MARKET));
				try {
					startActivity(intent);
				} catch (ActivityNotFoundException e) { // Se não tiver o Play Store
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ZXING_DIRECT));
					startActivity(intent);
				}
			}
		})
		.setNegativeButton("Cancelar", null).show();
		
	}

}

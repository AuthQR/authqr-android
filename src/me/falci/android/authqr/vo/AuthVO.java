package me.falci.android.authqr.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;

public class AuthVO {
	private final Map<String, String> map = new HashMap<String, String>();
	
	public AuthVO(final String code, final String user){
		init(code, user);
	}
	
	public AuthVO(Uri uri, String user) throws IllegalArgumentException {
		List<String> segments = uri.getPathSegments();
		
		if(segments.size() != 2){
			throw new IllegalArgumentException();
		}
		
		init(segments.get(1), user);
	}

	private void init(final String code, final String user) {
		map.put("code", code);
		map.put("user", user);
	}

	public Map<String, String> toMap() {
		return map;
	}
}

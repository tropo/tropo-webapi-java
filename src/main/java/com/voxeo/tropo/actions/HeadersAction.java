package com.voxeo.tropo.actions;

import com.voxeo.tropo.Key;
import com.voxeo.tropo.annotations.ValidKeys;

@ValidKeys(keys={})
public class HeadersAction extends JsonAction {
	
	public HeadersAction(String[]... headers) {
	
		super();
		setName("headers");
		Key[] keys = new Key[headers.length];
		int i =0;
		for(String[] header: headers) {
			if (header.length >= 2) {
				Key key = Key.createKey(header[0], header[1]);
				keys[i] = key;
			}
			i++;
		}
		addKeys(keys);
	}	
}

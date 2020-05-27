package com.hongrant.www.achieve.comm.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class StrTrim {

	public static Map<String, Object> mapTrim(Map<String, Object> map) {
		if (map == null) {
			return map;
		}
		HashMap<String, String> tempMap = Maps.newHashMap();
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			Object obj = entry.getValue();
			if (obj instanceof String) {
				String s = (String) obj;
				String trim = s.trim();
				tempMap.put(key, trim);
			}
		}

		for (String key : tempMap.keySet()) {
			map.put(key, tempMap.get(key));
		}
		
		return map;

	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> params = Maps.newHashMap();
		params.put("keyword", " 你  在  哪  ");
		params.put("shopCode", "code 0001");
		params.put("shopName", " name0001");
		params.put("chargePerson", "1");
		Map<String, Object> mapTrim = StrTrim.mapTrim(params);
		//System.out.println(mapTrim);
	}

}

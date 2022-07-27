package com.startuplab.common.vo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import lombok.Data;

@Data
public class FcmData {
	// String만 가능
	private String title;
	private String body;
	private String cmd;

	public Map<String, String> toMap() {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.convertValue(this, new TypeReference<HashMap<String, String>>() {});
		Iterables.removeIf(map.values(), x -> x == null);
		return map;
	}
}

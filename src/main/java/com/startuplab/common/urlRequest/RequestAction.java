package com.startuplab.common.urlRequest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

public class RequestAction {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public RequestVO execJson(String urlstr, Map<String, String> queryStrs) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		return exec("POST", urlstr, headers, queryStrs, null, true);
	}

	public RequestVO exec(String requestMethod, String urlstr, Map<String, String> headers, Map<String, String> queryStrs, FileInputStream fin) {
		return exec(requestMethod, urlstr, headers, queryStrs, fin, false);
	}

	public RequestVO exec(String requestMethod, String urlstr, Map<String, String> headers, Map<String, String> queryStrs, FileInputStream fin, boolean isJson) {
		RequestVO vo = new RequestVO();
		try {
			// 디폴트값 세팅
			if (requestMethod == null)
				requestMethod = "POST";
			// query parsing
			String queryStr = "";
			int i = 0;
			if (queryStrs != null && !isJson) {
				for (Entry<String, String> entry : queryStrs.entrySet()) {
					String gubun = "&";
					String key = entry.getKey();
					String value = entry.getValue();
					if (i == 0)
						gubun = "";
					queryStr += gubun + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
					i++;
				}
			}
			if (isJson) {
				requestMethod = "POST";
				queryStr = (new Gson()).toJson(queryStrs);
			}
			if (requestMethod.equals("GET") && !queryStr.equals(""))
				urlstr = urlstr + "?" + queryStr;
			URL url = new URL(urlstr);
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);
			// set header
			i = 0;
			if (headers != null) {
				for (Entry<String, String> entry : headers.entrySet()) {
					String key = URLEncoder.encode(entry.getKey(), "UTF-8");
					String value = URLEncoder.encode(entry.getValue(), "UTF-8");
					conn.setRequestProperty(key, value);
				}
			}
			if (queryStr != null && !queryStr.equals("")) {
				conn.setDoOutput(true);
				conn.setDoInput(true);
				OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
				os.write(queryStr);
				os.flush();
				os.close();
			}
			if (fin != null) {
				conn.setDoOutput(true);
				// conn.setDoInput(true);
				OutputStream os = conn.getOutputStream();
				int read = 0;
				byte[] buf1 = new byte[1024];
				while ((read = fin.read(buf1, 0, buf1.length)) != -1) {
					i++;
					os.write(buf1, 0, read);
					// logger.debug("upload==>"+Thread.currentThread().getId()+" ["+ i+"] " + "["+ i
					// / 1024 + "]");
				}
				os.flush();
				os.close();
				fin.close();
			}
			conn.connect();
			int responseCode = conn.getResponseCode();
			// logger.debug("responseCode : [{}]", responseCode);
			vo.setResponseCode(responseCode);
			if (responseCode == 200) {
				// header
				// logger.debug("responseHeader Start ===================================");
				Map<String, String> responseHeader = new HashMap<>();
				Map<String, List<String>> responseHeaders = conn.getHeaderFields();
				Iterator<String> it = responseHeaders.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					List<String> values = responseHeaders.get(key);
					StringBuffer sb = new StringBuffer();
					for (i = 0; i < values.size(); i++) {
						sb.append(";" + values.get(i));
					}
					// logger.debug("[{}] : [{}]", key, sb.toString().substring(1));
					responseHeader.put(key, sb.toString().substring(1));
				}
				// logger.debug("responseHeader End ===================================");
				vo.setResponseHeader(responseHeader);
				// body
				String responseBody = "";
				StringBuffer sbResult = new StringBuffer();
				InputStream is = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				while ((responseBody = br.readLine()) != null) {
					sbResult.append(responseBody);
				}
				responseBody = sbResult.toString().replaceAll("\r\n", "");
				// logger.debug("responseBody : [{}]", responseBody);
				vo.setResponseBody(responseBody);
			}
			conn.disconnect();
		} catch (Exception e) {
			logger.error("restful Exception Start ------------------");
			e.printStackTrace();
			logger.error("restful Exception End ------------------");
		}
		return vo;
	}
}

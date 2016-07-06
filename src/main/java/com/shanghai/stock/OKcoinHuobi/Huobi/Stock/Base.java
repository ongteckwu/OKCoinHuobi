package com.shanghai.stock.OKcoinHuobi.Huobi.Stock;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shanghai.stock.OKcoinHuobi.Huobi.Util.*;

public class Base {
	
	private Logger logger = LoggerFactory.getLogger(Base.class);
	//火币现货配置信息
	public static String HUOBI_ACCESS_KEY = "52612f43-9b250482-71090eca-af5e9";//Huobi申请的apiKey
	public static String HUOBI_SECRET_KEY = "b5c50526-dcd49dd6-5b3f5a46-1832b";//Huobi申请的secretKey
	public static String HUOBI_API_URL = "https://api.huobi.com/apiv3";
	
	//bitvc现货，期货共用accessKey,secretKey配置信息
	public static String BITVC_ACCESS_KEY = "";
	public static String BITVC_SECRET_KEY = "";
    
    
	
	
	protected static int success = 200;
	
	
	public String post(Map<String, Object> map,String url) throws Exception {
		return HttpUtil.post(url, map, new ResponseHandler<String>() {
			
			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int code = response.getStatusLine().getStatusCode();
				if(success == code) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				}
				logger.info("response code {}", code);
				return null;
			}
			
		});
	}
	
	public long getTimestamp() {
		return System.currentTimeMillis()/1000;
	}
	
	public String sign(TreeMap<String, Object> map) {
		StringBuilder inputStr = new StringBuilder();
		for (Map.Entry<String, Object> me : map.entrySet()) {
            inputStr.append(me.getKey()).append("=").append(me.getValue()).append("&");
        }
        String md5Str = EncryptUtil.MD5(inputStr.substring(0, inputStr.length() - 1)).toLowerCase();
        return md5Str;
	}
	
}

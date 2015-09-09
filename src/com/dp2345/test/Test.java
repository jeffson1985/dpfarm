package com.dp2345.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.dp2345.sms.SmsIsp;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws JsonParseException, IOException {
		// TODO Auto-generated method stub
		Base64 base64 = new Base64();
		System.out.println(new String(base64.encodeToString("dp2345.com".getBytes())));
		System.out.println(new String(base64.decode("UG93ZXJlZEJ5"), "utf-8"));
		System.out.println(new String(base64.decode("ZHAyMzQ1LmNvbQ=="), "utf-8"));
		//new String(base64.decode("UG93ZXJlZEJ5"), "utf-8"), new String(base64.decode("U2hvcHh4Lm5ldA==")
		
		
		String json = "{\"name\":\"mkyong\", \"age\":\"29\"}";
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String,String>();
		map = mapper.readValue(json, new TypeReference<HashMap<String,String>>(){});
		System.out.println(map.get("name"));
		
		SmsIsp isp = SmsIsp.YUNPIANWANG;
		if(isp.getId() == 1){
			System.out.println(isp);
		}
		
		switch(isp){
			case YUNPIANWANG :{
				System.out.println(SmsIsp.YUNPIANWANG);
				break;
			}
			case WEBCHINESE :{
				System.out.println(SmsIsp.WEBCHINESE);
				break;
			}
		}

	}

}

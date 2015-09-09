package com.dp2345.sms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.dp2345.exception.SmsException;
import com.dp2345.exception.SmsSendException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JavaSmsSenderImpl implements JavaSmsSender{
	private static final Log logger = LogFactory.getLog(JavaSmsSenderImpl.class);
	
	private SmsConfig config = null;
	
	public JavaSmsSenderImpl(){}
	
	public JavaSmsSenderImpl(SmsConfig config){
		this.config = config;
	}
	public static JavaSmsSenderImpl getInstance(SmsConfig config){
		return new JavaSmsSenderImpl(config);
	}

	public void send(SimpleSmsMessage simpleMessage) throws SmsException {
		try {
			String result = sendSmsByYunpian(simpleMessage);
			// 返回JSON内容解析
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> map = new HashMap<String,String>();
			map = mapper.readValue(result, new TypeReference<HashMap<String,String>>(){});
			String code =map.get("code");
			if(!code.equals("0")){
				throw new SmsSendException(code);
			}
			
		} catch (Exception e) {
			throw new SmsSendException("999", e);
		}
		
	}

	public void send(SimpleSmsMessage[] simpleMessages) throws SmsException{
		
			for(SimpleSmsMessage msg: simpleMessages){
				try {
					sendSmsByYunpian(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
	
	public void send(SmsConfig config, SimpleSmsMessage simpleMessage) throws SmsException {
		this.config = config;
		this.send(simpleMessage);
		
	}

	public void send(SmsConfig config, SimpleSmsMessage[] simpleMessages) throws SmsException {
		this.config = config;
		this.send(simpleMessages);
	}
	/**
	 * 云片网通用接口发短信（推荐）
	 * 
	 * @param apikey
	 *            apikey
	 * @param content
	 *            　短信内容
	 * @param mobile
	 *            　接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */
	public String sendSmsByYunpian(SimpleSmsMessage simpleMessage) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey",config.getSmsApiKey());
		params.put("text", simpleMessage.getText());
		params.put("mobile", simpleMessage.getTo());
		return post(config.getSmsApiHost(), params);
	}

	/**
	 * 基于HttpClient 4.3的通用POST方法
	 * 
	 * @param url
	 *            提交的URL
	 * @param paramsMap
	 *            提交<参数，值>Map
	 * @return 提交响应
	 * @throws IOException
	 */
	public  String post(String url, Map<String, String> paramsMap) throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(),
							param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
			}
			response = client.execute(method);
			client.execute(method);
			int status = response.getStatusLine().getStatusCode();
			// 连接状态判断
			if(status == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseText = EntityUtils.toString(entity);
					logger.info("云片网返回信息："+ responseText);
					System.out.println("云片网返回信息："+ responseText);
				}
			}else {
				throw new SmsSendException("999");
			}
			
		}finally {
			response.close();
		}
		return responseText;
	}
	

	public SmsConfig getConfig() {
		return config;
	}

	public void setConfig(SmsConfig config) {
		this.config = config;
	};
	

}
/*
 * 云片网返回值JSON
  {
    "code": 5,               //错误码
    "msg": "账户余额不足",   //错误描述
    "detail": "请充值后重试" //具体错误描述或解决方法
  }  
  	code = 0:	正确返回。可以从api返回的对应字段中取数据。
	code > 0:	调用API时发生错误，需要开发者进行相应的处理。
	-50 < code <= -1:	权限验证失败，需要开发者进行相应的处理。
	code <= -50:	系统内部错误，请联系技术支持，调查问题原因并获得解决方案。 
*/  

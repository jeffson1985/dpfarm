package com.dp2345.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 短信http接口的java代码调用示例
 * 
 * @author jeffson
 * @since 2013-12-1
 */
public class TestYunPianSms {
	/**
	 * 服务http地址
	 */
	private static String BASE_URI = "http://yunpian.com";
	/**
	 * 服务版本号
	 */
	private static String VERSION = "v1";
	/**
	 * 编码格式
	 */
	private static String ENCODING = "UTF-8";
	/**
	 * 查账户信息的http地址
	 */
	private static String URI_GET_USER_INFO = BASE_URI + "/" + VERSION
			+ "/user/get.json";
	/**
	 * 通用发送接口的http地址
	 */
	private static String URI_SEND_SMS = BASE_URI + "/" + VERSION
			+ "/sms/send.json";
	/**
	 * 模板发送接口的http地址
	 */
	private static String URI_TPL_SEND_SMS = BASE_URI + "/" + VERSION
			+ "/sms/tpl_send.json";

	/**
	 * 取账户信息
	 * 
	 * @return json格式字符串
	 * @throws IOException
	 */
	public static String getUserInfo(String apikey) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet method = new HttpGet(URI_GET_USER_INFO + "?apikey="
				+ apikey);
		
		CloseableHttpResponse response = null;
		String responseText = "";
		response = client.execute(method);
		client.execute(method);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			responseText = EntityUtils.toString(entity, "UTF-8");
		}
		return responseText;
	}

	/**
	 * 发短信
	 * 
	 * @param apikey
	 *            apikey
	 * @param text
	 *            　短信内容　
	 * @param mobile
	 *            　接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */
	public static String sendSms(String apikey, String text, String mobile)
			throws IOException {
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost method = new HttpPost(URI_TPL_SEND_SMS);
		CloseableHttpResponse response = null;
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("apikey", apikey));
		paramList.add(new BasicNameValuePair("text", text));
		paramList.add(new BasicNameValuePair("mobile", mobile));
		
		
		String responseText = "";
		method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
		response = client.execute(method);
		client.execute(method);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			responseText = EntityUtils.toString(entity);
		}
		
		return responseText;
	}

	/**
	 * 通过模板发送短信
	 * 
	 * @param apikey
	 *            apikey
	 * @param tpl_id
	 *            　模板id
	 * @param tpl_value
	 *            　模板变量值　
	 * @param mobile
	 *            　接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */
	public static String tplSendSms(String apikey, long tpl_id,
			String tpl_value, String mobile) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost method = new HttpPost(URI_TPL_SEND_SMS);
		CloseableHttpResponse response = null;
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("apikey", apikey));
		paramList.add(new BasicNameValuePair("tpl_id", String.valueOf(tpl_id)));
		paramList.add(new BasicNameValuePair("tpl_value", tpl_value));
		paramList.add(new BasicNameValuePair("mobile", mobile));
		
		
		String responseText = "";
		method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
		response = client.execute(method);
		client.execute(method);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			responseText = EntityUtils.toString(entity);
		}
		return responseText;
	}

	public static void main(String[] args) throws IOException {
		// 修改为您的apikey
		String apikey = "c5898c288d5b7b50664713eb47a89705";
		// 修改为您要发送的手机号
		String mobile = "15908025326";
		/**************** 查账户信息调用示例 *****************/
		System.out.println(TestYunPianSms.getUserInfo(apikey));
		/**************** 使用通用接口发短信 *****************/
		// 设置您要发送的内容
		String text = "甲东家庭农场网上商城短信测试";
		// 发短信调用示例
		System.out.println(TestYunPianSms.sendSms(apikey, text, mobile));
		/**************** 使用模板接口发短信 *****************/
		// 设置模板ID，如使用1号模板:您的验证码是#code#【#company#】
		//long tpl_id = 1;
		// 设置对应的模板变量值
		//String tpl_value = "#code#=1234&#company#=云片网";
		// 模板发送的调用示例
		//System.out.println(TestYunPianSms.tplSendSms(apikey, tpl_id, tpl_value,mobile));
	}
}

/**
/ configurations
int socketTimeout = 3;
int connectionTimeout = 3;
String userAgent = "My Http Client 0.1";
// request configuration
RequestConfig requestConfig = RequestConfig.custom()
    .setConnectTimeout(connectionTimeout)
    .setSocketTimeout(socketTimeout)
    .build();
// headers
List<Header> headers = new ArrayList<Header>();
headers.add(new BasicHeader("Accept-Charset","utf-8"));
headers.add(new BasicHeader("Accept-Language","ja, en;q=0.8"));
headers.add(new BasicHeader("User-Agent",userAgent));
// create client
HttpClient httpClient = HttpClientBuilder.create()
    .setDefaultRequestConfig(requestConfig)
    .setDefaultHeaders(headers).build();
    
    
    HttpGet httpGet = new HttpGet();
  HttpResponse response = httpClient.execute(method);  
  int responseStatus = response.getStatusLine().getStatusCode();
  String body = EntityUtils.toString(response.getEntity(), "UTF-8");
  
  HttpPost method = new HttpPost(url);
  List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
  requestParams.add(new BasicNameValuePair("foo","var");
  method.setEntity(new UrlEncodedFormEntity(requestParams));  
  HttpResponse response = httpClient.execute(method);  
  int responseStatus = response.getStatusLine().getStatusCode();
  String body = EntityUtils.toString(response.getEntity(), "UTF-8");
  
  
  

*/
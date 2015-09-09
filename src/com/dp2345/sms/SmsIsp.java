/**
 * 
 */
package com.dp2345.sms;

/**
 * 短信服务提供商
 * @author Sun
 *
 */
public enum SmsIsp {
	
	YUNPIANWANG(1,"云片网"), 	// 云片网
	SUBMAIL(2,"Submail"), 		//  https://api.submail.cn/message/xsend.json 邮件短信都提供
	WEBCHINESE(3,"中国网建"),   	// 中国网建 http://sms.web
	TUI3(4,"推立方"), 		 	// 推立方 不推荐http://www.tui3.com/api/code/?k=发送密钥&r=执行结果格式&t=接收手机号&c=发送的验证码&ti=模板内容索引
	LUOSIMAO(5,"螺丝帽"),  		// http://sms-api.luosimao.com/v1/send.[json|xml|jsonp]
	SMSBAO(6,"短信猫"); 		// 短信猫 http://api.smsbao.com/sms?u=USERNAME&p=PASSWORD&m=PHONE&c=CONTENT
	
	private int id;
	private String name;
	private SmsIsp(int _id, String _name){
		this.id  = _id;
		this.name = _name;
	}
	@Override
	public String toString(){
		return name;
	}
	
	public int getId(){
		return this.id;
	}
}

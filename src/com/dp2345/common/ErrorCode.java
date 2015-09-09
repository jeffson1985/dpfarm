package com.dp2345.common;

/**
 * 标题、简要说明. <br>
 * 类详细说明.暂时未启用
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 * @author Jeffson
 * @version 1.0.0
 */
public class ErrorCode {

    public class Message {

        public static final int CHECKCODE_ERROR = 4001; // 验证码错误

        public static final int REPEAT_RECEIVE = 4002;// 重复领取奖励

        public static final int SMS_EXPIRE = 4003; // 验证码已过期

        public static final int SMS_NOT_EXPIRE = 4004; // 验证码已过期

        public static final int SMS_NOTEXIST = 4005; // 验证码不存在，请重新发送

    }

    public class Customer {

        public static final int IDS_REQUIRED = 4006; // 需要勾选才能领取
    }

    public class Login {

        public static final int ENCRYPT_PWD_ERROR = 5001; // 登陆时，加密用户输入的密码用于比较失败
    }
}

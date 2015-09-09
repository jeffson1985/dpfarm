/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.template.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dp2345.entity.Member;
import com.dp2345.service.MemberService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 当前会员
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("currentMemberDirective")
public class CurrentMemberDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "currentMember";

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Member currentMember = memberService.getCurrent();
		if (body != null) {
			setLocalVariable(VARIABLE_NAME, currentMember, env, body);
		} else {
			if (currentMember != null) {
				Writer out = env.getOut();
				out.write(currentMember.getUsername());
			}
		}
	}

}
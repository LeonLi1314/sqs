package com.rtmap.traffic.sqs.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rtmap.traffic.sqs.domain.SecurityResult;
import com.rtmap.traffic.sqs.service.ISecurityQueueService;

/**
 * 安检排队控制器
 * 
 * @author liqingshan
 *
 */
@Controller
public class SecurityQueueController {

	@Resource
	private ISecurityQueueService securityQueueService;

	@ResponseBody
	@RequestMapping(value = "/latestResult.do", method = RequestMethod.GET)
	public SecurityResult getLatestProfessionResult() {
		return securityQueueService.getLatestProfessionResult();
	}

	@ResponseBody
	@RequestMapping(value = "/latestXmlResult.do", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getLatestProfessionXmlResult() {
		String rst = securityQueueService.getLatestProfessionXmlResult();
		return rst;
	}
}
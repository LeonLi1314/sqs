package com.rtmap.traffic.sqs.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rtmap.traffic.sqs.domain.StatusRule;

/**
 * 规则匹配器
 * 
 * @author liqingshan 2015-12-14
 *
 */
@Component
public class RuleMatcher {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<StatusRule> rules;

	/**
	 * 规则匹配器的构造函数
	 * 
	 * @param rules
	 *            配置文件中设置的规则集合
	 */
	public RuleMatcher(List<StatusRule> rules) {
		if(rules == null || rules.size() == 0)
			logger.error("applicationContext配置文件中的匹配规则配置有误！");
		
		this.rules = rules;
	}

	/**
	 * 匹配等待时长对应的规则
	 * 
	 * @param seconds
	 *            等待时长（秒）
	 * @return 对应的规则
	 */
	public StatusRule match(int seconds) {
		if (seconds <= 0)
			return rules.get(0);

		for (StatusRule statusRule : rules) {
			if (statusRule.getFloor() <= seconds && statusRule.getCeiling() >= seconds)
				return statusRule;
		}

		return rules.get(rules.size() - 1);
	}
}

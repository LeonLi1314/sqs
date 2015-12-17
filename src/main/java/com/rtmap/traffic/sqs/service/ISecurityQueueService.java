package com.rtmap.traffic.sqs.service;

import com.rtmap.traffic.sqs.domain.SecurityResult;

/**
 * 安检排队服务层接口
 * 
 * @author liqingshan
 *
 */
public interface ISecurityQueueService {
	/**
	 * 获取最近的专业结果
	 * 
	 * @return
	 */
	SecurityResult getLatestProfessionResult();

}

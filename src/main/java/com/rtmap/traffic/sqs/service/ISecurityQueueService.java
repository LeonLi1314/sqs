package com.rtmap.traffic.sqs.service;

import com.rtmap.traffic.sqs.domain.AsupData;
import com.rtmap.traffic.sqs.domain.SecurityResult;

/**
 * 安检排队服务层接口
 * 
 * @author liqingshan
 *
 */
public interface ISecurityQueueService {
	SecurityResult getLatestAvgProfessionResult();

	AsupData getLatestAvgProfessionData();

	SecurityResult getLatestMedianProfessionResult();

	AsupData getLatestMedianProfessionData();
}

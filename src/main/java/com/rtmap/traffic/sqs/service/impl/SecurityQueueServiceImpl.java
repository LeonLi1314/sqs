package com.rtmap.traffic.sqs.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rtmap.traffic.sqs.dao.IProfessionResultSegmtDao;
import com.rtmap.traffic.sqs.domain.ProfessionResultSegmt;
import com.rtmap.traffic.sqs.domain.SecurityResult;
import com.rtmap.traffic.sqs.domain.StatusRule;
import com.rtmap.traffic.sqs.service.ISecurityQueueService;

import lqs.frame.util.DatePatterns;
import lqs.frame.util.DateUtils;

/**
 * 安检排队业务处理类
 * 
 * @author liqingshan 2015-12-14
 *
 */
@Service
public class SecurityQueueServiceImpl implements ISecurityQueueService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RuleMatcher matcher;
	@Resource
	private IProfessionResultSegmtDao dao;

	@Override
	public SecurityResult getLatestProfessionResult() {
		// 查找当前十一分钟内最新的数据
		// Date date = DateUtils.addMinute(new Date(), -11);
		// test
		Date date = DateUtils.parseDate("2015-11-08");

		ProfessionResultSegmt segmt = dao.selectLatestByPorcessTime(date);

		// 查询前一天高峰的时间点
		// Date endDate = DateUtils.getCurrentDate();
		// test
		Date endDate = DateUtils.parseDate("2015-11-08");
		Date beginDate = DateUtils.addDay(endDate, -1);
		int topNum = 4;
		List<Date> setmts = dao.selectTopOfAvgDuarOfSegmts(beginDate, endDate, topNum);

		// 查询前一天5点至22点的每小时平均排队等待时长
		endDate = DateUtils.addHour(endDate, -1);
		beginDate = DateUtils.addHour(beginDate, 5);
		List<Integer> avgDuars = dao.selectPerHourAvgDuars(beginDate, endDate);

		SecurityResult rst = new SecurityResult();

		if (segmt == null || setmts == null || setmts.size() != 4 || avgDuars == null || avgDuars.size() != 18) {
			String msg = String.format("本次查询最新安检排队时长无有效结果，检查时刻：%s",
					DateUtils.formatDate(date, DatePatterns.POPULAR_DATE24TIME));
			rst.setStatusCode(-1);
			rst.setMessage(msg);
			logger.error(msg);
			return rst;
		}

		// 各数据获取成功，组装返回对象

		double loadRate = (segmt.getAvgDuar() / 480.0) * 100;
		StatusRule statusRule = matcher.match(segmt.getAvgDuar());
		rst.setStatusCode(100);
		rst.setMessage("成功");

		rst.setEvnt("STANDARDSECURITYINFO");
		rst.setDttm(DateUtils.getCurrentDateTime());
		rst.setArea("T3C-D");
		rst.setWaitingTime(segmt.getAvgDuar() / 60);
		rst.setLoadfactor((int) loadRate);
		rst.setWatingStatus(statusRule.getStatus());
		rst.setWatingLevel(statusRule.getColor());
		rst.setWatingPeopleCount(-1);
		rst.setPassRate((int) segmt.getPassRate());

		StringBuilder timeSb = new StringBuilder(32);
		for (Date setmt : setmts) {
			timeSb.append(DateUtils.formatDate(setmt, "HH:mm"));
			timeSb.append("|");
		}
		rst.setWatingPeakTime(timeSb.substring(0, timeSb.length() - 1).toString());

		StringBuilder colorSb = new StringBuilder(126);
		for (Integer integer : avgDuars) {
			colorSb.append(matcher.match(integer.intValue()).getColor());
			colorSb.append("|");
		}
		rst.setDailyWatingLevel(colorSb.substring(0, colorSb.length() - 1).toString());

		return rst;
	}
}

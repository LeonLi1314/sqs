package com.rtmap.traffic.sqs.service.impl;

import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.rtmap.traffic.sqs.dao.IProfessionResultSegmtDao;
import com.rtmap.traffic.sqs.domain.AsupBody;
import com.rtmap.traffic.sqs.domain.AsupBodyMsg;
import com.rtmap.traffic.sqs.domain.AsupData;
import com.rtmap.traffic.sqs.domain.AsupHeader;
import com.rtmap.traffic.sqs.domain.ProfessionResultSegmt;
import com.rtmap.traffic.sqs.domain.SecurityInfo;
import com.rtmap.traffic.sqs.domain.SecurityInfoBody;
import com.rtmap.traffic.sqs.domain.SecurityInfoMeta;
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

	private String currDateStr;

	public void setCurrDateStr(String currDateStr) {
		this.currDateStr = currDateStr;
	}

	public SecurityResult getLatestProfessionResult(boolean isMedian) {
		Date currDate = DateUtils.getCurrentDateTime();
		if (!StringUtils.isEmpty(currDateStr)) {
			currDate = DateUtils.parseDate(currDateStr);
		}
		// 查找当前十一分钟内最新的数据
		Date date = DateUtils.addMinute(currDate, -11);

		ProfessionResultSegmt segmt = dao.selectLatestByPorcessTime(date);
		// 查询前一天4点至23点的高峰时间点
		
		Date endDate = DateUtils.addHour(DateUtils.getDate(currDate), -1);
		Date beginDate = DateUtils.addHour(DateUtils.addDay(DateUtils.getDate(currDate), -1), 4);
		int topNum = 4;
		List<Date> setmts = dao.selectTopOfAvgDuarOfSegmts(beginDate, endDate, topNum, isMedian);

		// 查询前一天5点至22点（包含22点之后的分钟）的每小时平均排队等待时长
		Date endDate1 = DateUtils.addHour(DateUtils.getDate(currDate), -1);
		Date beginDate1 = DateUtils.addHour(DateUtils.addDay(DateUtils.getDate(currDate), -1), 5);
		List<Integer> avgDuars = dao.selectPerHourAvgDuars(beginDate1, endDate1, isMedian);

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

		double loadRate = (segmt.getMedianDuar() / 480.0) * 100;
		StatusRule statusRule = matcher.match(segmt.getMedianDuar());
		rst.setStatusCode(100);
		rst.setMessage("成功");

		rst.setEvnt("STANDARDSECURITYINFO");
		rst.setDttm(DateUtils.getCurrentDateTime());
		rst.setArea("T3C-D");
		rst.setWaitingTime(segmt.getMedianDuar() / 60);
		rst.setLoadfactor((int) loadRate);
		rst.setWaitingStatus(statusRule.getStatus());
		rst.setWaitingLevel(statusRule.getColor());
		rst.setWaitingPeopleCount(-1);
		rst.setPassRate((int) segmt.getPassRate());

		StringBuilder timeSb = new StringBuilder(32);
		for (Date setmt : setmts) {
			timeSb.append(DateUtils.formatDate(setmt, "HH:mm"));
			timeSb.append("|");
		}
		rst.setWaitingPeakTime(timeSb.substring(0, timeSb.length() - 1).toString());

		StringBuilder colorSb = new StringBuilder(126);
		for (Integer integer : avgDuars) {
			colorSb.append(matcher.match(integer.intValue()).getColor());
			colorSb.append("|");
		}
		rst.setDailyWaitingLevel(colorSb.substring(0, colorSb.length() - 1).toString());

		return rst;
	}

	public AsupData getLatestProfessionData(boolean isMedian) {
		SecurityResult result = getLatestProfessionResult(isMedian);

		if (result.getStatusCode() == -1) {
			logger.error(result.getMessage());
			return null;
		}

		AsupData data = new AsupData();
		AsupHeader asupHeader = new AsupHeader();
		asupHeader.setServiceName("SMISPDINFO");
		asupHeader.setSendSystem("SMISPD");
		asupHeader.setCreateTime(DateUtils.getCurrentDateTime());
		AsupBody asupBody = new AsupBody();
		data.setAsupHeader(asupHeader);
		data.setAsupBody(asupBody);

		AsupBodyMsg asupBodyMsg = new AsupBodyMsg();
		asupBody.setMsg(asupBodyMsg);

		SecurityInfo securityInfo = new SecurityInfo();
		asupBodyMsg.setSecurityInfo(securityInfo);

		SecurityInfoMeta infoMeta = new SecurityInfoMeta();
		infoMeta.setEvnt(result.getEvnt());
		infoMeta.setDttm(result.getDttm());
		SecurityInfoBody infoBody = new SecurityInfoBody();
		infoBody.setArea(result.getArea());
		infoBody.setWaitingTime(result.getWaitingTime());
		infoBody.setLoadfactor(result.getLoadfactor());
		infoBody.setWaitingStatus(result.getWaitingStatus());
		infoBody.setWaitingLevel(result.getWaitingLevel());
		infoBody.setWaitingPeopleCount(result.getWaitingPeopleCount());
		infoBody.setPassRate(result.getPassRate());
		infoBody.setWaitingPeakTime(result.getWaitingPeakTime());
		infoBody.setDailyWatingLevel(result.getDailyWaitingLevel());

		securityInfo.setMeta(infoMeta);
		securityInfo.setBody(infoBody);
		return data;
	}

	@Deprecated
	public String getLatestProfessionXmlResult(boolean isMedian) {
		SecurityResult result = getLatestProfessionResult(isMedian);

		if (result.getStatusCode() == -1) {
			logger.error(result.getMessage());
			return null;
		}

		AsupData data = new AsupData();
		AsupHeader asupHeader = new AsupHeader();
		asupHeader.setServiceName("SMISPDINFO");
		asupHeader.setSendSystem("SMISPD");
		asupHeader.setCreateTime(DateUtils.getCurrentDateTime());
		AsupBody asupBody = new AsupBody();
		data.setAsupHeader(asupHeader);
		data.setAsupBody(asupBody);

		AsupBodyMsg asupBodyMsg = new AsupBodyMsg();
		asupBody.setMsg(asupBodyMsg);

		SecurityInfo securityInfo = new SecurityInfo();
		asupBodyMsg.setSecurityInfo(securityInfo);

		SecurityInfoMeta infoMeta = new SecurityInfoMeta();
		infoMeta.setEvnt(result.getEvnt());
		infoMeta.setDttm(result.getDttm());
		SecurityInfoBody infoBody = new SecurityInfoBody();
		infoBody.setArea(result.getArea());
		infoBody.setWaitingTime(result.getWaitingTime());
		infoBody.setLoadfactor(result.getLoadfactor());
		infoBody.setWaitingStatus(result.getWaitingStatus());
		infoBody.setWaitingLevel(result.getWaitingLevel());
		infoBody.setWaitingPeopleCount(result.getWaitingPeopleCount());
		infoBody.setPassRate(result.getPassRate());
		infoBody.setWaitingPeakTime(result.getWaitingPeakTime());
		infoBody.setDailyWatingLevel(result.getDailyWaitingLevel());

		securityInfo.setMeta(infoMeta);
		securityInfo.setBody(infoBody);

		JAXBContext jaxbContext;

		try {
			jaxbContext = JAXBContext.newInstance(AsupData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(data, writer);

			return writer.toString();
		} catch (JAXBException e) {
			logger.error("JAXB 对象转化 XML 错误" + e.toString());
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public SecurityResult getLatestAvgProfessionResult() {
		return getLatestProfessionResult(false);
	}

	@Override
	public AsupData getLatestAvgProfessionData() {
		return getLatestProfessionData(false);
	}

	@Override
	public SecurityResult getLatestMedianProfessionResult() {
		return getLatestProfessionResult(true);
	}

	@Override
	public AsupData getLatestMedianProfessionData() {
		return getLatestProfessionData(true);
	}
}

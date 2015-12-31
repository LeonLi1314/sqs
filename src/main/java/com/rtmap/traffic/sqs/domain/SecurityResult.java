package com.rtmap.traffic.sqs.domain;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import lqs.frame.util.DatePatterns;

/**
 * 安检排队分析结果
 * 
 * @author liqingshan 2015-12-14
 *
 */
@JSONType(orders = { "statusCode", "message", "evnt", "dttm", "area", "waitingTime", "loadfactor", "watingStatus",
		"watingLevel", "watingPeopleCount", "passRate", "watingPeakTime", "dailyWatingLevel" }, ignores = {})
public class SecurityResult {
	private int statusCode;
	private String message;

	/**
	 * 获取操作状态码
	 * 
	 * @return 操作状态码
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * 设置操作状态码
	 * 
	 * @param statusCode
	 *            操作状态码
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 获取操作的返回信息
	 * 
	 * @return 返回信息
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置操作的返回信息
	 * 
	 * @param message
	 *            返回信息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	private String evnt;
	private Date dttm;
	private String area;
	private int waitingTime;
	private int loadfactor;
	private String waitingStatus;
	private String waitingLevel;
	private int waitingPeopleCount;
	private int passRate;
	private String waitingPeakTime;
	private String dailyWaitingLevel;

	/**
	 * 获取消息事件类型
	 */
	public String getEvnt() {
		return evnt;
	}

	/**
	 * 设置消息事件类型
	 */
	public void setEvnt(String evnt) {
		this.evnt = evnt;
	}

	/**
	 * 获取消息发布时间
	 */
	@JSONField(format = DatePatterns.POPULAR_DATE24TIME)
	public Date getDttm() {
		return dttm;
	}

	/**
	 * 设置消息发布时间
	 */
	public void setDttm(Date dttm) {
		this.dttm = dttm;
	}

	/**
	 * 获取消息对应区域
	 */
	public String getArea() {
		return area;
	}

	/**
	 * 设置消息对应区域
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * 获取等待时长（单位：分钟）
	 */
	public int getWaitingTime() {
		return waitingTime;
	}

	/**
	 * 设置等待时长（单位：分钟）
	 */
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	/**
	 * 获取负载率（以等待时长除以8得到的百分比）
	 */
	public int getLoadfactor() {
		return loadfactor;
	}

	/**
	 * 设置负载率（以等待时长除以8得到的百分比）
	 */
	public void setLoadfactor(int loadfactor) {
		this.loadfactor = loadfactor;
	}

	/**
	 * 获取等待时长对应显示的文字提示（顺畅|需排队等候|拥堵 后续后台可配置规则）
	 */
	public String getWaitingStatus() {
		return waitingStatus;
	}

	/**
	 * 设置等待时长对应显示的文字提示（顺畅|需排队等候|拥堵 后续后台可配置规则）
	 */
	public void setWaitingStatus(String watingStatus) {
		this.waitingStatus = watingStatus;
	}

	/**
	 * 获取等待时长对应显示的级别（顺畅对应绿色GREEN|需排队等候对应黄色YELLOW|拥堵对应红色RED 后续后台可配置规则）
	 */
	public String getWaitingLevel() {
		return waitingLevel;
	}

	/**
	 * 设置等待时长对应显示的级别（顺畅对应绿色GREEN|需排队等候对应黄色YELLOW|拥堵对应红色RED 后续后台可配置规则）
	 */
	public void setWaitingLevel(String watingLevel) {
		this.waitingLevel = watingLevel;
	}

	/**
	 * 获取排队等候区当前估算人数（单位：个）。暂不提供
	 */
	// @JSONField(serialize = false)
	public int getWaitingPeopleCount() {
		return waitingPeopleCount;
	}

	/**
	 * 设置排队等候区当前估算人数（单位：个）
	 */
	public void setWaitingPeopleCount(int watingPeopleCount) {
		this.waitingPeopleCount = watingPeopleCount;
	}

	/**
	 * 获取安检通道整体通过率（单位：个每10分钟）
	 */
	public int getPassRate() {
		return passRate;
	}

	/**
	 * 设置安检通道整体通过率（单位：个每10分钟）
	 */
	public void setPassRate(int passRate) {
		this.passRate = passRate;
	}

	/**
	 * 获取等待时长四个高峰时间点，规格HH24:mi|分割。
	 */
	public String getWaitingPeakTime() {
		return waitingPeakTime;
	}

	/**
	 * 设置等待时长四个高峰时间点，规格HH24:mi|分割。
	 */
	public void setWaitingPeakTime(String watingPeakTime) {
		this.waitingPeakTime = watingPeakTime;
	}

	/**
	 * 获取5点-22点 每小时段内整体状态水平，顺畅对应绿色GREEN|需排队等候对应黄色YELLOW|拥堵对应红色RED。
	 */
	public String getDailyWaitingLevel() {
		return dailyWaitingLevel;
	}

	/**
	 * 设置5点-22点 每小时段内整体状态水平，顺畅对应绿色GREEN|需排队等候对应黄色YELLOW|拥堵对应红色RED。
	 */
	public void setDailyWaitingLevel(String dailyWatingLevel) {
		this.dailyWaitingLevel = dailyWatingLevel;
	}
}

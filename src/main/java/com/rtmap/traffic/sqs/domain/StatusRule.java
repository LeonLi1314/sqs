package com.rtmap.traffic.sqs.domain;

/**
 * 安检排队时长计算规则
 * 
 * @author liqingshan 2015-12-14
 *
 */
public class StatusRule {
	private int floor;
	private int ceiling;
	private String status;
	private String color;

	/**
	 * 获取此规则区间的等待时长下限（秒）
	 * 
	 * @return 等待时长的下限
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * 设置此规则区间的等待时长上限（秒）
	 * 
	 * @param floor
	 *            等待时长的下限
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}

	/**
	 * 获取此规则区间的等待时长上限（秒）
	 * 
	 * @return 等待时长的上限
	 */
	public int getCeiling() {
		return ceiling;
	}

	/**
	 * 设置此规则区间的等待时长上限（秒）
	 * 
	 * @param ceiling
	 *            等待时长的上限
	 */
	public void setCeiling(int ceiling) {
		this.ceiling = ceiling;
	}

	/**
	 * 获取此规则区间对应的排队状态
	 * 
	 * @return 排队状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置此规则区间对应的排队状态
	 * 
	 * @param status
	 *            排队状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取此规则区间对应的等待级别对应的颜色
	 * 
	 * @return 级别对应的颜色
	 */
	public String getColor() {
		return color;
	}

	/**
	 * 设置此规则区间对应的等待级别对应的颜色
	 * 
	 * @param color
	 *            级别对应的颜色
	 */
	public void setColor(String color) {
		this.color = color;
	}

}
